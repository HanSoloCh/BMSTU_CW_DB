from bs4 import BeautifulSoup
import requests
from urllib.parse import urljoin
import re
import csv

from exceptiongroup import catch
from sympy.printing.latex import other_symbols

BOOK_FILE = 'books.csv'
AUTHORS_FILE = 'authors.csv'
BOOK_AUTHORS_FILE = 'bal.csv'

authors_set = set()
authors_list = list()

PAGE_URL = 'https://rgub.ru/searchopac/rubrics/rubric.php?bbk='

def get_links(url: str):
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')

    # Находим все ссылки с текстом "Подробная информация"
    return [i['href'] for i in soup.find_all('a', text='Подробная информация')]


def get_book_content(url: str):
    response = requests.get(urljoin(PAGE_URL, url))
    soup = BeautifulSoup(response.text, 'html.parser')

    book_data = soup.find_all(style="padding: 10px 20px 3px 3px;")

    book_dict = dict()
    for data in book_data:
        info = data.find_all("div")
        if len(info) == 2:
            key = info[0].get_text(strip=True).rstrip(':')
            if key == 'Другие авторы':
                value = info[1].get_text().replace('\xa0', ' ')
            else:
                value = info[1].get_text(strip=True).replace('\xa0', ' ')

            if key is not None:
                book_dict[key] = value

    return book_proc(book_dict)


def book_proc(book_data: dict):
    new_book_dict = dict()
    other_authors = book_data.get('Другие авторы')
    if other_authors is not None:
        other_authors = other_authors.split('\n')
    else:
        other_authors = [None]
    new_book_dict['Автор'] = [
        book_data.get('Автор'),
        *other_authors
    ]
    new_book_dict['Автор'] = [x for x in new_book_dict['Автор'] if x is not None and x]
    new_book_dict['Заглавие'] = book_data['Заглавие']
    new_book_dict['Тип носителя'] = book_data.get('Тип носителя')
    new_book_dict['Издательство'] = book_data.get('Издательство')
    new_book_dict['Год издания'] = (year_match.group()
                                    if (year_match := re.search(r'\d{4}', book_data['Год издания']))
                                    else None)
    new_book_dict['Объем'] = book_data.get('Объем')
    new_book_dict['Аннотация'] = book_data.get('Аннотация')
    new_book_dict['Язык'] = book_data.get('Язык')
    new_book_dict['Язык оригинала'] = book_data.get('Язык оригинала')
    new_book_dict['ББК'] = book_data['ББК']
    new_book_dict['ISBN'] = book_data.get('ISBN')
    new_book_dict['Размеры издания'] = book_data.get('Размеры издания')

    return new_book_dict


def write_author_info(author_name: str, author_ind: int):
    with open(AUTHORS_FILE, 'a', newline='') as file:
        writer = csv.writer(file)
        writer.writerow([
            author_ind,
            author_name
        ])


def write_author_book_info(author_name: str, book_ind: int):
    if author_name not in authors_set:
        authors_set.add(author_name)
        authors_list.append(author_name)
        author_ind =  len(authors_list)
        write_author_info(author_name, author_ind)
    else:
        author_ind = authors_list.index(author_name)

    with open(BOOK_AUTHORS_FILE, 'a', newline='') as file:
        writer = csv.writer(file)
        writer.writerow([
            book_ind,
            author_ind
        ])



def write_book_data(book_data: dict, book_ind: int):
    book_authors = book_data['Автор']
    for book_author in book_authors:
        write_author_book_info(book_author, book_ind)
    book_data.pop('Автор')

    with open(BOOK_FILE, 'a', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(
            [book_ind,
             *book_data.values()]
        )

if __name__ == '__main__':
    with open('BBK.txt') as file:
        try:
            with open(BOOK_FILE) as book_file:
                book_ind = len(book_file.readlines()) + 1
            with open(AUTHORS_FILE) as author_file:
                reader = csv.reader(author_file)
                for row in reader:
                    authors_set.add(row[1].strip())
                    authors_list.append(row[1].strip())

        except FileNotFoundError:
            book_ind = 1

        # Получаем общее количество BBK кодов для прогресс-бара
        bbk_codes = file.readlines()
        total_bbk = len(bbk_codes) / 2
        file.seek(0)  # Возвращаем указатель в начало файла

        errors_count = 0
        processed_bbk = 0

        while True:
            l1 = file.readline().strip()
            if not l1:
                break
            file.readline()

            processed_bbk += 1
            # Вычисляем процент выполнения
            progress = (processed_bbk / total_bbk) * 100
            # Простой текстовый прогресс-бар
            bar_length = 20
            filled_length = int(bar_length * processed_bbk // total_bbk)
            bar = '█' * filled_length + '-' * (bar_length - filled_length)

            print(f'\rОбработка: |{bar}| {progress:.1f}% ({processed_bbk}/{total_bbk}) BBK: {l1}', end='')

            book_links = get_links(PAGE_URL + l1)
            for i in range(min(len(book_links), 5)):
                try:
                    write_book_data(get_book_content(book_links[i]), book_ind)
                    book_ind += 1
                except Exception as e:
                    print(f'\nОшибка при обработке книги {urljoin(PAGE_URL, book_links[i])} ({str(e)})')
                    errors_count += 1

        # Перенос строки после завершения
        print()
        print(f'Всего книг скачано: {book_ind - 1}')
        print(f'Ошибок: {errors_count}')
        print(f'Авторов: {len(authors_set)}')
