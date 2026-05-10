package college.project.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SignUp {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long CollegeID;

    String FirstName;
    String LastName;
    Double PhoneNumber;
    String Email;


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Double getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(Double phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Long getCollegeID() {
        return CollegeID;
    }

    public void setCollegeID(Long collegeID) {
        CollegeID = collegeID;
    }
}
