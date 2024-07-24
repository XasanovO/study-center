package org.example.studycenter.controller;

import lombok.RequiredArgsConstructor;
import org.example.studycenter.entity.Lesson;
import org.example.studycenter.entity.StudentAttendance;
import org.example.studycenter.entity.TimeTable;
import org.example.studycenter.entity.enums.LessonStatus;
import org.example.studycenter.repo.LessonRepository;
import org.example.studycenter.repo.StudentAttendanceRepository;
import org.example.studycenter.repo.TimeTableRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequiredArgsConstructor
public class StudentAttendanceController {

    private final StudentAttendanceRepository studentAttendanceRepository;
    private final TimeTableRepository timeTableRepository;
    private final LessonRepository lessonRepository;

    @PostMapping("/studentAttendance")
    public String attendance(
            @RequestParam Integer studentAttendanceId,
            @RequestParam Integer timeTableId,
            @RequestParam Integer currentLessonId
    ) {
        Lesson lesson = lessonRepository.findById(currentLessonId).get();
        TimeTable timeTable = timeTableRepository.findById(timeTableId).get();
        StudentAttendance studentAttendance = studentAttendanceRepository.findById(studentAttendanceId).get();
        if (studentAttendance.getLesson().equals(lesson) && lesson.getStatus().equals(LessonStatus.IN_PROGRESS)) {
            studentAttendance.setHasInLesson(!studentAttendance.isHasInLesson());
            studentAttendanceRepository.save(studentAttendance);
        }
        return "redirect:/?groupId=" + timeTable.getGroup().getId() + "&timeTableId=" + timeTable.getId();
    }
}
