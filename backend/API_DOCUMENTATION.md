# API Documentation

## Base URL
```
Development: http://localhost:8080/api
Production: https://your-domain.com/api
```

## Authentication

All endpoints (except login/register) require JWT Bearer token in Authorization header:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## Common Response Format

### Success Response
```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    // Response data
  }
}
```

### Error Response
```json
{
  "success": false,
  "message": "Error description",
  "errorCode": "ERROR_CODE",
  "fieldErrors": [
    {
      "field": "email",
      "message": "Email is invalid",
      "rejectedValue": "invalid-email"
    }
  ]
}
```

## HTTP Status Codes

- `200 OK` - Request successful
- `201 Created` - Resource created successfully
- `204 No Content` - Request successful, no content to return
- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Authentication required or failed
- `403 Forbidden` - Access denied
- `404 Not Found` - Resource not found
- `409 Conflict` - Resource already exists
- `500 Internal Server Error` - Server error

## Endpoints

### Authentication

#### POST /auth/login
Authenticate user

**Request:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "eyJhbGc...",
    "refreshToken": "eyJhbGc...",
    "tokenType": "Bearer",
    "expiresIn": 3600,
    "user": {
      "id": 1,
      "username": "john_doe",
      "email": "user@example.com",
      "role": "EMPLOYEE"
    }
  }
}
```

#### POST /auth/register
Register new user

**Request:**
```json
{
  "username": "john_doe",
  "email": "user@example.com",
  "password": "SecurePass@123",
  "confirmPassword": "SecurePass@123",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response:** User object created

#### POST /auth/refresh
Refresh access token

**Headers:**
```
Authorization: Bearer <refresh_token>
```

**Response:** New access and refresh tokens

### Employees

#### GET /employees
Get all employees (paginated)

**Query Parameters:**
- `page` (default: 0)
- `size` (default: 20)
- `sort` (default: id)
- `direction` (asc/desc, default: desc)

**Response:**
```json
{
  "success": true,
  "data": {
    "content": [...],
    "pageNumber": 0,
    "pageSize": 20,
    "totalElements": 100,
    "totalPages": 5,
    "hasNext": true,
    "hasPrevious": false
  }
}
```

#### GET /employees/{id}
Get employee by ID

#### POST /employees
Create new employee

**Request:**
```json
{
  "userId": 1,
  "firstName": "John",
  "lastName": "Doe",
  "designation": "Software Engineer",
  "joinDate": "2024-01-15",
  "employmentType": "Permanent",
  "salary": 75000
}
```

#### PUT /employees/{id}
Update employee

#### DELETE /employees/{id}
Delete employee (Admin only)

### Departments

#### GET /departments
Get all departments

#### GET /departments/{id}
Get department by ID

#### POST /departments
Create new department

#### PUT /departments/{id}
Update department

#### DELETE /departments/{id}
Delete department (Admin only)

### Projects

#### GET /projects
Get all projects

#### GET /projects/{id}
Get project by ID

#### POST /projects
Create new project

#### PUT /projects/{id}
Update project

#### DELETE /projects/{id}
Delete project (Admin only)

### Tasks

#### GET /tasks
Get all tasks

#### GET /tasks/{id}
Get task by ID

#### POST /tasks
Create new task

#### PUT /tasks/{id}
Update task

#### DELETE /tasks/{id}
Delete task

### Attendance

#### GET /attendance
Get attendance records

#### GET /attendance/{id}
Get attendance record

#### POST /attendance
Mark attendance

#### PUT /attendance/{id}
Update attendance

### Leaves

#### GET /leaves
Get leave requests

#### GET /leaves/{id}
Get leave request

#### POST /leaves
Create leave request

#### PUT /leaves/{id}
Approve/reject leave

### Notifications

#### GET /notifications
Get notifications

#### GET /notifications/unread
Get unread notifications

#### PUT /notifications/{id}/read
Mark notification as read

## Pagination

All list endpoints support pagination:

```http
GET /api/employees?page=0&size=20&sort=firstName,asc
```

**Response includes:**
- `pageNumber` - Current page (0-indexed)
- `pageSize` - Items per page
- `totalElements` - Total items in database
- `totalPages` - Total pages
- `hasNext` - Whether next page exists
- `hasPrevious` - Whether previous page exists

## Error Codes

- `LOGIN_FAILED` - Login authentication failed
- `REGISTRATION_FAILED` - Registration failed
- `TOKEN_REFRESH_FAILED` - Token refresh failed
- `VALIDATION_ERROR` - Input validation failed
- `UNAUTHORIZED` - User not authenticated
- `FORBIDDEN` - User not authorized
- `NOT_FOUND` - Resource not found
- `DUPLICATE_RESOURCE` - Resource already exists
- `INTERNAL_SERVER_ERROR` - Server error

## Rate Limiting

API implements rate limiting:
- 1000 requests per hour per IP
- 100 requests per minute per user

## CORS

CORS is enabled for configured origins in `application.properties`:

```properties
cors.allowed-origins=http://localhost:3000,https://yourfrontend.vercel.app
```

## Versioning

API uses URL versioning:
- Current version: v1 (implicit)
- Future: v2, v3, etc.

## Webhooks

Supported webhook events:
- `employee.created`
- `employee.updated`
- `project.status.changed`
- `task.assigned`
- `leave.approved`

## Testing

### Using cURL
```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}'

# Get employees
curl -X GET http://localhost:8080/api/employees \
  -H "Authorization: Bearer <token>"
```

### Using Postman
1. Import API collection from Swagger
2. Set base URL: `http://localhost:8080/api`
3. Set Bearer token in Authorization
4. Use provided request templates

## Swagger/OpenAPI

Interactive API documentation available at:
- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

## Changelog

### Version 1.0.0
- Initial release
- Authentication & Authorization
- Employee management
- Project management
- Task management
- Attendance tracking
- Leave management
- Notifications
- Audit logging
