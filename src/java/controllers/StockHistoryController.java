/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import constant.Message;
import constant.Role;
import constant.Url;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import dto.StockHistory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import services.StockHistoryService;
import utils.AuthUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name = "StockHistoryController", urlPatterns = {"/stockHistory"})
public class StockHistoryController extends HttpServlet {

    private StockHistoryService stockHistoryService = new StockHistoryService();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String GET_ALL_STOCK_HISTORYS = "getAllStockHistory";
    private final String GET_STOCK_HISTORY_BY_DATE = "getStockHistoryByDate";
    private final String CREATE = "create";
    private final String UPDATE = "update";
    private final String DELETE = "delete";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // dùng khi cần check author, ko thì xoá
//        if (!AuthUtils.checkAuthorization(request, response, Role.ADMIN)) {
//            return;
//        }

        String action = request.getParameter("action");
        if (action == null) {
            action = GET_ALL_STOCK_HISTORYS;
        }

        List<StockHistory> shl = null;
        String url = Url.STOCK_HISTORY_LIST_PAGE;
        switch (action) {
            case CREATE: {
                url = Url.CREATE_STOCK_HISTORY_PAGE;
                break;
            }
            // tìm object cho trang update
            case UPDATE: {
                shl = new ArrayList<>();
                shl = getAllStockHistory(request, response);
                isAdmin(request, response);
                url = Url.UPDATE_STOCK_HISTORY_PAGE;
                break;
            }
            case GET_ALL_STOCK_HISTORYS: {
                shl = getAllStockHistory(request, response);
                break;
            }
            // chức năng search
            case GET_STOCK_HISTORY_BY_DATE: {
                shl = getStockHistoryByDate(request, response);
                break;
            }
        }

        // nếu là tìm cho update thì trả về thằng đầu tiên, ko thì trả ra mảng bình thường
        
            request.setAttribute("stockHistorys", shl);
        

        // lấy mảng các String là role nếu cần hiển thị
        // request.setAttribute("roleList", Role.values());
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        if (!AuthUtils.checkAuthorization(request, response, Role.ADMIN)) {
//            return;
//        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        String url = Url.STOCK_HISTORY_LIST_PAGE;
        try {
            switch (action) {
                case CREATE: {
                    create(request, response);
                    url = Url.CREATE_STOCK_HISTORY_PAGE;
                    break;
                }
                case UPDATE: {
                    update(request, response);
                    url = Url.UPDATE_STOCK_HISTORY_PAGE;
                    List<StockHistory> shl = new ArrayList<>();
                    shl = getAllStockHistory(request, response);
                    request.setAttribute("stockHistorys", shl);
                    break;
                }
                case DELETE: {
                    delete(request, response);
                    // lấy full để sau khi delete vẫn ở trang list cũ
                    request.setAttribute("stockHistorys", stockHistoryService.getAllStockHistory());
                    break;
                }
            }

//            request.setAttribute("roleList", Role.values());
            request.getRequestDispatcher(url).forward(request, response);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
            request.getRequestDispatcher(Url.ERROR_PAGE).forward(request, response);
        }
    }

    private void isAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (AuthUtils.hasRole(request, Role.ADMIN)) {
            request.setAttribute("can", true);

        } else {
            request.setAttribute("ERRMSG", Message.DO_NOT_HAVE_PERMISION);
        }
    }

    private List<StockHistory> getAllStockHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            return stockHistoryService.getAllStockHistory();
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<StockHistory> getStockHistoryByDate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String date1 = request.getParameter("date1");
            LocalDate _date1 = LocalDate.parse(date1, formatter);
            String date2 = request.getParameter("date2");
            LocalDate _date2 = LocalDate.parse(date2, formatter);

            return stockHistoryService.getStockHistoryByDate(_date1, _date2);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NumberFormatException {
        String ticker = request.getParameter("ticker");
        String date = request.getParameter("date");
        LocalDate _date = LocalDate.parse(date, formatter);
        String open = request.getParameter("open");
        LocalDate _open = LocalDate.parse(open, formatter);
        String close = request.getParameter("close");
        LocalDate _close = LocalDate.parse(close, formatter);
        float high = Float.parseFloat(request.getParameter("high"));
        float low = Float.parseFloat(request.getParameter("low"));

        String message = stockHistoryService.create(ticker, _date, _open, _close, high, low);

        request.setAttribute("MSG", message);
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NumberFormatException {
        String date = request.getParameter("date");
        LocalDate _date = LocalDate.parse(date, formatter);
        float high = Float.parseFloat(request.getParameter("high"));
        float low = Float.parseFloat(request.getParameter("low"));

        String message = stockHistoryService.update( _date, high, low);
        request.setAttribute("can", true);
        request.setAttribute("MSG", message);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NumberFormatException {
        String ticker = request.getParameter("ticker");
        String date = request.getParameter("date");
        LocalDate _date = LocalDate.parse(date, formatter);

        String message = stockHistoryService.delete(ticker, _date);

        request.setAttribute("MSG", message);
    }

}
