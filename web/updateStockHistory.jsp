<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Update News Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="container bg-white p-4 rounded shadow-sm">
            <h3>Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h3>

            <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST">
                <input type="submit" class="btn btn-danger logout-btn" value="LOGOUT" />
            </form>

            <h3>Update User</h3>
            <p class="message">${requestScope.MSG}</p>
            <hr />
            

                <h1>${requestScope.ERRMSG}</h1>
            <c:if test="${!empty can}">
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
            <form action="${pageContext.request.contextPath}/main/stockHistory/update" method="POST">
                <label for="content">Date</label>
                <input type="text" id="content" name="date" placeholder="Enter content" required class="form-control" />    
                <label for="content">High</label>
                <input type="number" id="content" name="high" placeholder="Enter content" required class="form-control" />
                <label for="content">Low</label>
                <input type="number" id="content" name="low" placeholder="Enter content" required class="form-control" />               
                <button type="submit" class="btn btn-primary w-100">Update</button>
            </form>
            <a href="${pageContext.request.contextPath}/main/stockHistory" class="back-link">Back to stock history list page</a>
        </div>

    </body>
</html>