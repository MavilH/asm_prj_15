/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import constant.Message;
import constant.Role;
import constant.Url;
import dto.Transaction;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.TransactionService;
import utils.AuthUtils;

/**
 *
 * @author ngogi
 */
@WebServlet(name = "TransactionController", urlPatterns = {"/transaction"})
public class TransactionController extends HttpServlet {

    private TransactionService transactionService = new TransactionService();

    private final String CREATE = "create";
    private final String GET_ALL_TRANSACTIONS = "getAllTransactions";
    private final String GET_TRANSACTION_BY_ID = "getTransactionByID";
    private final String GET_TRANSACTIONS_BY_ID = "getTransactionsByID";
    private final String GET_TRANSACTIONS_BY_USERID = "getTransactionsByUserID";
    private final String GET_TRANSACTION_BY_TYPE = "getTransactionByType";
    private final String GET_TRANSACTION_BY_TICKER = "getTransactionByTicker";
    private final String GET_TRANSACTION_BY_STATUS = "getTransactionByStatus";
    private final String UPDATE = "update";
    private final String DELETE = "delete";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.checkAuthorization(request, response, Role.ADMIN, Role.STAFF)) {
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = GET_ALL_TRANSACTIONS;
        }

        List<Transaction> transactions = null;
        String url = Url.TRANSACTION_LIST_PAGE;
        switch (action) {
            case CREATE: {
                url = Url.CREATE_TRANSACTION_PAGE;
                break;
            }
            case UPDATE: {
                transactions = new ArrayList<>();
                transactions.add(getTransactionByID(request, response));
                url = Url.UPDATE_TRANSACTION_PAGE;
                break;
            }
            case GET_ALL_TRANSACTIONS: {
                transactions = getAllTransactions(request, response);
                break;
            }
            case GET_TRANSACTIONS_BY_USERID: {
                transactions = getTransactionsByUserID(request, response);
                break;
            }
            case GET_TRANSACTION_BY_TYPE: {
                transactions = getTransactionByType(request, response);
                break;
            }
            case GET_TRANSACTION_BY_TICKER: {
                transactions = getTransactionByTicker(request, response);
                break;
            }
            case GET_TRANSACTION_BY_STATUS: {
                transactions = getTransactionByStatus(request, response);
                break;
            }
        }

        if (action.equals(UPDATE)) {
            request.setAttribute("transaction", transactions.get(0));
        } else {
            request.setAttribute("transactions", transactions);
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.checkAuthorization(request, response, Role.ADMIN, Role.STAFF)) {
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        String url = Url.TRANSACTION_LIST_PAGE;
        try {
            switch (action) {
                case CREATE: {
                    createTransaction(request, response);
                    url = Url.CREATE_TRANSACTION_PAGE;
                    break;
                }
                case UPDATE: {
                    updateTransaction(request, response);
                    url = Url.UPDATE_TRANSACTION_PAGE;
                    break;
                }
                case DELETE: {
                    deleteTransaction(request, response);
                    break;
                }
            }

            request.setAttribute("transactions", transactionService.getAllTransactions());
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
            request.getRequestDispatcher(Url.ERROR_PAGE).forward(request, response);
        }
    }

    private List<Transaction> getAllTransactions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            return transactionService.getAllTransactions();
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private Transaction getTransactionByID(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            boolean can = true;
            int id = Integer.parseInt(request.getParameter("id"));
            Transaction transaction = transactionService.getTransactionByID(id);
            if (AuthUtils.hasRole(request, Role.ADMIN)) {
                request.setAttribute("can", can);
                return transaction;
            }
            String userID = request.getParameter("userID");
            if(!transaction.getUserID().equalsIgnoreCase(userID)){
                
                request.setAttribute("MSG", Message.TRANSACTION_CREATER);
            }else{
                request.setAttribute("can", can);
                
            }
            return transaction;
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<Transaction> getTransactionsByUserID(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userID = request.getParameter("keySearch");
            return transactionService.getTransactionsByUserID(userID);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<Transaction> getTransactionByType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String type = request.getParameter("keySearch");
            return transactionService.getTransactionByType(type);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<Transaction> getTransactionByTicker(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String ticker = request.getParameter("keySearch");
            return transactionService.getTransactionByTicker(ticker);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<Transaction> getTransactionByStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String status = request.getParameter("keySearch");
            return transactionService.getTransactionByStatus(status);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private void createTransaction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, Exception {

        String userID = request.getParameter("userID");
        String ticker = request.getParameter("ticker");
        String type = request.getParameter("type");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        float price = Float.parseFloat(request.getParameter("price"));
        String status = request.getParameter("status");

        String message = transactionService.createTransaction(userID, ticker, type, quantity, price, status);

        request.setAttribute("MSG", message);
    }

    public void updateTransaction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String userID = request.getParameter("userID");
        String ticker = request.getParameter("ticker");
        String type = request.getParameter("type");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        float price = Float.parseFloat(request.getParameter("price"));
        String status = request.getParameter("status");

        String message = transactionService.updateTransaction(id, 
//                userID, ticker, type,
                quantity, price, status);
        request.setAttribute("can", true);
        Transaction transaction = new Transaction(id, userID, ticker, type, quantity, price, status);
        request.setAttribute("transaction", transaction);

        request.setAttribute("MSG", message);

    }

    public void deleteTransaction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, Exception {
        Transaction transaction = getTransactionByID(request, response);
        String message = "";
        if(request.getAttribute("can") != null){
            message = transactionService.deleteTransaction(transaction.getId());
        }else{
            message = Message.IS_NOT_CREATOR_TRANSACTION;
        }
        
        request.setAttribute("MSG", message);
    }

}
