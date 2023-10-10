package com.halfwind.core;

import com.halfwind.api.Patient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface PatientService {

    public Patient getPatient(Long id);

    public List<Patient> getPatients();

    public void createPatient(Patient patient);

    public void deletePatient(int id);

    CompletableFuture<String> createPatientV2(Patient patient);
}
