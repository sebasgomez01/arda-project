# Arda social network 

## Project under construction

Arda is a social network where users can share posts on different topics, make comments, and follow each other, among other functionalities. The user interface was inspired by X/Twitter and Reddit.
The technologies I used were Spring Boot for the backend with dependencies like Spring Security, Spring Data REST, and Spring Data JPA. 
Authentication is handled with JSON Web Tokens, and the data is stored in a MariaDB relational database. On the frontend, I use React with TypeScript.

 
# API Documentation

## Endpoints

### Create New User (Sign-Up)
- **Method**: `POST`
- **Endpoint**: `/users/credentials`
- **Description**: Creates a new user if the username does not already exist in the database.
- **Request Body**:
  ```json
  {
  "name":"Sebastian",
  "username":"seba",
  "password":"abcd1234",
  "role":"user"
  }

- **Responses**:
  - **Status Code**: `201 Created`
    - **Response Body**: `"Account created successfully"`
  - **Status Code**: `409 Conflict`
    - **Response Body**: `"Conflict. Username already exists."`

### Login user
- **Method**: `POST`
- **Endpoint**: `/users/login`
- **Description**: Logs in a user if the credentials are correct and returns a JWT in the Authorization header.
- **Request Body**:
  ```json
  {
  "username": "seba",
  "password": "abcd1234"
  }

- **Responses**:
  - **Status Code**: `200 OK`
    - **Header**: `Authorization: Bearer <generated-token>`
    - **Response Body**: `"Login successful"`
  - **Status Code**: `401 Unauthorized`
    - **Response Body**: `"Invalid credentials"`

### Create a new post
- **Method**: `POST`
- **Endpoint**: `/posts`
- **Description**: Create a new post by the user authenticate.
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**:
  ```json
  {
    "title":"hola",
    "textContent":"soy Germán",
    "imagePath":"/"
  }


- **Responses**:
  - **Status Code**: `200 OK`
    - **Response Body**:
      ```json
      {
       "id" : 1,
       "title" : "hola",
       "textContent" : "soy Germán",
       "imagePath" : "ruta_a_la_imagen"
      }

### Like a post
- **Method**: `PATCH`
- **Endpoint**: `/posts/like/{postId}`
- **Description**: Like the post that corresponds with the postId by the authetenticated user.
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**: empty
  
- **Responses**:
  - **Status Code**: `200 OK`
    - **Response Body**:
      ```json
      {
       "id" : 1,
       "title" : "hola",
       "textContent" : "soy Germán",
       "imagePath" : "ruta_a_la_imagen"
      }
### Delete the like from a post
- **Method**: `PATCH`
- **Endpoint**: `/posts/delete-like/{postId}`
- **Description**: Delete the like from the post that corresponds with the postId by the authetenticated user.
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**: empty
  
- **Responses**:
  - **Status Code**: `200 OK`
    - **Response Body**:
      ```json
      {
       "id" : 1,
       "title" : "hola",
       "textContent" : "soy Germán",
       "imagePath" : "ruta_a_la_imagen"
      }

### Dislike a post
- **Method**: `PATCH`
- **Endpoint**: `/posts/dislike/{postId}`
- **Description**: Dislike the post that corresponds with the postId by the authetenticated user.
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**: empty
  
- **Responses**:
  - **Status Code**: `200 OK`
    - **Response Body**:
      ```json
      {
       "id" : 1,
       "title" : "hola",
       "textContent" : "soy Germán",
       "imagePath" : "ruta_a_la_imagen"
      }

### Delete the dislike from a post
- **Method**: `PATCH`
- **Endpoint**: `/posts/delete-dislike/{postId}`
- **Description**: Delete the dislike from the post that corresponds with the postId by the authetenticated user.
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**: empty
  
- **Responses**:
  - **Status Code**: `200 OK`
    - **Response Body**:
      ```json
      {
       "id" : 1,
       "title" : "hola",
       "textContent" : "soy Germán",
       "imagePath" : "ruta_a_la_imagen"
      }

