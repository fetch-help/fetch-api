import pandas as pd
import csv
import requests
import json

# Create Token
# Download Product Catalog filtered by merchants postcode and instock products

callerId = "192.0.0.1"
url = 'http://localhost:8089/api/v1/token/create'
params = {"username": callerId}
r = requests.post(url,  params=params)
print r.status_code
token = r.text
print token

headers = {"Authorization": "Bearer "+token}
params = {"callerId": callerId, 'postalCode': 'TW32B'}
url = 'http://localhost:8084/api/v1/merchant/findCatalogByPostCode'
r = requests.get(url,  params=params, headers=headers)
print r.status_code
print r.json()
