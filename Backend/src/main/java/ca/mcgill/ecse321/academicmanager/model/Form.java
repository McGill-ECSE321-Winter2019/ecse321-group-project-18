package ca.mcgill.ecse321.academicmanager.model;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Form{
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


private String name;

public void setName(String value) {
this.name = value;
    }
public String getName() {
return this.name;
    }
   }
