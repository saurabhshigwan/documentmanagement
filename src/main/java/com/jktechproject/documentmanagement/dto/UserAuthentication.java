package com.jktechproject.documentmanagement.dto;

public class UserAuthentication {

    public UserAuthentication(String returnMessage,String jwtToken){
        this.returnMessage = returnMessage;
        this.jwtToken = jwtToken;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    private String returnMessage;

    private String jwtToken;

}
