<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Alert List</title>
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
            <h2 class="mb-4">Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h2>

            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/main/alert" class="btn btn-primary me-2">Alert CRUD</a>

                <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline">
                    <button type="submit" name="action" value="Logout" class="btn btn-danger">Logout</button>
                </form>
            </div>

            <form action="${pageContext.request.contextPath}/main/alert" method="GET" class="mb-3">
                <button type="submit" name="action" value="create" class="btn btn-success">Create Alert</button>
            </form>

            <form action="${pageContext.request.contextPath}/main/alert" method="GET" class="row g-2 mb-4">
                <div class="row g-2 align-items-center">
                    <div class="col-md-4">
                        <label for="action">Search By</label>
                        <select id="action" name="action" class="form-select" onchange="updateSelectOptions()">
                            <option value="getAlertsByDirection">Direction</option>
                            <option value="getAlertsByStatus">Status</option>
                            <option value="getAlertsByTicker">Ticker</option>
                        </select>
                    </div>

                    <div class="col-md-4" id="directionSelect">
                        <label for="directionValue">Direction</label>
                        <select id="directionValue" name="keySearch" class="form-select" disabled>
                            <option value="Increase">Increase</option>
                            <option value="Decrease">Decrease</option>
                        </select>
                    </div>

                    <div class="col-md-4" id="statusSelect">
                        <label for="statusValue">Status</label>
                        <select id="statusValue" name="keySearch" class="form-select" disabled>
                            <option value="Active">Active</option>
                            <option value="Inactive">Inactive</option>
                        </select>
                    </div>

                    <div class="col-md-4" id="tickerInput">
                        <label for="tickerValue">Ticker</label>
                        <input type="text" id="tickerValue" name="keySearch" placeholder="Ex: VNM" class="form-control" disabled />
                    </div>

                    <div class="col-md-4">
                        <button 
                            type="submit" 
                            style="margin-top:1%"
                            class="btn btn-primary w-100"
                        >Search</button>
                    </div>
                </div>
            </form>

            <c:if test="${!empty requestScope.MSG}">
                <div class="alert alert-success">${requestScope.MSG}</div>
            </c:if>
            <c:if test="${!empty requestScope.ERRMSG}">
                <div class="alert alert-danger">${requestScope.ERRMSG}</div>
            </c:if>

            <c:if test="${!empty alerts}">
                <table class="table table-bordered table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>No</th>
                            <th>User ID</th>
                            <th>Ticker</th>
                            <th>Threshold</th>
                            <th>Direction</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="alert" items="${requestScope.alerts}" varStatus="st">
                            <tr>
                                <td>${alert.alertID}</td>
                                <td>${alert.userID}</td>
                                <td>${alert.ticker}</td>
                                <td>${alert.threshold}</td>
                                <td>${alert.direction}</td>
                                <td>${alert.status}</td>
                                <td class="table-actions">
                                    <form action="${pageContext.request.contextPath}/main/alert/update" method="GET">
                                        <input type="hidden" name="alertID" value="${alert.alertID}">
                                        <input type="hidden" name="userID" value="${sessionScope.currentUser.userID}">
                                        <button type="submit" name="action" value="update" class="btn btn-sm btn-warning">Update</button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/main/alert/delete" method="POST" onsubmit="return confirm('Delete this alert?');">
                                        <input type="hidden" name="alertID" value="${alert.alertID}">
                                        <input type="hidden" name="userID" value="${sessionScope.currentUser.userID}">
                                        <button type="submit" name="userID" value="${alert.userID}" class="btn btn-sm btn-danger">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

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
            function updateSelectOptions() {
                const action = document.getElementById("action").value;

                document.getElementById("directionSelect").style.display = "none";
                document.getElementById("statusSelect").style.display = "none";
                document.getElementById("tickerInput").style.display = "none";

                document.getElementById("directionValue").disabled = true;
                document.getElementById("statusValue").disabled = true;
                document.getElementById("tickerValue").disabled = true;

                if (action === "getAlertsByDirection") {
                    document.getElementById("directionSelect").style.display = "block";
                    document.getElementById("directionValue").disabled = false;
                } else if (action === "getAlertsByStatus") {
                    document.getElementById("statusSelect").style.display = "block";
                    document.getElementById("statusValue").disabled = false;
                } else if (action === "getAlertsByTicker") {
                    document.getElementById("tickerInput").style.display = "block";
                    document.getElementById("tickerValue").disabled = false;
                }
            }

            window.onload = updateSelectOptions;
        </script>
    </body>
</html>
