package com.springboot.springbootapp.errors;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationStatus {
    private String emailIdErr;
    private String pwdErr;

    public RegistrationStatus() {
        emailIdErr = "----";
        pwdErr = "----";
    }

    public RegistrationStatus(String emailIdErr, String pwdErr) {
        this.emailIdErr = emailIdErr;
        this.pwdErr = pwdErr;
    }
}