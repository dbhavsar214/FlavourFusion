package org.example.responses;

public class LoginResponse {
    private String token;
    private long expiresIn;
    private long userID;

    public LoginResponse(String token, long expiresIn, long userID) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.userID = userID;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getUserID() { return userID;}

    public void setUserID(long userID) {this.userID = userID;}
}
