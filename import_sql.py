import mysql.connector
import os

# 读取 SQL 文件
sql_file = r'd:\vue3-master\backend\sql\themeweek.sql'
with open(sql_file, 'r', encoding='utf-8') as f:
    sql_content = f.read()

# 连接数据库
conn = mysql.connector.connect(
    host='localhost',
    user='root',
    password='123456',
    database='themeweek',
    charset='utf8mb4'
)

cursor = conn.cursor()

# 按分号分割 SQL 语句（注意处理注释中的分号）
statements = []
current_statement = []
in_comment = False
in_string = False
string_char = ''

for line in sql_content.split('\n'):
    line = line.strip()
    
    if not line:
        continue
    
    # 处理注释
    if line.startswith('/*') and not in_string:
        in_comment = True
    elif line.endswith('*/') and in_comment:
        in_comment = False
        continue
    elif in_comment:
        continue
    elif line.startswith('--') or line.startswith('#'):
        continue
    
    # 处理字符串
    for char in line:
        if char in ("'", '"') and not in_comment:
            if not in_string:
                in_string = True
                string_char = char
            elif string_char == char:
                in_string = False
        current_statement.append(char)
    
    if not in_string and ';' in line:
        full_statement = ''.join(current_statement).strip()
        if full_statement:
            statements.append(full_statement)
        current_statement = []

if current_statement:
    full_statement = ''.join(current_statement).strip()
    if full_statement:
        statements.append(full_statement)

# 执行 SQL 语句
for i, stmt in enumerate(statements):
    stmt = stmt.strip()
    if not stmt:
        continue
    try:
        cursor.execute(stmt)
        print(f'执行成功: {i+1}')
    except Exception as e:
        print(f'执行失败: {i+1} - {e}')
        print(f'SQL: {stmt[:200]}...')

conn.commit()
print('导入完成！')

# 验证
cursor.execute('SHOW TABLES')
tables = cursor.fetchall()
print(f'表数量: {len(tables)}')
for table in tables:
    print(f'  - {table[0]}')

cursor.close()
conn.close()
