package com.dev.tourguide.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dev.tourguide.MySQLConnUtils;
import com.dev.tourguide.beans.UserDetails;

public class UserDao {

	
	private final String checkDuplicate = "select id,firstName,lastName,emailAddress,password,mobile from userdetails where emailAddress=?";
	private final String insertQuery = "insert into userdetails(firstName,lastName,emailAddress,password,mobile) values(?,?,?,?,?)";
	private final String loginQuery = "select id,firstName,lastName,emailAddress,password,mobile from userdetails where emailAddress=? and password=?";
	
	public int saveUserDetails(UserDetails userDetails) {
		UserDetails details = checkUser(userDetails.getEmailAddress());
		if(details == null) {
			try(Connection connection = MySQLConnUtils.getMySQLConnection(); PreparedStatement prSt = connection.prepareStatement(insertQuery);){
				prSt.setString(1, userDetails.getFirstName());
				prSt.setString(2, userDetails.getLastname());
				prSt.setString(3, userDetails.getEmailAddress());
				prSt.setString(4, userDetails.getPassword());
				prSt.setString(5, userDetails.getMobile());
				return prSt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public UserDetails login(String emailAddress,String password){
		
		UserDetails userDetails =null;
		try(Connection connection = MySQLConnUtils.getMySQLConnection();PreparedStatement stmt = connection.prepareStatement(loginQuery)) {
			stmt.setString(1, emailAddress);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				userDetails = new UserDetails(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastname"), rs.getString("emailAddress"), rs.getString("password"),
						rs.getString("mobile"));
			}
			return userDetails;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public UserDetails checkUser(String emailAddress){
		UserDetails userDetails =null;
		try(Connection connection = MySQLConnUtils.getMySQLConnection();PreparedStatement stmt = connection.prepareStatement(checkDuplicate)) {
			stmt.setString(1, emailAddress);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				userDetails = new UserDetails(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastname"), rs.getString("emailAddress"), rs.getString("password"),
						rs.getString("mobile"));
			}
			return userDetails;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
