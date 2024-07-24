package org.example.studycenter.repo;

import org.example.studycenter.entity.Lesson;
import org.example.studycenter.entity.StudentAttendance;
import org.example.studycenter.entity.TimeTable;
import org.example.studycenter.entity.TimeTableStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Integer> {
    public List<StudentAttendance> findAllByTimeTableStudentOrderByLessonDateAsc(TimeTableStudent timeTableStudent);


}