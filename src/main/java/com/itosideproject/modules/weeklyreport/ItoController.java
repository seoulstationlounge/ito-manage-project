package com.itosideproject.modules.weeklyreport;

import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.account.AccountRepository;
import com.itosideproject.modules.account.AccountService;
import com.itosideproject.modules.account.CurrentAccount;
import com.itosideproject.modules.company.CompanyRepository;
import com.itosideproject.modules.vacation.VacationRepository;
import com.itosideproject.modules.vacation.VacationService;
import com.itosideproject.modules.weeklyreport.form.ItoReportForm;
import com.itosideproject.modules.weeklyreport.form.ProjectReportForm;
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
public class ItoController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;
    private final VacationRepository vacationRepository;
    private final VacationService vacationService;
    private final ItoReportService itoReportService;
    private final ItoReportRepository itoReportRepository;

//    @InitBinder("signUpForm")
//    public void initBinder(WebDataBinder webDataBinder) {
//        webDataBinder.addValidators(signUpFormValidator);
//    }

    @GetMapping("/ito-report-list")
    public String viewMonthlyReport(@CurrentAccount Account account, Model model) {
        if (account != null) {
            Account accountLoaded = accountRepository.findAccountWithTagsAndZonesById(account.getId());
            model.addAttribute(accountLoaded);

            List<ItoReport> itoReportList = itoReportRepository.findAll();
            model.addAttribute("itoReportList", itoReportList);
            model.addAttribute(new ItoReportForm());

            return "weeklyreport/ito-list";
        }

        return "redirect:/";
    }

    @GetMapping("/ito-report")
    public String WeeklyReportForm(Model model) {
        model.addAttribute(new ItoReportForm());
        return "itoreport/ito-report";
    }

    @GetMapping("/ito-report-update/{id}")
    public String updateMonthlyReportFrom(@PathVariable Long id, Model model, @CurrentAccount Account account) {
        model.addAttribute(account);

        ItoReport itoReportLoaded = itoReportRepository.findItoReportById(id);
        model.addAttribute("monthlyReportForm", itoReportLoaded);
        return "weeklyreport/ito-report";
    }

    @PostMapping("/ito-report")
    public String inputWeeklyReport(@Valid ItoReportForm itoReportForm, Errors errors, @CurrentAccount Account account) {
        if (errors.hasErrors()) {
            return "/weeklyreport/ito-list";
        }

        itoReportForm.setWriter(account);

        ItoReport itoReport = itoReportService.processNewItoReport(itoReportForm);
        return "redirect:/ito-report-list";
    }

    @PostMapping("/ito-report/update")
    public String updateMonthlyReport(@CurrentAccount Account account, @Valid ItoReportForm itoReportForm, Errors errors,
                                     Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "/weeklyreport/ito-list";
        }

        itoReportService.updateMonthlyReport(itoReportForm);
        attributes.addFlashAttribute("message", "보고서 정보를 수정했습니다.");
        return "redirect:/ito-report-list";
    }

    @DeleteMapping("/ito-report/{id}")
    public ResponseEntity deleteCompany(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        ItoReport itoReport = itoReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 보고서가 없습니다."));
        model.addAttribute(itoReport);

        itoReportService.deleteWeekly(id);
        attributes.addFlashAttribute("message", "보고서를 삭제했습니다.");
        return ResponseEntity.ok().build();
    }

}
