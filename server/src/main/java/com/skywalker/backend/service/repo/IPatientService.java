package com.skywalker.backend.service.repo;

import com.skywalker.backend.dto.Response;


public interface IPatientService {

    Response getAllPatients();

    Response getPatientById(Long id);

    Response deletePatient(Long id);

}
