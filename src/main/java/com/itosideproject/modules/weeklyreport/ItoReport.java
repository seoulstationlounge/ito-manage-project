package com.itosideproject.modules.weeklyreport;

import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.helpdesk.AbstractEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class ItoReport extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_itoreport_writer"))
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
