package pe.edu.utp.hrregionswebapp.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GrupoUTP on 30/09/2016.
 */
public class RegionsEntity extends BaseEntity {
    private static String DEFAULT_QUERY =
            "SELECT * FROM regions";

    public List<Region> findAll()  {
        return findAllByCriteria("");
    }

    public List<Region> findAllOrderedByName() {
        return findAllByCriteria("ORDER BY region_name");
    }

    public List<Region> findAllWithCountries() {
        return findAllByCriteria(
                "WHERE region_id IN " +
                "(SELECT DISTINCT region_id FROM countries)");
    }

    public int findCountriesCountFor(int id) {
        return findCountriesCountForCriteria(
                "WHERE countries.region_id = " +
                        Integer.toString(id));
    }

    public int findCountriesCountFor(String name) {
        return findCountriesCountForCriteria(
                "WHERE countries.region_id IN (" +
                        "SELECT region_id FROM regions " +
                        "WHERE region_name = '" + name + "')");
    }

    private int findCountriesCountForCriteria(String criteria) {
        String sql = "SELECT COUNT(*) " +
                "AS countries_count FROM countries" +
                " " + criteria;
        try {
            ResultSet resultSet = getConnection()
                    .createStatement()
                    .executeQuery(sql);
            if(resultSet.next())
                return resultSet.getInt("countries_count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private List<Region> findAllByCriteria(String criteria) {
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(DEFAULT_QUERY +
                    " " + criteria);
            if(resultSet != null) {
                List<Region> regions = new ArrayList<>();
                while(resultSet.next()) {
                    Region region = Region.buildFromResultSet(resultSet);
                    regions.add(region);
                }
                return regions;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Region findById(int id) {
        return findBy(" WHERE region_id = " +
                Integer.toString(id));
    }

    public Region findByName(String name) {
        return findBy("WHERE region_name = '" +
                name + "'");
    }

    private Region findBy(String condition) {
        String query = DEFAULT_QUERY + " "+ condition;
        try {
            ResultSet resultSet = getConnection()
                    .createStatement()
                    .executeQuery(query);
            if(resultSet == null) return null;
            if(resultSet.next()) {
                Region region = Region.buildFromResultSet(resultSet);
                return region;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(int id, String name) {
        return(executeUpdate(
                "INSERT INTO regions(region_id, region_name) VALUES(" +
                Integer.toString(id) + ", '" + name + "')") > 0);
    }

    public boolean delete(int id) {
        return(executeUpdate(
                "DELETE FROM regions WHERE region_id = " +
                Integer.toString(id)) > 0);
    }

    public boolean update(int id, String name) {
        return(executeUpdate(
                "UPDATE regions SET region_name = '" +
                name + "' WHERE region_id = " +
                        Integer.toString(id)) > 0);
    }

    public Region findLast() {
        return findBy("WHERE region_id = (SELECT MAX(region_id) FROM regions)");
    }

    public Region findOrCreateByName(String name) {
        Region region = findByName(name);
        if(region != null) return region;
        region = new Region(
                findLast().getId() + 1,
                name);
        return (add(region.getId(), region.getName()) ? region : null);
    }

}








