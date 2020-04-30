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

callerId = "192.0.0.1"

url = 'http://platformsecurity-env.eba-dzmy8nhd.us-east-2.elasticbeanstalk.com/api/v1/token/create'
params = {"username": callerId}
r = requests.post(url,  params=params)
print(r.status_code)
token = r.text
print(token)


headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
params = {}
#url = 'http://localhost:8084/public/api/v1/merchant/postCodes'
url = 'http://platformmerchant-env.eba-jqmqtsmc.us-east-2.elasticbeanstalk.com/api/v1/merchant/postCodes'
r = requests.post(url, data=json.dumps(payload), params=params, headers=headers)
print (r.status_code)
print (r.text)


