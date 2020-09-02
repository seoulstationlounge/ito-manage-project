package com.itosideproject.modules.weeklyreport;

import com.itosideproject.modules.weeklyreport.form.ItoReportForm;
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
public class ItoReportService {

    private final ItoReportRepository itoReportRepository;
    private final ModelMapper modelMapper;

    public ItoReport processNewItoReport(ItoReportForm itoReportForm) {

        ItoReport itoReport = saveNewItoReport(itoReportForm);
        return itoReport;
    }

    private ItoReport saveNewItoReport(@Valid ItoReportForm itoReportForm) {
        ItoReport itoReport = modelMapper.map(itoReportForm, ItoReport.class);
        return itoReportRepository.save(itoReport);
    }

    public void updateMonthlyReport(ItoReportForm itoReportForm) {
        ItoReport itoReport = itoReportRepository.findById(itoReportForm.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 리포트가 없습니다. : "));

        modelMapper.map(itoReportForm, itoReport);
        itoReportRepository.save(itoReport);
    }

    public void deleteWeekly(Long id) {
        itoReportRepository.deleteById(id);
    }

}
