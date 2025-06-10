<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Create Stock</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="post-container mt-5">
            <!-- Display current user -->
            <h3>Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h3>

            <!-- Logout button -->
            <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST">
                <input type="submit" class="btn btn-danger logout-btn" value="LOGOUT" />
            </form>

            <!-- Page title -->
            <h3>Create New Stock</h3>

            <!-- Notification message -->
            <c:if test="${not empty requestScope.MSG}">
                <p class="alert alert-info">${requestScope.MSG}</p>
            </c:if>

            <c:if test="${not empty requestScope.ERROR}">
                <p class="alert alert-danger">${requestScope.ERROR}</p>
            </c:if>

            <!-- Create stock form -->
            <form action="${pageContext.request.contextPath}/main/stock/create" method="POST">

                <div class="mb-3">
                    <label for="ticker" class="form-label">Ticker</label>
                    <input type="text" id="ticker" name="ticker" placeholder="Enter stock ticker" required class="form-control" />
                </div>

                <div class="mb-3">
                    <label for="name" class="form-label">Stock Name</label>
                    <input type="text" id="name" name="name" placeholder="Enter stock name" required class="form-control" />
                </div>

                <div class="mb-3">
                    <label for="sector" class="form-label">Sector</label>
                    <input type="text" id="sector" name="sector" placeholder="Enter sector" required class="form-control" />
                </div>

                <div class="mb-3">
                    <label for="price" class="form-label">Price</label>
                    <input type="number" id="price" name="price" placeholder="Enter price" step="0.01" required class="form-control" />
                </div>

                <div class="mb-3">
                    <label for="status" class="form-label">Status</label>
                    <select id="status" name="status" required class="form-select">
                        <option value="1">Executed</option>
                        <option value="0">Pending</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary w-100">Create Stock</button>
            </form>

            <!-- Back to list -->
            <a href="${pageContext.request.contextPath}/main/stock" class="back-link">Back to stock list</a>
        </div>
    </body>
</html>
