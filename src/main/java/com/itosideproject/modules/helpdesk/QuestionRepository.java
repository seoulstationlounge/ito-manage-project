package com.itosideproject.modules.helpdesk;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>{

    Question findQuestionById(Long Id);
}
