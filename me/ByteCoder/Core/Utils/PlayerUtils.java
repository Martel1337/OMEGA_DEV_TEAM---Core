package me.ByteCoder.Core.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.Data.Player.Player;

public class PlayerUtils {

public static void insterDataToSql(Player p){
	Main.getMySQLManager().SendResult("INSERT INTO PlayersData (ID, Name, UUID, Data) VALUES ('" + getLastPlayerID() + "', '" + p.getName() + "', '" + p.getUUID() + "', '" + p.getName() + ";" + p.getUUID() + ";" + p.getIP() + ";Default;1;null;0;1.0;0;0');");
}

public static int getLastPlayerID(){
	int ServerID = 1;
    try {
        PreparedStatement ps = Main.getMySQLManager().getConnection().prepareStatement("SELECT * FROM PlayersData ORDER BY ID DESC");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("ID");
        }
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
    return ServerID;
}

}
