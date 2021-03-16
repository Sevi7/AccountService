# AccountService
Account service according to REST guidelines.

## Endpoints:

### Get all accounts:

GET http://localhost:8080/accounts

### Get one account:

GET http://localhost:8080/accounts/{id}

### Create account:

POST http://localhost:8080/accounts  
Content-Type: application/json  
Parameters:
* name: String
* currency: String
* balance: number
* treasury: boolean

### Transfer money between accounts:
POST http://localhost:8080/accounts/transfer  
Content-Type: application/json  
Parameters:
* senderAccount: number
* receiverAccount: number
* amount: number
