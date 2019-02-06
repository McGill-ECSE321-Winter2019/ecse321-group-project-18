import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class CoopPosition{
private String jobID;
   
   public void setJobID(String value) {
this.jobID = value;
    }
@Id
public String getJobID() {
return this.jobID;
    }
private String term;

public void setTerm(String value) {
this.term = value;
    }
public String getTerm() {
return this.term;
    }
private Set<CoopTermRegistration> coopTermRegistration;

@OneToMany(mappedBy="coopCourse")
public Set<CoopTermRegistration> getCoopTermRegistration() {
   return this.coopTermRegistration;
}

public void setCoopTermRegistration(Set<CoopTermRegistration> coopTermRegistrations) {
   this.coopTermRegistration = coopTermRegistrations;
}

}
