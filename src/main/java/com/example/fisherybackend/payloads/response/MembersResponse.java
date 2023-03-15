package com.example.fisherybackend.payloads.response;

public class MembersResponse {
    private String message;

    public MembersResponse(String message){
        this.message = message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
