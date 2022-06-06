package org.lamisplus.modules.secugen.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import org.lamisplus.modules.secugen.dto.BiometricTemplate;

@Data
public class Biometric {
    @NotNull(message = "patientId is mandatory")
    private Long patientId;
    @NotNull(message = "completed is mandatory")
    Boolean completed;
    private HashMap<String, String> message;
    private String finger;
    private byte[] template;
    private HashMap<String, byte[]> capturedBiometrics;
    private String templateId;


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
