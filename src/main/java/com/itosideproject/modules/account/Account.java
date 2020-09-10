package com.itosideproject.modules.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itosideproject.modules.tag.Tag;
import com.itosideproject.modules.zone.Zone;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id @GeneratedValue
    @JsonProperty
    private Long id;

    private String companyPosition;

    @JsonProperty
    private String userName;

    private String companyName;

    @Column(unique = true)
    @JsonProperty
    private String email;

    @Column(nullable=false, length=20, unique=true)
    @JsonProperty
    private String nickname;

    @JsonIgnore
    private String password;

    @Column(columnDefinition = "varchar(255) default 'ROLE_USER'")
    private String role;

    private String part;

    private String system;

    private String contactNumber;

    private String emergencyNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startWorkTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endWorkTime;

    private double annualVacation;

    private String bizGroup;

    private String engUserName;

    private String dlGroupName;

    private String cardNumber;

    @Column(columnDefinition = "char default 'A'")
    private char activeAccount;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGeneratedAt;

    private LocalDateTime joinedAt;

    private String bio;

    private String url;

    private String occupation;

    private String location;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private boolean studyCreatedByEmail;

    private boolean studyCreatedByWeb = true;

    private boolean studyEnrollmentResultByEmail;

    private boolean studyEnrollmentResultByWeb = true;

    private boolean studyUpdatedByEmail;

    private boolean studyUpdatedByWeb = true;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    private Set<Zone> zones = new HashSet<>();

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeSignUp() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1));
    }



}
