mvn clean package install:install-file -DgroupId=com.fetch -DartifactId=persist -Dversion=0.0.1-SNAPSHOT -Dfile=target/persist-0.0.1-SNAPSHOT-original.jar -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=../platform-repository  -DcreateChecksum=true


mvn clean package install:install-file -DgroupId=com.fetch -DartifactId=persist -Dversion=0.0.1-SNAPSHOT -Dfile=target/persist-0.0.1-SNAPSHOT-original.jar -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

## Persisting the entities

### Create an address

Create an address and return the id, use the id for creating a Customer

HTTP POST
http://localhost:8081/api/v1/persist/save?type=Address

```json
{
      "line1":"Address line 1",
	  "line2":"Address line 2",
	  "line3":"Address line 3",
	  "postalCode":"TW32B",
	  "country":"UK"
}
```

### Create a customer

Use the id from the previous request to create an address in the address field.
After the Customer is created the id is returned.

HTTP POST

http://localhost:8081/api/v1/persist/save?type=Customer

```json
{ 
  "firstName":"Test",
  "lastName":"Customer1",
  "email":null,
  "phone":null,
  "address":{
      "id":1
   }
}
```

### Find a customer

Use the id from the previous request to find a Customer

HTTP GET

http://localhost:8081/api/v1/persist/find?type=Customer&id=2

```json
{
  "id": 2,
  "version": 0,
  "firstName": "Test",
  "lastName": "Customer1",
  "email": null,
  "phone": null,
  "address": {
    "id": 1,
    "version": 0,
    "line1": "Address line 1",
    "line2": "Address line 2",
    "line3": "Address line 3",
    "postalCode": "TW32B",
    "country": "UK",
    "createdOn": 1586874916449
  },
  "createdOn": 1586875081963,
  "lastUpdatedOn": 1586875081963
}
```

### Update a customer

Caveat: Replaces all the fields so pass the entire json from the previous request


HTTP PUT

http://localhost:8081/api/v1/persist/update?type=Customer&id=2

In the example updates only the first name

```json
{
  "id": 2,
  "version": 0,
  "firstName": "Test1",
  "lastName": "Customer1",
  "email": null,
  "phone": null,
  "address": {
    "id": 1,
    "version": 0,
    "line1": "Address line 1",
    "line2": "Address line 2",
    "line3": "Address line 3",
    "postalCode": "TW32B",
    "country": "UK",
    "createdOn": 1586874916449
  },
  "createdOn": 1586875081963,
  "lastUpdatedOn": 1586875081963
}
```

To check that the above flow works, find the Customer again

### Create a merchant

Create an address for the merchant as shown in Step 1, then create a Merchant

HTTP POST

http://localhost:8081/api/v1/persist/save?type=Merchant
```json
{
	"name":"Joes Groceries",
  "contactName":"Joe Merchant",
	"address":{
	    "id": 5
   },
  "currency": "GBP"
}
```
### Find a merchant

HTTP GET
http://localhost:8081/api/v1/persist/find?type=Merchant&id=6
```json
{
  "id": 6,
  "version": 0,
  "name": "Joes Groceries",
  "contactName": "Joe Merchant",
  "email": null,
  "phone": null,
  "address": {
    "id": 5,
    "version": 0,
    "line1": "Address line 1",
    "line2": "Address line 2",
    "line3": "Address line 3",
    "postalCode": "GRC1",
    "country": "UK",
    "createdOn": 1586881090764
  },
  "createdOn": 1586881134473,
  "lastUpdatedOn": 1586881134473,
  "locale": null,
  "currency": "GBP"
}
```

### Create a product

Create a couple of products passing in the Merchant id

HTTP POST

http://localhost:8081/api/v1/persist/save?type=Product
```json
{
  "name":"Milk",
  "price":3.0,
  "merchantId":2
}
```
```json
{
  "name":"Eggs",
  "price":2.0,
  "merchantId":2
}
```

### Find all the products of a particular merchant

HTTP GET

http://localhost:8081/api/v1/persist/findAllByParentId?type=Product&attr=merchantId&value=2

```json
[
  {
    "id": 3,
    "version": 0,
    "name": "Milk",
    "price": 3.0,
    "description": null,
    "createdOn": 1586882917254,
    "lastUpdatedOn": 1586882917254,
    "merchantId": 2
  },
  {
    "id": 4,
    "version": 0,
    "name": "Eggs",
    "price": 2.0,
    "description": null,
    "createdOn": 1586882969015,
    "lastUpdatedOn": 1586882969015,
    "merchantId": 2
  }
]

```





