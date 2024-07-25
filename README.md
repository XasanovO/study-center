

# study-center

This repository contains a study center management application developed as part of my learning process. 
The application is designed to help study centers manage students' monthly schedules, attendance, and class timetables.
Despite the project being incomplete, it provided significant hands-on experience with Spring Boot, Thymeleaf, and PostgreSQL.

## Overview

The study center management system allows administrators to create and manage timetables, track student attendance, and handle scheduling for various classes and lessons.

## Key Features

- **Timetable Creation**: Administrators can create timetables and add students to them.
- **Lesson Management**: Start and stop lessons, mark lessons as odd or even days, and track student attendance.
- **Attendance Tracking**: Record attendance for students during started lessons. Attendance for stopped lessons cannot be modified.
- **Timetable History**: View past timetables and carry forward students from completed timetables to new ones.
- **Student Management**: Add or remove students from timetables based on their attendance and performance.

## Technologies Used

- **Backend**: Spring Boot
- **Frontend**: Thymeleaf
- **Database**: PostgreSQL (including native queries for complex operations)

## How It Works

1. **Timetable Creation**:
   - Create a new timetable and add students.
   - Set lesson days as either odd or even.

2. **Lesson Management**:
   - Start and stop lessons.
   - Track attendance for each lesson.

3. **Attendance Tracking**:
   - Record attendance for students during lessons that are in progress.
   - Modify attendance only for lessons that are in the started status. Attendance for stopped lessons is locked and cannot be changed.

4. **Timetable History**:
   - Access past timetables.
   - Include students from past timetables in new timetables if desired.

## Learning Outcomes

This project provided substantial practical experience with Spring Boot, Thymeleaf, and PostgreSQL. 
I gained valuable insights into creating and managing complex data relationships using native queries and learned how to effectively implement 
attendance tracking and timetable management systems for educational institutions.
