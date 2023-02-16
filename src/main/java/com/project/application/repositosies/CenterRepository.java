package com.project.application.repositosies;

import com.project.application.models.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, String> {
    List<Center> findByCenterCode(String centerCode);
}
