<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Alert Page</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="post-container">
            <h1>Welcome, <c:out value="${sessionScope.currentUser.fullName}"/></h1>

            <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST">
                <input type="submit" class="btn btn-danger logout-btn" value="LOGOUT" />
            </form>

            <h3>Update Alert</h3>

            <c:if test="${not empty requestScope.ERRMSG}">
                <p class="message text-danger">${requestScope.ERRMSG}</p>
            </c:if>
            <c:if test="${not empty requestScope.MSG}">
                <p class="message text-success">${requestScope.MSG}</p>
            </c:if>

            <div class="table-responsive">
                <form action="${pageContext.request.contextPath}/main/alert/update" method="POST">
                    <label for="alertID">Alert ID</label>
                    <input id="AlertID" class="form-control" type="text" name="alertID" value="${alert.alertID}" readonly>
                    
                    <label for="userID">User ID</label>
                    <input id="userID" class="form-control" type="text" name="userID" value="${alert.userID}" readonly>
                    
                    <label for="ticker">Ticker</label>
                    <input id="ticker" class="form-control" type="text" name="ticker" value="${alert.ticker}" readonly>
                    
                    <label for="direction">Direction</label>
                    <c:choose>
                        <c:when test="${requestScope.can == true}">
                            <select  id="direction" name="direction" class="form-select">
                                <option value="${alert.direction}" selected>${alert.direction}</option>
                                <c:if test="${alert.direction.equalsIgnoreCase('increase')}">
                                    <option value="decrease">decrease</option>
                                </c:if>
                                <c:if test="${alert.direction.equalsIgnoreCase('decrease')}">
                                    <option value="increase">increase</option>
                                </c:if>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <input id="direction" class="form-control" type="text" name="direction" value="${alert.direction}" readonly>
                        </c:otherwise>
                    </c:choose>
                    
                    <label for="threshold">Threshold</label><br />
                    <c:choose>
                        <c:when test="${requestScope.can == true}">
                            <input id="threshold" class="form-control" type="number" name="threshold" step="0.01" value="${alert.threshold}" required />
                            <input id="threshold" class="form-control" type="hidden" name="_threshold" value="${alert.threshold}" />
                        </c:when>
                        <c:otherwise>
                            <input id="threshold" class="form-control" type="text" name="threshold" value="${alert.threshold}" readonly>
                        </c:otherwise>
                    </c:choose>
                    
                    <label for="status">Status</label><br />
                    <c:choose>
                        <c:when test="${requestScope.can == true}">
                            <select  id="status" name="status" class="form-select" >
                                <option value="${alert.status}" selected>${alert.status}</option>
                                <c:if test="${alert.status.equalsIgnoreCase('inactive')}">
                                    <option value="active">active</option>
                                </c:if>
                                <c:if test="${alert.status.equalsIgnoreCase('active')}">
                                    <option value="inactive">inactive</option>
                                </c:if>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <input id="status" class="form-control" type="text" name="status" value="${alert.status}" readonly>
                        </c:otherwise>
                    </c:choose>
                            
                    <br/>
                    <c:if test="${requestScope.can == true}">
                        <button type="submit" class="btn btn-primary w-100">Update</button>
                    </c:if>
                </form>
            </div>

            <!-- Back to list -->
            <a href="${pageContext.request.contextPath}/main/alert" class="back-link">Back to alert list</a>
        </div>
    </body>
</html>
