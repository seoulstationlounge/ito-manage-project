package com.itosideproject.modules.helpdesk;

import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.account.CurrentAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("helpdesk/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("")
	public ResponseEntity<?> create(@PathVariable Long questionId, String contents, @CurrentAccount Account account) {
		if (account == null) {
			return null;
		}

		Question question = questionRepository.findQuestionById(questionId);
		Answer answer = new Answer(account, question, contents);
		question.addAnswer();

		Answer reAnswer = answerRepository.save(answer);

		Map<String, Object> map = new HashMap<>();
		map.put("writerUserName", reAnswer.getWriter().getUserName());
		map.put("contest", reAnswer.getContents());
		map.put("questionId",reAnswer.getQuestion().getId());
		map.put("id",reAnswer.getId());

		return ResponseEntity.ok(map);
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
