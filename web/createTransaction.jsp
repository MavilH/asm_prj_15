<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Create Transaction Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="post-container">
            <h3>Welcome, <c:out value="${sessionScope.currentUser.fullName}"/></h3>
            
            <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST">
                <input type="submit" class="btn btn-danger logout-btn" value="LOGOUT" />
            </form>
            
            <h3>Create New Transaction</h3>

            <p class="message text-success">${requestScope.MSG}</p>
            
            <form action="${pageContext.request.contextPath}/main/transaction/create" method="POST">
                <label for="userID">User ID</label>
                <input type="text" id="userID" name="userID" value="${sessionScope.currentUser.userID}" readonly class="form-control" />

                <label for="ticker">Ticker</label>
                <input type="text" id="ticker" name="ticker" placeholder="Enter ticker" required class="form-control" />

                <label for="type">Type</label>
                <select name="type" required class="form-select">
                    <option value="buy">Buy</option>
                    <option value="sell">Sell</option>
                </select>

                <label for="quantity">Quantity</label>
                <input type="number" id="quantity" name="quantity" placeholder="Enter quantity" min="1" required class="form-control" />

                <label for="price">Price</label>
                <input type="number" id="price" name="price" placeholder="Enter price" step="0.01" min="0.01" required class="form-control" />

                <label for="status">Status</label>
                <select name="status" required class="form-select">
                    <option value="executed">Executed</option>
                    <option value="pending">Pending</option>
                </select>

                <button type="submit" name="action" value="create" class="btn btn-primary w-100 mt-3">Create Transaction</button>
            </form>

            <a href="${pageContext.request.contextPath}/main/transaction" class="back-link">Back to Transaction List</a>
        </div>
    </body>
</html>
