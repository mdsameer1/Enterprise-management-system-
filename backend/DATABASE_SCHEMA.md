# Database Schema

## Entity Relationship Diagram

```
users (1) ───── (1) employees
  ├── role: ENUM
  └── enabled, locked

employees (1) ───── (M) projects (as manager)
employees (1) ───── (M) tasks (assigned_to)
employees (1) ───── (1) department
employees (1) ───── (M) attendance
employees (1) ───── (M) leaves
employees (1) ───── (M) notifications

departments (1) ───── (M) employees

projects (1) ───── (M) tasks
projects (M) ───── (M) employees (team_members via project_team)

tasks (1) ───── (M) tasks (subtasks via parent_task)

audit_logs (M) ───── (1) users
```

## SQL Schema

```sql
-- Users Table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE') NOT NULL,
    enabled BOOLEAN DEFAULT FALSE,
    locked BOOLEAN DEFAULT FALSE,
    profile_picture_url VARCHAR(500),
    phone_number VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    last_login_at TIMESTAMP,
    last_password_changed_at TIMESTAMP,
    version BIGINT DEFAULT 0,
    INDEX idx_email (email),
    INDEX idx_username (username),
    INDEX idx_role (role)
);

-- Departments Table
CREATE TABLE departments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) UNIQUE NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    description VARCHAR(500),
    manager_id BIGINT,
    location VARCHAR(200),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (manager_id) REFERENCES employees(id),
    INDEX idx_dept_name (name),
    INDEX idx_dept_code (code)
);

-- Employees Table
CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id VARCHAR(50) UNIQUE NOT NULL,
    user_id BIGINT UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    designation VARCHAR(200),
    department VARCHAR(100),
    department_id BIGINT,
    manager_id BIGINT,
    phone_number VARCHAR(20),
    address VARCHAR(200),
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(10),
    country VARCHAR(100),
    date_of_birth VARCHAR(20),
    gender VARCHAR(20),
    blood_group VARCHAR(5),
    pan_number VARCHAR(100),
    aadhar_number VARCHAR(100),
    join_date VARCHAR(20) NOT NULL,
    employment_type VARCHAR(50),
    salary DECIMAL(10,2),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (manager_id) REFERENCES employees(id),
    INDEX idx_employee_id (employee_id),
    INDEX idx_department_id (department_id),
    INDEX idx_manager_id (manager_id)
);

-- Projects Table
CREATE TABLE projects (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    manager_id BIGINT NOT NULL,
    status ENUM('PLANNING', 'ACTIVE', 'ON_HOLD', 'COMPLETED', 'CANCELLED') NOT NULL,
    start_date VARCHAR(20) NOT NULL,
    end_date VARCHAR(20) NOT NULL,
    due_date VARCHAR(20) NOT NULL,
    budget DECIMAL(12,2),
    progress DECIMAL(5,2) DEFAULT 0.0,
    priority VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (manager_id) REFERENCES employees(id),
    INDEX idx_project_code (project_code),
    INDEX idx_project_status (status)
);

-- Tasks Table
CREATE TABLE tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_code VARCHAR(50) UNIQUE NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    project_id BIGINT NOT NULL,
    assigned_to_id BIGINT,
    start_date VARCHAR(20) NOT NULL,
    due_date VARCHAR(20) NOT NULL,
    status ENUM('TODO', 'IN_PROGRESS', 'IN_REVIEW', 'COMPLETED', 'BLOCKED') NOT NULL,
    priority VARCHAR(20),
    progress DECIMAL(5,2) DEFAULT 0.0,
    comments VARCHAR(500),
    parent_task_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (assigned_to_id) REFERENCES employees(id),
    FOREIGN KEY (parent_task_id) REFERENCES tasks(id),
    INDEX idx_task_code (task_code),
    INDEX idx_project_id (project_id),
    INDEX idx_assigned_to (assigned_to_id)
);

-- Attendance Table
CREATE TABLE attendance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    status ENUM('PRESENT', 'ABSENT', 'HALF_DAY', 'LEAVE', 'WORK_FROM_HOME') NOT NULL,
    check_in_time TIME NOT NULL,
    check_out_time TIME,
    remarks TEXT,
    working_hours VARCHAR(50),
    approved BOOLEAN DEFAULT FALSE,
    approved_by VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    INDEX idx_employee_date (employee_id, attendance_date),
    INDEX idx_attendance_date (attendance_date)
);

-- Leaves Table
CREATE TABLE leaves (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    number_of_days INT NOT NULL,
    leave_type ENUM('CASUAL', 'SICK', 'EARNED', 'MATERNITY', 'PATERNITY', 'UNPAID') NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL,
    reason TEXT,
    approved_by BIGINT,
    approval_remark TEXT,
    leave_year INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (approved_by) REFERENCES employees(id),
    INDEX idx_employee_leave (employee_id, leave_year),
    INDEX idx_leave_status (status)
);

-- Notifications Table
CREATE TABLE notifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    notification_type ENUM('TASK_ASSIGNED', 'TASK_COMPLETED', 'LEAVE_APPROVED', 'LEAVE_REJECTED', 'PROJECT_UPDATED', 'ATTENDANCE_MARKED', 'SYSTEM_ALERT') NOT NULL,
    related_entity VARCHAR(50),
    related_entity_id BIGINT,
    is_read BOOLEAN DEFAULT FALSE,
    priority VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_read_status (is_read)
);

-- AuditLog Table
CREATE TABLE audit_logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    action VARCHAR(50) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id BIGINT,
    old_values TEXT,
    new_values TEXT,
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    status VARCHAR(50),
    remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_action (action),
    INDEX idx_created_at (created_at)
);

-- RefreshToken Table
CREATE TABLE refresh_tokens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    token TEXT NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_token (token(100))
);

-- Project Team Table
CREATE TABLE project_team (
    project_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, employee_id),
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);
```

## Indexes for Performance

- All foreign keys are indexed
- Email and username are unique indexed
- Date columns are indexed for range queries
- Status columns are indexed for filtering
- User references are indexed for quick lookups

## Sample Data Seeding

See `seed-data.sql` for sample data to populate the database.
