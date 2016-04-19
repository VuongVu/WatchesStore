package com.wstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wstore.utils.DataConnect;



/**
 * Servlet implementation class DisplayImage
 */
@WebServlet("/DisplayImage")
public class DisplayImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DisplayImage() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		InputStream sImage;
		try {
			//get product id
			String id = request.getParameter("productId");
			//open connection
			conn = DataConnect.getConnection();
			stmt = conn.createStatement();
			String strSql = "select productImage from product where productId='" + id + "' ";
			//execute query get product image
			rs = stmt.executeQuery(strSql);
			if (rs.next()) {
				//read image
				byte[] bytearray = new byte[1048576];
				int size = 0;
				sImage = rs.getBinaryStream(1);
				response.reset();
				response.setContentType("image/jpeg, image/png");
				while ((size = sImage.read(bytearray)) != -1) {
					response.getOutputStream().
					write(bytearray, 0, size);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
