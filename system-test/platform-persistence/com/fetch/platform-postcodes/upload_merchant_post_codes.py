import pandas as pd
import csv
import requests
import json

# Upload Merchant Post Codes

df = pd.read_csv('merchantpostcodes.csv', delimiter=',')
print (df)
payload = []
for index, row in df.iterrows():
    #id,postcode,latitude,longitude
    payload.append({'postCode': row[1], 'lat': row[2], 'lon': row[3]})

print(payload)

headers = {'content-type': 'application/json'}
params = {}
url = 'http://localhost:8084/public/api/v1/merchant/postCodes'
r = requests.post(url, data=json.dumps(payload), params=params, headers=headers)
print (r.status_code)
print (r.text)


