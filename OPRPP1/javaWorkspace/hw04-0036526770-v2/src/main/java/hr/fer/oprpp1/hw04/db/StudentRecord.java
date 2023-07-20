package hr.fer.oprpp1.hw04.db;

public class StudentRecord {
    public String jmbag;
    public String lastName;
    public String firstName;
    public String finalGrade;

    public StudentRecord(String jmbag,String lastName,String firstName,String finalGrade){
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    public boolean equals (Object o){
        if(!(o instanceof StudentRecord)){
            return false;
        }
        StudentRecord s = (StudentRecord) o;
        return s.jmbag.equals(jmbag) ? true : false;
    }

    public int hashCode(){
        return jmbag.hashCode();
    }


}
