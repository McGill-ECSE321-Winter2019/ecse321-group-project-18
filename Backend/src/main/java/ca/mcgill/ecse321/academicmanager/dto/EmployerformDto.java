package ca.mcgill.ecse321.academicmanager.dto;

import java.util.Collections;
import java.util.List;

public class EmployerformDto {
	private String name;
	private List<FormDto> forms;

	public EmployerformDto() {
	}

	@SuppressWarnings("unchecked")
	public EmployerformDto(String name) {
		this(name, Collections.EMPTY_LIST);
	}

	public EmployerformDto(String name, List<FormDto> arrayList) {
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
