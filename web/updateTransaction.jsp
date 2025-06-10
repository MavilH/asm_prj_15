<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Transaction Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="post-container">
            <h3>Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h3>

            <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST">
                <input type="submit" class="btn btn-danger logout-btn" value="LOGOUT" />
            </form>

            <h3>Update Transaction</h3>
            <p class="message">${requestScope.MSG}</p>
            <hr />

            <form action="${pageContext.request.contextPath}/main/transaction/update" method="POST">
                <label for="id">Transaction ID</label>
                <input type="text" id="id" name="id" value="${transaction.id}" class="form-control" readonly />

                <label for="userID">User ID</label>
                <input type="text" id="userID" name="userID" value="${transaction.userID}" class="form-control" readonly />

                <label for="ticker">Ticker</label>
                <input type="text" id="ticker" name="ticker" value="${transaction.ticker}" class="form-control" readonly />

                <label for="type">Type</label>
                <input type="text" id="type" name="type" value="${transaction.type}" class="form-control" readonly>
                <!-- 
                    <c:choose>
                        <c:when test="${requestScope.can == true}">
                            <select name="type" id="type" class="form-select">
                                <option value="${transaction.type}" selected>${transaction.type}</option>
                                <c:if test="${transaction.type.equalsIgnoreCase('buy')}">
                                    <option>sell</option>
                                </c:if>
                                <c:if test="${transaction.type.equalsIgnoreCase('sell')}">
                                    <option>buy</option>
                                </c:if>
                            </select>
                        </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>
                -->
                <label for="quantity">Quantity</label>
                <input type="number" id="quantity" name="quantity" value="${transaction.quantity}" class="form-control" required />

                <label for="price">Price</label>
                <input type="number" id="price" name="price" value="${transaction.price}" step="0.01" class="form-control" required />

                <label for="status">Status</label>
                <c:choose>
                    <c:when test="${requestScope.can == true}">
                        <select name="status" id="status" class="form-select">
                            <option value="${transaction.status}" selected>${transaction.status}</option>
                            <c:if test="${transaction.status.equalsIgnoreCase('executed')}">
                                <option value="pending">pending</option>
                            </c:if>
                            <c:if test="${transaction.status.equalsIgnoreCase('pending')}">
                                <option value="executed">executed</option>
                            </c:if>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <input class="readonly-input" type="text" name="status" value="${transaction.status}" readonly>
                    </c:otherwise>
                </c:choose>

                <br />
                <c:if test="${!empty requestScope.can}">
                    <button type="submit" name="action" value="update" class="btn btn-primary w-100">Update</button>
                </c:if>
            </form>

            <a href="${pageContext.request.contextPath}/main/transaction" class="back-link">Back to transaction CRUD</a>
        </div>
    </body>
</html>
