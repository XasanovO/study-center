package org.example.studycenter.service;

import lombok.RequiredArgsConstructor;
import org.example.studycenter.entity.Lesson;
import org.example.studycenter.entity.TimeTable;
import org.example.studycenter.entity.enums.LessonPlan;
import org.example.studycenter.entity.enums.LessonStatus;
import org.example.studycenter.repo.LessonRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final StudentAttendanceService studentAttendanceService;

    public void generateLessonsAndSave(TimeTable timeTable) {

        LocalDate startDate = LocalDate.now();
        List<Lesson> lessons = new ArrayList<>();

        List<LocalDate> lessonDays;

        if (timeTable.getLessonPlan().equals(LessonPlan.EVEN_DAYS)) {
            lessonDays = getNextEvenDays(startDate, 12);
        } else {
            lessonDays = getNextOddDays(startDate, 12);
        }

        lessonDays.forEach(lessonDay -> {
            lessons.add(Lesson.builder().date(lessonDay).status(LessonStatus.CREATED).build());
        });

        lessonRepository.saveAll(lessons);

        studentAttendanceService.generateStudentAttendanceEachLessonAndSave(timeTable, lessons);
    }

    private List<LocalDate> getNextEvenDays(LocalDate startDate, int count) {
        List<LocalDate> evenDays = new ArrayList<>();
        LocalDate date = startDate;

        while (evenDays.size() < count) {
            date = date.plusDays(1);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.THURSDAY || dayOfWeek == DayOfWeek.SATURDAY) {
                evenDays.add(date);
            }
        }

        return evenDays;
    }

    private List<LocalDate> getNextOddDays(LocalDate startDate, int count) {
        List<LocalDate> oddDays = new ArrayList<>();
        LocalDate date = startDate;

        while (oddDays.size() < count) {
            date = date.plusDays(1);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.FRIDAY) {
                oddDays.add(date);
            }
        }

        return oddDays;
    }


    public Lesson getCurrentLessonByTimeTableId(Integer timeTableId) {
        return lessonRepository.findCurrentLessonByTimeTableId(timeTableId)
                .orElseGet(() -> lessonRepository.findFirstCreatedLessonByTimeTableId(timeTableId)
                        .orElse(null));
    }

    public void completeLesson(Integer lessonId) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();
            if (lesson.getStatus().equals(LessonStatus.IN_PROGRESS)) {
                lesson.setStatus(LessonStatus.COMPLETED);
                lessonRepository.save(lesson);
            }
        }
    }

    public void startLesson(Integer lessonId) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();
            if (lesson.getStatus().equals(LessonStatus.CREATED)) {
                lesson.setStatus(LessonStatus.IN_PROGRESS);
                lessonRepository.save(lesson);
            }
        }
    }
}
