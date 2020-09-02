package com.itosideproject.modules.weeklyreport.form;

import com.itosideproject.modules.account.Account;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ItoReportForm {

    private Long id;

    private Account writer;

    private String category;

    private String projectNature;

    private String refNo;

    private String task;

    private String detailContent;

    private String requestPart;

    private String status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate planedStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate planedEndDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate actualStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate actualEndDate;

    private Double mon;

    private Double tue;

    private Double wed;

    private Double thu;

    private Double fri;

    private Double sat;

    private Double sun;

    private Double sum;

    private Double prediction;

    private Double sumTotal;

}
