/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constant.Message;
import dao.AlertDAO;
import dto.Alert;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class AlertService {

    private AlertDAO dao = new AlertDAO();

    public List<Alert> getAllAlerts() throws SQLException {
        return dao.getAllAlert();
    }

    public Alert searchAlertByID(int id) throws SQLException {
        return dao.getAlertByID(id);
    }

    public List<Alert> searchAlertsByTicker(String ticker) throws SQLException {
        return dao.searchByTicker(ticker);
    }

    public List<Alert> searchAlertsByDirection(String direction) throws SQLException {
        return dao.searchByDirection(direction);
    }

    public List<Alert> searchAlertsByStatus(String status) throws SQLException {
        return dao.searchByStatus(status);
    }

    public String createAlert(String userID, String ticker, float threshold, String direction) throws SQLException {
        if(isNullOrEmpty(userID) || isNullOrEmpty(ticker) || isNullOrEmpty(direction)){
            return Message.ALL_FIELDS_ARE_REQUIRED;
        }
        if(threshold < 0){
            return Message.THRESHOLD_CAN_NOT_BE_NEGATIVE;
        }
        if (dao.create(userID, ticker, threshold, direction)) {
            return Message.CREATE_ALERT_SUCCESSFULLY;
        }
        return Message.CREATE_ALERT_FAILED;
    }

    public boolean isCreator(Alert alert, String userID) throws SQLException {
        return alert.getUserID().equalsIgnoreCase(userID);
    }

    public String updateAlert(int alertID, String direction, float threshold, String status) throws SQLException {
        if(threshold < 0){
            return Message.THRESHOLD_CAN_NOT_BE_NEGATIVE;
        }
        if (dao.update(alertID, direction, threshold, status)) {
            return Message.UPDATE_ALERT_SUCCESSFULLY;
        }
        return Message.UPDATE_ALERT_FAILED;
    }

    public String deleteAlert(int alertID, String userID) throws SQLException {

        if (dao.delete(alertID)) {
            return Message.DELETE_ALERT_SUCCESSFULLY;
        }
        return Message.DELETE_ALERT_FAILED;

    }

    public boolean isInactive(Alert alert) throws SQLException {
        return alert.getStatus().equalsIgnoreCase("inactive");
    }
    
    public boolean isNullOrEmpty(String str){
        return (str.isEmpty() || str == null);
    }
}
