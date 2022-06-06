package org.lamisplus.modules.secugen.controller;

import SecuGen.FDxSDKPro.jni.SGFDxSecurityLevel;
import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.secugen.dto.BiometricTemplate;
import org.lamisplus.modules.secugen.dto.Device;
import org.lamisplus.modules.secugen.entity.Biometric;
import org.lamisplus.modules.secugen.service.BiometricService;
import org.lamisplus.modules.secugen.service.SecugenManager;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.lang3.RandomStringUtils;

import javax.validation.Valid;

/**
 * @author
 */
@CrossOrigin(origins = "*")
@RestController
//@RequestMapping("/api/biometrics")
@RequiredArgsConstructor
public class BiometricController {
    //private final RestTemplate restTemplate = new RestTemplate();
    //private final static String BASE_URL = "/api/biometrics";
    //private List<BiometricTemplate> templates = new ArrayList<>();
    //private boolean templatesUpdated;
    private final BiometricService biometricService;
    //Versioning through URI Path
    private final String BASE_URL_VERSION_ONE = "/api/v1/biometrics/secugen";

    @Autowired
    private SecugenManager secugenManager;
    
    private static final Logger logger = LoggerFactory.getLogger(BiometricController.class);

    @GetMapping(BASE_URL_VERSION_ONE + "/server")
    public String getServerUrl() {
        return secugenManager.getSecugenProperties().getServerUrl();
    }

    @GetMapping(BASE_URL_VERSION_ONE + "/reader")
    public ResponseEntity<Object> getReaders() {
        List<Device> devices = secugenManager.getDevices();
        return ResponseEntity.ok(devices);
    }

    /*@PostMapping
    public Biometric identify(@RequestParam String reader, @RequestBody Biometric biometric){
    }*/

    @PostMapping(BASE_URL_VERSION_ONE + "/enrol")
    public Biometric enrol(@RequestParam String reader, @Valid @RequestBody Biometric biometric) {
        return biometricService.enrol(reader, biometric);
    }

    /*private boolean scannerIsNotSet(String reader) {
        Long readerId = secugenManager.getDeviceId(reader);
        for (Device device : secugenManager.getDevices()) {
            if (device.getId().equals(reader)) {
                secugenManager.boot(readerId);
                return false;
            }
        }
        return true;
    }*/

    /*@SneakyThrows
    private List<BiometricTemplate> biometricTemplates(String server, String accessToken) {
        String url = server + "/biometrics";
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        ResponseEntity<List<BiometricTemplate>> response = restTemplate.exchange(
                RequestEntity.get(new URI(url)).header("Authorization", "Bearer " + accessToken)
                        .header("Content-Type", "application/json").build(),
                new ParameterizedTypeReference<List<BiometricTemplate>>() {
                });
        return response.getBody();
    }

    @GetMapping
    public List<BiometricTemplate> getTemplates(@RequestParam String server, @RequestParam String token) {
        return biometricTemplates(server, token);
    }*/
}
