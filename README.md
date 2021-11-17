# Auth-Spring
Authentication using spring boot
# Backend
Backend API-SFUIT

## Login
**You send:**  Your  login credentials.

**You get:** An `API-Token` with wich you can make further actions.

**URL** : `/api/users/login`

**Method** : `POST`

**Request:**
```json
Accept: json
Content-Type: json

{
    "email": "username@mail.com",
    "password": "*********"
}
```
**Status 200:**
```json
HTTP/1.1 200 OK
Content-Type: json

{
   "device_id": "ABCD1234",
    "phone": "9876543210",
    "dob": "17/11/2021",
    "name": "John",
    "email": "username@mail.com",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IiQyYSQxMCRxRThIS0R6eGZseUVUM"
}
```
**Status 401:**
```json
HTTP/1.1 401 Bad Response
Content-Type: json

{
    "timestamp": "2021-11-17T12:47:37.948+00:00",
    "status": 401,
    "error": "Unauthorized",
    "path": "/api/users/login"
}
```

## Verifying Device 
**You send:**  Your Device ID by scanning QRCode.

**You get:** Device is Verified.

**URL** : `/api/users/verifyDevice`

**Method** : `POST`

**Request:**
```json
Accept: json
Content-Type: json

{
   "device_id":"ABCD1234"
}
```
**Status 200:**
```json
HTTP/1.1 200 OK
Content-Type: json

{
   "device_id": "ABCD1234"
}
```
**Status 401:**
```json
HTTP/1.1 401 Bad Response
Content-Type: json


{
    "timestamp": "2021-11-17T13:08:42.939+00:00",
    "status": 404,
    "error": "Not Found",
    "path": "/api/users/verifyDevice"
}
```

## Register
**You send:**  Your Details.

**You get:** An `API-Token` with which you can make further actions.

**URL** : `/api/users/register`

**Method** : `POST`

**Request:**
```json
Accept: json
Content-Type: json

{
    "name" : "John",
    "email":"username@mail.com",
    "dob":"17/11/2021",
    "phone":"9876543210",
    "password":"*********",
    "device_id":"ABCD1234"
}
```
**Status 200:**
```json
HTTP/1.1 200 OK
Content-Type: json

{
   "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IiQyYSQxMCRxRThIS0R6eGZseU"
}
```
**Status 401:**
```json
HTTP/1.1 401 Bad Response
Content-Type: json

{
    "timestamp": "2021-11-17T12:47:37.948+00:00",
    "status": 401,
    "error": "Unauthorized",
    "path": "/api/users/register"
}
```

## Verifying User through OTP
**You send:**  Your email and OTP, OTP is sent to registered Mail.

**You get:** An `OTP` with which you can verify yourself.

**URL** : `/api/users/verification`

**Method** : `POST`

**Request:**
```json
Accept: json
Content-Type: json

{
    "email" : "username@mail.com",
    "otp" :"****"
}
```
**Status 200:**
```json
HTTP/1.1 200 OK
Content-Type: json

{
   "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IiQyYSQxMCRxRThIS0R6eGZseUVUM"
}
```
**Status 401:**
```json
HTTP/1.1 401 Bad Response
Content-Type: json

{
    "timestamp": "2021-11-17T13:22:50.469+00:00",
    "status": 401,
    "error": "Unauthorized",
    "path": "/api/users/verification"
}
```

## Adding Device to Database
**Admin send:** Device ID .

**Admin get:** Device Successfully added.

**URL** : `/api/users/addDevice`

**Method** : `POST`

**Request:**
```json
Accept: json
Content-Type: json

{
    "device_id":"ABCD1234",
    "sensors":"pulse,ECG,temperature,Pedometer"
}
```
**Status 200:**
```json
HTTP/1.1 200 OK
Content-Type: json

{
   "sensors": "pulse,ECG,temperature,Pedometer",
    "device_id": "CD5BFAA3C9C8",
    "device_isverified": "false"
}
```
**Status 401:**
```json
HTTP/1.1 401 Bad Response
Content-Type: json

{
    "timestamp": "2021-11-17T12:47:37.948+00:00",
    "status": 401,
    "error": "Connectivity in DataBase",
    "path": "/api/users/addDevice"
}
```
## Dependencies
* spring-boot-starter-data-jpa
* java-jwt
* jbcrypt
* spring-boot-starter-jdbc
* spring-boot-starter-web
* spring-boot-devtools
* postgresql
* spring-boot-starter-mail
* jaxb-api
* spring-boot-starter-test

