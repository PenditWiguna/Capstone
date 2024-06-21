# Endpoint User
https://bali-destination-ilmyfxcvzq-et.a.run.app

## Signup
- URL
    - /auth/signup
- Method
    - POST
- Request Body
    - username as string
    - email as string
    - password as string
- Response
```json
{
    "error": false,
    "message": "User created"
}
```

## Login
- URL
    - /auth/login
- Method
    - POST
- Request Body
    - email as string
    - password as string
- Response
```json
{
    "error": false,
    "message": "success",
    "loginResult": {
        "id": "88295689-cb2c-48f4-82b5-2c60328f35ea",
        "username": "example",
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI4ODI5NTY4OS1jYjJjLTQ4ZjQtODJiNS0yYzYwMzI4ZjM1ZWEiLCJpYXQiOjE3MTg5NTMwMTd9.yoIvJtctw-wBpzKRAeWEmF9LyaPBaJ2GSZdBP_W9HgQ"
    }
}
```

## Logout
- URL
    - /auth/logout
- Method
    - POST
- Headers
    - Authorization: Bearer <token>
- Request Body
    - (tidak ada)
- Response
```json
{
  "message": "Logout successful"
}
```

## Get User Data
- URL
    - /user/profile
- Method
    - GET
- Headers
    - Authorization: Bearer <token>
- Request Body
    - (tidak ada)
- Response
```json
{
    "id": "88295689-cb2c-48f4-82b5-2c60328f35ea",
    "email": "example@bangkit.academy",
    "username": "example",
    "password": "$2a$10$wZcabW0MrWqpb6t.uQdHNeFiVfR/veMwLHXZ3F19KnbqzO/OglHj6",
    "createdAt": "2024-06-19T06:21:24.590Z",
    "updatedAt": "2024-06-19T06:21:24.590Z"
}
```

## Update User Data
- URL
    - /user/profile
- Method
    - PUT
- Headers
    - Authorization: Bearer <token>
- Request Body
    - new_username as string (optional)
    - new_email as string (optional)
    - new_password as string (optional)
- Response
```json
{
    "message": "User profile updated successfully"
}
```

## Delete User Data
- URL
    - /user/profile
- Method
    - DELETE
- Headers
    - Authorization: Bearer <token>
- Request Body
    - (tidak ada)
- Response
```json
{
  "message": "User account deleted successfully"
}
```

## Get Place Data
- URL
    - /search
- Method
    - GET
- Headers
    - Authorization: Bearer <token>
- Parameters
    - placeName as string
- Response
```json
{
    "results": [
        {
            "Place_Name": "Wisata Air Panas Toya Bungkah",
            "Description": "Pemandian air panas di dekat gunung Batur...",
            "Alamat": "Batur Tengah, Kec. Kintamani",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        },
        {
            "Place_Name": "Air Terjun Aling-Aling",
            "Description": "Keberadaan air terjun ini semakin mempercantik wisata...",
            "Alamat": "Jl. Raya Desa Sambangan",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        }
    ]
}
```

## How to run
- Clone the code or download it as a zip file
- Connect to your database and configure your service account key
- Run "npm install" to install dependencies
- Run "npm run start" to run the app