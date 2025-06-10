<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>News List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            body {
                padding: 30px;
            }
        </style>
    </head>
    <body>
        <div class="container bg-white p-4 rounded shadow-sm">
            <h2>Welcome, <c:out value="${sessionScope.currentUser.fullName}"/></h2>

            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/main/stockHistory" class="btn btn-primary me-2">StockHistory CRUD</a>

                <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline">
                    <button type="submit" name="action" value="Logout" class="btn btn-danger">Logout</button>
                </form>
            </div>

            <form action="${pageContext.request.contextPath}/main/stockHistory" method="GET" class="mb-3">
                <button type="submit" name="action" value="create" class="btn btn-success">Create StockHistory</button>
            </form>
            <form 
                action="${pageContext.request.contextPath}/main/stockHistory/update" 
                method="GET"
                >
                <button type="submit" class="btn btn-sm btn-warning">Update</button>
            </form>
            <br/>

            <form action="${pageContext.request.contextPath}/main/stockHistory/getStockHistoryByDate" method="GET" class="row g-2 mb-4" style="background-color: pink; padding:  10px">

                <label for="day1" class="col-md-4">First Day</label>
                <input id="day1" name="date1" 
                       placeholder="Enter First Day..." class="form-control col-md-4" required />
                <label for="day2" class="col-md-4">Second Day</label>
                <input id="day2" name="date2" 
                       placeholder="Enter Second Day..." class="form-control col-md-4" required />

                <!-- nếu cần tìm theo thuộc tính khác nhau thì xài cái dưới, nhớ chỉnh lại url nếu xài cái dưới
                    <div class="col-md-4">
                        <select name="action" class="form-select">
                            <option value="getNewsByContent">Search by Content</option>
                            <option value="getNewsByTitle">Search by Title</option>
                        </select> 

                    </div>
                    <div class="col-md-4">
                        <input type="text" class="form-control" id="keySearch" name="keySearch" placeholder="Search..." required />
                    </div>
                -->
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">Search</button>
                </div>
            </form>


            <br />
            <c:if test="${empty stockHistorys}">
                <div class="alert alert-warning">No matching stock history found!</div>
            </c:if>

            <c:if test="${not empty stockHistorys}">
                <table class="table table-bordered table-hover">
                    <thead class="table-light">
                        <tr>
                            <!-- chỉnh các tên cột -->
                            <th>No</th>
                            <th>StockHistory ID</th>
                            <th>Ticker</th>
                            <th>Date</th>
                            <th>Open</th>
                            <th>Close</th>
                            <th>High</th>
                            <th>Low</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${requestScope.stockHistorys}" varStatus="st">
                            <tr>
                                <td>${st.count}</td>
                                <td>${item.id}</td> <!-- để id do của dto là id chứ ko phải newsId -->
                                <td>${item.ticker}</td>
                                <td>${item.date}</td>
                                <td>${item.open}</td>
                                <td>${item.close}</td>
                                <td>${item.high}</td>
                                <td>${item.low}</td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <form 
                action="${pageContext.request.contextPath}/main/stockHistory/delete" 
                method="POST"
                style="background-color: pink; padding:  10px; margin-bottom:10px ">
                <label for="ticker" class="col-md-4">Ticker</label>
                <input id="ticker" name="ticker" 
                       placeholder="Enter ticker..." class="form-control col-md-4" required />
                <label for="date" class="col-md-4">Date</label>
                <input id="date" name="date" 
                       placeholder="Enter date..." class="form-control col-md-4" required />
                <button type="submit" class="btn btn-sm btn-warning">Delete</button>
            </form>
            <!-- Back to home -->
            <c:choose>
                <c:when test="${sessionScope.currentUser.role.name() == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/admin.jsp" class="btn btn-outline-primary mt-3">Back to admin page</a>
                </c:when>
                <c:when test="${sessionScope.currentUser.role.name() == 'STAFF'}">
                    <a href="${pageContext.request.contextPath}/welcome.jsp" class="btn btn-outline-primary mt-3">Back to home</a>
                </c:when>
            </c:choose>
        </div>
    </body>
</html>
