package ca.mcgill.ecse321.academicmanager.dto;

import java.sql.Date;


public class TermDto {

	private String termID;
	private String termName;
	private Date date1;
	private Date date2;


	public TermDto() {
	}


	public TermDto(String termID, String termName, Date date1, Date date2) {
		this.termID = termID;
		this.termName = termName;
		this.date1 = date1;
		this.date2 = date2;
	
	}

	public String getTermName() {
		return termName;
	}
	public String getTermID() {
		return termID;
	}
	public Date getDate1() {
		return date1;
	}
	public Date getDate2() {
		return date2;
	}


}