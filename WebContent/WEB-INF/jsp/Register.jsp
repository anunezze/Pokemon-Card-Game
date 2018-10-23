<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Registration</title>
	</head>
	<body>
		<h1>Registration</h1>
		<form action="Register" method="POST">
			Username:<input type="text" name="username"><br/>
			Password:<input type="password" name="password"><br/>
			<span style="color:red">${errorMessage}</span><br/>
			<input type="submit" value="Register">
		</form>
	</body>
</html>