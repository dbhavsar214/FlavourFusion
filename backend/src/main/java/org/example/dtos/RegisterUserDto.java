package org.example.dtos;

public class RegisterUserDto {
    private String email;

    private String password;

    // private String fullName;
    private String confirmPassword;

    private String bio;

    private String firstName;

    private String lastName;

    public RegisterUserDto(){
    }
    public RegisterUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

//    public String getFullName() {
//        return fullName;
//    }

//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }

    public String getBio(){ return bio;}

    public void setBio(String bio){ this.bio = bio;}

    public String getFirstName() { return firstName;}

    public void setFirstName(String firstName) { this.firstName = firstName;}

    public String getLastName() { return lastName;}

    public void setLastName(String lastName) { this.lastName = lastName;}
// getters and setters here...
}
