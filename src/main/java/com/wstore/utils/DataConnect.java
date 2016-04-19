package com.wstore.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataConnect {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataConnect.class);
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost/mydb";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	/**
	 *
	 * Connect database of mysql with project
	 */
	public static Connection getConnection() {
		Connection connection = null;

		try{
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
			return connection;
		} catch (Exception e){
			LOGGER.info("Database.getConnection() Error -->" + e.getMessage());
			return null;
		}

	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
			LOGGER.info(ex.getMessage());
		}
	}

}
