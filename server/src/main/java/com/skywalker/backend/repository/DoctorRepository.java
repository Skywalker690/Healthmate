package com.skywalker.backend.repository;

import com.skywalker.backend.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository  extends JpaRepository<Doctor,Long> {
}
