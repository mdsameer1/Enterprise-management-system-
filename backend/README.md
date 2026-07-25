# Enterprise Management System - Backend

Production-grade Spring Boot backend for Employee & Project Management System with JWT authentication, role-based access control, and comprehensive API.

## 🚀 Quick Start

### Prerequisites
- Java 21 or higher
- Maven 3.8+
- MySQL 8.0+
- Docker & Docker Compose (optional)

### Local Setup

1. **Clone Repository**
```bash
git clone https://github.com/mdsameer1/Enterprise-management-system-.git
cd Enterprise-management-system-/backend
```

2. **Configure Database**

Create `application-local.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/enterprise_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

3. **Build Project**
```bash
mvn clean install
```

4. **Run Application**
```bash
mvn spring-boot:run
```

5. **Access API**
- Base URL: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Health Check: `http://localhost:8080/api/health`

### Docker Setup

```bash
# Build and run with Docker Compose
docker-compose up -d

# View logs
docker-compose logs -f backend

# Stop services
docker-compose down
```

## 📋 Project Structure

```
backend/
├── src/main/java/com/enterprise/
│   ├── config/              # Spring configurations
│   ├── controller/          # REST API endpoints
│   ├── service/             # Business logic
│   ├── repository/          # Data access
│   ├── entity/              # JPA entities
│   ├── dto/                 # Data transfer objects
│   ├── security/            # JWT & authentication
│   ├── exception/           # Exception handling
│   ├── util/                # Utility classes
│   └── EnterpriseApplication.java
├── src/main/resources/
│   ├── application.properties
│   ├── application-dev.properties
│   └── application-prod.properties
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 🔐 Security Features

### JWT Authentication
- Access tokens (1 hour expiration)
- Refresh tokens (7 days expiration)
- Stateless session management
- Token validation on every request

### Role-Based Access Control
- **ADMIN**: Full system access
- **HR**: Employee, leave, attendance management
- **MANAGER**: Project, task, team management
- **EMPLOYEE**: Self-service access

### Password Security
- BCrypt hashing (strength 12)
- Password validation rules
- Secure password reset flow

## 📚 API Documentation

### Authentication Endpoints

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response:
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
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

#### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "user@example.com",
  "password": "SecurePass@123",
  "confirmPassword": "SecurePass@123",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### Refresh Token
```http
POST /api/auth/refresh
Authorization: Bearer <refresh_token>
```

### Employee Endpoints

#### Get All Employees
```http
GET /api/employees?page=0&size=20&sort=id,desc
Authorization: Bearer <access_token>
```

#### Get Employee by ID
```http
GET /api/employees/{id}
Authorization: Bearer <access_token>
```

#### Create Employee
```http
POST /api/employees
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "userId": 1,
  "firstName": "John",
  "lastName": "Doe",
  "designation": "Software Engineer",
  "joinDate": "2024-01-15"
}
```

#### Update Employee
```http
PUT /api/employees/{id}
Authorization: Bearer <access_token>
Content-Type: application/json
```

#### Delete Employee
```http
DELETE /api/employees/{id}
Authorization: Bearer <access_token>
```

## 🗄️ Database Schema

### Key Tables

**users**
- id (PK)
- username (unique)
- email (unique)
- password
- role (ENUM)
- enabled, locked, created_at, updated_at

**employees**
- id (PK)
- employee_id (unique)
- user_id (FK)
- first_name, last_name
- designation, department_id (FK)
- manager_id (FK)
- contact_info, address, documents

**departments**
- id (PK)
- name (unique)
- code (unique)
- manager_id (FK)
- description, location

**projects**
- id (PK)
- project_code (unique)
- name, description
- manager_id (FK), status
- start_date, end_date, due_date
- budget, progress, priority

**tasks**
- id (PK)
- task_code (unique)
- title, description
- project_id (FK)
- assigned_to_id (FK)
- status, priority, progress
- start_date, due_date

**attendance**
- id (PK)
- employee_id (FK)
- attendance_date
- status, check_in_time, check_out_time
- remarks, working_hours

**leaves**
- id (PK)
- employee_id (FK)
- start_date, end_date
- leave_type, status
- reason, approved_by (FK)

**notifications**
- id (PK)
- user_id (FK)
- title, message
- type, is_read, priority

**audit_logs**
- id (PK)
- user_id (FK)
- action, entity_type, entity_id
- old_values, new_values
- ip_address, status, created_at

## 🔧 Configuration

### Environment Variables (Production)

```bash
# Database
DB_URL=jdbc:mysql://host:3306/enterprise_db
DB_USERNAME=user
DB_PASSWORD=password

# JWT
JWT_SECRET=your-256-bit-secret-key

# Mail
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=app-specific-password

# CORS
CORS_ALLOWED_ORIGINS=https://yourfrontend.vercel.app
```

## 🧪 Testing

```bash
# Run tests
mvn test

# Run tests with coverage
mvn test jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

## 📦 Build & Deployment

### Build JAR
```bash
mvn clean package -DskipTests
```

### Docker Build
```bash
docker build -t enterprise-backend:1.0.0 .
```

### Deploy to Railway
1. Create Railway account and project
2. Connect GitHub repository
3. Set environment variables in Railway dashboard
4. Deploy

### Deploy to Render
1. Create Render account
2. Create Web Service from GitHub
3. Configure build command: `mvn clean package`
4. Configure start command: `java -jar target/enterprise-management-system-1.0.0.jar`
5. Add environment variables
6. Deploy

## 🔄 CI/CD Pipeline

GitHub Actions workflow included for:
- Automatic testing on push
- Build JAR artifacts
- Run SonarQube analysis
- Deploy to production

## 📊 Monitoring & Logging

### Actuator Endpoints
- `/actuator/health` - Application health
- `/actuator/metrics` - Application metrics
- `/actuator/info` - Application info

### Logging Levels
- Development: DEBUG
- Production: INFO/WARN

## 🐛 Troubleshooting

### Connection Issues
- Check database credentials
- Verify MySQL is running
- Check firewall settings

### JWT Token Issues
- Verify token format: `Bearer <token>`
- Check token expiration
- Ensure JWT_SECRET matches production value

### CORS Errors
- Update CORS_ALLOWED_ORIGINS in configuration
- Verify frontend URL is whitelisted

## 🤝 Contributing

1. Create feature branch: `git checkout -b feature/new-feature`
2. Commit changes: `git commit -am 'Add new feature'`
3. Push branch: `git push origin feature/new-feature`
4. Create Pull Request

## 📝 License

MIT License - See LICENSE file for details

## 📞 Support

For issues and questions:
- GitHub Issues: [Create Issue](https://github.com/mdsameer1/Enterprise-management-system-/issues)
- Email: support@enterprise.com

## 🎯 Roadmap

- [ ] Email notifications
- [ ] SMS notifications
- [ ] Advanced reporting
- [ ] Biometric integration
- [ ] Mobile app
- [ ] Real-time WebSocket updates
- [ ] Machine learning analytics

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JWT Best Practices](https://tools.ietf.org/html/rfc7519)
- [REST API Best Practices](https://restfulapi.net/)
