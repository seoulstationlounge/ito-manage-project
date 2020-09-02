package com.itosideproject.modules.weeklyreport.form;

import com.itosideproject.modules.account.Account;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class ProjectReportForm {

    private Long id;

    private String thisWeekContext;

    private String thisWeekPerfection;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate thisWeekStartDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate thisWeekEndDateTime;

    private String nextWeekContext;

    private String nextWeekPerfection;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate nextWeekStartDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate nextWeekEndDateTime;

    private Account writer;

}
