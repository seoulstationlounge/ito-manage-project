package com.itosideproject.modules.weeklyreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.helpdesk.AbstractEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class ProjectReport extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_projectreport_writer"))
    private Account writer;

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
}
