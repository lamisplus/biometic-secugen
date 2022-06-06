package org.lamisplus.modules.secugen.dto;

import lombok.Data;

@Data
public class BiometricRequest {

    private Long facilityId;
    
    private Long patientId;
    
    private String finger;
    
    private byte[] template;
}
