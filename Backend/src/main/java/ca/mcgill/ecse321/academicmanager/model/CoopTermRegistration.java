package ca.mcgill.ecse321.academicmanager.model;
import javax.persistence.OneToMany;
import java.util.Set;
import javax.persistence.Enumerated;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class CoopTermRegistration {
private Set<Form> form;

@OneToMany(mappedBy="coopTermRegistration")
public Set<Form> getForm() {
   return this.form;
}

public void setForm(Set<Form> forms) {
   this.form = forms;
}


	@Enumerated(EnumType.STRING)
	private TermStatus coopTermStatus;

	public void setCoopTermStatus(TermStatus value) {
		this.coopTermStatus = value;
	}

	public TermStatus getCoopTermStatus() {
		return this.coopTermStatus;
	}

	private Student student;

	@OneToOne(optional = false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	private CoopPosition coopCourse;

	@ManyToOne(optional = false)
	public CoopPosition getCoopCourse() {
		return this.coopCourse;
	}

	public void setCoopCourse(CoopPosition coopCourse) {
		this.coopCourse = coopCourse;
	}


	private String registrationID;

	public void setRegistrationID(String value) {
		this.registrationID = value;
	}

	@Id
	public String getRegistrationID() {
		return this.registrationID;
	}
}
