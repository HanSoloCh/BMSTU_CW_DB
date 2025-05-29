import csv

input_file = "book_author_uuid.csv"
output_file = "book_author_deduplicated.csv"

seen = set()
deduplicated_rows = []

with open(input_file, newline='', encoding='utf-8') as f_in:
    reader = csv.reader(f_in)
    for row in reader:
        if len(row) < 2:
            continue  # пропускаем некорректные строки
        key = (row[0], row[1])  # предполагается, что это book_id, author_id
        if key not in seen:
            seen.add(key)
            deduplicated_rows.append(row)

with open(output_file, 'w', newline='', encoding='utf-8') as f_out:
    writer = csv.writer(f_out)
    writer.writerows(deduplicated_rows)

print(f"Готово! Уникальных строк: {len(deduplicated_rows)}")
