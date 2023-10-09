package com.halfwind.core;

import com.halfwind.api.Patient;

import java.util.List;

public interface PatientService {

    public Patient getPatient(Long id);

    public List<Patient> getPatients();

    public void createPatient(Patient patient);
    public void deletePatient(int id);

    public String processPatient(Long id);

}
