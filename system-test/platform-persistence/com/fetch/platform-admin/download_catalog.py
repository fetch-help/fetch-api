import pandas as pd
import csv
import requests
import json

callerId = "192.0.0.1"

url = 'http://localhost:8089/api/v1/token/create'
params = {"username": callerId}
r = requests.post(url,  params=params)
print r.status_code
token = r.text
print token

headers = {"Authorization": "Bearer "+token}
params = {"callerId": callerId}
url = 'http://localhost:8084/api/v1/merchant/catalog'
r = requests.get(url,  params=params, headers=headers)
print r.status_code
print r.json()

headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
params = {"callerId": callerId, "level1": "grocery"}
url = 'http://localhost:8084/api/v1/merchant/catalog/level1'
r = requests.get(url, params=params, headers=headers)
print r.status_code
print r.json()

headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
params = {"callerId": callerId, "level2": "beverages"}
url = 'http://localhost:8084/api/v1/merchant/catalog/level2'
r = requests.get(url, params=params, headers=headers)
print r.status_code
print r.json()

headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
params = {"callerId": callerId, "level3": "specialty cheeses"}
url = 'http://localhost:8084/api/v1/merchant/catalog/level3'
r = requests.get(url, params=params, headers=headers)
print r.status_code
print r.json()




