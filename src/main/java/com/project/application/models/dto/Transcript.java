package com.project.application.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transcript {
    private String fresherCode;
    private String firstName;
    private String lastName;
    private String programmingLanguage;
    private Double thesisScore01;
    private Double thesisScore02;
    private Double thesisScore03;
    private Double thesisScoreAverage;
}
