import pandas as pd

# Загружаем CSV без заголовков
df = pd.read_csv("books.csv", header=None)

# Убираем первый столбец (индекс)
df_no_index = df.iloc[:, 1:]

# Находим полные дубликаты (без учета индекса)
duplicates = df[df_no_index.duplicated(keep=False)]

# Группируем дубликаты по названию книги (второй столбец)
grouped = duplicates.groupby(1)

# Выводим название книги и все строки, где она встречается
for book_title, rows in grouped:
    print(f"{book_title}")
    print(rows.iloc[:, 0].to_string(index=False, header=False))
    print("-" * 50)
