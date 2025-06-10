package controllers;

import constant.Message;
import constant.Role;
import constant.Url;
import dto.Stock;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.StockService;
import utils.AuthUtils;

@WebServlet(name = "StockController", urlPatterns = {"/stock"})
public class StockController extends HttpServlet {
    private StockService stockService = new StockService();

    private final String CREATE = "create";
    private final String GET_ALL = "getAll";
    private final String ORDER_BY_PRICE = "orderByPrice";
    private final String SEARCH_BY_PRICE = "searchByPrice";
    private final String SEARCH_BY_NAME = "searchByName";
    private final String SEARCH_BY_TICKER = "searchByTicker";
    private final String SEARCH_BY_SECTOR = "searchBySector";
    private final String UPDATE = "update";
    private final String DELETE = "delete";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.checkAuthorization(request, response, Role.ADMIN, Role.STAFF)) {
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = GET_ALL;
        String url = Url.STOCK_LIST_PAGE;
        List<Stock> stocks = null;
        switch (action) {
            case GET_ALL:
                stocks = getAllStocks(request, response);
                break;
            case ORDER_BY_PRICE:
                stocks = findAllOrderByPrice(request, response);
                break;
            case SEARCH_BY_PRICE:
                stocks = searchByPrice(request, response);
                break;
            case SEARCH_BY_NAME:
                stocks = searchByName(request, response);
                break;
            case SEARCH_BY_TICKER:
                stocks = searchByTicker(request, response);
                break;
            case SEARCH_BY_SECTOR:
                stocks = searchBySector(request, response);
                break;
            case "filter":
                stocks = filterStocks(request, response);
                break;
            case UPDATE:
                url = Url.UPDATE_STOCK_PAGE;
                loadUpdateStockForm(request, response);
                break;
            case CREATE: {
                url = Url.CREATE_STOCK_PAGE;
                break;
            }
        }

        request.setAttribute("stocks", stocks);
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.checkAuthorization(request, response, Role.ADMIN)) {
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "";
        String url = Url.STOCK_LIST_PAGE;

        try {
            switch (action) {
                case CREATE:
                    createStock(request, response);
                    url = Url.CREATE_STOCK_PAGE;
                    break;
                case UPDATE:
                    updateStock(request, response);
                    Stock stock = searchByTicker(request, response).get(0);
                    request.setAttribute("stock", stock);
                    url = Url.UPDATE_STOCK_PAGE;
                    break;
                case DELETE:
                    delete(request, response);
                    break;
            }

            request.setAttribute("stocks", stockService.getAllStock());
            request.getRequestDispatcher(url).forward(request, response);
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
            request.getRequestDispatcher(Url.ERROR_PAGE).forward(request, response);
        }
    }

    private List<Stock> getAllStocks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            return stockService.getAllStock();
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private List<Stock> findAllOrderByPrice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String order = request.getParameter("order");
            if (order == null || (!order.equalsIgnoreCase("ASC") && !order.equalsIgnoreCase("DESC"))) {
                order = "ASC";
            }
            return stockService.findAllOrderByPrice(order);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.EMTY_STOCK_LIST);
        }
        return null;
    }

    private List<Stock> searchByPrice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String minStr = request.getParameter("minPrice");
            String maxStr = request.getParameter("maxPrice");

            float min = (minStr == null || minStr.isEmpty()) ? 0 : Float.parseFloat(minStr);
            float max = (maxStr == null || maxStr.isEmpty()) ? Float.MAX_VALUE : Float.parseFloat(maxStr);

            if (min > max) {
                float tmp = min;
                min = max;
                max = tmp;
            }

            return stockService.searchbyPrice(min, max);
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.EMTY_STOCK_PRICE);
        }
        return null;
    }

    private List<Stock> searchByName(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            return stockService.searchByName(name);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.STOCK_NOT_FOUND);
        }
        return null;
    }

    private List<Stock> searchByTicker(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String ticker = request.getParameter("ticker");
            return stockService.searchByTicker(ticker);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.TICKER_NOT_FOUND);
        }
        return null;
    }

    private List<Stock> searchBySector(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String sector = request.getParameter("sector");
            return stockService.searchBySector(sector);
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SECTOR_NOT_FOUND);
        }
        return null;
    }

    private void createStock(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String ticker = request.getParameter("ticker");
        String name = request.getParameter("name");
        String sector = request.getParameter("sector");
        String priceStr = request.getParameter("price");
        String statusStr = request.getParameter("status");

        if (ticker == null || ticker.isEmpty() || name == null || name.isEmpty() ||
            sector == null || sector.isEmpty() || priceStr == null || priceStr.isEmpty() || statusStr == null) {
            request.setAttribute("MSG", Message.CREATE_STOCK_FAILED);
            return;
        }

        float price;
        try {
            price = Float.parseFloat(priceStr);
            if (price <= 0) {
                request.setAttribute("MSG", Message.STOCK_PRICE_UNDER_LIMIT);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("MSG", Message.STOCK_PRICE_UNDER_LIMIT);
            return;
        }

        boolean status = statusStr.equals("1");

        Stock stock = new Stock(ticker, name, sector, price, status);
        String message = stockService.create(stock);
        request.setAttribute("MSG", message);
    }

    private void updateStock(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String ticker = request.getParameter("ticker");
        String name = request.getParameter("name");
        String sector = request.getParameter("sector");
        String priceStr = request.getParameter("price");
        String statusInput = request.getParameter("status");
        if (ticker == null || name == null || sector == null || statusInput == null ||
            ticker.isEmpty() || name.isEmpty() || sector.isEmpty() || priceStr == null || priceStr.isEmpty()) {
            request.setAttribute("MSG", Message.CREATE_STOCK_FAILED);
            return;
        }

        float price;
        try {
            price = Float.parseFloat(priceStr);
            if (price <= 0) {
                request.setAttribute("MSG", Message.STOCK_PRICE_UNDER_LIMIT);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("MSG", Message.STOCK_PRICE_UNDER_LIMIT);
            return;
        }

        boolean status;
        if ("executed".equalsIgnoreCase(statusInput)) {
            status = true;
        } else if ("pending".equalsIgnoreCase(statusInput)) {
            status = false;
        } else {
            request.setAttribute("MSG", Message.STOCK_STATUS_RONGE);
            return;
        }

        Stock updatedStock = new Stock(ticker, name, sector, price, status);
        String message = stockService.update(updatedStock);

        request.setAttribute("MSG", message);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String ticker = request.getParameter("ticker");
        String message = stockService.delete(ticker);
        request.setAttribute("MSG", message);
    }

    private List<Stock> filterStocks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String sector = request.getParameter("sector");
            String ticker = request.getParameter("ticker");

            if ((name == null || name.isEmpty()) &&
                (sector == null || sector.isEmpty()) &&
                (ticker == null || ticker.isEmpty())) {
                return stockService.getAllStock();
            }

            if (ticker != null && !ticker.isEmpty()) {
                return stockService.searchByTicker(ticker);
            } else if (name != null && !name.isEmpty()) {
                return stockService.searchByName(name);
            } else {
                return stockService.searchBySector(sector);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
        }
        return null;
    }

    private void loadUpdateStockForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String ticker = request.getParameter("ticker");

        try {
            List<Stock> results = stockService.searchByTicker(ticker);
            if (!results.isEmpty()) {
                request.setAttribute("stock", results.get(0));
            } else {
                request.setAttribute("MSG",Message.STOCK_NOT_FOUND);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("MSG", Message.UPDATE_STOCK_FAILED);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}

