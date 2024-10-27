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
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    phone VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
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
    instructor_id INT NULL,
    start_date DATE,
    end_date DATE,
    meeting_time VARCHAR(255),
    schedule VARCHAR(255),
    price DOUBLE NOT NULL,  -- Giá khóa học
    status BIT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (instructor_id) REFERENCES Users(user_id) ON DELETE SET NULL
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
    status ENUM('completed', 'not_completed') NOT NULL DEFAULT 'not_completed',
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
    is_required BOOLEAN NOT NULL DEFAULT TRUE,
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
('Quản trị viên'),
('Quản lý khóa học'),
('Học viên nâng cao'),
('Giảng viên chính'),
('Giảng viên trợ lý'),
('Quản lý hệ thống'),
('Giảng viên thực hành'),
('Học viên dự bị');

-- Thêm dữ liệu vào bảng Users
INSERT INTO Users (user_id, name, phone, email, password_hash, role_id, avatar_url, status)
VALUES 
(1, 'Nguyễn Văn A', '0987654321', 'nguyenvana@example.com', 'hash_password_1', 1, 'avatar_a.jpg', 1),
(2, 'Trần Thị B', '0987654322', 'tranthib@example.com', 'hash_password_2', 2, 'avatar_b.jpg', 1),
(3, 'Phạm Văn C', '0987654323', 'phamvanc@example.com', 'hash_password_3', 2, 'avatar_c.jpg', 1),
(4, 'Lê Thị D', '0987654324', 'lethid@example.com', 'hash_password_4', 1, 'avatar_d.jpg', 1),
(5, 'Vũ Văn E', '0987654325', 'vuvane@example.com', 'hash_password_5', 3, 'avatar_e.jpg', 1),
(6, 'Nguyễn Thị F', '0987654326', 'nguyenthif@example.com', 'hash_password_6', 1, 'avatar_f.jpg', 1),
(7, 'Trần Văn G', '0987654327', 'tranvang@example.com', 'hash_password_7', 2, 'avatar_g.jpg', 1),
(8, 'Phạm Thị H', '0987654328', 'phamthih@example.com', 'hash_password_8', 2, 'avatar_h.jpg', 1),
(9, 'Lê Văn I', '0987654329', 'levani@example.com', 'hash_password_9', 1, 'avatar_i.jpg', 1),
(10, 'Vũ Thị J', '0987654310', 'vuthij@example.com', 'hash_password_10', 3, 'avatar_j.jpg', 1);

-- Thêm dữ liệu vào bảng Courses
INSERT INTO Courses (title, description, img_url, instructor_id, start_date, end_date, meeting_time, schedule, price, status)
VALUES 
('Lập trình Java cơ bản', 'Khóa học cung cấp kiến thức cơ bản về Java', 'java.jpg', 1, '2024-01-10', '2024-03-10', '19:00 - 21:00', 'Thứ 2, 4, 6', 1000000, 1),
('Lập trình Python nâng cao', 'Khóa học nâng cao kỹ năng lập trình Python', 'python.jpg', 4, '2024-02-05', '2024-05-05', '18:00 - 20:00', 'Thứ 3, 5, 7', 1500000, 1),
('Lập trình Web cơ bản', 'Khóa học về HTML, CSS, JavaScript', 'java.jpg', 2, '2024-03-01', '2024-05-01', '20:00 - 22:00', 'Thứ 2, 4', 1200000, 1),
('Lập trình di động với Flutter', 'Khóa học phát triển ứng dụng di động', 'java.jpg', 1, '2024-04-10', '2024-06-10', '19:00 - 21:00', 'Thứ 3, 5', 1800000, 1),
('Cơ sở dữ liệu nâng cao', 'Khóa học về SQL và NoSQL', 'java.jpg', 3, '2024-05-15', '2024-07-15', '18:00 - 20:00', 'Thứ 4, 6', 1300000, 1),
('An ninh mạng căn bản', 'Khóa học giới thiệu về an ninh mạng', 'java.jpg', 1, '2024-02-20', '2024-04-20', '19:00 - 21:00', 'Thứ 2, 5', 1600000, 1),
('Học máy và trí tuệ nhân tạo', 'Khóa học về machine learning', 'java.jpg', 2, '2024-03-20', '2024-06-20', '20:00 - 22:00', 'Thứ 3, 6', 2000000, 1),
('Thiết kế UX/UI', 'Khóa học về thiết kế trải nghiệm người dùng', 'java.jpg', 4, '2024-01-05', '2024-04-05', '18:00 - 20:00', 'Thứ 2, 4', 1400000, 1),
('Lập trình game với Unity', 'Khóa học phát triển game với Unity', 'java.jpg', 3, '2024-06-01', '2024-08-01', '19:00 - 21:00', 'Thứ 3, 5', 2200000, 1),
('Phát triển ứng dụng với Node.js', 'Khóa học phát triển ứng dụng backend', 'java.jpg', 1, '2024-07-10', '2024-09-10', '18:00 - 20:00', 'Thứ 4, 6', 1700000, 1);

