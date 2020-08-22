package com.itosideproject.modules.vacation;

import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.account.AccountRepository;
import com.itosideproject.modules.vacation.form.VacationForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VacationService {

    private final AccountRepository accountRepository;
    private final VacationRepository vacationRepository;
    private final ModelMapper modelMapper;

    static final String congratulationVacation = "경조";
    static final String annualVcation = "연차";
    static final String annualVcationAm = "반차(오전)";
    static final String annualVcationPm = "반차(오후)";

    static final String otAlternative = "OT대체";
    static final String alternativeVacation = "대체";



    public Vacation processNewVacation(Account account, VacationForm vacationForm) {
        vacationForm.setApplicationDate(LocalDate.now());
        vacationForm.setPart(account.getPart());
        vacationForm.setUserName(account.getUserName());
        vacationForm.setBusinessAgent(vacationForm.getBusinessAgent());
        vacationForm.setNickname(account.getNickname());

        if(vacationForm.getOverTimeName().equals("반차(오전)")) {
            vacationForm.setVacationAm(0.5);
        }

        if(vacationForm.getOverTimeName().equals("반차(오후)")) {
            vacationForm.setVacationPm(0.5);
        }

        Vacation newVacation = saveNewVacation(vacationForm);
        return newVacation;
    }

    private Vacation saveNewVacation(@Valid VacationForm vacationForm) {
        Vacation vacation = modelMapper.map(vacationForm, Vacation.class);
        return vacationRepository.save(vacation);
    }

    public void processNewOt(Account account, VacationForm vacationForm) {
        vacationForm.setApplicationDate(LocalDate.now());
        vacationForm.setPart(account.getPart());
        vacationForm.setOverTimeName("OT대체");
        vacationForm.setUserName(account.getUserName());
        vacationForm.setNickname(account.getNickname());

        saveNewOt(vacationForm);
    }

    private Vacation saveNewOt(@Valid VacationForm vacationForm) {
        Vacation vacation = modelMapper.map(vacationForm, Vacation.class);
        return vacationRepository.save(vacation);
    }

    public void deleteVacation(Long id) {
        vacationRepository.deleteById(id);
    }

    public List<VacationStatistics> calculTotalVacation(Account account) {
        List<VacationStatistics> vsList = new ArrayList<>();

        List<Vacation> vacationList = vacationRepository.findAll();

        List<String> nicknameList = vacationList.stream()
                                            .map(Vacation::getNickname)
                                            .filter(val -> val != null)
                                            .distinct()
                                            .collect(Collectors.toList());

        for( String name : nicknameList ) {
            Account statisticsAccount = accountRepository.findByNickname(name);
            VacationStatistics vacationStatistics = new VacationStatistics();

            vacationStatistics.setNickname(statisticsAccount.getNickname());
            vacationStatistics.setAnnualOccur(statisticsAccount.getAnnualVacation());

            vacationStatistics.setPart(statisticsAccount.getPart());
            vacationStatistics.setSystem(statisticsAccount.getSystem());
            vacationStatistics.setUserName(statisticsAccount.getUserName());
            vacationStatistics.setStartWorkTime(statisticsAccount.getStartWorkTime());
            vacationStatistics.setEndWorkTime(statisticsAccount.getEndWorkTime());

            vacationStatistics.setAlternativeOccur(
                    vacationList.stream()
                            .filter(vaca -> vaca.getNickname().equals(name))
                            .filter(vaca -> vaca.getOverTimeName().equals(otAlternative))
                            .mapToDouble(Vacation::getVacationSum)
                            .sum()
            );

            vacationStatistics.setOccurSum(
                    vacationStatistics.getAnnualOccur() + vacationStatistics.getAlternativeOccur()
            );

            vacationStatistics.setCongratulationUse(
                    vacationList.stream()
                            .filter(vaca -> vaca.getNickname().equals(name))
                            .filter(vaca -> vaca.getOverTimeName().equals(congratulationVacation))
                            .mapToDouble(Vacation::getVacationSum)
                            .sum()
            );

            vacationStatistics.setAnnualUse(
                    vacationList.stream()
                            .filter(vaca -> vaca.getNickname().equals(name))
                            .filter(vaca -> vaca.getOverTimeName().equals(annualVcation)
                                    || vaca.getOverTimeName().equals(annualVcationAm)
                                    || vaca.getOverTimeName().equals(annualVcationPm))
                            .mapToDouble(Vacation::getVacationSum)
                            .sum()
            );

            vacationStatistics.setAlternativeUse(
                    vacationList.stream()
                            .filter(vaca -> vaca.getNickname().equals(name))
                            .filter(vaca -> vaca.getOverTimeName().equals(alternativeVacation))
                            .mapToDouble(Vacation::getVacationSum)
                            .sum()
            );

            vacationStatistics.setUseSum(
                    vacationStatistics.getCongratulationUse()
                    + vacationStatistics.getAnnualUse()
                    + vacationStatistics.getAlternativeUse()
                    + vacationStatistics.getAlternativeUse()
            );

            vacationStatistics.setVacationTotal(
                    vacationStatistics.getOccurSum() - vacationStatistics.getUseSum()
            );

            vsList.add(vacationStatistics);
        }

        return vsList;
    }

}
