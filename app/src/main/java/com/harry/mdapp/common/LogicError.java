package com.harry.mdapp.common;

import com.harry.mdapp.model.BaseResponse;

public class LogicError extends Exception {
    private static final long serialVersionUID = 7739186324576518504L;
    private String status;
    private String desc;
    
    public LogicError(BaseResponse response, String status, String message) {
        this.desc = message;
        this.status = status;
    }
    
    /**
     * 获取服务端返回的status
     * 
     * @return
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * 获取服务端返回的message
     * 
     * @return
     */
    public String getDesc() {
        return desc;
    }
}
