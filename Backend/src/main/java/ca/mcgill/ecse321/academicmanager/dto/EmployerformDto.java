package ca.mcgill.ecse321.academicmanager.dto;

import java.util.Collections;
import java.util.List;

public class EmployerformDto {
	private String name;
	private List<String> formLinks;

	public EmployerformDto() {
	}

	@SuppressWarnings("unchecked")
	public EmployerformDto(String name) {
		this(name, Collections.EMPTY_LIST);
	}

	public EmployerformDto(String name, List<String> formLinks) {
		this.name = name;
		this.formLinks = formLinks;
	}

	public String getName() {
		return name;
	}

	public List<String> getFormLinks() {
		return formLinks;
	}

	public void setFormLinks(List<String> formLinks) {
		this.formLinks = formLinks;
	}
}
