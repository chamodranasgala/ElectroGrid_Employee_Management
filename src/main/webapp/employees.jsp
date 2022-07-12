<%@page import="com.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">

<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/employees.js"></script>

</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-6">

				<h1>Employee Management</h1>

				<form id="formItem" name="formItem">
					Employee Name: <input id="ename" name="ename" type="text"
						class="form-control form-control-sm"> <br> Email: <input
						id="email" name="email" type="text"
						class="form-control form-control-sm"> <br> Phone: <input
						id="phone" name="phone" type="text"
						class="form-control form-control-sm"> <br> Gender: <input
						id="gender" name="gender" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>

				<br>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>

				<div id="divItemsGrid">
					<%
					Employee empObj = new Employee();
					out.print(empObj.readEmployees());
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
