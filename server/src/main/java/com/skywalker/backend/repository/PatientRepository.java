package com.skywalker.backend.repository;

import com.skywalker.backend.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository  extends JpaRepository<Patient,Long> {

    void deleteByUserId(Long userId);
}
