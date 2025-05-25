
#  Document Management System

A Spring Boot-based Document Management System with **JWT authentication**, **role-based access control**, and **Redis-backed token caching**.

---

##  Features

- ✅ User registration and login with JWT authentication  
- ✅ Role-based access control (`ADMIN`, `EDITOR`, `VIEWER`)  
- ✅ Token caching using Redis  
- ✅ RESTful API endpoints for document management (Add and Search)  
- ✅ API documentation via Swagger UI  

---

##  Prerequisites

- Java 17 or higher  
- Maven  
- Redis server running locally on default port  
- PostgreSQL with a database named **`docman`** (or configure another name in `application.properties`)  

---

##  Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/saurabhshigwan/documentmanagement.git
cd documentmanagement
```

### 2. Build and Run the Application

Using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

Using IntelliJ IDEA:  
Run `DocumentmanagementApplication.class` directly.

---

##  API Endpoints

### 1. Register User

- **POST** `/jktech/docman/auth/register`  
  Request Body:

  ```json
  {
    "userName": "john_doe",
    "password": "securePassword",
    "name": "John Doe",
    "roles": ["ADMIN", "EDITOR"]
  }
  ```

---

### 2. Login

- **POST** `/jktech/docman/auth/login`  
  Request Body:

  ```json
  {
    "userName": "john_doe",
    "password": "securePassword"
  }
  ```

---

### 3. Status

- **GET** `/jktech/docman/auth/status`  
   Check authentication status.

---

### 4. Upload Document

- **POST** `/jktech/docman/doc/upload`  

  > **How to Upload via Postman:**
  - In **Headers**, set:  
    `Authorization: Bearer <your-JWT-token>`
  - In **Body**, select `form-data`:
    - Key: `file`
    - Type: `File`
    - Value: Select the file to upload

---

### 5. Search Documents

- **GET** `/jktech/docman/doc/search`  
   Supports searching by:
  - `uploadedBy`
  - `fileType`
  - `parsedText` (content)
  - `filename`

  **Examples:**
  - Search by content:  
    `http://localhost:8080/jktech/docman/doc/search?parsedText=test`
  - Search by uploaded user:  
    `http://localhost:8080/jktech/docman/doc/search?uploadedBy=t1_user`

---

###  Swagger UI

- Access API documentation at:  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---
