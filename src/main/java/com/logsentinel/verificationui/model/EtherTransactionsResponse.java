package com.logsentinel.verificationui.model;

import java.util.List;

public class EtherTransactionsResponse {
    private String status;
    private String message;
    private List<EtherTransaction> result;

    public EtherTransactionsResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<EtherTransaction> getResult() {
        return result;
    }

    public void setResult(List<EtherTransaction> result) {
        this.result = result;
    }
}
