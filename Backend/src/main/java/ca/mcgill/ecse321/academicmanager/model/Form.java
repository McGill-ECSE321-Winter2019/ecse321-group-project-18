package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Form{
	private CoopTermRegistration coopTermRegistration;

	@ManyToOne
	public CoopTermRegistration getCoopTermRegistration() {
	   return this.coopTermRegistration;
	}
	
	public void setCoopTermRegistration(CoopTermRegistration coopTermRegistration) {
	   this.coopTermRegistration = coopTermRegistration;
	}
	
	private String formID;
	
	public void setFormID(String value) {
		this.formID = value;
	}
	@Id
	public String getFormID() {
		return this.formID;
    }
	private String name;
	
	public void setName(String value) {
	   this.name = value;
	}
	
	public String getName() {
	   return this.name;
	}
	
	/**
	 * <pre>
	 *           1..1     1..1
	 * Form ------------------------> FormType
	 *           &lt;       formType
	 * </pre>
	 */
	private FormType formType;
	
	public void setFormType(FormType value) {
	   this.formType = value;
	}
	
	public FormType getFormType() {
	   return this.formType;
	}
	
	private String pdfLink;
	
	public void setPdfLink(String value) {
		this.pdfLink = value;
    }
	public String getPdfLink() {
		return this.pdfLink;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formID == null) ? 0 : formID.hashCode());
		result = prime * result + ((pdfLink == null) ? 0 : pdfLink.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Form other = (Form) obj;
		if (formID == null) {
			if (other.formID != null)
				return false;
		} else if (!formID.equals(other.formID))
			return false;
		if (pdfLink == null) {
			if (other.pdfLink != null)
				return false;
		} else if (!pdfLink.equals(other.pdfLink))
			return false;
		return true;
	}
	
}
