import pandas as pd
import csv
import requests
import json

df = pd.read_csv('product_detail.csv', delimiter='|')
#print df
df = df[['level3', 'level2']]
print df.duplicated().any()

df = df.drop_duplicates(subset=None, keep='first', inplace=False)

print df
