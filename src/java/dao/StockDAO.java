package dao;

import constant.Message;
import constant.Role;
import dto.Stock;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {
    private final String GET_STOCK_BY_TICKER = "SELECT * FROM tblStocks WHERE ticker LIKE ?";
    private final String GET_ALL_STOCK = "SELECT * FROM tblStocks";
    private final String ADD_STOCK = "INSERT INTO tblStocks(ticker, name, sector, price, status) VALUES(?,?,?,?,?)";
    private final String DELETE_STOCK = "DELETE FROM tblStocks WHERE ticker LIKE ?";
    private final String SEARCH_BY_PRICE = "SELECT * FROM tblStocks WHERE price BETWEEN ? AND ?";
    private final String UPDATE = "UPDATE tblStocks SET name=?, sector=?, price=?, status=? WHERE ticker=?";
    private final String ORDER_BY_PRICE = "SELECT * FROM tblStocks ORDER BY price ";
    private final String SEARCH_BY_NAME = "SELECT * FROM tblStocks WHERE name LIKE ?";
    private final String SEARCH_BY_TICKER = "SELECT * FROM tblStocks WHERE ticker LIKE ?";
    private final String SEARCH_BY_SECTOR = "SELECT * FROM tblStocks WHERE sector LIKE ?";

    public boolean isTickerExist(String ticker) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_STOCK_BY_TICKER)) {
            stm.setString(1, ticker + "%");
            return stm.executeQuery().next();
        }
    }

    public String create(Stock s) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(ADD_STOCK)) {

            ps.setString(1, s.getTicker().trim());
            ps.setString(2, s.getName().trim());
            ps.setString(3, s.getSector().trim());
            ps.setFloat(4, s.getPrice());
            ps.setBoolean(5, s.isStatus());

            int result = ps.executeUpdate();
            return (result > 0) ? Message.CREATE_STOCK_SUCCESSFULLY : Message.CREATE_STOCK_FAILED;
        }
    }


    public int delete(String ticker) throws SQLException {
        try ( Connection conn = DBContext.getConnection();  PreparedStatement stm = conn.prepareStatement(DELETE_STOCK)) {
            stm.setString(1, ticker + "%");
            return stm.executeUpdate();
        }
    }

    public List<Stock> searchByName(String name) throws SQLException {
        return searchByKeyword(SEARCH_BY_NAME, name);
    }

    public List<Stock> searchByTicker(String ticker) throws SQLException {
        return searchByKeyword(SEARCH_BY_TICKER, ticker);
    }

    public List<Stock> searchBySector(String sector) throws SQLException {
        return searchByKeyword(SEARCH_BY_SECTOR, sector);
    }

    private List<Stock> searchByKeyword(String sql, String keyword) throws SQLException {
        List<Stock> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public List<Stock> searchByPrice(double min, double max) throws SQLException {
        List<Stock> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(SEARCH_BY_PRICE)) {
            ps.setDouble(1, min);
            ps.setDouble(2, max);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public List<Stock> getAllStock() throws SQLException {
        List<Stock> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ALL_STOCK);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public List<Stock> findAllOrderByPrice(String direction) throws SQLException {
        List<Stock> list = new ArrayList<>();
        String sql = ORDER_BY_PRICE + direction;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public int update(Stock stock) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(UPDATE)) {

            stm.setString(1, stock.getName());
            stm.setString(2, stock.getSector());
            stm.setFloat(3, stock.getPrice());
            stm.setBoolean(4, stock.isStatus());
            stm.setString(5, stock.getTicker());

            return stm.executeUpdate(); 
        }
    }


    private Stock mapRow(ResultSet rs) throws SQLException {
        return new Stock(
                rs.getString("ticker"),
                rs.getString("name"),
                rs.getString("sector"),
                rs.getFloat("price"),
                rs.getBoolean("status")
        );
    }
}
