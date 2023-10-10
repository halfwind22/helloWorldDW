package com.halfwind.core;

import com.halfwind.api.Patient;

import java.util.Map;
import java.util.Set;

public class ExecutionService implements Runnable {

    private final PatientServiceImpl patientService;

    public ExecutionService(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }

    public void longRunningService() {

        /*
         * Simulate a long-running operation and remove the element from the map once the result is ready.
         * The result size will progressively come down as we get rid of the elements each iteration
         */

        Map<Long, Patient> patientMap = patientService.getPatientMap();
        Map<Long, String> resultList = patientService.getResultList();

        Set<Long> setOfKeys = patientMap.keySet();
        for(Long key:setOfKeys){
            StringBuilder resultString = new StringBuilder();
            patientMap.forEach((k,v)-> resultString.append(v.getName()).append("<--->"));
            resultString.append(key);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            resultList.put(key,resultString.toString());
            patientMap.remove(key);
        }

//        resultList.forEach((k,v)-> System.out.println("For key: "+ k.toString() +" the value is: "+ v));

    }


    @Override
    public void run() {
        longRunningService();

    }
}
