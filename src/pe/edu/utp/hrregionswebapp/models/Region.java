package pe.edu.utp.hrregionswebapp.models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by GrupoUTP on 30/09/2016.
 */
public class Region implements Serializable {
    private int id;
    private String name;

    public Region(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Region() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Region buildFromResultSet(ResultSet resultSet) {
        Region region = null;
        try {
            region = new Region(
                    resultSet.getInt("region_id"),
                    resultSet.getString("region_name")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return region;
    }
}
