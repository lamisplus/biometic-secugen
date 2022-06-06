package org.lamisplus.modules.secugen.entity;

import lombok.Data;

@Data
public class CapturedBiometrics {
    private String templateType;
    private byte[] template;
}
