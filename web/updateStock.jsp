<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Stock</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="post-container mt-5">
            <h3>Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h3>

            <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST">
                <input type="submit" class="btn btn-danger logout-btn" value="LOGOUT" />
            </form>

            <h2>Update Stock</h2>

            <c:if test="${not empty MSG}">
                <p class="text-success">${MSG}</p>
            </c:if>
            <c:if test="${not empty ERROR}">
                <p class="text-danger">${ERROR}</p>
            </c:if>

            <form action="${pageContext.request.contextPath}/main/stock/update" method="POST">
                <input type="hidden" name="ticker" value="${stock.ticker}" />

                <div class="mb-3">
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" value="${stock.name}" class="form-control" required />
                </div>

                <div class="mb-3">
                    <label for="sector">Sector</label>
                    <input type="text" id="sector" name="sector" value="${stock.sector}" class="form-control" required />
                </div>

                <div class="mb-3">
                    <label for="price">Price</label>
                    <input type="number" id="price" name="price" value="${stock.price}" step="0.01" class="form-control" required />
                </div>

                <div class="mb-3">
                    <label for="status">Status</label>
                    <select id="status" name="status" class="form-select" required>
                        <option value="pending" ${!stock.status ? "selected" : ""}>Pending</option>
                        <option value="executed" ${stock.status ? "selected" : ""}>Executed</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary w-100">Update</button>
            </form>
            
            <!-- Back to list -->
            <a href="${pageContext.request.contextPath}/main/stock" class="back-link">Back to stock list</a>
        </div>
    </body>
</html>
