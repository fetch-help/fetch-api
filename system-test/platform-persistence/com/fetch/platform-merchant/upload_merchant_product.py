import requests
import json
import pandas as pd

# Create Token
# Login
# Find Merchant by Name
# Upload products

callerId = "192.0.0.1"

url = 'http://localhost:8089/api/v1/token/create'
params = {"username": callerId}
r = requests.post(url,  params=params)
print r.status_code
token = r.text
print token

merchantName = 'Joes Groceries'
url = 'http://localhost:8084/api/v1/merchant/login'
headers = {"Authorization": "Bearer "+token}
params = {"callerId": callerId, 'username': merchantName, 'password': 'testpass'}
r = requests.post(url, params=params, headers=headers)
print r.status_code
token = r.text
print token

url = 'http://localhost:8084/api/v1/merchant/profile-by-name'
headers = {'Authorization': 'Bearer ' + token}
params = {'name': merchantName}
r = requests.get(url, params=params, headers=headers)
print r.status_code
response = r.json()
print response
print(json.dumps(response, indent=4, sort_keys=True))

merchantId = response['id']

df = pd.read_csv('product_detail_mini.csv', delimiter='|')
print df

payload = []
for index, row in df.iterrows():
    print row['name'], row['price'], row['unit'], row['catalog_id']
    payload.append({'name': row['name'],
                    'price': row['price'],
                    'unit': row['unit'],
                    'inStock': True,
                    'productCatalogId': row['catalog_id'],
                    'merchantId': merchantId})

print 'Uploading ', payload
url = 'http://localhost:8084/api/v1/merchant/product-upload'
headers = {'Authorization': 'Bearer ' + token, 'content-type': 'application/json'}
params = {'merchantId': merchantId}
r = requests.post(url, data=json.dumps(payload), params=params, headers=headers)
print r.status_code
