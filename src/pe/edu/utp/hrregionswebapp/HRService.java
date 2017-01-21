package pe.edu.utp.hrregionswebapp;

import pe.edu.utp.hrregionswebapp.models.CountriesEntity;
import pe.edu.utp.hrregionswebapp.models.Country;
import pe.edu.utp.hrregionswebapp.models.Region;
import pe.edu.utp.hrregionswebapp.models.RegionsEntity;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by GrupoUTP on 30/09/2016.
 */
public class HRService implements Serializable {
    private Connection connection;
    private RegionsEntity regionsEntity;
    private CountriesEntity countriesEntity;

    public HRService() {
        InitialContext ctx = null;
        try {
            ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/MySQLDataSource");
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private RegionsEntity getRegionsEntity() {
        if(regionsEntity == null) {
            regionsEntity = new RegionsEntity();
            regionsEntity.setConnection(connection);
        }
        return regionsEntity;
    }

    public List<Region> getRegions() {
        if(getConnection() != null) {
            return getRegionsEntity().findAll();
        }
        return null;
    }

    public int getRegionsCount() { return this.getRegions().size();}

    public Region getRegion(int id) {
        if(getConnection() != null) {
            return getRegionsEntity().findById(id);
        }
        return null;
    }

    public Region getRegion(String name) {
        if(getConnection() != null) {
            return getRegionsEntity().findByName(name);
        }
        return null;
    }

    public boolean addRegion(int id, String name) {
        return ((getConnection() != null) ?
                getRegionsEntity().add(id, name) : false);
    }

    public boolean deleteRegion(int id) {
        return ((getConnection() != null) ?
                getRegionsEntity().delete(id) : false);
    }

    public boolean updateRegion(int id, String name) {
        return ((getConnection() != null) ?
                getRegionsEntity().update(id, name) : false);
    }

    public boolean updateRegion(Region region) {
        return updateRegion(region.getId(), region.getName());
    }

    public CountriesEntity getCountriesEntity() {
        if(countriesEntity == null) {
            countriesEntity = new CountriesEntity();
            countriesEntity.setConnection(connection);
        }
        return countriesEntity;
    }

    public List<Country> getCountries() {
        if(connection != null) {
            return getCountriesEntity().findAll();
        }
        return null;
    }

    public List<Country> getCountriesWithRegion() {
        if(connection != null) {
            return getCountriesEntity().findAllWithRegion(getRegionsEntity());
        }
        return null;
    }

}










