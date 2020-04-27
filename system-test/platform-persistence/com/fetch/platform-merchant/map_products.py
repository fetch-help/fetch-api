import requests
import json
import pandas as pd
import requests
import json
import csv

cats = pd.read_csv('catalog.csv', delimiter=',')
print (cats)

prods = pd.read_csv('product_detail.csv', delimiter='|')
print (prods)

for index, row in prods.iterrows():
    #id,cat,subcat,nestedsubcat
    catid = cats.loc[cats['subcat'] == row['level2']].loc[cats['nestedsubcat'] == row['level3'],'id'].iloc[0]

    print(catid, row['name'], row['level2'], row['level3'], )

with open('products.csv', mode='w') as f:
    writer = csv.writer(f, delimiter="|")
    writer.writerow(('cat-id', 'product-name'))
    for index, row in prods.iterrows():
        # id,cat,subcat,nestedsubcat
        catid = cats.loc[cats['subcat'] == row['level2']].loc[cats['nestedsubcat'] == row['level3'], 'id'].iloc[0]

        writer.writerow([catid, row['name']])
