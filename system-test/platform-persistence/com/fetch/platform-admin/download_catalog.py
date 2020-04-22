import pandas as pd
import csv
import requests
import json

url = 'http://localhost:8084/public/api/v1/merchant/catalog'
r = requests.get(url)
print r.status_code
print r.json()


