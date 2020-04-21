
## Register a Merchant

HTTP POST
http://localhost:8084/public/api/v1/merchant/register
```json
{
	"name":"Joes Groceries",
    "contactName":"Joe Merchant",
	"address":{
	    "line1": "Address line 1",
        "line2": "Address line 2",
        "line3": "Address line 3",
        "postalCode": "TW32B",
        "country": "UK"
    },
    "currency": "GBP"
}
```
response
```json
{
  "userId": 2,
  "password": "b1a810b7-7ef7-4748-b5b5-d539c722bbc9",
  "userName": "Joes Groceries"
}
```

## Login a Merchant

HTTP POST

http://localhost:8084/public/api/v1/merchant/login?username=Joes%20Groceries&password=b1a810b7-7ef7-4748-b5b5-d539c722bbc9

response
eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWyiwuVrJSSkstSc7QTSzIVNJRykwsUbIyNLUwNzY0tDQ201FKrSiACZiaggRKi1OL8hJzU4EavfJTixXci_KTU4syU4uVagEnmDC4UQAAAA.TaE-ORSx5UnWStx-HSwjjnwov2t-iCA9XYM0cCWrqSo

## Add a Merchant Bank account

HTTP POST

Authorization Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWyiwuVrJSSkstSc7QTSzIVNJRykwsUbIyNLUwN7KwMDEx0lFKrSiAClgaGYAESotTi_ISc1OBGr3yU4sV3Ivyk1OLMlOLlWoBr-2X21EAAAA.SsdOH8DRAsGMX7QvA1O-TWzrCebDtssBi5AQ_kBxZUY
http://localhost:8084/api/v1/merchant/bank-account?merchantId=2

```json
{
  "accountHolder": "Joe",
  "accountHolderType": "Merchant",
  "accountNumber": "BAN18765",
  "bankName": "Bank of Mum and Dad",
  "country": "UK",
  "currency": "GB",
  "iban": ""
}
```

## Find a merchant by  id

HTTP GET

Authorization Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWyiwuVrJSSkstSc7QTSzIVNJRykwsUbIyNLUwN7KwMDEx0lFKrSiAClgaGYAESotTi_ISc1OBGr3yU4sV3Ivyk1OLMlOLlWoBr-2X21EAAAA.SsdOH8DRAsGMX7QvA1O-TWzrCebDtssBi5AQ_kBxZUY
http://localhost:8084/api/v1/merchant/profile-by-id?id=2

```json
{
  "id": 2,
  "version": 4,
  "name": "Joes Groceries",
  "contactName": "Joe Merchant",
  "email": null,
  "phone": null,
  "address": {
    "id": 3,
    "version": 1,
    "line1": "Address line 1",
    "line2": "Address line 2",
    "line3": "Address line 3",
    "postalCode": "TW32",
    "country": "UK"
  },
  "locale": null,
  "currency": "GBP",
  "bankAccount": {
    "id": 4,
    "version": 4,
    "accountHolder": "Joe",
    "accountHolderType": "Merchant",
    "accountNumber": "BAN18765",
    "bankName": "Bank of Mum and Dad",
    "country": "UK",
    "currency": "GB",
    "iban": "iban23456789"
  }
}
```

## Find a merchant by name

HTTP GET

Authorization Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWyiwuVrJSSkstSc7QTSzIVNJRykwsUbIyNLUwN7KwMDEx0lFKrSiAClgaGYAESotTi_ISc1OBGr3yU4sV3Ivyk1OLMlOLlWoBr-2X21EAAAA.SsdOH8DRAsGMX7QvA1O-TWzrCebDtssBi5AQ_kBxZUY
http://localhost:8084/api/v1/merchant/profile-by-name?name=Joes%20Groceries

```json
{
  "id": 2,
  "version": 4,
  "name": "Joes Groceries",
  "contactName": "Joe Merchant",
  "email": null,
  "phone": null,
  "address": {
    "id": 3,
    "version": 1,
    "line1": "Address line 1",
    "line2": "Address line 2",
    "line3": "Address line 3",
    "postalCode": "TW32",
    "country": "UK"
  },
  "locale": null,
  "currency": "GBP",
  "bankAccount": {
    "id": 4,
    "version": 4,
    "accountHolder": "Joe",
    "accountHolderType": "Merchant",
    "accountNumber": "BAN18765",
    "bankName": "Bank of Mum and Dad",
    "country": "UK",
    "currency": "GB",
    "iban": "iban23456789"
  }
}
```
## Update a Merchant Bank account

HTTP PUT

Authorization Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWyiwuVrJSSkstSc7QTSzIVNJRykwsUbIyNLUwN7KwMDEx0lFKrSiAClgaGYAESotTi_ISc1OBGr3yU4sV3Ivyk1OLMlOLlWoBr-2X21EAAAA.SsdOH8DRAsGMX7QvA1O-TWzrCebDtssBi5AQ_kBxZUY
http://localhost:8084/api/v1/merchant/bank-account?merchantId=2

Pass in the same id and version number from the find merchant (bank account) call

```json
{
  "id": 4,
  "version": 1,
  "accountHolder": "Joe",
  "accountHolderType": "Merchant",
  "accountNumber": "BAN18765",
  "bankName": "Bank of Mum and Dad",
  "country": "UK",
  "currency": "GB",
  "iban": "iban23456789"
}
```

## Change password

HTTP PUT

Authorization Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWyiwuVrJSSkstSc7QTSzIVNJRykwsUbIyNLUwN7KwMDEx0lFKrSiAClgaGYAESotTi_ISc1OBGr3yU4sV3Ivyk1OLMlOLlWoBr-2X21EAAAA.SsdOH8DRAsGMX7QvA1O-TWzrCebDtssBi5AQ_kBxZUY
http://localhost:8084/api/v1/merchant/change-password

```json
{
	"username": "Joes Groceries",
	"password": "new password"
}
```

## Change address

HTTP PUT

Authorization Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWyiwuVrJSSkstSc7QTSzIVNJRykwsUbIyNLUwN7KwMDEx0lFKrSiAClgaGYAESotTi_ISc1OBGr3yU4sV3Ivyk1OLMlOLlWoBr-2X21EAAAA.SsdOH8DRAsGMX7QvA1O-TWzrCebDtssBi5AQ_kBxZUY
http://localhost:8084/api/v1/merchant/change-address

Pass in the same id and version number from the get merchant (address) call
```json
{
    "id": 3,
    "version": 1,
    "line1": "Address line 1",
    "line2": "Address line 2",
    "line3": "Address line 3",
    "postalCode": "TW32",
    "country": "UK"
}
```
