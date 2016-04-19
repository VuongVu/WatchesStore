package com.wstore.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wstore.utils.DataConnect;



public class EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

	private EmailService(){

	}
	/**
	 *
	 * @param email
	 * @checkEmailExists
	 * used to check email already exists in database
	 */
	public static boolean checkEmail(String email) throws SQLException{
		String query = "Select * from customer where email = '" + email + "'";
		Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;
		boolean exist = false;
		try {
			connection = DataConnect.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			//if email exists return true
			if(resultSet.next()){
				exist = true;
			}

		} catch(SQLException e) {
			LOGGER.info(e.getMessage());
		}finally {
			if (resultSet != null) {
				try{
					resultSet.close();
				} catch (SQLException e){
					LOGGER.info(e.getMessage());
				}
			}
			if (statement != null) {
				try{
					statement.close();
				} catch (SQLException e){
					LOGGER.info(e.getMessage());
				}
			}
			if (connection != null) {
				DataConnect.close(connection);
			}
		}
		return exist;
	}
}
