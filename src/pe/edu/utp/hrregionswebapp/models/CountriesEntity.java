package pe.edu.utp.hrregionswebapp.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GrupoUTP on 14/10/2016.
 */
public class CountriesEntity extends BaseEntity {
    private static String DEFAULT_QUERY =
            "SELECT * FROM countries";

    public List<Country> findAll()  {
        return findAllByCriteria("");
    }

    public List<Country> findAllWithRegion(RegionsEntity regionsEntity)  {
        return findAllByCriteria("", regionsEntity);
    }


    public List<Country> findAllOrderedByName() {
        return findAllByCriteria("ORDER BY country_name");
    }

    public List<Country> findAllWithLocations() {
        return findAllByCriteria(
                "WHERE country_id IN " +
                        "(SELECT DISTINCT country_id FROM locations)");
    }

    public int findLocationsCountFor(String id) {
        return findLocationsCountForCriteria(
                "WHERE locations.country_id = '" +
                        id + "'");
    }


    private int findLocationsCountForCriteria(String criteria) {
        String sql = "SELECT COUNT(*) " +
                "AS locations_count FROM locations" +
                " " + criteria;
        try {
            ResultSet resultSet = getConnection()
                    .createStatement()
                    .executeQuery(sql);
            if(resultSet.next())
                return resultSet.getInt("locations_count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private List<Country> findAllByCriteria(String criteria) {
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(DEFAULT_QUERY +
                    " " + criteria);
            if(resultSet != null) {
                List<Country> countries = new ArrayList<>();
                while(resultSet.next()) {
                    Country country = Country.buildFromResultSet(resultSet);
                    countries.add(country);
                }
                return countries;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Country> findAllByCriteria(String criteria, RegionsEntity regionsEntity) {
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(DEFAULT_QUERY +
                    " " + criteria);
            if(resultSet != null) {
                List<Country> countries = new ArrayList<>();
                while(resultSet.next()) {
                    Country country = Country.buildFromResultSet(resultSet, regionsEntity);
                    countries.add(country);
                }
                return countries;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Country findById(String id) {
        return findBy(" WHERE country_id = '" +
                id + "'");
    }

    public Country findByName(String name) {
        return findBy("WHERE country_name = '" +
                name + "'");
    }

    private Country findBy(String condition) {
        String query = DEFAULT_QUERY + condition;
        try {
            ResultSet resultSet = getConnection()
                    .createStatement()
                    .executeQuery(query);
            if(resultSet == null) return null;
            if(resultSet.next()) {
                Country country = Country.buildFromResultSet(resultSet);
                return country;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(String id, String name, Region region) {
        return(executeUpdate(
                "INSERT INTO countries(country_id, country_name, region_id) VALUES('" +
                        id + "', '" + name + "', " + Integer.toString(region.getId())+")") > 0);
    }

    public boolean add(Country country) {
        return(executeUpdate(
                "INSERT INTO countries(country_id, country_name, region_id) VALUES('" +
                        country.getId() + "', '" + country.getName() + "', " + Integer.toString(country.getRegion().getId())+")") > 0);

    }
    public boolean delete(String id) {
        return(executeUpdate(
                "DELETE FROM countries WHERE country_id = '" +
                        id + "'") > 0);
    }

    public boolean update(String id, String name, Region region) {
        return(executeUpdate(
                "UPDATE countries SET country_name = '" +
                        name + "', region_id = " +
                        Integer.toString(region.getId()) + " WHERE country_id = '" +
        id + "'") > 0);
    }

    public Country findOrCreateByName(Country country) {
        Country storedCountry = findByName(country.getName());
        if(storedCountry != null) return storedCountry;
        return (add(country) ? country : null);
    }
}
