package org.example.studycenter.controller;

import lombok.RequiredArgsConstructor;
import org.example.studycenter.dto.StudentReq;
import org.example.studycenter.entity.TimeTable;
import org.example.studycenter.repo.TimeTableRepository;
import org.example.studycenter.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final TimeTableRepository timeTableRepository;
    private final StudentService studentService;

    @GetMapping("/add")
    public String sendToAddStudentPage(Model model, @RequestParam Integer timeTableId) {
        model.addAttribute("timeTableId", timeTableId);
        return "addStudent";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute StudentReq studentReq, @RequestParam Integer timeTableId) {

        studentService.save(studentReq, timeTableId);

        TimeTable timeTable = timeTableRepository.findById(timeTableId).get();

        return "redirect:/?groupId=" + timeTable.getGroup().getId() + "&timeTableId=" + timeTable.getId();
    }
}
