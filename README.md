# Enterprise Management System

Production-grade Enterprise Employee & Project Management System built with Spring Boot, React, and MySQL.

## 📋 Project Overview

A comprehensive enterprise management platform featuring:
- **Employee Management**: Complete employee lifecycle management
- **Project Management**: Project tracking with tasks and milestones
- **Task Management**: Task assignment, tracking, and progress monitoring
- **Attendance System**: Automated attendance tracking and reporting
- **Leave Management**: Leave request and approval workflow
- **Role-Based Access Control**: Multi-role access with granular permissions
- **JWT Authentication**: Secure authentication with refresh tokens
- **Real-time Notifications**: System notifications and alerts
- **Audit Logging**: Complete audit trail for compliance

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Frontend (React + TypeScript)            │
│              Material-UI | Zustand | React Router            │
└──────────────────────────┬──────────────────────────────────┘
                           │ HTTPS/REST API
┌──────────────────────────▼──────────────────────────────────┐
│                  Backend (Spring Boot)                       │
│        JWT Auth | Spring Security | Spring Data JPA         │
└──────────────────────────┬──────────────────────────────────┘
                           │ JDBC
┌──────────────────────────▼──────────────────────────────────┐
│                    MySQL Database                            │
│              (Employees, Projects, Tasks, etc.)              │
└──────────────────────────────────────────────────────────────┘
```

## 📁 Repository Structure

```
Enterprise-management-system-/
├── backend/                    # Spring Boot backend
│   ├── src/
│   ├── pom.xml                # Maven configuration
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── README.md
├── frontend/                   # React + TypeScript frontend
│   ├── src/
│   ├── package.json
│   ├── vite.config.ts
│   ├── Dockerfile
│   └── README.md
├── docs/                       # Documentation
├── docker-compose.yml          # Full stack orchestration
└── README.md
```

## 🚀 Quick Start

### Prerequisites
- Java 21+ & Maven 3.8+
- Node.js 18+ & npm/yarn
- MySQL 8.0+
- Docker & Docker Compose (optional)

### Local Development (Both Services)

```bash
# Clone repository
git clone https://github.com/mdsameer1/Enterprise-management-system-.git
cd Enterprise-management-system-

# Start with Docker Compose (recommended)
docker-compose up -d

# Or start services individually:

# Backend
cd backend
mvn clean install
mvn spring-boot:run

# Frontend (in another terminal)
cd frontend
npm install
npm run dev
```

### Access Points
- **Frontend**: http://localhost:5173
- **Backend API**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **MySQL**: localhost:3306

## 🔐 Security

- **JWT Authentication**: Access tokens (1hr) + Refresh tokens (7 days)
- **Role-Based Access Control**: ADMIN, HR, MANAGER, EMPLOYEE roles
- **Password Security**: BCrypt hashing with strength 12
- **CORS Protection**: Whitelist-based origin validation
- **Audit Logging**: Complete activity tracking

## 📚 Documentation

- [Backend Setup & API Docs](./backend/README.md)
- [Frontend Setup & Features](./frontend/README.md)
- [Database Schema](./docs/DATABASE.md) (coming soon)
- [API Documentation](./docs/API.md) (coming soon)

## 🛠️ Tech Stack

### Backend
- **Language**: Java 21
- **Framework**: Spring Boot 3.x
- **Security**: Spring Security + JWT
- **Database**: MySQL 8.0 + JPA/Hibernate
- **Build**: Maven 3.8+
- **API Docs**: Swagger/OpenAPI

### Frontend
- **Language**: TypeScript
- **Framework**: React 18
- **UI Library**: Material-UI v5
- **State**: Zustand
- **Routing**: React Router v6
- **HTTP Client**: Axios
- **Build**: Vite

## 📊 Database

MySQL with comprehensive schema for:
- User management and authentication
- Employee data and organizational structure
- Project and task tracking
- Attendance and leave management
- Notifications and audit logs

## 🧪 Testing

```bash
# Backend tests
cd backend
mvn test

# Frontend tests
cd frontend
npm run test
```

## 🚀 Deployment

### Docker Deployment
```bash
docker-compose up -d
```

### Cloud Deployment
- **Backend**: Railway, Render, AWS EC2, Google Cloud Run
- **Frontend**: Vercel, Netlify, AWS S3 + CloudFront
- **Database**: AWS RDS, Google Cloud SQL, Railway

## 🤝 Contributing

1. Create feature branch: `git checkout -b feature/your-feature`
2. Make changes and commit: `git commit -am 'Add feature'`
3. Push to branch: `git push origin feature/your-feature`
4. Create Pull Request

## 📝 License

MIT License - See LICENSE file for details

## 📞 Support

- GitHub Issues: [Create Issue](https://github.com/mdsameer1/Enterprise-management-system-/issues)
- Email: support@enterprise.com

## 🎯 Roadmap

- [ ] Advanced reporting and analytics
- [ ] Email and SMS notifications
- [ ] Biometric integration
- [ ] Mobile app (React Native)
- [ ] Real-time WebSocket updates
- [ ] Machine learning-based analytics
- [ ] Multi-language support
- [ ] Dark mode UI theme
