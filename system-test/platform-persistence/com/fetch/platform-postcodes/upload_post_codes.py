import pandas as pd
import csv
import requests
import json

# Upload Post Codes
headers = {'content-type': 'application/json'}
params = {'type': 'PostalCode'}

with open('ukpostcodes.csv', mode='r') as f:
    reader = csv.reader(f, delimiter=",")
    for row in reader:
        print(row[0], row[1], row[2], row[3])
        if row[1] == 'postcode':
            continue
        payload = {'code': row[1], 'lat': row[2], 'lon': row[3]}
        url = 'http://localhost:8081/api/v1/persist/save'
        r = requests.post(url, data=json.dumps(payload), params=params, headers=headers)
        print (r.status_code)


