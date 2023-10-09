package com.halfwind.core;

import com.halfwind.api.Patient;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class PatientServiceImpl implements PatientService {

    Map<Long, Patient> patientMap = new HashMap<Long, Patient>();
    long currentId = 123;

    List<String> listOfNames = new LinkedList<>();

    public PatientServiceImpl() {
        patientMap.put(currentId,new Patient(currentId,"John"));
    }

    @Override
    public Patient getPatient(Long id) {
        return patientMap.get(id);
    }

    @Override
    public List<Patient> getPatients() {
        // values returns a collection of V (out of K,V ) from a map
        Collection<Patient> patients = patientMap.values();
        return new ArrayList<Patient>(patients);
    }

    @Override
    public void createPatient(Patient patient) {

        patientMap.put(patient.getId(),patient);
        listOfNames.add(patient.getName());

    }

    @Override
    public void deletePatient(int id) {

    }

    @Override
    public String processPatient(Long id) {
        StringBuilder emptyString = new StringBuilder();
        for (String name : listOfNames) {
            emptyString.append(name).append("---");
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Returning " + emptyString + " from thread: "+ Thread.currentThread().getName());
        return emptyString.substring(0, emptyString.length()-3);
    }
}
