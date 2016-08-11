package com.kjuns.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtils {
	
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    
    static {
    	try {
    		 Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			 throw new RuntimeException(e);  
		}
    }

    public Connection getConntion(){        
        if(null == conn){
        	try {
        	     conn = DriverManager.getConnection(SysConf.JDBC_URL, SysConf.JDBC_USERNAME, SysConf.JDBC_PWD);
			} catch (Exception e) {
				throw new RuntimeException(e);  
			}
        }
        return conn;
    }
   
    public void closeResources(){
        if(null != rs){
            try {
                rs.close();
            } catch (SQLException e) {
            	throw new RuntimeException(e);
            }
        }
        if(pstmt!=null){
            try {
                pstmt.close();
            } catch (SQLException e) {
            	throw new RuntimeException(e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
            	throw new RuntimeException(e);
            }
        }
    }

    public int execOther(PreparedStatement pstmt){
        try {
            int affectedRows = pstmt.executeUpdate();
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet execQuery(PreparedStatement pstmt){
        try {
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
