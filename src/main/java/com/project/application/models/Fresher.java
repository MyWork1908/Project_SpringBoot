package com.project.application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "fresher")
@Setter
@Getter
@NoArgsConstructor
public class Fresher {
    @Id
    @Column(name = "fresher_code")
    private String fresherCode;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "center_code")
    private String centerCode;

    public Fresher(String fresherCode, String firstName, String lastName, String gender, Date dateOfBirth, String address, String email, String phone) {
        this.fresherCode = fresherCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }
}
