package org.lamisplus.modules.secugen.controller;

import SecuGen.FDxSDKPro.jni.SGFDxSecurityLevel;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.secugen.dto.BiometricTemplate;
import org.lamisplus.modules.secugen.dto.Device;
import org.lamisplus.modules.secugen.entity.Biometric;
import org.lamisplus.modules.secugen.service.BiometricService;
import org.lamisplus.modules.secugen.service.SecugenManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
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
    private final String BASE_URL_VERSION_ONE = "/api/v1/biometrics";

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


    @PostMapping(BASE_URL_VERSION_ONE + "/enrol2")
    public Biometric enrol2(@RequestParam String reader, @Valid @RequestBody Biometric biometric) {
        biometric.setMessage(new HashMap<String, String>());
        if(!reader.equals("SG_DEV_AUTO")) {
            biometric.getMessage().put("ERROR", "READER NOT AVAILABLE");
            biometric.setType(Biometric.Type.ERROR);
            return biometric;
        }
        if(!biometric.getBiometricType().equals("FINGERPRINT")) {
            biometric.getMessage().put("ERROR", "TemplateType not FINGERPRINT");
            biometric.setType(Biometric.Type.ERROR);
            return biometric;
        }

        try(InputStream in=new ClassPathResource("biometrics_payload.txt").getInputStream()){
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            Biometric biometric1 = mapper.readValue(in, Biometric.class);
            return biometric1;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

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
