# Expense Sharing Application

## Running the Application
#### Clone the repository.
#### Navigate to the project directory.
#### Run mvn spring-boot:run to start the application.
#### Use the above API endpoints to interact with the application.

## Dependencies
#### Spring Boot
#### Spring Data JPA
#### H2


## Overview
This application allows users to manage and share expenses. It includes features for adding expenses, retrieving expenses, and generating balance sheets.

## API Endpoints

### Expense Endpoints

#### 1. Get All Expenses
- **URL**: `/api/expenses`
- **Method**: GET
- **Description**: Retrieves a list of all expenses.
- **Example Request**:
  ```bash
  curl -X GET http://localhost:8080/api/expenses


#### 2. Add a New Expense
- **URL**: `/api/expenses`
- **Method**: POST
- **Description**: Adds a new expense.
- **Example Request**:
  ```bash
  curl -X POST http://localhost:8080/api/expenses \
    -H "Content-Type: application/json" \
    -d '{
    "description": "Dinner",
    "amount": 3000,
    "splitType": "EQUAL",
    "user": { "id": 1 },
    "splits": [
    { "user": { "id": 1 }, "percentage": 50 },
    { "user": { "id": 2 }, "percentage": 50 }
    ]
    }'


#### 3. Get Expense by ID
- **URL**: `/api/expenses/{id}`
- **Method**: GET
- **Description**: Retrieves an expense by its ID.
- **Example Request**:
- ```bash
  curl -X GET http://localhost:8080/api/expenses/1


#### 4. Get User Expenses
- **URL**: `/api/expenses/user/{userId}`
- **Method**: GET
- **Description**: Retrieves expenses for a specific user.
- **Example Request**:
- ```bash
  curl -X GET http://localhost:8080/api/expenses/user/1

#### 5. Delete an Expense
- **URL**: `/api/expenses/{id}`
- **Method**: DELETE
- **Description**: Deletes an expense by its ID.
- **Example Request**:
- ```bash
  curl -X DELETE http://localhost:8080/api/expenses/1


#### 6. Download Balance Sheet
- **URL**: `/api/expenses/balance-sheet`
- **Method**: GET
- **Description**: Downloads the balance sheet as an Excel file.
- **Example Request**:
- ```bash
  curl -X GET http://localhost:8080/api/expenses/balance-sheet -O balance_sheet.xlsx


#### Models
```
{
  "id": 1,
  "description": "Dinner",
  "amount": 3000,
  "splitType": "EQUAL",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "mobileNumber": "1234567890"
  },
  "splits": [
    {
      "id": 1,
      "user": {
        "id": 1,
        "name": "John Doe",
        "email": "john@example.com",
        "mobileNumber": "1234567890"
      },
      "amount": 1500,
      "percentage": 50
    },
    {
      "id": 2,
      "user": {
        "id": 2,
        "name": "Jane Doe",
        "email": "jane@example.com",
        "mobileNumber": "0987654321"
      },
      "amount": 1500,
      "percentage": 50
    }
  ]
}

### User

{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "mobileNumber": "1234567890"
}


### Split

{
  "id": 1,
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "mobileNumber": "1234567890"
  },
  "amount": 1500,
  "percentage": 50
}



