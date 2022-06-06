package org.lamisplus.modules.secugen.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.lamisplus.modules.secugen.dto.Device;
import org.lamisplus.modules.secugen.entity.Biometric;
import org.lamisplus.modules.secugen.entity.CapturedBiometrics;
import org.lamisplus.modules.secugen.enumeration.ErrorCode;
import org.lamisplus.modules.secugen.util.HttpConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BiometricService {
    //private final RestTemplate restTemplate = new RestTemplate();
    private final SecugenManager secugenManager;

    @Value("org.lamisplus.biometric.application-url")
    private String token;

    @Value("org.lamisplus.biometric.application-token")
    private String url;


    public Biometric enrol(String reader, Biometric biometric){
        /*try {
            reader = URLDecoder.decode(reader, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
        }*/
        if(biometric.getMessage() == null){
            biometric.setMessage(new HashMap<>());
        }

        if (this.scannerIsNotSet(reader)) {
            biometric.getMessage().put("CANNOT FIND READER", "READER NOT AVAILABLE");
            biometric.setType(Biometric.Type.ERROR);
            return biometric;
        }
        biometric.setDeviceName(reader);

        //log.info("STARTED CAPTURING +++++++++++++++++");
        biometric.getMessage().put("STARTED CAPTURING", "PROCEEDING...");
        Long readerId = secugenManager.getDeviceId(reader);

        secugenManager.boot(readerId);
        if (secugenManager.getError() > 0L) {
            ErrorCode errorCode = ErrorCode.getErrorCode(secugenManager.getError());
            if(errorCode == null) {
                biometric.getMessage().put("ERROR", "SECUGEN ERROR");
            } else {
                biometric.getMessage().put("ERROR", errorCode.getErrorName() + ": " + errorCode.getErrorMessage());
            }
            return biometric;
        }

        try {
            biometric = secugenManager.captureFingerPrint(biometric);

            AtomicReference<Boolean> matched = new AtomicReference<>(false);
            if (biometric.getTemplate().length > 200 && biometric.getImageQuality() >= 80) {

                byte[] scannedTemplate = biometric.getTemplate();
                if(biometric.getTemplate() != null && biometric.getCapturedBiometricsList() != null) {
                    for (CapturedBiometrics capturedBiometrics : biometric.getCapturedBiometricsList()) {
                        matched.set(secugenManager.matchTemplate(capturedBiometrics.getTemplate(), biometric.getTemplate()));
                        if (matched.get()) {
                            log.info("Fingerprint already exist");
                            biometric.getMessage().put("PATIENT_IDENTIFIED", "Fingerprint already captured");
                            biometric.setType(Biometric.Type.ERROR);
                            return biometric;
                        }
                    }
                } else {
                    biometric.setCapturedBiometricsList(new ArrayList<>());
                }

                biometric.getMessage().put("REGISTRATION", "PROCEEDING...");
                biometric.setType(Biometric.Type.SUCCESS);
                //biometric.setTemplate(scannedTemplate);
                CapturedBiometrics capturedBiometrics = new CapturedBiometrics();
                capturedBiometrics.setTemplate(scannedTemplate);
                capturedBiometrics.setTemplateType(biometric.getTemplateType());
                biometric.getCapturedBiometricsList().add(capturedBiometrics);
                //biometric.setIso(true);
                biometric.setTemplate(scannedTemplate);
                //biometric.setTemplateId("currentUser_" + RandomStringUtils.randomAlphabetic(5));
                //return biometric;
            }else {
                biometric.getMessage().put("ERROR", "COULD_NOT_CAPTURE_TEMPLATE...");
                biometric.setType(Biometric.Type.ERROR);
                return biometric;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            biometric.getMessage().put("ERROR", exception.getMessage());
            biometric.setType(Biometric.Type.ERROR);
            return biometric;
        }
        if(biometric.getCompleted() == true){
            //save all biometric and return biometric
            //maybe send to server
            return this.sendBiometrics(biometric);
        }
        return biometric;
    }

    private Biometric sendBiometrics(Biometric biometric) {
        byte [] byteArray = SerializationUtils.serialize(biometric);
        try {
            String response = new HttpConnectionManager().post(byteArray, token, url);
            return biometric;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean scannerIsNotSet(String reader) {
        Long readerId = secugenManager.getDeviceId(reader);
        for (Device device : secugenManager.getDevices()) {
            if (device.getId().equals(String.valueOf(readerId))) {
                secugenManager.boot(readerId);
                return false;
            }
        }
        return true;
    }
}
