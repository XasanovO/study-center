package org.example.studycenter.service;

import lombok.RequiredArgsConstructor;
import org.example.studycenter.dto.StudentReq;
import org.example.studycenter.entity.Student;
import org.example.studycenter.entity.TimeTable;
import org.example.studycenter.entity.TimeTableStudent;
import org.example.studycenter.repo.StudentRepository;
import org.example.studycenter.repo.TimeTableRepository;
import org.example.studycenter.repo.TimeTableStudentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final TimeTableRepository timeTableRepository;
    private final TimeTableStudentRepository timeTableStudentRepository;
    private final StudentRepository studentRepository;

    public void save(StudentReq studentReq, Integer timeTableId) {

        TimeTable timeTable = timeTableRepository.findById(timeTableId).get();

        Student student = Student.builder()
                .firstName(studentReq.firstName())
                .lastName(studentReq.lastName())
                .phone(studentReq.phone())
                .build();
        studentRepository.save(student);

        TimeTableStudent tableStudent = TimeTableStudent.builder()
                .student(student)
                .timeTable(timeTable)
                .build();
        timeTableStudentRepository.save(tableStudent);

    }
}
