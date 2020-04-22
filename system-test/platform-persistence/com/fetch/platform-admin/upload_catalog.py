import pandas as pd
import csv
import requests
import json

df = pd.read_csv('product_detail.csv', delimiter='|')
print df
# select only categories
df = df[['level3', 'level2']]
print df.duplicated().any()
# drop duplicate categories
df = df.drop_duplicates(subset=None, keep='first', inplace=False)

payload = []
for index, row in df.iterrows():
    print 'grocery', row[1], row[0]
    payload.append({'level1': 'grocery', 'level2': row[1], 'level3': row[0]})

headers = {'content-type': 'application/json'}
params = {'type': 'ProductCatalog'}
url = 'http://localhost:8081/api/v1/persist/saveList'
r = requests.post(url, data=json.dumps(payload), params=params, headers=headers)
print r.status_code


