package ca.mcgill.ecse321.academicmanager.dto;

import java.util.Collections;
import java.util.List;

public class StudentformDto {

	private String name;
	private List<FormDto> forms;

	public StudentformDto() {
	}

	@SuppressWarnings("unchecked")
	public StudentformDto(String name) {
		this(name, Collections.EMPTY_LIST);
	}

	public StudentformDto(String name, List<FormDto> arrayList) {
		this.name = name;
		this.forms = arrayList;
	}

	public String getName() {
		return name;
	}

	public List<FormDto> getForms() {
		return forms;
	}

	public void setForms(List<FormDto> forms) {
		this.forms = forms;
	}

}