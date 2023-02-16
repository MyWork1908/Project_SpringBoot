package com.project.application.models.mapper;

import com.project.application.models.Fresher;
import com.project.application.models.Thesis;
import com.project.application.models.dto.Transcript;

import java.util.Optional;

public class TranscriptMapper {
    public static Transcript toTranscript(Thesis thesis, Optional<Fresher> fresher) {
        Transcript transcript = new Transcript();
        transcript.setFresherCode(fresher.get().getFresherCode());
        transcript.setFirstName(fresher.get().getFirstName());
        transcript.setLastName(fresher.get().getLastName());
        transcript.setProgrammingLanguage(thesis.getProgrammingLanguage());
        transcript.setThesisScore01(thesis.getThesisScore01());
        transcript.setThesisScore02(thesis.getThesisScore02());
        transcript.setThesisScore03(thesis.getThesisScore03());
        transcript.setThesisScoreAverage(thesis.getThesisScoreAverage());
        return transcript;
    }
}
