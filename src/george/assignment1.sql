/* author: George Young
 * Date Created: September 16th 2016
 * Last Modified: September 16th 2016
 *
 */


USE ejd;

drop table if exists Course, Student, CourseStudent;

create table Course
(
    id varchar(9) not null primary key,
    title varchar(50) not null,
    credit double not null
);

create table Student
(
    id varchar(9) not null Primary key,
    firstName varchar(25) not null,
    lastName varchar(25) not null
);

create table CourseStudent
(
    courseId varchar(9) not null references Course(id),
    studentId varchar(9) not null references Student(id),
    primary key (courseId, studentId)
);

insert into Course (id, title, credit) values
('PROG10000','Java Programming', 6.0 ),
('DBAS20000','Database Design', 3.0 ),
('MATH30000','Linear Algebra', 2.0 );

insert into Student (id, firstName, lastName) values
('000000001', 'Alex', 'Abby'),
('000000002', 'Bob', 'Brown'),
('000000003', 'Caroline', 'Cabot'),
('000000004', 'Damian', 'David'),
('000000005', 'Ed', 'Edison'),
('000000006', 'Fiona', 'Fox');

insert into CourseStudent (courseId, studentId) values
('PROG10000', '000000001'),
('PROG10000', '000000003'),
('PROG10000', '000000005'),
('DBAS20000', '000000002'),
('DBAS20000', '000000004'),
('DBAS20000', '000000006'),
('MATH30000', '000000001'),
('MATH30000', '000000004'),
('MATH30000', '000000005'),
('MATH30000', '000000006');
