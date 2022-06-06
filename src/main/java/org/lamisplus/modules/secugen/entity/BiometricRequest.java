package org.lamisplus.modules.secugen.entity;

import lombok.Data;

@Data
public class BiometricRequest {
    //private Long facilityId;
    private String finger;
    private byte[] template;
}
