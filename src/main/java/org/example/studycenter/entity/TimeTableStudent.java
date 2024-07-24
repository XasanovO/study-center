package org.example.studycenter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.studycenter.entity.abs.BaseEntity;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class TimeTableStudent extends BaseEntity {
    @ManyToOne
    private TimeTable timeTable;
    @ManyToOne
    private Student student;
}
