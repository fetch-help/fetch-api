import requests
import json
import pandas as pd

payload = {
    "name": "Joes Groceries",
    "contactName": "Joe Merchant",
    "address": {
        "line1": "Address line 1",
        "line2": "Address line 2",
        "line3": "Address line 3",
        "postalCode": "TW32B",
        "country": "UK"
    },
    "currency": "GBP"
}

url = 'http://localhost:8084/public/api/v1/merchant/register'
headers = {'content-type': 'application/json'}
r = requests.post(url, data=json.dumps(payload), headers=headers, allow_redirects=True)
print r.status_code
response = r.json()
merchantId = response['userId']
password = response['password']
merchantUserName = response['userName']
print merchantId
print password
print merchantUserName

url = 'http://localhost:8084/public/api/v1/merchant/login'
# headers = {'content-type': 'application/json'}
params = { 'username': merchantUserName, 'password': password}
r = requests.post(url, params=params, allow_redirects=True)
print r.status_code
token = r.content
print token

payload = {
  "accountHolder": "Joe",
  "accountHolderType": "Merchant",
  "accountNumber": "BAN18765",
  "bankName": "Bank of Mum and Dad",
  "country": "UK",
  "currency": "GB",
  "iban": "iban12345678"
}

url = 'http://localhost:8084/api/v1/merchant/bank-account'
headers = {'Authorization': 'Bearer ' + token, 'content-type': 'application/json'}
params = {'merchantId': merchantId}
r = requests.post(url, data=json.dumps(payload), params=params, headers=headers, allow_redirects=True)
print r.status_code

url = 'http://localhost:8084/api/v1/merchant/profile-by-id'
headers = {'Authorization': 'Bearer ' + token}
params = {'id': merchantId}
r = requests.get(url, params=params, headers=headers, allow_redirects=True)
print r.status_code
response = r.json()
print response
print(json.dumps(response, indent=4, sort_keys=True))

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

