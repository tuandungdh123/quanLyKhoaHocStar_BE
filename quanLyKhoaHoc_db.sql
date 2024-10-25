DROP DATABASE star_quan_ly_khoa_hoc;
CREATE DATABASE star_quan_ly_khoa_hoc;
USE star_quan_ly_khoa_hoc;
-- Bảng Roles
CREATE TABLE Roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

-- Bảng Users
CREATE TABLE Users (
    user_id INT PRIMARY KEY auto_increment,
    name VARCHAR(100),
    phone VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role_id INT,
    avatar_url VARCHAR(255),
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE CASCADE
);

-- Bảng Courses
CREATE TABLE Courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    img_url VARCHAR(255),
    instructor_id INT NULL,  -- Cho phép NULL cho giảng viên
    start_date DATE,
    end_date DATE,
    meeting_time VARCHAR(255),
    schedule VARCHAR(255),
    price DOUBLE NOT NULL,  -- Giá khóa học
    status BIT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (instructor_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- Bảng Enrollments
CREATE TABLE Enrollments (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    course_id INT NOT NULL,
    status ENUM('completed', 'in_progress') NOT NULL DEFAULT 'in_progress',
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status ENUM('pending', 'completed', 'failed') NOT NULL DEFAULT 'pending',
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id) ON DELETE CASCADE
);

-- Bảng Modules
CREATE TABLE Modules (
    module_id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    order_number INT NOT NULL, -- Thứ tự phần học
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id) ON DELETE CASCADE
);

-- Bảng Lessons
CREATE TABLE Lessons (
    lesson_id INT PRIMARY KEY AUTO_INCREMENT,
    module_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255),
    video_url VARCHAR(255),
    duration INT,  -- Thời gian video (giây)
    order_number INT NOT NULL, -- Thứ tự bài học
    status ENUM('completed', 'not_completed') NOT NULL DEFAULT 'not_completed',  -- Trạng thái bài học
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (module_id) REFERENCES Modules(module_id) ON DELETE CASCADE
);

-- Bảng Quizzes
CREATE TABLE Quizzes (
    quiz_id INT PRIMARY KEY AUTO_INCREMENT,
    module_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (module_id) REFERENCES Modules(module_id) ON DELETE CASCADE
);

-- Bảng Questions
CREATE TABLE Questions (
    question_id INT PRIMARY KEY AUTO_INCREMENT,
    quiz_id INT NOT NULL,
    question_text VARCHAR(255) NOT NULL,
    is_required BOOLEAN NOT NULL DEFAULT TRUE,  -- Câu hỏi bắt buộc
    FOREIGN KEY (quiz_id) REFERENCES Quizzes(quiz_id) ON DELETE CASCADE
);

-- Bảng Choices
CREATE TABLE Choices (
    choice_id INT PRIMARY KEY AUTO_INCREMENT,
    question_id INT NOT NULL,
    choice_text VARCHAR(255) NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (question_id) REFERENCES Questions(question_id) ON DELETE CASCADE
);

-- Bảng Meeting_Schedules
CREATE TABLE Meeting_Schedules (
    meeting_id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    meeting_date DATETIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id) ON DELETE CASCADE
);

-- Bảng Payments
CREATE TABLE Payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    enrollment_id INT NOT NULL,
    amount DOUBLE NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'completed', 'failed') NOT NULL DEFAULT 'pending',
    transaction_id VARCHAR(255) UNIQUE,
    FOREIGN KEY (enrollment_id) REFERENCES Enrollments(enrollment_id) ON DELETE CASCADE
);

-- Bảng Submission_History
CREATE TABLE Submission_History (
    history_id INT PRIMARY KEY AUTO_INCREMENT, 
    user_id INT NOT NULL, 
    course_id INT NOT NULL,
    module_id INT NOT NULL,
    quiz_id INT NOT NULL,
    question_id INT NOT NULL, 
    score DOUBLE,
    assignment_status BIT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    FOREIGN KEY (user_id) REFERENCES Users(user_id), 
    FOREIGN KEY (course_id) REFERENCES Courses(course_id), 
    FOREIGN KEY (module_id) REFERENCES Modules(module_id),
    FOREIGN KEY (quiz_id) REFERENCES Quizzes(quiz_id), 
    FOREIGN KEY (question_id) REFERENCES Questions(question_id) 
);
-- Thêm dữ liệu vào bảng Roles
INSERT INTO Roles (role_name)
VALUES 
('Giảng viên'),
('Học viên'),
('Quản trị viên');

