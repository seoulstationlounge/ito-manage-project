package com.itosideproject.modules.company;

import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.account.AccountRepository;
import com.itosideproject.modules.account.AccountService;
import com.itosideproject.modules.account.CurrentAccount;
import com.itosideproject.modules.company.form.CompanyForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

//    @InitBinder("signUpForm")
//    public void initBinder(WebDataBinder webDataBinder) {
//        webDataBinder.addValidators(signUpFormValidator);
//    }

    @GetMapping("/companies")
    public String viewCompanys(@CurrentAccount Account account, Model model) {

        if (account != null) {
            Account accountLoaded = accountRepository.findAccountWithTagsAndZonesById(account.getId());
            model.addAttribute(accountLoaded);

            List<Company> companyList = companyRepository.findAll();
            model.addAttribute("companyList", companyList);
            return "company/companies";
        }

        return "redirect:/";
    }

    @GetMapping("/company")
    public String CompanyForm(Model model) {
        model.addAttribute(new CompanyForm());
        return "company/companyForm";
    }

    @PostMapping("/company")
    public String signUpSubmit(@Valid CompanyForm companyForm, Errors errors) {
        if (errors.hasErrors()) {
            return "/companies";
        }

        Company company = companyService.processNewCompany(companyForm);
//        accountService.login(account);
        return "redirect:/companies";
    }

    @PostMapping("/company/update")
    public String updateCompany(@CurrentAccount Account account, @Valid CompanyForm companyForm, Errors errors,
                                Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "/companies";
        }

        companyService.updateCompany(companyForm);
        attributes.addFlashAttribute("message", "회사정보를 수정했습니다.");
        return "redirect:/companies";
    }

    @GetMapping("/update-company/{id}")
    public String updateCompany(@PathVariable Long id, Model model, @CurrentAccount Account account) {
        model.addAttribute(account);

        Company companyLoaded = companyRepository.findCompanyById(id);
        model.addAttribute("companyForm", companyLoaded);
        return "company/companyUpdateForm";
    }

    @GetMapping("/company/{id}")
    public String viewCompany(@PathVariable Long id, Model model, Account account) {
        Company companyToView = companyRepository.findCompanyById(id);
        model.addAttribute(companyToView);
        return "company/companies";
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity deleteCompany(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회사가 없습니다."));
        model.addAttribute(company);

        companyService.deleteCompany(id);
        attributes.addFlashAttribute("message", "회사를 삭제했습니다.");
        return ResponseEntity.ok().build();
    }
}
