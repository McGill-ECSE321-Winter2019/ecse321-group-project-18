import javax.persistence.Entity;

@Entity
public enum FormType{
private FormType studentEvalution;
   
   public void setStudentEvalution(FormType value) {
this.studentEvalution = value;
    }
public FormType getStudentEvalution() {
return this.studentEvalution;
    }
private FormType coopEvaluation;

public void setCoopEvaluation(FormType value) {
this.coopEvaluation = value;
    }
public FormType getCoopEvaluation() {
return this.coopEvaluation;
       }
   }
