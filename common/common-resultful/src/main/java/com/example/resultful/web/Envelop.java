package com.example.resultful.web;

import com.example.resultful.web.status.EnvelopStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Rest Model - 基类
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Envelop implements Serializable {
    protected String message;
    protected Integer status = EnvelopStatus.success.code;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Envelop getSuccess(String message) {
        Envelop envelop = new Envelop();
        envelop.setMessage(message);
        envelop.setStatus(200);
        return envelop;
    }

    public static Envelop getError(String message, int errorCode) {
        Envelop envelop = new Envelop();
        envelop.setMessage(message);
        envelop.setStatus(errorCode);
        return envelop;
    }

    public static Envelop getError(String message) {
        Envelop envelop = new Envelop();
        envelop.setMessage(message);
        envelop.setStatus(-1);
        return envelop;
    }

}
