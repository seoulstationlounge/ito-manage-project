package com.itosideproject.modules.helpdesk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itosideproject.modules.account.Account;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	@JsonProperty
	private Account writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
	@JsonProperty
	private Question question;
	
	@Lob
	@JsonProperty
	private String contents;
	
	public Answer() {
	}
	
	public Answer(Account writer, Question question, String contents) {
		this.writer = writer;
		this.question = question;
		this.contents = contents;
	}
	
	public boolean isSameWriter(Account loginUser) {
		return loginUser.equals(this.writer);
	}


	@Override
	public String toString() {
		return "Answer [" + super.toString() + ", writer=" + writer + ", contents=" + contents + "]";
	}
}
