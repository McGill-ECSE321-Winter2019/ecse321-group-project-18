package ca.mcgill.ecse321.academicmanager.dto;

public class FormDto {

	private String formID;
	private String name;
	private String pdflink;

	public FormDto() {
	}

	public FormDto(String formID) {
		this(formID,"", "");
	}

	public FormDto(String formID, String name, String pdflink) {
		this.formID = formID;
		this.name = name;
		this.pdflink = pdflink;
	
	}

	public String getName() {
		return name;
	}
	public String getformID() {
		return formID;
	}
	public String getPdflink() {
		return pdflink;
	}

}