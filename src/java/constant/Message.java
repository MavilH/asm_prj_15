/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constant;

/**
 *
 * @author Admin
 */
public class Message {
    // System
    public static final String SYSTEM_ERROR = "System error";
    public static final String UNAUTHENTICATION = "You are not authenticated. Please log in.";
    public static final String UNAUTHORIZED = "You do not have permission to access this function.";
    public static final String CONTROLLER_NOT_FOUND = "Controller not found.";
    public static final String ALL_FIELDS_ARE_REQUIRED = "All fields are required.";
    
    // User
    public static final String PASSWORD_NOT_MATCH_CONFIRM_PASSWORD = "Password not match confirm password!";
    public static final String PASSWORD_AND_CONFIRM_PASSWORD_ARE_REQUIRED = "Password and confirm password are required";
    public static final String OLD_PASSWORD_ARE_REQUIRED = "Old password is required";
    public static final String ROLE_ID_NOT_FOUND = "Role ID not found";
    public static final String CREATE_USER_SUCCESSFULLY = "Create user successfully";
    public static final String CREATE_USER_FAILED = "Create user failed";
    public static final String USER_ID_IS_EXISTED = "User ID is existed";
    public static final String UPDATE_USER_SUCCESSFULLY = "Update user successfully";
    public static final String UPDATE_USER_FAILED = "Update user failed";
    public static final String DELETE_USER_SUCCESSFULLY = "Delete user successfully";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String INVALID_USER_ID_OR_PASSWORD = "Invalid user ID or password.";

    // Stock
    public static final String EMTY_STOCK_LIST = "Not found any stock.";
    public static final String EMTY_STOCK_PRICE = "Not found any stock in range.";
    public static final String STOCK_NOT_FOUND = "Stock not found";
    public static final String TICKER_NOT_FOUND = "Ticker not found";
    public static final String SECTOR_NOT_FOUND = "Sector not found";
    public static final String STOCK_TICKER_IS_EXISTED = "Stock ticker is existed";
    public static final String STOCK_PRICE_UNDER_LIMIT = "Price is under limit";
    public static final String CREATE_STOCK_SUCCESSFULLY = "Create stock successfully";
    public static final String CREATE_STOCK_FAILED = "Create stock failed";
    public static final String UPDATE_STOCK_FAILED = "Update stock failed";
    public static final String UPDATE_STOCK_SUCCESSFULLY = "Update stock successfully";
    public static final String DELETE_STOCK_FAILED = "Delete stock failed";
    public static final String DELETE_STOCK_SUCCESSFULLY = "Delete stock successfully";
    public static final String STOCK_STATUS_RONGE = "Invalid valued";
    
    // Transaction
    public static final String TRANSACTION_ID_IS_EXISTED = "Transaction ID is existed";
    public static final String CREATE_TRANSACTION_SUCCESSFULLY = "Create Transaction successfully";
    public static final String CREATE_TRANSACTION_FAILED = "Create Transaction failed";
    public static final String UPDATE_TRANSACTION_SUCCESSFULLY = "UPDATE Transaction successfully";
    public static final String UPDATE_TRANSACTION_FAILED = "UPDATE Transaction failed";
    public static final String DELETE_TRANSACTION_SUCCESSFULLY = "Delete Transaction successfully";
    public static final String TRANSACTION_NOT_FOUND = "TRANSACTION not found";
    public static final String IS_NOT_CREATOR_TRANSACTION = "You are not the creator of this transaction";
    public static final String UPDATE_TRANSACTION_FAILED_QUANTITY = "UPDATE Transaction failed (quantity > 0)";
    public static final String UPDATE_TRANSACTION_FAILED_PRICE = "UPDATE Transaction failed (price > 0)";
    public static final String TRANSACTION_CREATER = "you are not a creater of this tracsaction";
    public static final String QUANTITY_CAN_NOT_BE_NEGATIVE = "Quantity can not be negetive";
    public static final String PRICE_CAN_NOT_BE_NEGATIVE = "Price can not be negetive";

    
    // Alert
    public static final String CREATE_ALERT_SUCCESSFULLY = "Create alert successfully";
    public static final String CREATE_ALERT_FAILED = "Create alert failed";
    public static final String UPDATE_ALERT_SUCCESSFULLY = "Update alert successfully";
    public static final String UPDATE_ALERT_FAILED = "Update alert failed";
    public static final String DELETE_ALERT_SUCCESSFULLY = "Delete alert successfully";
    public static final String DELETE_ALERT_FAILED = "Delete alert failed: ";
    public static final String ALERT_NOT_FOUND = "Alert not found";
    public static final String ALERT_FOUND = "Alert found";
    public static final String IS_NOT_CREATOR = "You are not the creator of this alert";
    public static final String THRESHOLD_CAN_NOT_BE_NEGATIVE = "Thrshold can not be negative";
    public static final String ALERT_STATUS_IS_ACTIVE = "Alert status is active, can not update or delete";
    public static final String ALL_FIELDS_ARE_REQUIRED_INPUT_TRUE_FROM = "All fields are required and please input true form of fields";
    
    // Sample
    public static final String CREATE_STOCK_HISTORY_SUCCESSFULLY = "Create stock history successfully";
    public static final String CREATE_STOCK_HISTORY_FAILED = "Create stock history failed";
    public static final String UPDATE_STOCK_HISTORY_SUCCESSFULLY = "Update stock history successfully";
    public static final String UPDATE_STOCK_HISTORY_FAILED = "Update stock history failed";
    public static final String DELETE_STOCK_HISTORY_SUCCESSFULLY = "Delete stock history successfully";
    public static final String DELETE_STOCK_HISTORY_FAILED = "Delete stock history failed: ";
    public static final String STOCK_HISTORY_NOT_FOUND = "Stock history not found";
    public static final String STOCK_HISTORY_FOUND = "Stock history found";
    
    public static final String DO_NOT_HAVE_PERMISION = "You do not have permision to do this action";
}
