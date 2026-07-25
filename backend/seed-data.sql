-- Sample Data for Enterprise Management System

-- Insert Admin User
INSERT INTO users (username, email, password, role, enabled, locked, created_by, updated_by, last_login_at)
VALUES ('admin', 'admin@enterprise.com', '$2a$12$...hashed_password...', 'ADMIN', TRUE, FALSE, 'system', 'system', NOW());

-- Insert HR Users
INSERT INTO users (username, email, password, role, enabled, locked, created_by, updated_by, last_login_at)
VALUES 
('hr_manager', 'hr@enterprise.com', '$2a$12$...hashed_password...', 'HR', TRUE, FALSE, 'system', 'system', NOW()),
('hr_officer', 'hr.officer@enterprise.com', '$2a$12$...hashed_password...', 'HR', TRUE, FALSE, 'system', 'system', NOW());

-- Insert Manager Users
INSERT INTO users (username, email, password, role, enabled, locked, created_by, updated_by, last_login_at)
VALUES 
('manager1', 'manager1@enterprise.com', '$2a$12$...hashed_password...', 'MANAGER', TRUE, FALSE, 'system', 'system', NOW()),
('manager2', 'manager2@enterprise.com', '$2a$12$...hashed_password...', 'MANAGER', TRUE, FALSE, 'system', 'system', NOW());

-- Insert Employee Users
INSERT INTO users (username, email, password, role, enabled, locked, created_by, updated_by, last_login_at)
VALUES 
('john_doe', 'john@enterprise.com', '$2a$12$...hashed_password...', 'EMPLOYEE', TRUE, FALSE, 'system', 'system', NOW()),
('jane_smith', 'jane@enterprise.com', '$2a$12$...hashed_password...', 'EMPLOYEE', TRUE, FALSE, 'system', 'system', NOW()),
('mike_johnson', 'mike@enterprise.com', '$2a$12$...hashed_password...', 'EMPLOYEE', TRUE, FALSE, 'system', 'system', NOW());

-- Insert Departments
INSERT INTO departments (name, code, description, location, active, created_at, updated_at)
VALUES 
('Engineering', 'ENG', 'Software Engineering Department', 'Building A', TRUE, NOW(), NOW()),
('Human Resources', 'HR', 'Human Resources Department', 'Building B', TRUE, NOW(), NOW()),
('Sales', 'SALES', 'Sales Department', 'Building C', TRUE, NOW(), NOW()),
('Marketing', 'MKT', 'Marketing Department', 'Building A', TRUE, NOW(), NOW());

-- Insert Employees
INSERT INTO employees (employee_id, user_id, first_name, last_name, designation, department, department_id, phone_number, join_date, employment_type, salary, active, created_at, updated_at)
VALUES 
('EMP001', 1, 'John', 'Doe', 'Senior Software Engineer', 'Engineering', 1, '9876543210', '2023-01-15', 'Permanent', 75000.00, TRUE, NOW(), NOW()),
('EMP002', 2, 'Jane', 'Smith', 'HR Manager', 'Human Resources', 2, '9876543211', '2022-06-01', 'Permanent', 65000.00, TRUE, NOW(), NOW()),
('EMP003', 3, 'Mike', 'Johnson', 'Sales Executive', 'Sales', 3, '9876543212', '2023-03-20', 'Permanent', 55000.00, TRUE, NOW(), NOW());

-- Insert Projects
INSERT INTO projects (project_code, name, description, manager_id, status, start_date, end_date, due_date, budget, progress, priority, created_at, updated_at)
VALUES 
('PRJ001', 'Mobile App Development', 'Development of mobile application', 1, 'ACTIVE', '2024-01-01', '2024-06-30', '2024-06-15', 100000.00, 45.00, 'HIGH', NOW(), NOW()),
('PRJ002', 'Cloud Migration', 'Migration of legacy systems to cloud', 1, 'ACTIVE', '2024-02-01', '2024-08-31', '2024-08-15', 150000.00, 30.00, 'CRITICAL', NOW(), NOW()),
('PRJ003', 'Website Redesign', 'Redesign of company website', 2, 'PLANNING', '2024-04-01', '2024-09-30', '2024-09-15', 50000.00, 0.00, 'MEDIUM', NOW(), NOW());

-- Insert Tasks
INSERT INTO tasks (task_code, title, description, project_id, assigned_to_id, status, priority, progress, start_date, due_date, created_at, updated_at)
VALUES 
('TASK001', 'API Development', 'Develop REST APIs for mobile app', 1, 1, 'IN_PROGRESS', 'HIGH', 60.00, '2024-01-15', '2024-03-31', NOW(), NOW()),
('TASK002', 'Database Design', 'Design and setup database schema', 1, 2, 'COMPLETED', 'HIGH', 100.00, '2024-01-01', '2024-02-15', NOW(), NOW()),
('TASK003', 'UI Development', 'Create user interface components', 1, 3, 'IN_PROGRESS', 'MEDIUM', 50.00, '2024-02-01', '2024-04-30', NOW(), NOW());

-- Insert Attendance Records
INSERT INTO attendance (employee_id, attendance_date, status, check_in_time, check_out_time, working_hours, approved, created_at, updated_at)
VALUES 
(1, '2024-07-22', 'PRESENT', '09:00:00', '17:30:00', '8.5', TRUE, NOW(), NOW()),
(1, '2024-07-23', 'PRESENT', '08:45:00', '17:45:00', '9.0', TRUE, NOW(), NOW()),
(2, '2024-07-22', 'PRESENT', '09:15:00', '17:00:00', '7.75', TRUE, NOW(), NOW()),
(2, '2024-07-23', 'HALF_DAY', '09:00:00', '12:30:00', '3.5', TRUE, NOW(), NOW());

-- Insert Leave Records
INSERT INTO leaves (employee_id, start_date, end_date, number_of_days, leave_type, status, reason, leave_year, created_at, updated_at)
VALUES 
(1, '2024-08-01', '2024-08-05', 5, 'CASUAL', 'PENDING', 'Personal work', 2024, NOW(), NOW()),
(2, '2024-08-10', '2024-08-12', 3, 'SICK', 'APPROVED', 'Medical treatment', 2024, NOW(), NOW()),
(3, '2024-08-20', '2024-08-22', 3, 'CASUAL', 'APPROVED', 'Family time', 2024, NOW(), NOW());

-- Insert Notifications
INSERT INTO notifications (user_id, title, message, notification_type, is_read, priority, created_at, updated_at)
VALUES 
(1, 'Task Assigned', 'You have been assigned to TASK001', 'TASK_ASSIGNED', FALSE, 'HIGH', NOW(), NOW()),
(2, 'Leave Approved', 'Your leave request has been approved', 'LEAVE_APPROVED', TRUE, 'MEDIUM', NOW(), NOW()),
(3, 'Project Updated', 'PRJ001 has been updated with new milestones', 'PROJECT_UPDATED', FALSE, 'LOW', NOW(), NOW());

-- Note: Passwords are hashed with BCrypt
-- To generate hashed passwords, use:
-- org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
-- String hashed = encoder.encode("your-password");
-- Common test password: "password123" hashed is typically stored
