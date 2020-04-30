import pandas as pd
import csv
import requests
import json

# Upload Post Codes
headers = {'content-type': 'application/json'}
params = {'type': 'PostalCode'}

payload = []
counter = 0
with open('ukpostcodes.csv', mode='r') as f:
    reader = csv.reader(f, delimiter=",")
    for row in reader:
        print(row[0])
        if row[1] == 'postcode':
            continue
        counter = counter + 1
        payload.append( {'code': row[1], 'lat': row[2], 'lon': row[3]})
        #url = 'http://localhost:8081/api/v1/persist/save'
        if counter > 10000:
            #url = 'http://localhost:8081/api/v1/persist/saveList'
            #url = 'http://localhost:5000/api/v1/persist/saveList'
            url = 'http://platformpersistence-env-1.eba-pdmmzrjj.us-east-2.elasticbeanstalk.com/api/v1/persist/saveList'
            r = requests.post(url, data=json.dumps(payload), params=params, headers=headers)
            print (r.status_code)
            counter = 0
            payload = []


