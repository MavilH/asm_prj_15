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
import java.util.List;
import services.AlertService;
import utils.AuthUtils;
import dto.Alert;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AlertController", urlPatterns = {"/alert"})
public class AlertController extends HttpServlet {

    private AlertService alertService = new AlertService();

    private final String CREATE = "create";
    private final String GET_ALL_ALERTS = "getAllUsers";
    private final String GET_ALERTS_BY_TICKER = "getAlertsByTicker";
    private final String GET_ALERTS_BY_DIRECTION = "getAlertsByDirection";
    private final String GET_ALERTS_BY_STATUS = "getAlertsByStatus";
    private final String UPDATE = "update";
    private final String DELETE = "delete";
    private final String GET_ALERT_BY_ID = "getAlertById";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.checkAuthorization(request, response, Role.ADMIN, Role.STAFF)) {
            return;
        }

        String action = request.getParameter("action");
        if (action == null || action.equals("")) {
            action = GET_ALL_ALERTS;
        }

        List<Alert> alerts = null;
        String url = Url.ALERT_LIST_PAGE;
        switch (action) {
            case CREATE: {
                url = Url.CREATE_ALERT_PAGE;
                break;
            }
            case UPDATE: {
                alerts = new ArrayList<>();
                alerts.add(getAlertrByID(request, response));
                url = Url.UPDATE_ALERT_PAGE;
                break;
            }
            case GET_ALL_ALERTS: {
                alerts = getAllAlerts(request, response);
                break;
            }

            case GET_ALERTS_BY_TICKER: {
                alerts = getAlertsByTicker(request, response);
                break;
            }
            case GET_ALERTS_BY_DIRECTION: {
                alerts = getAlertsByDirection(request, response);
                break;
            }
            case GET_ALERTS_BY_STATUS: {
                alerts = getAlertsByStatus(request, response);
                break;
            }
        }

        if (action.equals(UPDATE)) {
            request.setAttribute("alert", alerts.get(0));
        } else {
            request.setAttribute("alerts", alerts);
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
        String url = Url.ALERT_LIST_PAGE;
        try {
            switch (action) {
                case CREATE: {
                    createAlert(request, response);
                    url = Url.CREATE_ALERT_PAGE;
                    break;
                }
                case UPDATE: {
                    updateAlert(request, response);
                    url = Url.UPDATE_ALERT_PAGE;
                    break;
                }
                case DELETE: {
                    deleteAlert(request, response);
                    break;
                }
            }

            request.setAttribute("alerts", alertService.getAllAlerts());
            request.getRequestDispatcher(url).forward(request, response);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
            request.getRequestDispatcher(Url.ERROR_PAGE).forward(request, response);
        }
    }

    private Alert getAlertrByID(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NumberFormatException {
        try {
            String alertID = request.getParameter("alertID");
            int alertID_ = Integer.parseInt(alertID);
            String userID = request.getParameter("userID");
            Alert alert = alertService.searchAlertByID(alertID_);
            boolean can = false;
            if (alert == null) {
                alert = new Alert();
                request.setAttribute("ERRMSG", Message.ALERT_NOT_FOUND);
            } else {
                if (alertService.isCreator(alert, userID)) {
                    if (alertService.isInactive(alert)) {
                        can = true;
                    } else {
                        request.setAttribute("ERRMSG", Message.ALERT_STATUS_IS_ACTIVE);
                    }
                } else {
                    request.setAttribute("ERRMSG", Message.IS_NOT_CREATOR);
                }
            }
            request.setAttribute("can", can);
            return alert;
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("ERRMSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<Alert> getAllAlerts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            return alertService.getAllAlerts();
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<Alert> getAlertsByTicker(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String keySearch = request.getParameter("keySearch");
            return alertService.searchAlertsByTicker(keySearch);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<Alert> getAlertsByDirection(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String keySearch = request.getParameter("keySearch");
            return alertService.searchAlertsByDirection(keySearch);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<Alert> getAlertsByStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String keySearch = request.getParameter("keySearch");
            return alertService.searchAlertsByStatus(keySearch);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private void createAlert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String userID = request.getParameter("userID");
        String ticker = request.getParameter("ticker");
        String direction = request.getParameter("direction");
        float threshold = 0;
        boolean continous = true;
        try {
            threshold = Float.parseFloat(request.getParameter("threshold"));
        } catch (Exception ex) {
            continous = false;
        }
        if (continous) {
            String message = alertService.createAlert(userID, ticker, threshold, direction);
            if (message.equals(Message.THRESHOLD_CAN_NOT_BE_NEGATIVE) || message.equals(Message.ALL_FIELDS_ARE_REQUIRED)) {
                request.setAttribute("ERRMSG", message);
            } else {
                request.setAttribute("MSG", message);
            }
        } else {
            request.setAttribute("ERRMSG", Message.ALL_FIELDS_ARE_REQUIRED_INPUT_TRUE_FROM);
        }
    }

    private void updateAlert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NumberFormatException {
        String ticker = request.getParameter("ticker");
        String direction = request.getParameter("direction");
        int alertID = Integer.parseInt(request.getParameter("alertID"));
        String userID = request.getParameter("userID");
        float _threshold = Float.parseFloat(request.getParameter("_threshold"));
        float threshold = _threshold;
        try {
            threshold = Float.parseFloat(request.getParameter("threshold"));
        } catch (Exception ex) {
            threshold = _threshold;
        }
        String status = request.getParameter("status");
        String message = alertService.updateAlert(alertID, direction, threshold, status);
        request.setAttribute("alert", new Alert(alertID, userID, ticker, threshold, direction, status));
        if (message.equals(Message.THRESHOLD_CAN_NOT_BE_NEGATIVE)) {
            request.setAttribute("ERRMSG", message);
        } else {
            request.setAttribute("MSG", message);
        }
        if (status.equalsIgnoreCase("inactive")) {
            boolean can = true;
            request.setAttribute("can", can);
        }
    }

    private void deleteAlert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NumberFormatException {
        Alert alert = getAlertrByID(request, response);
        String message = "";
        String _can = request.getAttribute("can").toString();
        boolean can = Boolean.parseBoolean(_can);
        if (can == true) {
            message = alertService.deleteAlert(alert.getAlertID(), alert.getUserID());
        }
        request.setAttribute("MSG", message);
    }
}
