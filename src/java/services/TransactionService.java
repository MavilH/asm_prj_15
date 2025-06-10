/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;


import constant.Message;
import dao.TransactionDAO;
import dao.UserDAO;
import dto.Transaction;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ngogi
 */
public class TransactionService {
    private TransactionDAO transactionDAO = new TransactionDAO();
    private UserDAO userDAO = new UserDAO();
    
    public String createTransaction(String userID, String ticker,
            String type, int quantity, float price, String status)throws SQLException, Exception{
        if (isNullOrEmptyString(userID) 
                || isNullOrEmptyString(ticker)
                || isNullOrEmptyString(type)
                || isNullOrEmptyString(status)){
            return Message.ALL_FIELDS_ARE_REQUIRED;
        }
        
        if(quantity <= 0){
            return Message.QUANTITY_CAN_NOT_BE_NEGATIVE;
        }
        
        if(price <= 0.0){
            return Message.PRICE_CAN_NOT_BE_NEGATIVE;
        }
        
        if(!transactionDAO.createTransaction(userID, ticker, type, quantity, price, status)){
            return Message.CREATE_TRANSACTION_FAILED;
        }
        return Message.CREATE_TRANSACTION_SUCCESSFULLY;
    }
    
    public String updateTransaction(int id,
            //String userID, String ticker, String type,
            int quantity, float  price, String status) throws SQLException, Exception{
        if(!transactionDAO.checkTransactionExists(id)){
            return Message.TRANSACTION_NOT_FOUND;
        }
        
        Transaction transaction = transactionDAO.getTransactionByID(id);
        
//        if(isNullOrEmptyString(userID)){
//            userID = transaction.getUserID();
//        }
//        
//        if(isNullOrEmptyString(ticker)){
//            ticker = transaction.getTicker();
//        }
//        
//        if(isNullOrEmptyString(type)){
//            type = transaction.getType();
//        }
        
        if(quantity <= 0){
            return Message.QUANTITY_CAN_NOT_BE_NEGATIVE;
        }
        
        if(price <= 0.0){
            return Message.PRICE_CAN_NOT_BE_NEGATIVE;
        }
        
        if(isNullOrEmptyString(status)){
            status = transaction.getStatus();
        }
        
        if(!transactionDAO.updateTransaction(id,
                //userID, ticker, type, 
                quantity, price, status)){
            return Message.UPDATE_TRANSACTION_FAILED;
        }
        
        return Message.UPDATE_TRANSACTION_SUCCESSFULLY;
        
    }
    
    public String deleteTransaction(int id) throws SQLException{
        if(!transactionDAO.deleteTransaction(id)){
            return Message.TRANSACTION_NOT_FOUND;
        }
        return Message.DELETE_TRANSACTION_SUCCESSFULLY;
        
    }
    
    public List<Transaction> getAllTransactions() throws SQLException{
        return transactionDAO.getAllTransaction();
    }
    
    public Transaction getTransactionByID (int id) throws SQLException{
        return transactionDAO.getTransactionByID(id);
    }
    
    public List<Transaction> getTransactionsByUserID(String id) throws SQLException{
        List resultList = null;
        if(!id.isEmpty()){
            resultList = transactionDAO.searchByUserID(id);
        }
        return resultList;
    }
    
    public List<Transaction> getTransactionByType(String type) throws SQLException{
//        List resultList = null;
//        if(!type.isEmpty()){
//            resultList = transactionDAO.searchByType(type);
//        }
//        return resultList;
            return transactionDAO.searchByType(type);
    }
    
    public List<Transaction> getTransactionByTicker(String ticker) throws SQLException{
//        List resultList = null;
//        if(!ticker.isEmpty()){
//            resultList = transactionDAO.searchByTicker(ticker);
//        }
//        return resultList;
            return transactionDAO.searchByTicker(ticker);
    }
    
     public List<Transaction> getTransactionByStatus(String status) throws SQLException{
        List resultList = null;
        if(!status.isEmpty()){
            resultList = transactionDAO.searchByStatus(status);
        }
        return resultList;
    }
    
     private boolean isNullOrEmptyString(String str){
        return str == null || str.isEmpty();
    }
    
}
