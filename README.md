# user-management-application
The application allows to authenticate with JWT tokens and manage users and their roles, including ability to list, create, modify and delete users

#Requirements
Please create a Spring Boot (or Grails) REST application (back-end API only with MySQL
db) and put i t on Github. API shall return JSON.
The app shall have the following endpoints (it will be tested using Postman or similar):
1. Controller: Auth
Actions:
a. Login
2. Controller: User
Actions (permissions based on role shall be checked for every user endpoint):
a. Create User
b. Update User
c. Delete User
d. List users
Fields:
a. Username
b. Password
c. Role
3. Controller: Role
Actions (only admin (predefined fixed role) user shall have access):
a. Create Role
b. Update Role
c. Delete Role
d. List roles
Fields:
a. Role name
b. Permissions (available options - can be multiple are):
i. Create user
ii. Update user
iii. Delete user
iv. List users
An predefined admin user shall be available, who will be allowed to create new roles (with
1-4 permissions) and then create users using these roles.
Example case:
a) Admin credentials are used to l og i n and create a new role with only ‘ Create User’
permission
b) Admin user creates a new user using the new role defined i n (a)
c) New user l ogs i n and can only access ‘ Create User’ endpoint
BONUS: create a docker-compose file to start back-end and db
See page 2 for additional i nformation!
Additional i nformation:
host: localhost:8080
default_user: admin
default_password: admin
endpoints:
all UNAUTHORISED requests should return:
- HTTP status 401
- empty body
all PERMISSION DENIED requests should return:
- HTTP status 403
- empty body
all ERROR requests should return:
- HTTP status 500
- empty body
all NOT FOUND requests should return:
- HTTP status 404
- empty body
LOGIN:
- POST /v1/vis-test/login
- body:
{
'user': "string",
'password': "string"
}
- response:
HTTP status 200
{
"token": "string"
}
USER:
GET /v1/vis-test/users
- response:
HTTP status 200
[{
"id": "int",
"username": "string",
"role": "roleObject"
}]
POST /v1/vis-test/users
- request (body):
{
"username": "string",
"password": "string",
"roleId": int
}
- response:
HTTP status 201
{
"id": int,
"username": "string",
"role": "roleObject"
}
PUT /v1/vis-test/users/id
- request (body):
{
"username": "string",
"roleId": int
}
- response:
HTTP status 200
{
"id": "int",
"username": "string",
"role": "roleObject"
}
DELETE /v1/vis-test/users/id
- response:
HTTP status 200
empty body
ROLE:
PERMISSIONS:
- LIST_USERS
- CREATE_USERS
- DELETE_USERS
- EDIT_USERS
GET /v1/vis-test/roles
- response:
HTTP status 200
[{
"id": "int",
"name": "string",
"permissions": array
}]
POST /v1/vis-test/roles
- request (body):
{
"name": "string",
"permissions": array
}
- response:
HTTP status 201
{
"id": int,
"name": "string",
"permissions": array
}
PUT /v1/vis-test/roles/id
- request (body):
{
"name": "string",
"permissions": array
}
- response:
HTTP status 200
{
"id": "int",
"name": "string",
"permissions": array
}
DELETE /v1/vis-test/roles/id
- response:
HTTP status 200
empty body

