<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film Details</title>
</head>
<body>
	<h1>Film Details</h1>
	<br>
	<c:if test="${not empty film }">
		Title: ${film.title }, ${film.releaseYear }, Rated ${film.rating }<br>
		Description: ${film.description }<br>
		${film.length } minutes<br>
		<h4>Categories</h4>
		<ul>
			<c:forEach var="c" items="${categories}">
				<li>${c.name }</li>
			</c:forEach>
		</ul>
		<h4>Cast</h4>
		<table>
			<tr>
				<th>Actor Name</th>
				<th>Actor Id</th>
			</tr>
			<c:forEach var="a" items="${actors }">
				<tr>
					<td>${a.firstName } ${a.lastName }</td>
					<td>${a.id }</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<a href="home.do">Home</a>
		<br>
		<a href="deleteFilm.do?filmId=${film.id}">Delete this film</a>
		<br>
		<a href="filmToUpdate.do?filmId=${film.id}">Update this film
			information</a>
	</c:if>
	<c:if test="${empty film }">Sorry, but that film ID doesn't exist.  Please Try Again!
	<br>
		<a href="home.do">Home</a>
	</c:if>
</body>
</html>