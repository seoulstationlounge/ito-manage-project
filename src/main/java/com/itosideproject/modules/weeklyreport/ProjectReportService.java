package com.itosideproject.modules.weeklyreport;

import com.itosideproject.modules.weeklyreport.form.ProjectReportForm;
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
public class ProjectReportService {

    private final ProjectReportRepository projectReportRepository;
    private final ModelMapper modelMapper;

    public ProjectReport processNewProjectReport(ProjectReportForm projectReportForm) {
        ProjectReport projectReport = saveNewProjectReport(projectReportForm);
        return projectReport;
    }

    private ProjectReport saveNewProjectReport(@Valid ProjectReportForm projectReportForm) {
        ProjectReport projectReport = modelMapper.map(projectReportForm, ProjectReport.class);
        return projectReportRepository.save(projectReport);
    }

    public void updateProjectReport(ProjectReportForm projectReportForm) {
        ProjectReport projectReport = projectReportRepository.findProjectReportById(projectReportForm.getId());

        modelMapper.map(projectReportForm, projectReport);
        projectReportRepository.save(projectReport);
    }

    public void deleteCompany(Long id) {
        projectReportRepository.deleteById(id);
    }

}
