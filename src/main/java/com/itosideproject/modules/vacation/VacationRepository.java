package com.itosideproject.modules.vacation;

import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface VacationRepository extends JpaRepository<Vacation, Long>, QuerydslPredicateExecutor<Vacation> {

    List<Vacation> findVacationsByNickname(String nickname);

    List<Vacation> findVacationsById(Long id);

}
