package com.itosideproject.modules.weeklyreport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ItoReportRepository extends JpaRepository<ItoReport, Long>, QuerydslPredicateExecutor<ItoReport> {

    ItoReport findItoReportById(Long id);

}
