package org.lamisplus.modules.secugen.entity;

import lombok.Data;

@Data
public class BiometricTemplate {
    private String id;
    private Boolean iso;
    private byte[] template;
}