-- Thêm dữ liệu vào bảng Enrollments
INSERT INTO Enrollments (user_id, course_id, status, payment_status)
VALUES 
(2, 1, 'in_progress', 'completed'),
(3, 1, 'in_progress', 'pending'),
(2, 2, 'completed', 'completed'),
(4, 2, 'in_progress', 'pending'),
(5, 1, 'in_progress', 'pending'),
(6, 3, 'completed', 'completed'),
(7, 4, 'in_progress', 'pending'),
(8, 5, 'completed', 'completed'),
(9, 6, 'in_progress', 'pending'),
(10, 7, 'completed', 'completed');

-- Thêm dữ liệu vào bảng Modules
INSERT INTO Modules (course_id, title, order_number)
VALUES 
(1, 'Giới thiệu về Java', 1),
(1, 'Kiểm thử trong Java', 2),

(2, 'Lập trình hướng đối tượng trong Python', 1),
(2, 'Python nâng cao', 2),

(3, 'HTML và CSS cơ bản', 1),
(3, 'JavaScript cơ bản', 2),

(4, 'Cài đặt môi trường Flutter', 1),
(4, 'Phát triển ứng dụng di động', 2),

(5, 'Cơ sở dữ liệu SQL', 1),
(5, 'Cơ sở dữ liệu NoSQL', 2);

-- Thêm dữ liệu vào bảng Lessons
INSERT INTO Lessons (module_id, title, content, video_url, duration, order_number, status)
VALUES 
(1, 'Lập trình hàm trong Java', 'Hướng dẫn sử dụng hàm trong Java', 'java_functions.mp4', 900, 1, 'not_completed'),
(1, 'Cấu trúc dữ liệu trong Java', 'Tìm hiểu về các cấu trúc dữ liệu', 'java_data_structures.mp4', 1200, 2, 'completed'),
(1, 'Lập trình đa luồng trong Java', 'Giới thiệu về lập trình đa luồng', 'java_multithreading.mp4', 1500, 3, 'not_completed'),
(1, 'Hướng dẫn sử dụng ArrayList', 'Cách sử dụng ArrayList trong Java', 'java_arraylist.mp4', 900, 4, 'not_completed'),
(1, 'Lambda trong Java', 'Sử dụng lambda expression trong Java', 'java_lambda.mp4', 1000, 5, 'completed'),
(1, 'Stream API trong Java', 'Giới thiệu về Stream API', 'java_stream_api.mp4', 1100, 6, 'not_completed'),
(1, 'Java Collections Framework', 'Giới thiệu về Collections Framework', 'java_collections.mp4', 1200, 7, 'completed'),

(2, 'Kiểm thử cơ bản', 'Cách thực hiện kiểm thử ứng dụng Java', 'java_testing_1.mp4', 800, 1, 'completed'),
(2, 'Kiểm thử nâng cao', 'Cách thực hiện kiểm thử ứng dụng Java', 'java_testing_2.mp4', 800, 2, 'completed'),

(3, 'Lập trình hướng đối tượng nâng cao', 'Khám phá OOP nâng cao trong Python', 'advanced_oop_python.mp4', 1000, 3, 'not_completed'),
(3, 'Thực hành dự án Python', 'Xây dựng dự án nhỏ bằng Python', 'python_project.mp4', 1200, 5, 'completed'),
(3, 'Sử dụng Flask trong Python', 'Giới thiệu về Flask', 'flask_intro.mp4', 1100, 6, 'not_completed'),

(4, 'Đọc và ghi tệp trong Python', 'Hướng dẫn đọc và ghi tệp trong Python', 'python_file_handling.mp4', 900, 7, 'not_completed'),
(4, 'Sử dụng thư viện Pandas', 'Giới thiệu về Pandas', 'python_pandas.mp4', 1200, 8, 'completed'),
(4, 'Xử lý dữ liệu với NumPy', 'Giới thiệu về NumPy', 'python_numpy.mp4', 1100, 9, 'not_completed'),

(5, 'Responsive Design với CSS', 'Cách làm thiết kế web responsive bằng CSS', 'css_responsive.mp4', 1000, 3, 'completed'),

(6, 'Lập trình JavaScript nâng cao', 'Các khái niệm nâng cao trong JavaScript', 'advanced_js.mp4', 1300, 3, 'not_completed'),
(6, 'Làm việc với API trong JavaScript', 'Hướng dẫn làm việc với API', 'js_api.mp4', 1000, 4, 'completed'),
(6, 'Tối ưu hóa JavaScript', 'Cách tối ưu hóa mã JavaScript', 'optimize_js.mp4', 900, 5, 'not_completed'),

