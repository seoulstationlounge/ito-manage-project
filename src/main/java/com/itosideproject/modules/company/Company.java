package com.itosideproject.modules.company;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Company {

    @Id @GeneratedValue
    private Long id;

    private String nickname;

    private String companyName;

    private String manager;

    private String salesYn;

    private String contactNumber;

    private String email;

    private String fieldManager;
}
