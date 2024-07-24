package org.example.studycenter.controller;

import lombok.RequiredArgsConstructor;
import org.example.studycenter.entity.Lesson;
import org.example.studycenter.entity.StudentAttendance;
import org.example.studycenter.entity.TimeTable;
import org.example.studycenter.entity.TimeTableStudent;
import org.example.studycenter.entity.enums.TimeTableStatus;
import org.example.studycenter.repo.*;
import org.example.studycenter.service.LessonService;
import org.example.studycenter.service.StudentAttendanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GroupRepository groupRepository;
    private final TimeTableRepository timeTableRepository;
    private final TimeTableStudentRepository timeTableStudentRepository;
    private final StudentAttendanceService studentAttendanceService;
    private final LessonService lessonService;

    @GetMapping("/")
    public String home(
            Model model,
            @RequestParam(required = false) Integer groupId,
            @RequestParam(required = false) Integer timeTableId
    ) {
        model.addAttribute("groups", groupRepository.findAll());
        if (groupId != null) {
            model.addAttribute("currentGroup", groupRepository.findById(groupId).get());
            model.addAttribute("timeTables", timeTableRepository.findAllByGroupId(groupId));
        }
        if (timeTableId != null) {
            TimeTable currentTimeTable = timeTableRepository.findById(timeTableId).get();
            List<TimeTableStudent> currentTimeTableStudents = timeTableStudentRepository.findByTimeTableId(timeTableId);
            model.addAttribute("currentTimeTable", currentTimeTable);
            model.addAttribute("currentTimeTableStudents", currentTimeTableStudents);
            if (currentTimeTable.getStatus().equals(TimeTableStatus.IN_PROGRESS) || currentTimeTable.getStatus().equals(TimeTableStatus.COMPLETED)) {
                HashMap<TimeTableStudent, List<StudentAttendance>> eachStudentAttendance = studentAttendanceService.getEachStudentAttendance(currentTimeTableStudents);
                Lesson currentLesson = lessonService.getCurrentLessonByTimeTableId(timeTableId);
                if (currentLesson == null && currentTimeTable.getStatus() != TimeTableStatus.COMPLETED) {
                    currentTimeTable.setStatus(TimeTableStatus.COMPLETED);
                    timeTableRepository.save(currentTimeTable);
                } else {
                    model.addAttribute("currentLesson", currentLesson);
                }
                model.addAttribute("eachStudentAttendance", eachStudentAttendance);
            }
        }
        return "home";
    }
}