(7, 'Phát triển ứng dụng với Dart', 'Khám phá Dart trong Flutter', 'dart_development.mp4', 1200, 3, 'completed'),

(8, 'Quản lý trạng thái trong Flutter', 'Hướng dẫn quản lý trạng thái', 'flutter_state_management.mp4', 1300, 4, 'not_completed'),

(9, 'SQL cơ bản', 'Giới thiệu về SQL và cách sử dụng', 'basic_sql.mp4', 800, 3, 'completed'),
(9, 'Chạy câu lệnh SQL', 'Hướng dẫn chạy câu lệnh SQL', 'run_sql.mp4', 1000, 4, 'not_completed'),
(9, 'SQL Server', 'Giới thiệu về SQL Server', 'sql_server_intro.mp4', 900, 6, 'not_completed'),
(9, 'Tạo bảng trong SQL', 'Hướng dẫn tạo bảng trong SQL', 'create_table.mp4', 1000, 7, 'completed'),
(9, 'Chạy truy vấn SQL nâng cao', 'Hướng dẫn các truy vấn SQL nâng cao', 'advanced_sql_queries_2.mp4', 1200, 8, 'not_completed'),
(9, 'Tối ưu hóa SQL', 'Cách tối ưu hóa truy vấn SQL', 'optimize_sql.mp4', 900, 5, 'completed'),
(9, 'SQL cho phân tích dữ liệu', 'Hướng dẫn sử dụng SQL cho phân tích dữ liệu', 'sql_for_analysis.mp4', 1100, 3, 'not_completed'),
(9, 'Lập trình SQL nâng cao', 'Khám phá các kỹ thuật lập trình SQL nâng cao', 'advanced_sql_programming.mp4', 1300, 4, 'completed'),

(10, 'NoSQL cơ bản', 'Giới thiệu về NoSQL', 'nosql_basics.mp4', 1000, 3, 'completed'),
(10, 'So sánh NoSQL và SQL', 'So sánh hai hệ thống quản lý cơ sở dữ liệu', 'sql_nosql_comparison.mp4', 900, 4, 'not_completed'),
(10, 'Làm việc với MongoDB', 'Hướng dẫn làm việc với MongoDB', 'mongodb_intro.mp4', 1200, 5, 'completed'),
(10, 'Triển khai NoSQL trong dự án', 'Hướng dẫn triển khai NoSQL', 'nosql_deployment.mp4', 1500, 6, 'not_completed');

-- Thêm dữ liệu vào bảng Quizzes
INSERT INTO Quizzes (module_id, title, description)
VALUES 
(2, 'Kiểm tra kiến thức Java cơ bản', 'Bài kiểm tra kiến thức về Java sau khi hoàn thành module này'),
(3, 'Kiểm tra OOP trong Python', 'Bài kiểm tra kiến thức về lập trình hướng đối tượng trong Python'),
(5, 'Kiểm tra kiến thức HTML cơ bản', 'Bài kiểm tra kiến thức về HTML sau module này'),
(5, 'Kiểm tra kiến thức CSS cơ bản', 'Bài kiểm tra kiến thức về CSS sau module này'),
(6, 'Kiểm tra kiến thức JavaScript', 'Bài kiểm tra kiến thức về JavaScript sau module này'),
(8, 'Kiểm tra kiến thức Flutter', 'Bài kiểm tra kiến thức về Flutter sau module này'),
(10, 'Kiểm tra kiến thức SQL', 'Bài kiểm tra kiến thức về SQL sau module này'),
(10, 'Kiểm tra kiến thức NoSQL', 'Bài kiểm tra kiến thức về NoSQL sau module này');


-- Thêm dữ liệu vào bảng Questions
INSERT INTO Questions (quiz_id, question_text, is_required)
VALUES 
(1, 'Java là gì?', TRUE),
(1, 'Định nghĩa biến trong Java?', TRUE),
(2, 'Python được phát triển bởi ai?', TRUE),
(2, 'Cú pháp cơ bản để in ra màn hình trong Python là gì?', TRUE),
(3, 'HTML là viết tắt của gì?', TRUE),
(4, 'CSS được dùng để làm gì?', TRUE),
(5, 'JavaScript là gì?', TRUE),
(5, 'Cú pháp nào dùng để khai báo biến trong JavaScript?', TRUE),
(5, 'JavaScript được sử dụng chủ yếu cho mục đích gì?', TRUE),
(5, 'Sự kiện nào xảy ra khi người dùng nhấn chuột vào một phần tử trong HTML?', TRUE),
(5, 'Câu lệnh nào được sử dụng để in ra thông tin trên console?', TRUE),
(6, 'Flutter được phát triển bởi ai?', TRUE),
(6, 'Flutter sử dụng ngôn ngữ nào?', TRUE),
(7, 'Câu lệnh SELECT trong SQL được dùng để làm gì?', TRUE),
(8, 'SQL là viết tắt của gì?', TRUE);


