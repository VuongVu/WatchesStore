package com.wstore.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wstore.utils.DataConnect;



public class UserNameService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserNameService.class);

	public UserNameService() {

	}
	/**
	 * check userName exists in database
	 * @param userName
	 * @return exist
	 * @throws SQLException
	 */
	public static boolean checkUserName(String userName) throws SQLException{
		String query = "Select * from admin where userName = '" + userName + "'";
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