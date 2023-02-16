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
@Table(name = "thesis")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Thesis {
    @Id
    @Column(name = "fresher_code")
    private String fresherCode;
    @Column(name = "programming_language")
    private String programmingLanguage;
    @Column(name = "thesis_score_01")
    private Double thesisScore01;
    @Column(name = "thesis_score_02")
    private Double thesisScore02;
    @Column(name = "thesis_score_03")
    private Double thesisScore03;
    @Column(name = "thesis_score_average")
    private Double thesisScoreAverage;

}
