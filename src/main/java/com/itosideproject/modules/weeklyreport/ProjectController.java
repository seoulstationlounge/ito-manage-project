package com.itosideproject.modules.weeklyreport;

import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.account.AccountRepository;
import com.itosideproject.modules.account.AccountService;
import com.itosideproject.modules.account.CurrentAccount;
import com.itosideproject.modules.company.CompanyRepository;
import com.itosideproject.modules.vacation.VacationRepository;
import com.itosideproject.modules.vacation.VacationService;
import com.itosideproject.modules.weeklyreport.form.ProjectReportForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;
    private final VacationRepository vacationRepository;
    private final VacationService vacationService;
    private final ProjectReportService projectReportService;
    private final ProjectReportRepository projectReportRepository;

//    @InitBinder("signUpForm")
//    public void initBinder(WebDataBinder webDataBinder) {
//        webDataBinder.addValidators(signUpFormValidator);
//    }

    @GetMapping("/project-report-list")
    public String viewProjectReport(@CurrentAccount Account account, Model model) {
        if (account != null) {
            Account accountLoaded = accountRepository.findAccountWithTagsAndZonesById(account.getId());
            model.addAttribute(accountLoaded);

            List<ProjectReport> projectReportList = projectReportRepository.findAll();
            model.addAttribute("projectReportList", projectReportList);
            model.addAttribute(new ProjectReportForm());

            return "weeklyreport/project-list";
        }

        return "redirect:/";
    }

    @GetMapping("/project-report")
    public String ProejctReportForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);

        model.addAttribute(new ProjectReportForm());
        return "weeklyreport/project-report";
    }

    @GetMapping("/project-report-update/{id}")
    public String updateProjectReportFrom(@PathVariable Long id, Model model, @CurrentAccount Account account) {
        model.addAttribute(account);

        ProjectReport projectReportLoaded = projectReportRepository.findProjectReportById(id);
        model.addAttribute("projectReportForm", projectReportLoaded);
        return "weeklyreport/project-report-update";
    }

    @PostMapping("/project-report")
    public String inputProjectReport(@Valid ProjectReportForm projectReportForm, Errors errors, @CurrentAccount Account account) {
        if (errors.hasErrors()) {
            return "/weeklyreport/project-list";
        }

        projectReportForm.setWriter(account);

        ProjectReport projectReport = projectReportService.processNewProjectReport(projectReportForm);
        return "redirect:/project-report-list";
    }

    @PostMapping("/project-report/update")
    public String updateProjectReport(@CurrentAccount Account account, @Valid ProjectReportForm projectReportForm, Errors errors,
                                     Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "/weeklyreport/project-list";
        }

        projectReportService.updateProjectReport(projectReportForm);
        attributes.addFlashAttribute("message", "보고서 정보를 수정했습니다.");
        return "redirect:/project-report-list";
    }

    @DeleteMapping("/project-report/{id}")
    public ResponseEntity deleteCompany(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        ProjectReport projectReport = projectReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 보고서가 없습니다."));
        model.addAttribute(projectReport);

        projectReportService.deleteCompany(id);
        attributes.addFlashAttribute("message", "보고서를 삭제했습니다.");
        return ResponseEntity.ok().build();
    }

}
