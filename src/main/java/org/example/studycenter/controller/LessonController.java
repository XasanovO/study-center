package org.example.studycenter.controller;

import lombok.RequiredArgsConstructor;
import org.example.studycenter.entity.TimeTable;
import org.example.studycenter.repo.TimeTableRepository;
import org.example.studycenter.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final TimeTableRepository timeTableRepository;
    private final LessonService lessonService;


    @PostMapping("/start")
    public String startLesson(@RequestParam Integer lessonId, @RequestParam Integer timeTableId) {

        lessonService.startLesson(lessonId);

        TimeTable timeTable = timeTableRepository.findById(timeTableId).get();

        return "redirect:/?groupId=" + timeTable.getGroup().getId() + "&timeTableId=" + timeTable.getId();
    }

    @PostMapping("/complete")
    public String completeLesson(@RequestParam Integer lessonId, @RequestParam Integer timeTableId) {

        lessonService.completeLesson(lessonId);

        TimeTable timeTable = timeTableRepository.findById(timeTableId).get();

        return "redirect:/?groupId=" + timeTable.getGroup().getId() + "&timeTableId=" + timeTable.getId();
    }

}
