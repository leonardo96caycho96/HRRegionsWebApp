package pe.edu.utp.hrregionswebapp.models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by GrupoUTP on 14/10/2016.
 */
public class Country implements Serializable {
    private String id;
    private String name;
    private Region region;

    public Country() {
    }

    public Country(String id, String name, Region region) {
        this.id = id;
        this.name = name;
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public static Country buildFromResultSet(ResultSet resultSet) {
        Country country = null;
        try {
            country = new Country(
                    resultSet.getString("country_id"),
                    resultSet.getString("country_name"),
                    null
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }
    public static Country buildFromResultSet(ResultSet resultSet, RegionsEntity regionsEntity) {
        Country country = null;
        try {
            country = new Country(
                    resultSet.getString("country_id"),
                    resultSet.getString("country_name"),
                    regionsEntity.findById(resultSet.getInt("region_id"))
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

}
