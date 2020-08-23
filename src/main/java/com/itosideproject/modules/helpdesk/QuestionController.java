package com.itosideproject.modules.helpdesk;

import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.account.CurrentAccount;
import com.itosideproject.modules.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("helpdesk/questions")
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/form")
	public String form(@CurrentAccount Account account) {
		if (account == null) {
			return "/login";
		}
		
		return "/helpdesk/form";
	}
	
	@PostMapping("")
	public String create(@CurrentAccount Account account, String title, String contents) {
		if (account == null) {
			return "/login";
		}

		Question newQuestion = new Question(account, title, contents);
		questionRepository.save(newQuestion);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findById(id));
		return "/helpdesk/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@CurrentAccount Account account, @PathVariable Long id, Model model) {
		Question question = questionRepository.findQuestionById(id);
		Result result = valid(account, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/login";
		}
		
		model.addAttribute("question", question);
		return "/helpdesk/updateForm";
	}
	
	private Result valid(Account account, Question question) {
		if (account == null) {
			return Result.fail("로그인이 필요합니다.");
		}

		if (!question.isSameWriter(account)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		
		return Result.ok();
	}
	
	@PutMapping("/{id}")
	public String update(@CurrentAccount Account account, @PathVariable Long id, String title, String contents, Model model) {
		Question question = questionRepository.findQuestionById(id);
		Result result = valid(account, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/login";
		}
		
		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@CurrentAccount Account account, @PathVariable Long id, Model model) {
		Question question = questionRepository.findQuestionById(id);
		Result result = valid(account, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/login";
		}
		
		questionRepository.deleteById(id);
		return "redirect:/";
	}
}
