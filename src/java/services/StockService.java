/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import constant.Message;
import dao.StockDAO;
import dto.Stock;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Huy
 */
public class StockService {
    private StockDAO stockDAO = new StockDAO();
    
    //create
    public String create(Stock stock) throws SQLException{
        if(stockDAO.isTickerExist(stock.getTicker())){
            return Message.STOCK_TICKER_IS_EXISTED;
        }
        if(stock.getPrice()<=0){return Message.STOCK_PRICE_UNDER_LIMIT;}
        return stockDAO.create(stock);
    }
    
    //update
    public String update(Stock stock) throws SQLException {
        if(stockDAO.update(stock) == 0){
            return Message.UPDATE_STOCK_FAILED;
        }
        
        return Message.UPDATE_STOCK_SUCCESSFULLY;
    }
    
    //delete
    public String delete(String ticker) throws SQLException{
        if(stockDAO.delete(ticker) == 0){
            return Message.STOCK_NOT_FOUND;
        }
        
        return Message.DELETE_STOCK_SUCCESSFULLY;
    }
    
    private boolean isNullOrEmptyString(String str){
        return str == null || str.isEmpty();
    }
    
    private boolean checkConfirmPassword(String pwd, String confirmPwd) {
        return pwd.equals(confirmPwd);
    }

    
    //get all
    public List<Stock> getAllStock() throws SQLException{
        return stockDAO.getAllStock();
    }
    
//    //find asc price
//    public List<Stock> findAllOrderByPriceAsc()
//            throws SQLException  {
//        return stockDAO.findAllOrderByPriceAsc();
//    }
//    
    //find desc price
    public List<Stock> findAllOrderByPrice(String dir)
            throws SQLException  {
        return stockDAO.findAllOrderByPrice(dir);
    }
    
    //search by price
    public List<Stock> searchbyPrice(double min,double max) throws SQLException {
        if(min > max){
            double temp = min;
            min = max;
            max = temp;
        }
        return stockDAO.searchByPrice(min, max);
    }
    
    //search by name
    public List<Stock> searchByName(String name) throws SQLException{
        return stockDAO.searchByName(name);
    }
    
    //search by ticker
    public List<Stock> searchByTicker(String ticker) throws SQLException{
        return stockDAO.searchByTicker(ticker);
    }
    
    //search by sector
    public List<Stock> searchBySector(String sector) throws SQLException{
        return stockDAO.searchBySector(sector);
    }
}
