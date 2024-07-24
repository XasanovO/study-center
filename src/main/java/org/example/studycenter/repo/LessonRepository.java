package org.example.studycenter.repo;

import org.example.studycenter.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("SELECT l FROM Lesson l " +
            "JOIN StudentAttendance sa ON sa.lesson.id = l.id " +
            "JOIN TimeTableStudent tts ON tts.id = sa.timeTableStudent.id " +
            "WHERE tts.timeTable.id = :timeTableId " +
            "AND l.status = 'IN_PROGRESS'")
    Optional<Lesson> findCurrentLessonByTimeTableId(@Param("timeTableId") Integer timeTableId);

    @Query("SELECT l FROM Lesson l " +
            "JOIN StudentAttendance sa ON sa.lesson.id = l.id " +
            "JOIN TimeTableStudent tts ON tts.id = sa.timeTableStudent.id " +
            "WHERE tts.timeTable.id = :timeTableId " +
            "AND l.status = 'CREATED' " +
            "ORDER BY l.date ASC limit 1")
    Optional<Lesson> findFirstCreatedLessonByTimeTableId(@Param("timeTableId") Integer timeTableId);
}