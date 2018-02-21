package me.ByteCoder.Core.Connections.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class MySQL
{
  private static Connection con;
  
  protected static void connect(String host, String base, String user, String password)
  {
    if (!isConnected()) {
      try
      {
        con = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + base + "?autoReconnect=true", user, password);
    	Logger.println("MySQL connected to database!", LoggType.INFO);
      }
      catch (SQLException e)
      {
    	  Logger.println("While connect to MySQL: " + e.getMessage(), LoggType.ERROR);
      }
    }
  }
  
  protected static boolean Connected()
  {
    return con != null;
  }
  
  protected static void disconnect()
  {
    if (isConnected()) {
      try
      {
        con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  protected static ResultSet Result(String query)
  {
    ResultSet r = null;
    try
    {
      Statement t = (Statement) MySQL.getConnection().createStatement();
      r = t.executeQuery(query);
    }
    catch (SQLException s)
    {
      System.err.println(s);
    }
    return r;
  }
  
  protected static PreparedStatement getStatement(String sql)
  {
    if (isConnected()) {
      try
      {
        return con.prepareStatement(sql);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    return null;
  }
  
  protected static ResultSet getResult(String sql)
  {
    if (isConnected()) {
      try
      {
        PreparedStatement ps = getStatement(sql);
        return ps.executeQuery();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    return null;
  }
  
  protected static void update(String query)
  {
    if (isConnected()) {
      try
      {
        con.createStatement().executeUpdate(query);
      }
      catch (SQLException s)
      {
        s.printStackTrace();
      }
    }
  }
  
  
  protected static boolean isConnected()
  {
    return con != null;
  }
  
  protected static Connection getConnection()
  {
    return con;
  }
}
