import requests
import json
import pandas as pd
import requests
import json

callerId = "192.0.0.1"

url = 'http://localhost:8089/api/v1/token/create'
params = {"username": callerId}
r = requests.post(url,  params=params)
print r.status_code
token = r.text
print token

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

url = 'http://localhost:8084/api/v1/merchant/register'
headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
params = {"callerId": callerId}
r = requests.post(url, data=json.dumps(payload), params=params, headers=headers, allow_redirects=True)
print r.status_code
response = r.json()
merchantId = response['userId']
password = response['password']
merchantUserName = response['userName']
print merchantId
print password
print merchantUserName

url = 'http://localhost:8084/api/v1/merchant/login'
headers = {"Authorization": "Bearer "+token}
params = {"callerId": callerId, 'username': merchantUserName, 'password': password}
r = requests.post(url, params=params, headers=headers)
print r.status_code
token = r.text
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
