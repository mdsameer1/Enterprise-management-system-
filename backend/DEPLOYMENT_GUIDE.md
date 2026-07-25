# Deployment Guide

## Prerequisites
- Docker & Docker Compose
- Git
- Cloud accounts (Railway/Render + Planetscale/AWS RDS)

## Environment Setup

### Production Environment Variables

Create `.env.production`:
```bash
# Database
DB_URL=jdbc:mysql://your-db-host:3306/enterprise_db
DB_USERNAME=db_user
DB_PASSWORD=secure_password

# JWT
JWT_SECRET=generate-256-bit-secure-key

# Mail
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=app-specific-password

# CORS
CORS_ALLOWED_ORIGINS=https://yourfrontend.vercel.app

# Application
SPRING_PROFILES_ACTIVE=prod
```

## Deploy to Railway

### Step 1: Create Railway Project
1. Go to [railway.app](https://railway.app)
2. Sign up and create new project
3. Connect GitHub account

### Step 2: Add Services
1. Create MySQL database service
   - Select "MySQL" from templates
   - Note the connection string

2. Create Web Service from GitHub
   - Select your repository
   - Select `enterprise-management-system-` repo
   - Railway auto-detects Maven build

### Step 3: Configure Environment
1. Go to Variables tab
2. Add environment variables from `.env.production`
3. Set `SPRING_PROFILES_ACTIVE=prod`

### Step 4: Deploy
1. Push to main branch
2. Railway auto-builds and deploys
3. Access at provided Railway domain

### Step 5: Verify Deployment
```bash
curl https://your-railway-app.railway.app/api/health
```

## Deploy to Render

### Step 1: Create Render Service
1. Go to [render.com](https://render.com)
2. Create new Web Service
3. Connect GitHub
4. Select repository

### Step 2: Configure Service
- **Build Command**: `mvn clean package -DskipTests`
- **Start Command**: `java -jar target/enterprise-management-system-1.0.0.jar`
- **Instance Type**: Starter (free tier) or Standard

### Step 3: Add Environment Variables
1. Go to Environment tab
2. Add all variables from `.env.production`
3. Add `RENDER=true`

### Step 4: Create Database
1. Create PostgreSQL or MySQL database
2. Get connection string
3. Add to environment variables

### Step 5: Deploy
1. Manual or auto-deploy on push
2. Monitor build logs
3. Access deployed application

## Database Setup

### AWS RDS (MySQL)

1. Create RDS instance
   - Engine: MySQL 8.0
   - Instance class: db.t3.micro (free tier)
   - Storage: 20GB
   - Multi-AZ: No (for dev)

2. Configure security group
   - Allow inbound on port 3306
   - Allow from application security group

3. Get connection details
   ```
   DB_URL=jdbc:mysql://your-rds-endpoint:3306/enterprise_db
   DB_USERNAME=admin
   DB_PASSWORD=your_secure_password
   ```

### PlanetScale (MySQL)

1. Sign up at [planetscale.com](https://planetscale.com)
2. Create database
3. Get connection string (SSL required)
   ```
   DB_URL=jdbc:mysql://your-host.psdb.cloud/enterprise_db?sslMode=VERIFY_IDENTITY
   DB_USERNAME=username
   DB_PASSWORD=password
   ```

### Google Cloud SQL

1. Create Cloud SQL instance
2. Enable Cloud Proxy
3. Get connection details
4. Use Cloud Proxy for secure connection

## Docker Deployment

### Build Docker Image
```bash
docker build -t enterprise-backend:latest .
```

### Push to Docker Registry
```bash
docker tag enterprise-backend:latest your-registry/enterprise-backend:latest
docker push your-registry/enterprise-backend:latest
```

### Docker Compose Production
```bash
# With environment file
docker-compose --env-file .env.production up -d

# View logs
docker-compose logs -f

# Stop
docker-compose down
```

## Kubernetes Deployment

### Prerequisites
- kubectl installed
- Kubernetes cluster access
- Docker image in registry

### Deployment Files

**deployment.yaml**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: enterprise-backend
spec:
  replicas: 3
  selector:
    matchLabels:
      app: enterprise-backend
  template:
    metadata:
      labels:
        app: enterprise-backend
    spec:
      containers:
      - name: enterprise-backend
        image: your-registry/enterprise-backend:latest
        ports:
        - containerPort: 8080
        env:
        - name: DB_URL
          valueFrom:
            secretKeyRef:
              name: db-credentials
              key: url
        - name: DB_USERNAME
          valueFrom:
            secretKeyRef:
              name: db-credentials
              key: username
        resources:
          requests:
            memory: "256Mi"
            cpu: "250m"
          limits:
            memory: "512Mi"
            cpu: "500m"
        livenessProbe:
          httpGet:
            path: /api/health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
```

### Deploy to Kubernetes
```bash
# Create secrets
kubectl create secret generic db-credentials \
  --from-literal=url=jdbc:mysql://... \
  --from-literal=username=admin

# Deploy
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml

# Check status
kubectl get pods
kubectl logs -f deployment/enterprise-backend
```

## SSL/TLS Certificate

### Railway (Automatic)
- Automatic SSL certificate generation
- Free .railway.app domain
- Custom domain support

### Render (Automatic)
- Free SSL certificate via Let's Encrypt
- Auto-renewal
- Custom domain support

### Manual Setup
```bash
# Generate self-signed certificate
openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365

# Configure in Spring Boot
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=password
server.ssl.key-store-type=PKCS12
```

## Monitoring & Logging

### Enable Monitoring
```properties
# application-prod.properties
management.endpoints.web.exposure.include=health,metrics,prometheus
management.metrics.export.prometheus.enabled=true
```

### Setup CloudWatch (AWS)
```bash
# Add CloudWatch appender to logback
# Reference: AWS CloudWatch Logs Java Integration
```

### Setup Logging
- Railway: Built-in logging dashboard
- Render: Logs available in service page
- Custom: Configure centralized logging with ELK stack

## Performance Tuning

### Database Connection Pool
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.idle-timeout=300000
```

### Caching
```properties
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
```

### JVM Tuning
```bash
# In Dockerfile or deployment config
JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"
```

## Troubleshooting

### Connection Issues
- Check firewall rules
- Verify credentials
- Test connection: `telnet db-host 3306`

### Build Failures
- Check Java version (21+)
- Verify Maven cache
- Review build logs

### Runtime Errors
- Check application logs
- Verify environment variables
- Check database connectivity

## Backup & Recovery

### Database Backup
```bash
# MySQL backup
mysqldump -h host -u user -p database > backup.sql

# Restore
mysql -h host -u user -p database < backup.sql
```

### Automated Backups
- Configure AWS RDS automated backups
- PlanetScale automatic backups
- GCS Cloud SQL automated backups

## Maintenance

### Regular Tasks
- Monitor application logs
- Check disk space
- Update dependencies
- Review security updates
- Clean old audit logs

### Database Maintenance
- Optimize indexes
- Update table statistics
- Archive old records
- Monitor query performance
