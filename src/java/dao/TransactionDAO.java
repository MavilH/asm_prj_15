/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.DBContext;

/**
 *
 * @author admin
 */
public class TransactionDAO {

    private final String GET_ALL_TRANSACTION = "SELECT * FROM tblTransactions";
    private final String GET_TRANSACTION_BY_ID = "SELECT * FROM tblTransactions WHERE id LIKE ?";
    private final String SEARCH_TRANSACTION_BY_USERID = "SELECT * FROM tblTransactions WHERE userID LIKE ?";
    private final String SEARCH_TRANSACTION_BY_TICKER = "SELECT * FROM tblTransactions WHERE ticker LIKE ?";
    private final String SEARCH_TRANSACTION_BY_TYPE = "SELECT * FROM tblTransactions WHERE type LIKE ?";
    private final String SEARCH_TRANSACTION_BY_STATUS = "SELECT * FROM tblTransactions WHERE status LIKE ?";
    private final String CREATE_TRANSACTION = "INSERT INTO tblTransactions (userID, ticker, type, quantity, price, status)  VALUES(?,?,?,?,?,?)";
    //private final String UPDATE_TRANSACTION = "UPDATE tblTransactions SET userID = ?, ticker = ?, type = ?, quantity = ?, price = ?, status = ? WHERE id = ?";
    private final String UPDATE_TRANSACTION = "UPDATE tblTransactions SET quantity = ?, price = ?, status = ? WHERE id = ?";
    private final String DELETE_TRANSACTION = "DELETE FROM tblTransactions WHERE id LIKE ?";
    
    public List<Transaction> getAllTransaction() throws SQLException {
        List<Transaction> resultList = new ArrayList<>();
        try ( Connection conn = DBContext.getConnection();
                PreparedStatement stm = conn.prepareStatement(GET_ALL_TRANSACTION); 
                ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                resultList.add(mapRow(rs));
            }
        }
        return resultList;
    }
    
    public Transaction getTransactionByID(int id) throws SQLException{
        try ( Connection conn = DBContext.getConnection();
                PreparedStatement stm = conn.prepareStatement(GET_TRANSACTION_BY_ID)) {
            stm.setInt(1, id);
            try( ResultSet rs = stm.executeQuery()){
                if(rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }
    
    public List<Transaction> searchByUserID(String search) throws SQLException {
        return searchByKeyWord(SEARCH_TRANSACTION_BY_USERID, search);
    }

    public List<Transaction> searchByTicker(String search) throws SQLException {
       return searchByKeyWord(SEARCH_TRANSACTION_BY_TICKER, search);
    }

    public List<Transaction> searchByType(String search) throws SQLException {
        return searchByKeyWord(SEARCH_TRANSACTION_BY_TYPE, search);
    }

    public List<Transaction> searchByStatus(String search) throws SQLException {
        return searchByKeyWord(SEARCH_TRANSACTION_BY_STATUS, search);
    }

    private List<Transaction> searchByKeyWord(String sql, String keyword) throws SQLException{
        List<Transaction> resultList = new ArrayList<>();
        try ( Connection conn = DBContext.getConnection();
                PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, "%" + keyword + "%");
            try ( ResultSet rs = stm.executeQuery()){
                while (rs.next()){
                    resultList.add(mapRow(rs));
                }
            }
        }
        return resultList;
    }
    
    public boolean createTransaction(String userID, String ticker,
            String type, int quantity, float price, String status) throws Exception {
        boolean isCreated = false;
        try ( Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(CREATE_TRANSACTION)) {
           
                stm.setString(1, userID);
                stm.setString(2, ticker);
                stm.setString(3, type);
                stm.setInt(4, quantity);
                stm.setFloat(5, price);
                stm.setString(6, status);
                
                isCreated = stm.executeUpdate() > 0;
        }
        return isCreated;

    }
    
    public boolean updateTransaction(int id,
//            String userID, String sticker, String type,
            int quantity, float  price, String status) throws Exception {
        boolean isUpdated = false;
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(UPDATE_TRANSACTION)) {  
//                stm.setString(1, userID);
//                stm.setString(2, sticker);
//                stm.setString(3, type);
                stm.setInt(1, quantity);
                stm.setFloat(2, price);
                stm.setString(3, status);
                stm.setInt(4, id);
                isUpdated = stm.executeUpdate() > 0;
        }
        return isUpdated;
    }

    public boolean deleteTransaction(int id) throws SQLException {
        boolean isDelete = false;
        try ( Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                PreparedStatement stm = conn.prepareStatement(DELETE_TRANSACTION);
                stm.setInt(1, id);
                isDelete = stm.executeUpdate() > 0;
            }
            return isDelete;
        }
    }

    private Transaction mapRow(ResultSet rs) throws SQLException{
        return new Transaction(
                            rs.getInt("id"),
                            rs.getString("userID"), rs.getString("ticker"),
                            rs.getString("type"), rs.getInt("quantity"),
                            rs.getFloat("price"), rs.getString("status")
        );
    } 
    
    public boolean checkTransactionExists(int id) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(GET_TRANSACTION_BY_ID)) {
            stm.setInt(1, id);
            return stm.executeQuery().next();
        }
    }
}
