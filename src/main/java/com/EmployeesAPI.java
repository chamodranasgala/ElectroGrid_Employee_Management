package com;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EmployeesAPI")
public class EmployeesAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Employee empObj = new Employee();

	public EmployeesAPI() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		
		String output = empObj.insertEmployee(request.getParameter("ename"),

				request.getParameter("email"), request.getParameter("phone"), request.getParameter("gender"));

		response.getWriter().write(output);

	}

	@SuppressWarnings("rawtypes")
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map paras = getParasMap(request);
		
		String output = empObj.updateEmployee(paras.get("hidItemIDSave").toString(), paras.get("ename").toString(),
				paras.get("email").toString(), paras.get("phone").toString(), paras.get("gender").toString());

		response.getWriter().write(output);
	}

	@SuppressWarnings("rawtypes")
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map paras = getParasMap(request);

		String output = empObj.deleteEmployee(paras.get("eid").toString());

		response.getWriter().write(output);

	}

	// Convert request parameters to a Map
	@SuppressWarnings("rawtypes")
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();

		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");

			for (String param : params) {
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}

		} catch (Exception e) {
		}

		return map;
	}
}