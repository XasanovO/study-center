package org.example.studycenter.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.studycenter.entity.Group;
import org.example.studycenter.entity.Student;
import org.example.studycenter.entity.TimeTable;
import org.example.studycenter.entity.TimeTableStudent;
import org.example.studycenter.entity.enums.LessonPlan;
import org.example.studycenter.entity.enums.TimeTableStatus;
import org.example.studycenter.repo.GroupRepository;
import org.example.studycenter.repo.TimeTableRepository;
import org.example.studycenter.repo.TimeTableStudentRepository;
import org.example.studycenter.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timeTable")
public class TimeTableController {

    private final TimeTableRepository timeTableRepository;
    private final GroupRepository groupRepository;
    private final LessonService lessonService;
    private final TimeTableStudentRepository timeTableStudentRepository;

    @GetMapping("/add")
    public String sendToAddPage(
            @RequestParam Integer groupId,
            Model model,
            @RequestParam(required = false) Boolean allBtn,
            @RequestParam(required = false) Integer removeTtsId,
            @RequestParam(required = false) String clear,
            HttpSession session
    ) {
        Optional<TimeTable> optionalTimeTable = timeTableRepository.findLastCompletedTimeTableByGroupId(groupId);
        List<TimeTableStudent> timeTableStudents;
        List<TimeTableStudent> newTimeTableStudents = new ArrayList<>();

        if (optionalTimeTable.isPresent()) {
            TimeTable timeTable = optionalTimeTable.get();
            model.addAttribute("lastCompletedTimeTable", timeTable);
            timeTableStudents = timeTableStudentRepository.findByTimeTableId(timeTable.getId());
            if (Boolean.TRUE.equals(allBtn)) {
                if (clear != null) {
                    session.setAttribute("removedStudentIds", new ArrayList<>());
                }
                model.addAttribute("clear", "clear");
                newTimeTableStudents = new ArrayList<>(timeTableStudentRepository.findByTimeTableId(timeTable.getId()));

                List<Integer> removedStudentIds = (List<Integer>) session.getAttribute("removedStudentIds");
                if (removedStudentIds == null) {
                    removedStudentIds = new ArrayList<>();
                }

                if (removeTtsId != null) {
                    removedStudentIds.add(removeTtsId);
                    session.setAttribute("removedStudentIds", removedStudentIds);
                }


                List<Integer> finalRemovedStudentIds = removedStudentIds;
                newTimeTableStudents.removeIf(item -> finalRemovedStudentIds.contains(item.getId()));
                timeTableStudents.removeIf(item -> !finalRemovedStudentIds.contains(item.getId()));
            }
            model.addAttribute("timeTableStudents", timeTableStudents);
            model.addAttribute("newTimeTableStudents", newTimeTableStudents);
        }
        model.addAttribute("groupId", groupId);
        return "addTimeTable";
    }

    @PostMapping("/add")
    public String addTimeTable(@RequestParam Integer groupId,
                               @RequestParam String name,
                               @RequestParam Integer price,
                               @RequestParam LessonPlan lessonPlan,
                               @RequestParam(required = false) List<Integer> newTtsId
    ) {
        Group group = groupRepository.findById(groupId).get();
        TimeTable timeTable = TimeTable.builder()
                .name(name)
                .group(group)
                .lessonPlan(lessonPlan)
                .status(TimeTableStatus.CREATED)
                .price(price)
                .build();
        timeTableRepository.save(timeTable);

        if (newTtsId != null) {
            for (Integer id : newTtsId) {
                Student student = timeTableStudentRepository.findById(id).get().getStudent();
                timeTableStudentRepository.save(
                        new TimeTableStudent(timeTable, student)
                );
            }
        }
        return "redirect:/?groupId=" + timeTable.getGroup().getId();
    }

    @PostMapping("/start")
    public String startTimeTable(@RequestParam Integer timeTableId) {
        TimeTable timeTable = timeTableRepository.findById(timeTableId).get();
        if (timeTable.getStatus().equals(TimeTableStatus.CREATED)) {

            lessonService.generateLessonsAndSave(timeTable);
            timeTable.setStatus(TimeTableStatus.IN_PROGRESS);
            timeTableRepository.save(timeTable);

        }
        return "redirect:/?groupId=" + timeTable.getGroup().getId() + "&timeTableId=" + timeTableId;
    }
}
