package com.project.application.repositosies;

import com.project.application.models.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FresherRepository extends JpaRepository<Fresher, String> {
    List<Fresher> findByFresherCode(String fresherCode);
    List<Fresher> findByLastName(String lastName);
    List<Fresher> findByEmail(String email);
}
