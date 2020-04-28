import pandas as pd
import csv
import requests
import json

# Upload Merchant Post Codes
# Upload all post codes

#AB10 1XG,57.144165160000000,-2.114847768000000
#payload = {'postCode': 'AB10 1XG', 'lat': 57.144165160000000, 'lon': -2.114847768000000}
params = {'postalCode': 'AB10 1XG'}
headers = {'content-type': 'application/json'}
url = 'http://localhost:8086/public/api/v1/postcode/merchant-post-codes-by-distance'
r = requests.get(url, params=params, headers=headers)
print (r.status_code)
print (r.json())

#710,AB23 8HU,57.245363420000000,-2.053538000000000

#payload = {'postCode': 'AB23 8HU', 'lat': 57.245363420000000, 'lon': -2.053538000000000}
headers = {'content-type': 'application/json'}
params = {'postalCode': 'AB23 8HU'}
url = 'http://localhost:8086/public/api/v1/postcode/merchant-post-codes-by-distance'
r = requests.get(url, params=params, headers=headers)
print (r.status_code)
print (r.json())
