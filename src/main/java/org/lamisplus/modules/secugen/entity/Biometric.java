package org.lamisplus.modules.secugen.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Biometric implements Serializable {
    @NotNull(message = "patientId is mandatory")
    private Long patientId;

    //@NotNull(message = "completed is mandatory")
    //Boolean completed;

    private HashMap<String, String> message;
    private byte[] template;
    private List<CapturedBiometrics> capturedBiometricsList;

    @NotBlank(message = "templateType is mandatory")
    private String templateType;

    private String deviceName;
    @NotBlank(message = "biometricType is mandatory")
    private String biometricType;


    //BiometricTemplate biometricTemplate;
    public enum Type {ERROR, SUCCESS}
    private Type type;
    private boolean iso;
    private int imageHeight;

    private int imageWeight;

    private int imageResolution;

    private int matchingScore;

    private int imageQuality;

    private byte[] image;
}
