package com.home.vlas.vine.activity.model;


public class TokenRequest {

    private String imei;
    private String login;
    private String password;

    public TokenRequest(String imei, String login, String password) {
        this.imei = imei;
        this.login = login;
        this.password = password;
    }

    public TokenRequest() {
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
