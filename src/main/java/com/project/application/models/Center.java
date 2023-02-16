package com.project.application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "center")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Center {
    @Id
    @Column(name = "center_code")
    private String centerCode;
    @Column(name = "center_name")
    private String centerName;
    @Column(name = "center_address")
    private String centerAddress;
    @Column(name = "center_phone")
    private String centerPhone;
}
