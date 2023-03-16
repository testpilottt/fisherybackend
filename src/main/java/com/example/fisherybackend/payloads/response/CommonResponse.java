package com.example.fisherybackend.payloads.response;

public class CommonResponse {
    private String message;

    public CommonResponse(String message){
        this.message = message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
