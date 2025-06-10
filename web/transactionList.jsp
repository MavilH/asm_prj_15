<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Transaction List Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            input, select {
                margin-bottom: 0px;
            }
        </style>
    </head>

    <body>
        <div class="container bg-white p-4 rounded shadow-sm">
            <h2>Welcome, <c:out value="${sessionScope.currentUser.fullName}"/></h2>

            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/main/transaction" class="btn btn-primary me-2">Transaction CRUD</a>

                <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline">
                    <button type="submit" name="action" value="Logout" class="btn btn-danger">Logout</button>
                </form>
            </div>

            <form action="${pageContext.request.contextPath}/main/transaction" method="GET" class="mb-3">
                <button type="submit" name="action" value="create" class="btn btn-success">Create Transaction</button>
            </form>

            <!-- Search form -->
            <form action="${pageContext.request.contextPath}/main/transaction" method="GET" class="mb-3">
                <div class="row g-2 align-items-center">
                    <div class="col-md-4">
                        <select id="search" name="action" class="form-select">
                            <option value="getTransactionByTicker">Search by ticker</option>
                            <option value="getTransactionByType">Search by type</option>
                            <option value="getTransactionByStatus">Search by status</option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <input type="text" id="keySearch" name="keySearch" class="form-control" placeholder="Search..." />
                    </div>
                    <div class="col-md-4" id="type-options" style="display:none;">
                        <select id="typeSearch" class="form-select">
                            <option value="buy">buy</option>
                            <option value="sell">sell</option>
                        </select>
                    </div>
                    <div class="col-md-4" id="status-options" style="display:none;">
                        <select id="statusSearch" class="form-select">
                            <option value="executed">Executed</option>
                            <option value="pending">Pending</option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <button type="submit" class="btn btn-success">Search</button>
                    </div>
                </div>
            </form>

            <c:if test="${empty transactions}">
                <div class="alert alert-warning">No matching transactions found!</div>
            </c:if>

            <c:if test="${not empty requestScope.MSG}">
                <div class="alert alert-success">${requestScope.MSG}</div>
            </c:if>

            <table class="table table-bordered table-striped">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>User ID</th>
                        <th>Ticker</th>
                        <th>Type</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="transaction" items="${requestScope.transactions}">
                        <tr>
                            <td>${transaction.id}</td>
                            <td>${transaction.userID}</td>
                            <td>${transaction.ticker}</td>
                            <td>${transaction.type}</td>
                            <td>${transaction.quantity}</td>
                            <td>${transaction.price}</td>
                            <td>${transaction.status}</td>
                            <td>
                                <div class="d-flex gap-2">
                                    <form action="${pageContext.request.contextPath}/main/transaction/update" method="GET">
                                        <input type="hidden" name="id" value="${transaction.id}">
                                        <input type="hidden" name="userID" value="${sessionScope.currentUser.userID}">
                                        <button type="submit" name="action" value="update" class="btn btn-warning btn-sm">Update</button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/main/transaction/delete" method="POST">
                                        <input type="hidden" name="id" value="${transaction.id}">
                                        <input type="hidden" name="userID" value="${sessionScope.currentUser.userID}">
                                        <button type="submit" name="action" value="delete" class="btn btn-danger btn-sm">Delete</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

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
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const searchSelect = document.getElementById("search");
                const statusOptions = document.getElementById("status-options");
                const statusSearch = document.getElementById("statusSearch");
                const typeOptions = document.getElementById("type-options");
                const typeSearch = document.getElementById("typeSearch");
                const keySearch = document.getElementById("keySearch");

                function updateSearchVisibility() {
                    const value = searchSelect.value;
                    if (value === "getTransactionByStatus") {
                        statusOptions.style.display = "block";
                        typeOptions.style.display = "none";
                        keySearch.style.display = "none";
                        keySearch.value = statusSearch.value;
                    } else if (value === "getTransactionByType") {
                        typeOptions.style.display = "block";
                        statusOptions.style.display = "none";
                        keySearch.style.display = "none";
                        keySearch.value = typeSearch.value;
                    } else {
                        keySearch.style.display = "block";
                        typeOptions.style.display = "none";
                        statusOptions.style.display = "none";
                        keySearch.value = "";
                    }
                }

                searchSelect.addEventListener("change", updateSearchVisibility);
                statusSearch.addEventListener("change", () => keySearch.value = statusSearch.value);
                typeSearch.addEventListener("change", () => keySearch.value = typeSearch.value);

                updateSearchVisibility(); // Khởi tạo đúng giao diện
            });
        </script>
    </body>
</html>
