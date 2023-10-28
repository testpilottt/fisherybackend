package com.example.fisherybackend.payloads.response;

public class CommonResponse {
    private String message;
    private boolean isValid = true;

    public CommonResponse(String message){
        this.message = message;
    }

    public CommonResponse(String message, boolean isValid){
        this.message = message;
        this.isValid = isValid;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
