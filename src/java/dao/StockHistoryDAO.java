/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.StockHistory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;
import utils.DBContext;

/**
 *
 * @author admin
 */
public class StockHistoryDAO {

    // GET
    private final String GET_ALL_STOCK_HISTORY = "SELECT * FROM HistoricalPrices";
    private final String GET_STOCK_HISTORY_BY_ID = "SELECT * FROM HistoricalPrices WHERE id = ?";
    // CREATE
    private final String CREATE_STOCK_HISTORY = "INSERT INTO HistoricalPrices (ticker, date, openDay, closeDay, high, low) VALUES(?, ?, ?, ?, ?, ?)";
    // UPDATE
    private final String UPDATE_STOCK_HISTORY = "UPDATE HistoricalPrices SET high = ?, low = ? WHERE date = ?";
    //DELETE
    private final String DELETE_STOCK_HISTORY = "DELETE FROM HistoricalPrices WHERE ticker like ? AND date = ?";
    //SEARCH
    private final String SEARCH_STOCK_HISTORY_BY_DATE = "SELECT * FROM HistoricalPrices WHERE date BETWEEN ? AND ?";
    // lấy 1 object
    public StockHistory getStockHistoryById(int id) throws SQLException{
        try(Connection conn = DBContext.getConnection();
                PreparedStatement stm = conn.prepareStatement(GET_STOCK_HISTORY_BY_ID)){
            stm.setInt(1, id);
            try(ResultSet rs = stm.executeQuery()){
                while(rs.next()){
                    return map(rs);
                }
            }
        }
        return null;
    }
    
    public List<StockHistory> getStockHistorysByDate(LocalDate date1, LocalDate date2) throws SQLException{
        List<StockHistory> list = new ArrayList<>();
        try(Connection conn = DBContext.getConnection();
                PreparedStatement stm = conn.prepareStatement(SEARCH_STOCK_HISTORY_BY_DATE)){
            stm.setDate(1, java.sql.Date.valueOf(date1));
            stm.setDate(2, java.sql.Date.valueOf(date2));
            
            try(ResultSet rs = stm.executeQuery()){
                while(rs.next()){
                    list.add(map(rs));
                }
            } 
        }
        return list;
    }
    

    
    // lấy 1 mảng
    public List<StockHistory> getAllStockHistorys() throws SQLException {
        List<StockHistory> resultList = new ArrayList<>();
        try ( Connection conn = DBContext.getConnection();  
                PreparedStatement stm = conn.prepareStatement(GET_ALL_STOCK_HISTORY);) {
            try ( ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    resultList.add(map(rs));
                }
            }
        }
        return resultList;
    }

    public int create(String ticker, LocalDate date, LocalDate open, LocalDate close, float high, float low) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  
                PreparedStatement stm = conn.prepareStatement(CREATE_STOCK_HISTORY);) {
            stm.setString(1, ticker.toUpperCase());
            stm.setDate(2, java.sql.Date.valueOf(date));
            stm.setDate(3, java.sql.Date.valueOf(open));
            
            stm.setDate(4, java.sql.Date.valueOf(close));
            stm.setFloat(5, high);
             stm.setFloat(6, low);
            return stm.executeUpdate();
        }
    }

    public int update(LocalDate date, float high, float low) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  
                PreparedStatement stm = conn.prepareStatement(UPDATE_STOCK_HISTORY);) {
            stm.setDate(3, java.sql.Date.valueOf(date));
            stm.setFloat(1, high);
            stm.setFloat(2, low);
            return stm.executeUpdate();
        }
    }

    public int delete(String ticker, LocalDate date) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  
                PreparedStatement stm = conn.prepareStatement(DELETE_STOCK_HISTORY);) {
            stm.setString(1, "%" + ticker + "%");
            stm.setDate(2, java.sql.Date.valueOf(date));
            return stm.executeUpdate();
        }
    }
    
    public StockHistory map(ResultSet rs) throws SQLException{
        return new StockHistory(rs.getInt("id"), rs.getString("ticker"),
                        rs.getDate("date"),
                rs.getDate("openDay"),
                rs.getDate("closeDay"),
                rs.getFloat("high"),
                rs.getFloat("low"));
    }
}
