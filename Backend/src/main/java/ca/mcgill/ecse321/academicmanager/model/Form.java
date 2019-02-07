package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Form{
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
@Id
public String getPdfLink() {
return this.pdfLink;
    }
private CoopTermRegistration coopTermRegistration;

@ManyToOne(optional=false)
public CoopTermRegistration getCoopTermRegistration() {
   return this.coopTermRegistration;
}

public void setCoopTermRegistration(CoopTermRegistration coopTermRegistration) {
   this.coopTermRegistration = coopTermRegistration;
}

}
