
package org.lamisplus.modules.secugen.enumeration;

import SecuGen.FDxSDKPro.jni.SGFDxErrorCode;
import SecuGen.FDxSDKPro.jni.SGFDxErrorCode;
import lombok.Getter;

/**
 * @author
 */
public enum ErrorCode {

    SGFDX_ERROR_NONE(SGFDxErrorCode.SGFDX_ERROR_NONE, "SGFDX_ERROR_NONE", "Success"),
    SGFDX_ERROR_CREATION_FAILED(Long.valueOf(SGFDxErrorCode.SGFDX_ERROR_CREATION_FAILED), "SGFDX_ERROR_CREATION_FAILED", "JSGFPLib object creation failed"),
    SGFDX_ERROR_FUNCTION_FAILED(SGFDxErrorCode.SGFDX_ERROR_FUNCTION_FAILED, "SGFDX_ERROR_FUNCTION_FAILED", "Function call failed"),
    SGFDX_ERROR_INVALID_PARAM(SGFDxErrorCode.SGFDX_ERROR_INVALID_PARAM, "SGFDX_ERROR_INVALID_PARAM", "Invalid parameter used"),
    SGFDX_ERROR_NOT_USED(SGFDxErrorCode.SGFDX_ERROR_NOT_USED, "SGFDX_ERROR_NOT_USED", "Not used function"),
    SGFDX_ERROR_DLLLOAD_FAILED(SGFDxErrorCode.SGFDX_ERROR_DLLLOAD_FAILED, "SGFDX_ERROR_DLLLOAD_FAILED", "DLL loading failed"),
    SGFDX_ERROR_DLLLOAD_FAILED_DRV(SGFDxErrorCode.SGFDX_ERROR_DLLLOAD_FAILED_DRV, "USB UPx driver", "260*300"),
    SGFDX_ERROR_DLLLOAD_FAILED_ALG(SGFDxErrorCode.SGFDX_ERROR_DLLLOAD_FAILED_ALG, "SGFDX_ERROR_DLLLOAD_FAILED_ALG", "Algorithm DLL loading failed"),
    SGFDX_ERROR_SYSLOAD_FAILED(SGFDxErrorCode.SGFDX_ERROR_SYSLOAD_FAILED, "SGFDX_ERROR_SYSLOAD_FAILED", "Cannot find driver sys file"),
    SGFDX_ERROR_INITIALIZE_FAILED(SGFDxErrorCode.SGFDX_ERROR_INITIALIZE_FAILED, "SGFDX_ERROR_INITIALIZE_FAILED", "Chip initialization failed"),
    SGFDX_ERROR_LINE_DROPPED(SGFDxErrorCode.SGFDX_ERROR_LINE_DROPPED, "SGFDX_ERROR_LINE_DROPPED", " Image data lost");



    @Getter
    private final Long errorID;

    @Getter
    private final String errorName;

    @Getter
    private final String errorMessage;

    ErrorCode(Long errorID, String errorName, String errorMessage) {
        this.errorID = errorID;
        this.errorName = errorName;
        this.errorMessage = errorMessage;
    }   

    
    public static ErrorCode getErrorCode(Long errorID){
        for(ErrorCode errorCode : ErrorCode.values()){
            if (errorCode.getErrorID() == errorID){
                return errorCode;
            }
        }
        return null;
    }
}
