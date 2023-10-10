package com.halfwind.core;

import com.halfwind.api.Patient;
import lombok.Getter;

import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.*;

@Singleton
public class PatientServiceImpl implements PatientService {

    @Getter
    private final Map<Long, Patient> patientMap;
    @Getter
    private final Map<Long, String> resultList;

    long currentId = 123;

    public PatientServiceImpl() {
        this.patientMap = new ConcurrentHashMap<Long, Patient>();
        this.resultList = new ConcurrentHashMap<Long, String>();
        this.patientMap.put(currentId, new Patient(currentId, "John"));
        System.out.println("Instantiated with mapzise: " + this.patientMap.size());
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
        patientMap.put(patient.getId(), patient);

    }

    @Override
    public void deletePatient(int id) {

    }

    @Override
    public CompletableFuture<String> createPatientV2(Patient patient) {

        int patientId = patient.getId().intValue();
        patientMap.put(patient.getId(), patient);
        System.out.println("Inserted and giving a future!");
        return CompletableFuture.supplyAsync(() -> retrieveResult(patientId));
    }

    public String retrieveResult(int id) {
        /*
         * Blocking
         * */
        while (!resultList.containsKey((long) id)) {
            try {
                Thread.sleep(1000);
                System.out.println("Waiting for the result of: " + id + " !");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Finally got the result of: " + id + " !");
        return resultList.get((long) id);
    }
}
