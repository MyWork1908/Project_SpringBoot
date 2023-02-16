package com.project.application.repositosies;

import com.project.application.models.Fresher;
import com.project.application.models.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThesisRepository extends JpaRepository<Thesis, String> {
    List<Thesis> findByFresherCode(String fresherCode);
    List<Thesis> findByProgrammingLanguage(String programmingLanguage);
}
