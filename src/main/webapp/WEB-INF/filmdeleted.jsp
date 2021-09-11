<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film Deleted</title>
</head>
<body>
<c:if test="${not empty film }">
The film, ${film.title } was deleted successfully!
<br><a href="home.do">Home</a>
</c:if>
<c:if test="${empty film }">
That film does not exist.  Please Try again!
<br><a href="home.do">Home</a>
</c:if>
</body>
</html>