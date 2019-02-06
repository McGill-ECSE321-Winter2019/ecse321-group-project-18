import javax.persistence.Entity;

@Entity
public enum TermStatus{
private TermStatus ongoing;
   
   public void setOngoing(TermStatus value) {
this.ongoing = value;
    }
public TermStatus getOngoing() {
return this.ongoing;
    }
private TermStatus passed;

public void setPassed(TermStatus value) {
this.passed = value;
    }
public TermStatus getPassed() {
return this.passed;
    }
private TermStatus failed;

public void setFailed(TermStatus value) {
this.failed = value;
    }
public TermStatus getFailed() {
return this.failed;
       }
   }
