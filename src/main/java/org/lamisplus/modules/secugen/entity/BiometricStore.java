package org.lamisplus.modules.secugen.entity;

import lombok.Data;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class BiometricStore {
    //public static List<CapturedBiometrics> capturedBiometrics;
    private static HashMap<Long, List<CapturedBiometrics>> patientBiometricStore;


    public static HashMap<Long, List<CapturedBiometrics>> addCapturedBiometrics(Long patientId, CapturedBiometrics capturedBiometric){
        if(patientBiometricStore == null){
            ArrayList<CapturedBiometrics> capturedBiometrics = new ArrayList<>();
            patientBiometricStore = new HashMap<>();
            capturedBiometrics.add(capturedBiometric);
            patientBiometricStore.put(patientId, capturedBiometrics );
            return patientBiometricStore;
        }
        if(!BiometricStore.patientBiometricStore.containsKey(patientId)){
            BiometricStore.patientBiometricStore = null;
            addCapturedBiometrics(patientId, capturedBiometric);
        }
        patientBiometricStore.get(patientId).add(capturedBiometric);
        return patientBiometricStore;
    }

    public static HashMap<Long, List<CapturedBiometrics>> getPatientBiometricStore(){
        if(patientBiometricStore == null){
            return new HashMap<Long, List<CapturedBiometrics>>();
        }
        return patientBiometricStore;
    }
}
