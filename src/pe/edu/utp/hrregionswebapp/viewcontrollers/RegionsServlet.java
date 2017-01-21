package pe.edu.utp.hrregionswebapp.viewcontrollers;

import pe.edu.utp.hrregionswebapp.HRService;
import pe.edu.utp.hrregionswebapp.models.Region;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by GrupoUTP on 11/11/2016.
 */
@WebServlet(name = "RegionsServlet", urlPatterns = "/regions")
public class RegionsServlet extends HttpServlet {
    HRService service = new HRService();
    public static String REGIONS_URI_EDIT = "/editRegion.jsp";
    public static String REGIONS_URI_NEW = "/newRegion.jsp";
    public static String REGIONS_URI_INDEX = "/listRegions.jsp";
    public static String ACTIONS_NEW = "new";
    public static String ACTIONS_EDIT = "edit";
    public static String ACTIONS_CREATE = "create";
    public static String ACTIONS_UPDATE = "update";
    public static String ACTIONS_DELETE = "delete";
    public static String ACTIONS_INDEX = "index";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String actionUri;
        if(action.equalsIgnoreCase(ACTIONS_UPDATE)) {
            Region region = service.getRegion(
                    Integer.parseInt(request.getParameter("id")));
            region.setName(request.getParameter("name"));
            String message = service.updateRegion(region) ?
                    "Region updated" : "Error while updating";
            log(message);
            actionUri = REGIONS_URI_INDEX;
        } else if(action.equalsIgnoreCase(ACTIONS_CREATE)) {
            String message = service.addRegion(
                    Integer.parseInt(request.getParameter("id")),
                    request.getParameter("name")) ?
                    "Region created" : "Error while creating";
            log(message);
            actionUri = REGIONS_URI_INDEX;
        } else {
            actionUri = REGIONS_URI_INDEX;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(actionUri);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String actionUri;
        if(action.equalsIgnoreCase(ACTIONS_NEW)) {
            actionUri = REGIONS_URI_NEW;
            request.setAttribute("action", ACTIONS_NEW);
        } else if(action.equalsIgnoreCase(ACTIONS_EDIT)) {
            Region region = service.getRegion(
                    Integer.parseInt(request.getParameter("id")));
            request.setAttribute("region", region);
            request.setAttribute("action", ACTIONS_EDIT);
            actionUri = REGIONS_URI_EDIT;
        } else if(action.equalsIgnoreCase(ACTIONS_DELETE)) {
            actionUri = REGIONS_URI_INDEX;
            String message = service.deleteRegion(
                    Integer.parseInt(request.getParameter("id"))) ?
                    "Region deleted" : "Error while deleting";
            log(message);
        } else {
            actionUri = REGIONS_URI_INDEX;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(actionUri);
        dispatcher.forward(request, response);
    }
}
