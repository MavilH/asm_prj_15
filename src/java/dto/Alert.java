/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author admin
 */
public class Alert {
    private int alertID;
    private String userID;
    private String ticker;
    private float threshold;
    private String direction;
    private String status;

    public Alert() {
    }

    public Alert(int alertID, float threshold) {
        this.alertID = alertID;
        this.threshold = threshold;
    }

    public Alert(int alertID, String userID, String ticker, float threshold, String direction, String status) {
        this.alertID = alertID;
        this.userID = userID;
        this.ticker = ticker;
        this.threshold = threshold;
        this.direction = direction;
        this.status = status;
    }

    public Alert(String userID, String ticker, float threshold, String direction) {
        this.userID = userID;
        this.ticker = ticker;
        this.threshold = threshold;
        this.direction = direction;
    }

    public int getAlertID() {
        return alertID;
    }

    public void setAlertID(int alertID) {
        this.alertID = alertID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
