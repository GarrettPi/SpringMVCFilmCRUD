<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Actor Added</title>
</head>
<body>
	<c:if test="${not empty actor }">
	${actor.firstName } ${actor.lastName } added successfully
	</c:if>
	<c:if test="${empty actor }">
	There was an error in adding the actor.
	</c:if>
	<br>
	<a href="home.do">Home</a>
</body>
</html>