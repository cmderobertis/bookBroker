<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Tacos</title>
  <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
  <script src="/webjars/jquery/jquery.min.js"></script>
  <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<h1 class="text-center">Check out these books, <c:out value="${user.username}!"/><a class="btn btn-danger" href="/logout">Logout</a></h1>
<div class="row justify-content-center">
  <div class="col-auto">
    <div class="card">
      <div class="card-body">
      <div class="card-title">
        <h3>Books Available to Borrow...</h3>
      </div>
        <table class="table table-hover table-striped">
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author Name</th>
            <th>Owner</th>
            <th>Actions</th>
          </tr>
          <c:forEach var="book" items="${unborrowedBooks}">
            <tr>
              <td><c:out value="${book.id}"/></td>
              <td><a href="/books/${book.id}"><c:out value="${book.title}"/></a></td>
              <td><c:out value="${book.author}"/></td>
              <td><c:out value="${book.owner.username}"/></td>
              <td>
                <c:choose>
                  <c:when test="${book.owner == user}">
                    <div class="d-flex">
                    <a class="btn btn-success me-3" href="/books/${book.id}/edit">Edit</a>
                    <form action="/books/${book.id}" method="post">
                      <input type="hidden" name="_method" value="delete">
                      <input type="submit" value="Delete" class="btn btn-danger">
                    </form>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <c:choose>
                      <c:when test="${book.borrower == null}">
                        <form action="/books/${book.id}/borrow" method="post">
                          <input type="hidden" name="_method" value="put">
                          <input type="submit" value="Borrow" class="btn btn-warning">
                        </form>
                      </c:when>
                      <c:otherwise>
                        <p class="btn btn-outline-warning">Borrowed</p>
                      </c:otherwise>
                    </c:choose>
                  </c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
    <div class="card mt-3">
      <div class="card-body">
      <div class="card-title">
        <h3>Books I'm Borrowing...</h3>
      </div>
        <table class="table table-hover table-striped">
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author Name</th>
            <th>Owner</th>
            <th>Actions</th>
          </tr>
          <c:forEach var="book" items="${borrowedBooks}">
            <tr>
              <td><c:out value="${book.id}"/></td>
              <td><a href="/books/${book.id}"><c:out value="${book.title}"/></a></td>
              <td><c:out value="${book.author}"/></td>
              <td><c:out value="${book.owner.username}"/></td>
              <td>
                <form action="/books/${book.id}/return" method="post">
                  <input type="hidden" name="_method" value="put">
                  <input type="submit" value="Return" class="btn btn-warning">
                </form>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </div>
  <a href="/books/new">Add Book</a>


</div>
</body>
</html>