<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Results</title>
</head>
<body>
	<h1>Films with matching keywords</h1>
	<c:if test="${not empty films }">
	<br>That keyword has found ${films.size() } records.<br>
	<h4>Please select a film for more information.</h4>
	<div>
		<form action="idLookup.do" method="POST">
			<c:forEach var="f" items="${films }">
				<div>
					<input type="radio" id="${f.id }" name="filmId" value="${f.id }"> ${film.title }
					<label>${f.title }</label>, ${f.releaseYear }, ${f.rating }<br>
				</div>
			</c:forEach>
			<input type="submit" value="More Info">
		</form>
	</div>
	</c:if>
	<c:if test="${empty films }">
	No films match that keyword.  Please try again.
	</c:if>
	<br><a href="home.do">Home</a>
</body>
</html>