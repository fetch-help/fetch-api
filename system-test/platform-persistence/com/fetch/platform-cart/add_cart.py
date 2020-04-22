import requests
import json

#use IP or jsessionid for not logged in users
userId = "192.0.0.1"

url = 'http://localhost:8089/api/v1/token/create'
params = {"username": userId}
r = requests.post(url,  params=params)
print r.status_code
token = r.text
print token
url = 'http://localhost:8086/api/v1/cart'

cart = {'items': [{"productId": 10, "quantity": 2}, {"productId": 5, "quantity": 2}]}

headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
params = {"userIdentifier": userId}
r = requests.post(url+"/add", data=json.dumps(cart), params=params, headers=headers)
print r.status_code

headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
params = {"userIdentifier": userId}
r = requests.get(url+"/get", params=params, headers=headers)
print r.status_code
print r.json()

headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
params = {"userIdentifier": userId}
r = requests.delete(url+"/delete", params=params, headers=headers)
print r.status_code

headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
params = {"userIdentifier": userId}
r = requests.get(url+"/get", params=params, headers=headers)
print r.status_code



