package org.example.studycenter.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.studycenter.entity.abs.BaseEntity;
import org.example.studycenter.entity.enums.LessonStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Lesson extends BaseEntity {
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private LessonStatus status;
}
