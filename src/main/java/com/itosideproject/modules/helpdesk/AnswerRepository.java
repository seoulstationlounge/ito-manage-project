package com.itosideproject.modules.helpdesk;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
    Answer findAnswerById(Long id);
}
