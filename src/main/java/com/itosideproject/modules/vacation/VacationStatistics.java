package com.itosideproject.modules.vacation;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class VacationStatistics {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String nickname;

    private String part;

    private String system;

    private String userName;

    private LocalDate startWorkTime;

    private LocalDate endWorkTime;

    private Double annualOccur;

    private Double alternativeOccur;

    private Double occurSum;

    private Double congratulationUse;

    private Double annualUse;

    private Double alternativeUse;

    private Double UseSum;

    private Double vacationTotal;
}
