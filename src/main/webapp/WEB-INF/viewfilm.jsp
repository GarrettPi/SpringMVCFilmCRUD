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
		${film.length } minutes
	<br><a href="home.do">Home</a>
	<br><a href="deleteFilm.do?filmId=${film.id }">Delete this film</a>
	<br><a href="updateFilm.do?filmId=${film.id }">Update the film information</a>
	</c:if>
	<c:if test="${empty film }">Sorry, but that film ID doesn't exist.  Please Try Again!
	<br><a href="home.do">Home</a>
	</c:if>
</body>
</html>