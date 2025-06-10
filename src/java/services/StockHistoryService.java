/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constant.Message;
import dao.StockHistoryDAO;
import dao.StockDAO;
import dto.StockHistory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class StockHistoryService {
    private StockDAO stockDAO = new StockDAO();
    private StockHistoryDAO shDao = new StockHistoryDAO();

    public List<StockHistory> getAllStockHistory() throws SQLException {
        return shDao.getAllStockHistorys();
    }

    public StockHistory getStockHistoryByID(int id) throws SQLException {
        return shDao.getStockHistoryById(id);
    }
    public List<StockHistory> getStockHistoryByDate(LocalDate date1, LocalDate date2) throws SQLException {
        return shDao.getStockHistorysByDate(date1, date2);
    }
    
   
    public String create (String ticker, LocalDate date, LocalDate open, LocalDate close, float high, float low) throws SQLException{
        // check empty
//        if(isNullOrEmptyString(ticker))
//                {
//            return Message.ALL_FIELDS_ARE_REQUIRED;
//        }
        
        // gọi DAO của table khác nếu có khoá ngoại
        if(!stockDAO.isTickerExist(ticker)){
            return Message.STOCK_NOT_FOUND;
        }
        
        // kiểm tra số âm nếu có
        
        // tạo và trả về kết quả
        if(shDao.create(ticker, date, open, close, high, low) == 0){
            return Message.CREATE_STOCK_HISTORY_FAILED;
        }
        
        return Message.CREATE_STOCK_HISTORY_SUCCESSFULLY;
    }
    
    public String update(LocalDate date, float high, float low) 
            throws SQLException{

        
        // nếu rỗng hay null thì lấy value cũ

        
        // update
        if(shDao.update(date, high, low) == 0){
            return Message.UPDATE_STOCK_HISTORY_FAILED;
        }
        
        return Message.UPDATE_STOCK_HISTORY_SUCCESSFULLY;
    }
    
    public String delete(String ticker, LocalDate date) throws SQLException{
        if(shDao.delete(ticker, date) == 0){
            return Message.STOCK_HISTORY_NOT_FOUND;
        }
        
        return Message.DELETE_STOCK_HISTORY_SUCCESSFULLY;
    }
    
    private boolean isNullOrEmptyString(String str){
        return str == null || str.isEmpty();
    }
}
