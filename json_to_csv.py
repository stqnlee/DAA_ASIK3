import json
import csv

# Читаем output.json
try:
    with open('output.json', 'r') as f:
        data = json.load(f)
except FileNotFoundError:
    print("ERROR: output.json not found! Run: mvn exec:java")
    exit(1)

# Проверяем структуру
if 'results' not in data:
    print("ERROR: Invalid output.json format")
    exit(1)

results = data['results']

# Заголовки CSV
fieldnames = [
    'graph_id', 'vertices', 'edges',
    'prim_cost', 'kruskal_cost',
    'prim_time_ms', 'kruskal_time_ms',
    'prim_ops', 'kruskal_ops'
]

# Записываем CSV
with open('summary.csv', 'w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    for item in results:
        row = {
            'graph_id': item['graph_id'],
            'vertices': item['input_stats']['vertices'],
            'edges': item['input_stats']['edges'],
            'prim_cost': item['prim']['total_cost'],
            'kruskal_cost': item['kruskal']['total_cost'],
            'prim_time_ms': item['prim']['execution_time_ms'],
            'kruskal_time_ms': item['kruskal']['execution_time_ms'],
            'prim_ops': item['prim']['operations_count'],
            'kruskal_ops': item['kruskal']['operations_count']
        }
        writer.writerow(row)


print("summary.csv has been created successfully!")