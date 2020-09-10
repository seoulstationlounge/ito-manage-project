package com.itosideproject.modules.account.form;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class SignUpForm {

    private Long id;

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String nickname;

    @Email
    @NotBlank
    private String email;

    // 업데이트를 하기위해 NotBlank 없애기
    @Length(min = 8, max = 50)
    private String password;

    private String companyName;

    private String part;

    private String system;

    private String contactNumber;

    private String userName;

    private String role;

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

    private char activeAccount;

}
