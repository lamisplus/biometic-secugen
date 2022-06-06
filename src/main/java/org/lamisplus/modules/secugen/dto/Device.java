package org.lamisplus.modules.secugen.dto;

import org.lamisplus.modules.secugen.enumeration.DeviceNames;
import lombok.Data;

@Data
public class Device {
    private String id;
    private String name;   
    private DeviceNames deviceName;
    private int imageWidth, 
            imageHeight, 
            contrast, 
            brightness, 
            gain, 
            imageDPI, 
            FWVersion;
}
