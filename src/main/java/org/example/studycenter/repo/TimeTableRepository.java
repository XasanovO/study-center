package org.example.studycenter.repo;

import org.example.studycenter.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {

    List<TimeTable> findAllByGroupId(int groupId);

    @Query("""
                SELECT tt FROM TimeTable tt
                WHERE tt.id = (
                    SELECT MAX(tt_inner.id) FROM TimeTable tt_inner
                    WHERE tt_inner.status = 'COMPLETED'
                    AND tt_inner.group.id = :groupId
                )
            """)
    Optional<TimeTable> findLastCompletedTimeTableByGroupId(@Param("groupId") Integer groupId);
}