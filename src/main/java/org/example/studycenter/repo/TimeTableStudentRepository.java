package org.example.studycenter.repo;

import org.example.studycenter.entity.TimeTableStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeTableStudentRepository extends JpaRepository<TimeTableStudent, Integer> {
    List<TimeTableStudent> findByTimeTableId(Integer timeTableId);

    @Query("""
            SELECT tts FROM TimeTableStudent tts 
            JOIN tts.timeTable tt 
            JOIN tt.group g 
            WHERE tt.id = ( 
                SELECT MAX(tt_inner.id) FROM TimeTable tt_inner 
                WHERE tt_inner.status = 'COMPLETED' 
                AND tt_inner.group.id = :groupId 
            )
            """)
    List<TimeTableStudent> findLastCompletedTimeTableTimeTableStudents(@Param("groupId") Integer groupId);
}