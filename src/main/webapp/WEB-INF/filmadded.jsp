<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film Added</title>
</head>
<body>
	<c:if test="${not empty film }">
	${film.title } added successfully
	</c:if>
	<c:if test="${empty film }">
	There was an error in creating the film
	</c:if>
	<br>
	<a href="home.do">Home</a>
</body>
</html>