-- Thêm dữ liệu vào bảng Choices
INSERT INTO Choices (question_id, choice_text, is_correct)
VALUES 
(1, 'Ngôn ngữ lập trình', TRUE),
(1, 'Hệ điều hành', FALSE),
(1, 'Ứng dụng', FALSE),
(1, 'Mạng máy tính', FALSE),

(2, 'Là một ô nhớ', TRUE),
(2, 'Là một hàm', FALSE),
(2, 'Là một lớp', FALSE),
(2, 'Không phải', FALSE),

(3, 'Guido van Rossum', TRUE),
(3, 'James Gosling', FALSE),
(3, 'Bjarne Stroustrup', FALSE),
(3, 'Linus Torvalds', FALSE),

(4, 'print()', TRUE),
(4, 'echo', FALSE),
(4, 'Console.WriteLine', FALSE),
(4, 'System.out.println', FALSE),

(5, 'Hyper Text Markup Language', TRUE),
(5, 'High Text Markup Language', FALSE),
(5, 'Hyper Transfer Markup Language', FALSE),
(5, 'Hyper Text Multi Language', FALSE),

(6, 'Định dạng trang', TRUE),
(6, 'Tạo nội dung', FALSE),
(6, 'Lập trình', FALSE),
(6, 'Kết nối cơ sở dữ liệu', FALSE),

(7, 'Ngôn ngữ lập trình', TRUE),
(7, 'Ngôn ngữ đánh dấu', FALSE),
(7, 'Hệ điều hành', FALSE),
(7, 'Ứng dụng di động', FALSE),

(8, 'let', TRUE),
(8, 'var', TRUE),
(8, 'const', TRUE),
(8, 'int', FALSE),

(9, 'Xây dựng trang web tương tác', TRUE),
(9, 'Quản lý cơ sở dữ liệu', FALSE),
(9, 'Lập trình hệ thống', FALSE),
(9, 'Phát triển ứng dụng di động', FALSE),

(10, 'onclick', TRUE),
(10, 'onmouseover', FALSE),
(10, 'onload', FALSE),
(10, 'onsubmit', FALSE),

(11, 'console.log()', TRUE),
(11, 'print()', FALSE),
(11, 'echo', FALSE),
(11, 'write()', FALSE),

(12, 'Google', TRUE),
(12, 'Facebook', FALSE),
(12, 'Apple', FALSE),
(12, 'Microsoft', FALSE),

(13, 'Dart', TRUE),
(13, 'Java', FALSE),
(13, 'JavaScript', FALSE),
(13, 'C#', FALSE),

(14, 'Lấy dữ liệu', TRUE),
(14, 'Chèn dữ liệu', FALSE),
(14, 'Cập nhật dữ liệu', FALSE),
(14, 'Xóa dữ liệu', FALSE),

(15, 'Structured Query Language', TRUE),
(15, 'Structured Question Language', FALSE),
(15, 'Simple Query Language', FALSE),
(15, 'Structured Quick Language', FALSE);

-- Thêm dữ liệu vào bảng Meeting_Schedules
INSERT INTO Meeting_Schedules (course_id, meeting_date)
VALUES 
(1, '2024-01-12 19:00:00'),
(2, '2024-02-07 18:00:00'),
(3, '2024-03-02 20:00:00'),
(4, '2024-04-12 19:00:00'),
(5, '2024-05-16 18:00:00'),
(6, '2024-06-20 19:00:00'),
(7, '2024-07-15 20:00:00'),
(8, '2024-08-10 19:00:00'),
(9, '2024-09-05 18:00:00'),
(10, '2024-10-01 20:00:00');

-- Thêm dữ liệu vào bảng Payments
INSERT INTO Payments (enrollment_id, amount, payment_date, status, transaction_id)
VALUES 
(1, 1000000, '2024-01-11 12:00:00', 'completed', 'TXN001'),
(3, 1500000, '2024-02-06 14:00:00', 'completed', 'TXN002'),
(4, 1200000, '2024-03-15 15:00:00', 'completed', 'TXN003'),
(5, 1800000, '2024-04-20 16:00:00', 'completed', 'TXN004'),
(6, 1300000, '2024-05-10 12:00:00', 'completed', 'TXN005'),
(7, 1600000, '2024-06-30 14:00:00', 'completed', 'TXN006'),
(8, 2000000, '2024-07-25 10:00:00', 'completed', 'TXN007'),
(9, 1700000, '2024-08-15 11:00:00', 'completed', 'TXN008'),
(10, 2200000, '2024-09-01 13:00:00', 'completed', 'TXN009'),
(2, 1500000, '2024-09-20 09:00:00', 'completed', 'TXN010');
