
## Register a Merchant

HTTP POST

http://localhost:8084/public/api/v1/merchant/register

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



## Login a Merchant

HTTP POST

http://localhost:8084/public/api/v1/merchant/login?username=Joes%20Groceries&password=028941d9-7251-430c-a328-f01e2d336183