-- Thêm dữ liệu vào bảng Users
INSERT INTO Users (name, phone, email, password_hash, role_id, avatar_url, status)
VALUES 
( 'Nguyễn Văn A', '0987654321', 'nguyenvana@example.com', 'hash_password_1', 1, 'avatar_a.jpg', 1),
( 'Trần Thị B', '0987654322', 'tranthib@example.com', 'hash_password_2', 2, 'avatar_b.jpg', 1),
( 'Phạm Văn C', '0987654323', 'phamvanc@example.com', 'hash_password_3', 2, 'avatar_c.jpg', 1),
( 'Lê Thị D', '0987654324', 'lethid@example.com', 'hash_password_4', 1, 'avatar_d.jpg', 1),
( 'Vũ Văn E', '0987654325', 'vuvane@example.com', 'hash_password_5', 3, 'avatar_e.jpg', 1);

-- Thêm dữ liệu vào bảng Courses
INSERT INTO Courses (title, description, img_url, instructor_id, start_date, end_date, meeting_time, schedule, price, status)
VALUES 
('Lập trình Java cơ bản', 'Khóa học cung cấp kiến thức cơ bản về Java', 'java.jpg', 1, '2024-01-10', '2024-03-10', '19:00 - 21:00', 'Thứ 2, 4, 6', 1000000, 1),
('Lập trình Python nâng cao', 'Khóa học nâng cao kỹ năng lập trình Python', 'python.jpg', 4, '2024-02-05', '2024-05-05', '18:00 - 20:00', 'Thứ 3, 5, 7', 1500000, 1);

-- Thêm dữ liệu vào bảng Enrollments
INSERT INTO Enrollments (user_id, course_id, status, payment_status)
VALUES 
(2, 1, 'in_progress', 'completed'),
(3, 1, 'in_progress', 'pending'),
(2, 2, 'completed', 'completed'),
(4, 2, 'in_progress', 'pending'),
(5, 1, 'in_progress', 'pending');

-- Thêm dữ liệu vào bảng Modules
INSERT INTO Modules (course_id, title, order_number)
VALUES 
(1, 'Giới thiệu về Java', 1),
(1, 'Các khái niệm cơ bản', 2),
(2, 'Lập trình hướng đối tượng trong Python', 1),
(2, 'Xử lý ngoại lệ trong Python', 2);

-- Thêm dữ liệu vào bảng Lessons
INSERT INTO Lessons (module_id, title, content, video_url, duration, order_number, status)
VALUES 
(1, 'Cài đặt môi trường Java', 'Hướng dẫn cài đặt JDK và IDE', 'setup_java.mp4', 600, 1, 'completed'),
(2, 'Biến và kiểu dữ liệu', 'Giới thiệu về các kiểu dữ liệu trong Java', 'datatypes_java.mp4', 1200, 2, 'not_completed'),
(3, 'Các khái niệm cơ bản của OOP', 'Lập trình hướng đối tượng trong Python', 'oop_python.mp4', 900, 1, 'completed');

-- Thêm dữ liệu vào bảng Quizzes
INSERT INTO Quizzes (module_id, title, description)
VALUES 
(1, 'Kiểm tra kiến thức Java cơ bản', 'Bài kiểm tra kiến thức sau module Java cơ bản'),
(2, 'Kiểm tra lập trình hướng đối tượng Python', 'Bài kiểm tra kiến thức về OOP trong Python');

-- Thêm dữ liệu vào bảng Questions
INSERT INTO Questions (quiz_id, question_text, is_required)
VALUES 
(1, 'Java được phát triển bởi công ty nào?', TRUE),
(2, 'Tính chất nào của lập trình hướng đối tượng cho phép đa hình?', TRUE);

-- Thêm dữ liệu vào bảng Choices
INSERT INTO Choices (question_id, choice_text, is_correct)
VALUES 
(1, 'Sun Microsystems', TRUE),
(1, 'Microsoft', FALSE),
(2, 'Đóng gói (Encapsulation)', FALSE),
(2, 'Đa hình (Polymorphism)', TRUE);

-- Thêm dữ liệu vào bảng Meeting_Schedules
INSERT INTO Meeting_Schedules (course_id, meeting_date)
VALUES 
(1, '2024-01-12 19:00:00'),
(2, '2024-02-07 18:00:00');

-- Thêm dữ liệu vào bảng Payments
INSERT INTO Payments (enrollment_id, amount, payment_date, status, transaction_id)
VALUES 
(1, 1000000, '2024-01-11 12:00:00', 'completed', 'TXN001'),
(3, 1500000, '2024-02-06 14:00:00', 'completed', 'TXN002');
