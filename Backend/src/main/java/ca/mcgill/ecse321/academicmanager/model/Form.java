package ca.mcgill.ecse321.academicmanager.model;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Form{
private CoopTerm coopTerm;

@ManyToOne(optional=false)
public CoopTerm getCoopTerm() {
   return this.coopTerm;
}

public void setCoopTerm(CoopTerm coopTerm) {
   this.coopTerm = coopTerm;
}

private String downloadLink;

public void setDownloadLink(String value) {
this.downloadLink = value;
    }
public String getDownloadLink() {
return this.downloadLink;
    }
   }
