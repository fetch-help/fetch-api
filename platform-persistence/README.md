## Building it

Clone the repo and build it using

persist$ mvn clean package

## Running the microservice

persist$ java -jar target/persist-0.0.1-SNAPSHOT.jar

## Persisting the entities

### Create an address

Create an address and return the id, use the id for creating a Customer

HTTP POST
http://localhost:8081/api/v1/persist/save?type=Address
{
      "line1":"Address line 1",
	  "line2":"Address line 2",
	  "line3":"Address line 3",
	  "postalCode":"TW32B",
	  "country":"UK"
}

### Create a customer

Use the id from the previous request to create an address in the address field.
After the Customer is created the id is returned.

HTTP POST

http://localhost:8081/api/v1/persist/save?type=Customer
{ 
  "firstName":"Test",
  "lastName":"Customer1",
  "email":null,
  "phone":null,
  "address":{
      "id":1
   }
}

### Find a customer

Use the id from the previous request to find a Customer

HTTP GET

http://localhost:8081/api/v1/persist/find?type=Customer&id=2

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

### Update a customer

Caveat: Replaces all the fields so pass the entire json from the previous request


HTTP PUT

http://localhost:8081/api/v1/persist/update?type=Customer&id=2

In the example updates only the first name

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

To check that the above flow works, find the Customer again

### Create a supplier




