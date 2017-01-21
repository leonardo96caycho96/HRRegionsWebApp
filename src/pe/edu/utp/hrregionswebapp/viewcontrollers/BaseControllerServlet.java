package pe.edu.utp.hrregionswebapp.viewcontrollers;

import pe.edu.utp.hrregionswebapp.HRService;
import pe.edu.utp.hrregionswebapp.models.Region;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GrupoUTP on 12/11/2016.
 */
public class BaseControllerServlet extends HttpServlet {
    HRService service = new HRService();
    private String singularEntityName;
    private String pluralizedEntityName;

    protected HRService getService() { return service;}
    public void setSingularEntityName(String name) { this.singularEntityName = name;}
    public void setPluralizedEntityName(String name) { this.pluralizedEntityName = name;}
    private String getViewUriEdit() { return "/edit"+ singularEntityName + ".jsp";}
    private String getViewUriNew() { return "/new" + singularEntityName + ".jsp";}
    private String getViewUriIndex() { return "/list"+pluralizedEntityName + ".jsp";}
    public static String ACTIONS_NEW = "new";
    public static String ACTIONS_EDIT = "edit";
    public static String ACTIONS_CREATE = "create";
    public static String ACTIONS_UPDATE = "update";
    public static String ACTIONS_DELETE = "delete";
    public static String ACTIONS_INDEX = "index";
    private String actionMessage;
    private Object entityObjectForEdit;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String actionUri;
        if(action.equalsIgnoreCase(ACTIONS_UPDATE)) {
            onUpdate(request);
            log(actionMessage);
            actionUri = getViewUriIndex();
        } else if(action.equalsIgnoreCase(ACTIONS_CREATE)) {
            onCreate(request);
            log(actionMessage);
            actionUri = getViewUriIndex();
        } else {
            actionUri = getViewUriIndex();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(actionUri);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String actionUri;
        if(action.equalsIgnoreCase(ACTIONS_NEW)) {
            actionUri = getViewUriNew();
            request.setAttribute("action", ACTIONS_NEW);
        } else if(action.equalsIgnoreCase(ACTIONS_EDIT)) {
            onEdit(request);
            request.setAttribute(singularEntityName.toLowerCase(), entityObjectForEdit);
            request.setAttribute("action", ACTIONS_EDIT);
            actionUri = getViewUriEdit();
        } else if(action.equalsIgnoreCase(ACTIONS_DELETE)) {
            onDelete(request);
            actionUri = getViewUriIndex();
            log(actionMessage);
        } else {
            actionUri = getViewUriIndex();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(actionUri);
        dispatcher.forward(request, response);
    }

    protected void setActionMessage(String message) { this.actionMessage = message; }

    protected void onUpdate(HttpServletRequest request) {
        Region region = service.getRegion(
                Integer.parseInt(request.getParameter("id")));
        region.setName(request.getParameter("name"));
        String message = service.updateRegion(region) ?
                "Region updated" : "Error while updating";
        setActionMessage(message);
    }

    protected void onCreate(HttpServletRequest request) {
        String message = service.addRegion(
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("name")) ?
                "Region created" : "Error while creating";
        setActionMessage(message);
    }

    protected void onDelete(HttpServletRequest request) {
        String message = service.deleteRegion(
                Integer.parseInt(request.getParameter("id"))) ?
                "Region deleted" : "Error while deleting";
        setActionMessage(message);
    }

    protected void onEdit(HttpServletRequest request) {
        Region region = service.getRegion(
                Integer.parseInt(request.getParameter("id")));
        setEntityObjectForEdit(region);
    }
    protected void setEntityObjectForEdit(Object entityObject) {
        this.entityObjectForEdit = entityObject;
    }
}
