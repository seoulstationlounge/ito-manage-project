package com.itosideproject.modules.vacation.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class VacationForm {
    private Long id;

    private String nickname;

    private String userName;

    private String OverTimeName;

    private LocalDate applicationDate;

    private Double vacationAm;

    private Double vacationPm;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startOtTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endOtTime;

    private Double vacationSum;

    private String remark;

    private String part;

    private String businessAgent;

    private int restTime;

    private String emergencyNumber;

}
