package com.itosideproject.modules.company;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CompanyRepository extends JpaRepository<Company, Long>, QuerydslPredicateExecutor<Company> {

    @EntityGraph(attributePaths = {"tags", "zones"})
    Company findCompanyWithTagsAndZonesById(Long id);

    Company findByCompanyName(String companyName);

    Company findCompanyById(Long id);

    void deleteCompanyById(Long id);
}
