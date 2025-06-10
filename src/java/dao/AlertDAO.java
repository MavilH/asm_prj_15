/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.DBContext;
import utils.DBContext;

/**
 *
 * @author admin
 */
public class AlertDAO {

    private final String SEARCH_ALERT_BY_TICKER = "SELECT * FROM tblAlerts WHERE  ticker LIKE ?";
    private final String SEARCH_ALERT_BY_DIRECTION = "SELECT * FROM tblAlerts WHERE  direction LIKE ?";
    private final String SEARCH_ALERT_BY_STATUS = "SELECT * FROM tblAlerts WHERE  status LIKE ?";
    private final String CREATE_ALERT = "INSERT INTO tblAlerts(userID, ticker, threshold, direction) VALUES(?, ?, ?, ?)";
    private final String UPDATE_ALERT = "UPDATE tblAlerts SET direction = ?, threshold = ?, status = ? WHERE alertID = ?";
    private final String DELETE_ALERT = "DELETE FROM tblAlerts WHERE alertID = ? AND status = 'inactive'";
    private final String GET_ALL_ALERT = "SELECT * FROM tblAlerts";
    private final String GET_ALERT_BY_ID = "SELECT * FROM tblAlerts WHERE alertID = ?";
    public Alert getAlertByID(int alertID) throws SQLException{
        try(Connection conn = DBContext.getConnection();
                PreparedStatement stm = conn.prepareStatement(GET_ALERT_BY_ID)){
            stm.setInt(1, alertID);
            try(ResultSet rs = stm.executeQuery()){
                while(rs.next()){
                    return mapThrows(rs);
                }
            }
        }
        return null;
    }
    public ArrayList<Alert> getAllAlert() throws SQLException {
        ArrayList<Alert> resultList = new ArrayList<>();
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(GET_ALL_ALERT);) {

            try ( ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    resultList.add(mapThrows(rs));
                }
            }
        }
        return resultList;
    }
    public ArrayList<Alert> searchByTicker(String ticker) throws SQLException {
        return SearchAlertByKeyword(SEARCH_ALERT_BY_TICKER, ticker);
    }

    public ArrayList<Alert> searchByDirection(String direction) throws SQLException {
        return SearchAlertByKeyword(SEARCH_ALERT_BY_DIRECTION, direction);
    }

    public ArrayList<Alert> searchByStatus(String status) throws SQLException {
        return SearchAlertByKeyword(SEARCH_ALERT_BY_STATUS, status);
    }

    public ArrayList<Alert> SearchAlertByKeyword(String sql, String keyword) throws SQLException{
        ArrayList<Alert> resultList = new ArrayList<>();
        try ( Connection conn = DBContext.getConnection();  
                PreparedStatement stm = conn.prepareStatement(sql)) {
            if(sql.equalsIgnoreCase("SELECT * FROM tblAlerts WHERE  ticker LIKE ?")){
                stm.setString(1, "%" + keyword + "%");
            }else stm.setString(1, keyword);
            try ( ResultSet rs = stm.executeQuery();) {
                while (rs.next()) {
                    resultList.add(mapThrows(rs));
                }
            }
        }
        return resultList;
    }
    public boolean create(String userID, String ticker, float threshold, String direction) throws SQLException {

        boolean isCreated = false;
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(CREATE_ALERT);) {
            stm.setString(1, userID);
            stm.setString(2, ticker.toUpperCase());
            stm.setFloat(3, threshold);
            stm.setString(4, direction);
            isCreated = stm.executeUpdate() > 0;
        }
        return isCreated;
    }

    public boolean update(int alertID, String direction, float threshold, String status) throws SQLException {

        boolean isUpdated = false;
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(UPDATE_ALERT);) {
            stm.setString(1, direction);
            stm.setFloat(2, threshold);
            stm.setString(3, status);
            stm.setInt(4, alertID);
            isUpdated = stm.executeUpdate() > 0;
        }
        return isUpdated;
    }

    public boolean delete(int alertID) throws SQLException {
        boolean isDeleted = false;
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(DELETE_ALERT);) {
            stm.setInt(1, alertID);
            isDeleted = stm.executeUpdate() > 0;
        }
        return isDeleted;
    }
    
    
    public Alert mapThrows(ResultSet rs) throws SQLException{
        return new Alert(rs.getInt("alertID"), 
                         rs.getString("userID"), 
                         rs.getString("ticker"),
                         rs.getFloat("threshold"), 
                         rs.getString("direction"), 
                         rs.getString("status"));
    }
}
