import pandas as pd
import csv
import requests
import json

# Upload Merchant Post Codes
# Upload all post codes


callerId = "192.0.0.1"

url = 'http://platformsecurity-env.eba-dzmy8nhd.us-east-2.elasticbeanstalk.com/api/v1/token/create'
params = {"username": callerId}
r = requests.post(url,  params=params)
print(r.status_code)
token = r.text
print(token)


#AB10 1XG,57.144165160000000,-2.114847768000000
#payload = {'postCode': 'AB10 1XG', 'lat': 57.144165160000000, 'lon': -2.114847768000000}
params = {'postalCode': 'AB10 1XG'}
headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
#url = 'http://localhost:8084/public/api/v1/merchant/findMerchantPostCodesByDistance'
url = 'http://platformmerchant-env.eba-jqmqtsmc.us-east-2.elasticbeanstalk.com/api/v1/merchant/findMerchantPostCodesByDistance'
r = requests.get(url, params=params, headers=headers)
print (r.status_code)
print (r.json())

#710,AB23 8HU,57.245363420000000,-2.053538000000000

#payload = {'postCode': 'AB23 8HU', 'lat': 57.245363420000000, 'lon': -2.053538000000000}
headers = {"Authorization": "Bearer "+token, 'content-type': 'application/json'}
params = {'postalCode': 'AB23 8HU'}
#url = 'http://localhost:8084/public/api/v1/merchant/findMerchantPostCodesByDistance'
url = 'http://platformmerchant-env.eba-jqmqtsmc.us-east-2.elasticbeanstalk.com/api/v1/merchant/findMerchantPostCodesByDistance'
r = requests.get(url, params=params, headers=headers)
print (r.status_code)
print (r.json())
