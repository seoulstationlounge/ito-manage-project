package com.itosideproject.modules.company;

import com.itosideproject.modules.company.form.CompanyForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService  {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public Company getCompany(String companyName) {
        Company company = companyRepository.findByCompanyName(companyName);
        if (company == null) {
            throw new IllegalArgumentException(companyName + "에 해당하는 사용자가 없습니다.");
        }
        return company;
    }

    public Company processNewCompany(CompanyForm companyForm) {
        Company newCompany = saveNewCompany(companyForm);
        return newCompany;
    }

    private Company saveNewCompany(@Valid CompanyForm companyForm) {
        Company company = modelMapper.map(companyForm, Company.class);
        return companyRepository.save(company);
    }

    public void updateCompany(CompanyForm companyForm) {
        Company company = companyRepository.findById(companyForm.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. companyname=" + companyForm.getCompanyName()));

        modelMapper.map(companyForm, company);
        companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

}
