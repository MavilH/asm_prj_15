<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Create Alert Page</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            .message.success {
                color: green;
            }
            .message.error {
                color: red;
            }
        </style>
    </head>
    <body>
        <div class="post-container">
            <h3>Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h3>

            <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST">
                <input type="submit" class="btn btn-danger logout-btn" value="LOGOUT" />
            </form>

            <h3>Create New Alert</h3>

            <c:if test="${not empty requestScope.MSG}">
                <p class="message success">${requestScope.MSG}</p>
            </c:if>

            <c:if test="${not empty requestScope.ERRMSG}">
                <p class="message error">${requestScope.ERRMSG}</p>
            </c:if>

            <form action="${pageContext.request.contextPath}/main/alert/create" method="POST">
                <label for="userID">User ID</label>
                <input type="text" id="userID" name="userID" class="form-control" value="${sessionScope.currentUser.userID}" readonly />

                <label for="ticker">Ticker</label>
                <input type="text" id="ticker" name="ticker" class="form-control" placeholder="Enter ticker" required />

                <label for="threshold">Threshold</label>
                <input type="number" id="threshold" name="threshold" class="form-control" placeholder="Enter threshold" step="0.01" required />

                <label for="direction">Direction</label>
                <select id="direction" name="direction" class="form-select" required>
                    <option value="increase">increase</option>
                    <option value="decrease">decrease</option>
                </select>

                <button type="submit" name="action" value="create" class="btn btn-primary w-100">Create Alert</button>
            </form>

            <!-- Back to list -->
            <a href="${pageContext.request.contextPath}/main/alert" class="back-link">Back to alert list</a>
        </div>
    </body>
</html>
