package pe.edu.utp.hrregionswebapp.viewcontrollers;

import pe.edu.utp.hrregionswebapp.HRService;
import pe.edu.utp.hrregionswebapp.models.Region;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by GrupoUTP on 30/09/2016.
 */
@WebServlet(name = "RegionsListServlet", urlPatterns = "/regionsList")
public class RegionsListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/MySQLDataSource");
            HRService service = new HRService();
            service.setConnection(dataSource.getConnection());
            List<Region> regions = service.getRegions();
            PrintWriter out = response.getWriter();
            out.println("Region Name");
            for( Region region : regions ) {
                out.println(region.getName());
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }
}
