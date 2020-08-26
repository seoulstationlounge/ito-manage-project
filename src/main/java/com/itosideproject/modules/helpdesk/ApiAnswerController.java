package com.itosideproject.modules.helpdesk;

import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.account.CurrentAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("helpdesk/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, @CurrentAccount Account account) {
		if (account == null) {
			return null;
		}

		Question question = questionRepository.findQuestionById(questionId);
		Answer answer = new Answer(account, question, contents);
		question.addAnswer();
		return answerRepository.save(answer);
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long questionId, @PathVariable Long id, @CurrentAccount Account account) {
		if (account == null) {
			return Result.fail("로그인해야 합니다.");
		}
		
		Answer answer = answerRepository.findAnswerById(id);
		if (!answer.isSameWriter(account)) {
			return Result.fail("자신의 글만 삭제할 수 있습니다.");
		}
		
		answerRepository.deleteById(id);
		
		Question question = questionRepository.findQuestionById(questionId);
		question.deleteAnswer();
		questionRepository.save(question);
		return Result.ok();
	}
}
