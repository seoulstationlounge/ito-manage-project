package com.itosideproject.modules.helpdesk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelpdeskController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/helpdesk")
	public String helpdeskHome(Model model) {
		model.addAttribute("questionList", questionRepository.findAll());
		return "helpdesk/index";
	}
}
