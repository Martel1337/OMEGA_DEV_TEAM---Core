package me.ByteCoder.Core.Connections.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class MySQLManager {

private int totalResults;
	
public MySQLManager(){
	Main.loadedManagers++;
	Logger.println("MySQL manager loaded. (" + Main.loadedManagers + "/" + Main.totalManagers + ")", LoggType.INFO);
}

public void connect(String host, String base, String user, String password){
	MySQL.connect(host, base, user, password);
}

public void disconnect(){
	if(this.isConnected()){
		MySQL.disconnect();
	}
}

public boolean isConnected(){
	return MySQL.isConnected();
}

public void createDefaultTables(){
	if(this.isConnected()){
		try {
			MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PlayersData (ID int, Name VARCHAR(32), UUID VARCHAR(36), Data VARCHAR(9999))").executeUpdate();
			//MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PlayersStats ()");
		} catch (SQLException e) {
			Logger.println(e.getMessage(), LoggType.INFO);
		}
	}
}

public Connection getConnection(){
	return MySQL.getConnection();
}

public ResultSet getResult(String sql){
	ResultSet rs = null;
	if(this.isConnected()){
		try {
			Statement statement = MySQL.getConnection().createStatement();
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.totalResults++;
	}
	return rs;
}

public void SendResult(String sql){
	if(this.isConnected()){
		MySQL.update(sql);
	}
}

public int getTotalResults(){
	return this.totalResults;
}

}
