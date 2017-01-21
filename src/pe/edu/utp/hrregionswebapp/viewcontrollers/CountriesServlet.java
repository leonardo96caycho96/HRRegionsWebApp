package pe.edu.utp.hrregionswebapp.viewcontrollers;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by GrupoUTP on 12/11/2016.
 */
public class CountriesServlet extends BaseControllerServlet {

    public CountriesServlet() {
        super();
        setSingularEntityName("Country");
        setPluralizedEntityName("Countries");
    }
    @Override
    protected void onCreate(HttpServletRequest request) {
        String message = service.addCountry(
                request.getParameter("id"),
                request.getParameter("name"),
                getService().getRegion(Integer.parseInt(
                        request.getParameter("regionId")))) ?
                "Region created" : "Error while creating";
        setActionMessage(message);
    }
    @Override
    protected void onUpdate(HttpServletRequest request) {

    }
}