### Repost a post
- **Method**: `PATCH`
- **Endpoint**: `/posts/repost/{postId}`
- **Description**: Repost the post that corresponds with the postId by the authetenticated user.
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**: empty
  
- **Responses**:
  - **Status Code**: `200 OK`
    - **Response Body**:
      ```json
      {
       "id" : 1,
       "title" : "hola",
       "textContent" : "soy Germán",
       "imagePath" : "ruta_a_la_imagen"
      }

### Delete the repost of a post
- **Method**: `PATCH`
- **Endpoint**: `/posts/delete-repost/{postId}`
- **Description**: Delete the repost of the post that corresponds with the postId by the authetenticated user.
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**: empty
  
- **Responses**:
  - **Status Code**: `200 OK`
    - **Response Body**:
      ```json
      {
       "id" : 1,
       "title" : "hola",
       "textContent" : "soy Germán",
       "imagePath" : "ruta_a_la_imagen"
      }

### Delete a post 
- **Method**: `DELETE`
- **Endpoint**: `/posts/{postId}`
- **Description**: Delete the post that corresponds with the postId of the authetenticated user.
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**: empty
  
- **Responses**:
  - **Status Code**: `200 OK`
    - **Response Body**:
      ```json
      {
       "id" : 1,
       "title" : "hola",
       "textContent" : "soy Germán",
       "imagePath" : "ruta_a_la_imagen"
      }

### Create a comment 
- **Method**: `POST`
- **Endpoint**: `/comments`
- **Description**: Create a new comment by the authenticated user 
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**:
  ```json
      {
       "post":{"id": 1},
       "user":null,
       "textContent":"Esto es un comentario"
      }
  
- **Responses**:
  - **Status Code**: `201 CREATED`
    - **Response Body**:
      ```json
       {
         "id" : 1,
         "textContent" : "Esto es un comentario",
         "post" : {
         "id" : 1,
         "title" : "hola",
         "textContent" : "soy Germán",
         "imagePath" : "ruta_a_la_imagen"
       }

### Like a comment 
- **Method**: `PATCH`
- **Endpoint**: `/comments/like/{commentId}`
- **Description**: Like the comment that corresponds with the commentId by the authetenticated user.
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**: empty
- **Responses**:
  - **Status Code**: `200 OK`
    - **Response Body**:
      ```json
       {
         "id" : 1,
         "textContent" : "Esto es un comentario",
         "post" : {
         "id" : 1,
         "title" : "hola",
         "textContent" : "soy Germán",
         "imagePath" : "ruta_a_la_imagen"
       }
      
### Dislike a comment 
- **Method**: `PATCH`
- **Endpoint**: `/comments/dislike/{commentId}`
- **Description**: Dislike the comment that corresponds with the commentId by the authetenticated user.
- **Header**: `Authorization: Bearer <authenticated-token>`
- **Request Body**: empty
- **Responses**:
  - **Status Code**: `200 OK`
    - **Response Body**:
      ```json
       {
         "id" : 1,
         "textContent" : "Esto es un comentario",
         "post" : {
         "id" : 1,
         "title" : "hola",
         "textContent" : "soy Germán",
         "imagePath" : "ruta_a_la_imagen"
       }

## Data Model

### UserCredentials

The UserCredentials entity represents a user's login credentials (username and password) in the system.

- **id** (Long): Unique identifier for the user (auto-generated by the database).
- **username** (String): User's username. Must be unique in the database.
- **password** (String): User's password.
- **role** (String): User's role.

**Example** `UserCredentials` entity:
  ```json
  {
    "id": 1,
    "username": "seba",
    "password": "abcd1234",
    "role":"user"
  }
  ```

### User

The User entity represents a user without sensitive data like password and role. It has a implicit one by one relation with UserCredentials, and it has
declared relations with the other entities in the system.  

- **id** (Long): Unique identifier for the user (auto-generated by the database).
- **name** (String); User's name.
- **username** (String): User's username. Must be unique in the database.
- 

**Example** `UserCredentials` entity:
  ```json
  {
    "id": 1,
    "name": "Sebastián",
    "username": "seba",
    
  }
  ```
