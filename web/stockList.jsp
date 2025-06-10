<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Stock List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="container bg-white p-4 rounded shadow-sm">
            <!-- User and Logout -->
            <h2>Welcome, <c:out value="${sessionScope.currentUser.fullName}" /></h2>

            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/main/stock" class="btn btn-primary me-2">Stock CRUD</a>

                <form action="${pageContext.request.contextPath}/main/auth/logout" method="POST" class="d-inline">
                    <button type="submit" name="action" value="Logout" class="btn btn-danger">Logout</button>
                </form>
            </div>

            <!-- Create button -->
            <form action="${pageContext.request.contextPath}/main/stock" method="GET" class="mb-3">
                <button type="submit" name="action" value="create" class="btn btn-success">Create Stock</button>
            </form>

            <!-- Search Filters -->
            <form action="${pageContext.request.contextPath}/main/stock" method="GET" class="row g-2 mb-4">
                <input type="hidden" name="action" value="filter" />
                <div class="col-md-3">
                    <input type="text" name="name" class="form-control" placeholder="Search by Name" />
                </div>
                <div class="col-md-3">
                    <input type="text" name="ticker" class="form-control" placeholder="Search by Ticker" />
                </div>
                <div class="col-md-3">
                    <input type="text" name="sector" class="form-control" placeholder="Search by Sector" />
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-outline-primary w-100">Search</button>
                </div>
            </form>

            <!-- Search by price -->
            <form action="${pageContext.request.contextPath}/main/stock" method="GET" class="row g-2 mb-4">
                <input type="hidden" name="action" value="searchByPrice" />
                <div class="col-md-3">
                    <input type="number" name="minPrice" class="form-control" placeholder="Min Price" step="0.01" />
                </div>
                <div class="col-md-3">
                    <input type="number" name="maxPrice" class="form-control" placeholder="Max Price" step="0.01" />
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-outline-secondary w-100">Search by Price Range</button>
                </div>
            </form>

            <!-- Sort -->
            <div class="mb-3">
                <form action="${pageContext.request.contextPath}/main/stock" method="GET" class="d-inline">
                    <input type="hidden" name="action" value="orderByPrice" />
                    <input type="hidden" name="order" value="ASC" />
                    <button type="submit" class="btn btn-info btn-sm">Sort by Price ↑</button>
                </form>
                <form action="${pageContext.request.contextPath}/main/stock" method="GET" class="d-inline">
                    <input type="hidden" name="action" value="orderByPrice" />
                    <input type="hidden" name="order" value="DESC" />
                    <button type="submit" class="btn btn-info btn-sm">Sort by Price ↓</button>
                </form>
            </div>

            <!-- Messages -->
            <c:if test="${not empty requestScope.MSG}">
                <div class="alert alert-success">${requestScope.MSG}</div>
            </c:if>
            <c:if test="${not empty requestScope.ERROR}">
                <div class="alert alert-danger">${requestScope.ERROR}</div>
            </c:if>

            <!-- Stock Table -->
            <table class="table table-bordered table-hover">
                <thead class="table-light">
                    <tr>
                        <th>Ticker</th>
                        <th>Name</th>
                        <th>Sector</th>
                        <th>Price</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${stocks}">
                        <tr>
                            <td>${s.ticker}</td>
                            <td>${s.name}</td>
                            <td>${s.sector}</td>
                            <td>${s.price}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${s.status}">Executed</c:when>
                                    <c:otherwise>Pending</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <!-- Update button -->
                                <form action="${pageContext.request.contextPath}/main/stock/update" method="GET" style="display:inline;">
                                    <input type="hidden" name="ticker" value="${s.ticker}" />
                                    <button type="submit" class="btn btn-sm btn-warning">Update</button>
                                </form>
                                <!-- Delete button -->
                                <form 
                                    action="${pageContext.request.contextPath}/main/stock/delete" 
                                    method="POST" 
                                    style="display:inline;" 
                                    onsubmit="return confirm('Delete this stock?');"
                                    >
                                    <input type="hidden" name="ticker" value="${s.ticker}" />
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
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
    </body>
</html>
