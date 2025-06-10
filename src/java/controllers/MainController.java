/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import constant.Message;
import constant.Url;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.AuthUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name="MainController", urlPatterns={"/main/*"})
public class MainController extends HttpServlet {
    
    private final String ERROR = "ERROR";
    
    private final String AUTH = "auth";
    private final String USER = "user";
    private final String STOCK = "stock";
    private final String TRANSACTION = "transaction";
    private final String ALERT = "alert";
    // thêm url của controller rồi thêm case dưới switch case
    private final String STOCK_HISTORY = "stockHistory";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String path = request.getRequestURI(); // /StockManagement/main/controller/action
        String[] parts = path.split("/"); // ''/StockManagement/main/controller/action
        
        String url = Url.ERROR_PAGE;
        try {
            if (parts.length >= 4) {
                String controller = parts[3]; // controller
                String action = parts.length >= 5 ? parts[4] : ""; // action
                // in case url is /main/controller?action=
//                if (action.isEmpty() && controller.contains("?action=")) {
//                    parts = controller.split("\\?action=");
//                    controller = parts[0]; // "controller"
//                    if (parts.length > 1) {
//                        action = parts[1]; // "action"
//                    }
//                }
                    
                if (!controller.equals(AUTH) && !action.equals("login")) {
                    if (!AuthUtils.checkAuthentication(request, response)) return;
                }
                
                switch (controller) {
                    case AUTH: {
                        url = Url.AUTH_CONTROLLER;
                        break;
                    }
                    case USER: {
                        url = Url.USER_CONTROLLER;
                        break;
                    }
                    case STOCK:{
                        url = Url.STOCK_CONTROLLER;
                        break;
                    }
                    case TRANSACTION: {
                        url = Url.TRANSACTION_CONTROLLER;
                        break;
                    }
                    case ALERT:{
                        url = Url.ALERT_CONTROLLER;
                        break;
                    }
                    case STOCK_HISTORY:{
                        url = Url.STOCK_HISTORY_CONTROLLER;
                        break;
                    }
                    default: {
                        throw new Exception();
                    }
                }
                if(!action.isEmpty()){
                    url = url + "?action=" + action;
                }
            }
        } catch (Exception e) {
            request.setAttribute("MSG", Message.CONTROLLER_NOT_FOUND);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    } 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
}
