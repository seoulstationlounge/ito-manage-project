package com.itosideproject.modules.vacation;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Vacation {

    @Id @GeneratedValue
    private Long id;

    private String nickname;

    private String OverTimeName;

    private String userName;

    private LocalDate applicationDate;

    private Double vacationAm;

    private Double vacationPm;

    private Double vacationSum;

    private LocalDate startDateTime;

    private LocalDate endDateTime;

    private int restTime;

    private String remark;

    private String part;

    private String emergencyNumber;

    private String businessAgent;

    private LocalTime startOtTime;

    private LocalTime endOtTime;

}
