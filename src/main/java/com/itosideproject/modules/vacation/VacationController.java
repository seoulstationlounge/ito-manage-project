package com.itosideproject.modules.vacation;

import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.account.AccountRepository;
import com.itosideproject.modules.account.AccountService;
import com.itosideproject.modules.account.CurrentAccount;
import com.itosideproject.modules.company.Company;
import com.itosideproject.modules.company.CompanyRepository;
import com.itosideproject.modules.vacation.form.VacationForm;
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
public class VacationController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;
    private final VacationRepository vacationRepository;
    private final VacationService vacationService;

//    @InitBinder("signUpForm")
//    public void initBinder(WebDataBinder webDataBinder) {
//        webDataBinder.addValidators(signUpFormValidator);
//    }

    @GetMapping("/vacation")
    public String viewItoVacation(@CurrentAccount Account account, Model model) {
        if (account != null) {
            model.addAttribute(account);

            List<Vacation> vacationList;

            if ( account.getRole().equals("ROLE_ADMIN") ) {
                vacationList = vacationRepository.findAll();
            } else {
                vacationList = vacationRepository.findVacationsById(account.getId());
            }

            model.addAttribute("vacationList", vacationList);

            return "vacation/vacation-list";
        }

        return "redirect:/";
    }

    @GetMapping("/vacation-statistics")
    public String viewVacationStatistics(@CurrentAccount Account account, Model model) {
        if (account != null) {
            Account accountLoaded = accountRepository.findAccountWithTagsAndZonesById(account.getId());
            model.addAttribute(accountLoaded);

            List<VacationStatistics> vacationStatistics = vacationService.calculTotalVacation(account);
            model.addAttribute("vacationStatistics", vacationStatistics);

            return "vacation/vacation-statistics";
        }

        return "redirect:/";
    }

    @GetMapping("/vacationApply")
    public String vacationApply(@CurrentAccount Account account, Model model) {
        if (account != null) {
            Account accountLoaded = accountRepository.findAccountWithTagsAndZonesById(account.getId());
            model.addAttribute(accountLoaded);

            Company company = companyRepository.findByCompanyName(accountLoaded.getCompanyName());
            model.addAttribute("company", company);

            model.addAttribute(new VacationForm());

            return "vacation/apply-vacation";
        }

        return "vacation";
    }

    @GetMapping("/otApply")
    public String otApply(@CurrentAccount Account account, Model model) {
        if (account != null) {
            Account accountLoaded = accountRepository.findAccountWithTagsAndZonesById(account.getId());
            model.addAttribute(accountLoaded);

            Company company = companyRepository.findByCompanyName(accountLoaded.getCompanyName());
            model.addAttribute("company", company);

            model.addAttribute(new VacationForm());

            return "vacation/apply-ot";
        }

        return "vacation";
    }

    @PostMapping("/vacationApply")
    public String newVacationApply(@CurrentAccount Account account, @Valid VacationForm vacationForm, Errors errors) {
        if (errors.hasErrors()) {
            return "/vacation";
        }

        Vacation vacation = vacationService.processNewVacation(account, vacationForm);
//        accountService.login(account);
        return "redirect:/vacation";
    }

    @PostMapping("/otApply")
    @ResponseBody
    public ResponseEntity newOtApply(@CurrentAccount Account account, @RequestBody VacationForm vacationForm, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            return ResponseEntity.notFound().build();
        }

        vacationService.processNewOt(account, vacationForm);
//        accountService.login(account);
        attributes.addFlashAttribute("message", "근태를 등록했습니다.");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/vacation/{id}")
    public ResponseEntity deleteVacation(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 휴가가 없습니다."));
        model.addAttribute(vacation);

        vacationService.deleteVacation(id);
        attributes.addFlashAttribute("message", "근태를 삭제했습니다.");
        return ResponseEntity.ok().build();
    }
}
