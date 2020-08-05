<%@page import="java.io.*"%>
<%@ page import="java.sql.*,javax.sql.*,javax.naming.*" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	
	<%
	String jndiNameStr = "";
	for(String jndiName:jndiNameStr.split(",")){
	    Connection connOracle = null;
	    Statement pstmt=null;
	    //String jndiName = "jdbc/wp";

		try {
			//1、初始化名称查找上下文
			Context ctx = new InitialContext();
			//2、通过JNDI名称找到DataSource对象,对名称进行定位java:comp/env是必须加的,后面跟的是DataSource名				
				//weblogic上用
 			DataSource ds = (DataSource) ctx.lookup(jndiName);
            //3、通过DataSource取得一个连接
            connOracle = ds.getConnection();
			pstmt = connOracle.createStatement();
			String sql = "select 1 from dual";
			pstmt.executeQuery(sql);
            System.out.println("Oracle Connection pool connected !!"); 
            //4、操作数据库
     %>
     	<div style="width: 70px; height: 50px;background-color: green;" ></div>
     <%       
		}catch (NamingException e) {
            System.out.println(e.getMessage());
     %>
     	<div style="width: 70px; height: 50px;background-color: red;" ></div>
     <%
        } catch (SQLException e) {
             e.printStackTrace();
     %>
     	<div style="width: 70px; height: 50px;background-color: red;" ></div>
     <%        
         } finally {
             //5、关闭数据库，关闭的时候是将连接放回到连接池之中
             if(connOracle != null) {
	             try{
	            	 connOracle.close();
	             } catch (SQLException e) {
	            	 
	             }
             }
		 }
		}
	%>
	


