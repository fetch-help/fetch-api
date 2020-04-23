import requests
import json
import pandas as pd


url = 'http://localhost:8084/api/v1/merchant/product-upload'
payload = [
    {
      "name": "Eggs",
      "price": 2.0
    },
    {
      "name": "Milk",
      "price": 3.5
    },
    {
      "name": "Sugar",
      "price": 5.0
    }
]

headers = {'Authorization': 'Bearer ' + token, 'content-type': 'application/json'}
params = {'merchantId': merchantId}
r = requests.post(url, data=json.dumps(payload), params=params, headers=headers, allow_redirects=True)
print r.status_code

url = 'http://localhost:8084/api/v1/merchant/products'
headers = {'Authorization': 'Bearer ' + token, 'content-type': 'application/json'}
params = {'merchantId': merchantId}
r = requests.get(url, params=params, headers=headers, allow_redirects=True)
print r.status_code
response = r.json()
print response
print(json.dumps(response, indent=4, sort_keys=True))

#products = json.loads(response)

productString = ''

for product in response:
    productString += json.dumps(product)
    productString += '\n'

db = pd.read_json(productString, lines=True)
print db

eggProductId = db.loc[db['name'] == 'Eggs']['id'].iloc[0]

print 'Deleting eggs ' + str(eggProductId)

url = 'http://localhost:8084/api/v1/merchant/delete-product'
headers = {'Authorization': 'Bearer ' + token, 'content-type': 'application/json'}
params = {'merchantId': merchantId, 'productId': eggProductId}
r = requests.delete(url, params=params, headers=headers, allow_redirects=True)
print r.status_code

