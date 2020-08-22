package com.itosideproject.modules.company.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CompanyForm {

    private Long id;

    private String nickname;

    @Email
    @NotBlank
    private String email;

    private String manager;

    private String salesYn;

    private String contactNumber;

    private String fieldManager;

    private String companyName;
}
