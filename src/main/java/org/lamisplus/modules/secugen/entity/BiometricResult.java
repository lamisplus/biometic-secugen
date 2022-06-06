
package org.lamisplus.modules.secugen.entity;

import lombok.Data;

@Data
public class BiometricResult {
    private String id;
    private String message;
    private byte[] template;
    private boolean identified;
    private boolean iso;
}
