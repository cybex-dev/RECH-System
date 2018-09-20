package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.ApplicationSystem.EntityEthicsApplication;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import dao.NMU.EntityDepartment;
import dao.NMU.EntityFaculty;
import dao.UserSystem.EntityPerson;
import helpers.CookieTags;
import models.UserSystem.UserType;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class APIController extends Controller {
    public Result getData() {

        String data = request().body().asJson().asText();

        switch (data) {
            case "faculties": {
                return ok(Json.toJson(EntityFaculty.getAllFacultyNames()));
            }
            case "departments": {
                return ok(Json.toJson(EntityDepartment.getAllDepartmentNames()));
            }
            default: return ok();
        }
    }

    public static List<String> getDataDirect(String data) {
        List<String> list = new ArrayList<>();
        switch (data) {
            case "faculties": {
                list = EntityFaculty.getAllFacultyNames();
                break;
            }
            case "departments": {
                list = EntityDepartment.getAllDepartmentNames().stream().map(departmentContainer -> departmentContainer.dept).collect(Collectors.toList());
                break;
            }

            case "pi_firstname":{
                EntityPerson personById = EntityPerson.getPersonById(session().get(CookieTags.user_id));
                if (personById == null){
                    list.add("");
                } else {
                    list.add(personById.getUserFirstname());
                }
                break;
            }

            case "pi_lastname":{
                EntityPerson personById = EntityPerson.getPersonById(session().get(CookieTags.user_id));
                if (personById == null){
                    list.add("");
                } else {
                    list.add(personById.getUserLastname());
                }
                break;
            }

            case "pi_degree":{
                EntityPerson personById = EntityPerson.getPersonById(session().get(CookieTags.user_id));
                if (personById == null){
                    list.add("");
                } else {
                    list.add(personById.getCurrentDegreeLevel());
                }
                break;
            }

            case "prp_contact_email": {
                list = EntityPerson.find.all()
                        .stream()
                        .filter(entityPerson -> entityPerson.userType() != UserType.RCD &&
                                entityPerson.userType() != UserType.PrimaryInvestigator)
                        .map(entityPerson -> entityPerson.getUserTitle().concat(" ").concat(entityPerson.getUserFirstname()).concat(" ").concat(entityPerson.getUserLastname()).concat(" [").concat(entityPerson.getUserEmail().concat("]")))
                        .collect(Collectors.toList());
            }

            default: break;
        }
        return list;
    }

    public Result searchPerson(String email) {

        Map<String, String> map = new HashMap<>();
        EntityPerson entityPerson1 = EntityPerson.find.all().stream().filter(entityPerson -> entityPerson.getUserEmail().equals(email)).findFirst().orElse(null);
        if (entityPerson1 != null) {
            map.put("firstname", entityPerson1.getUserFirstname());
            map.put("lastname", entityPerson1.getUserLastname());
            map.put("telephone", entityPerson1.getContactOfficeTelephone());
            map.put("mobile", entityPerson1.getContactNumberMobile());
            map.put("address", entityPerson1.getOfficeAddress());
            map.put("faculty", entityPerson1.getFacultyName());
            map.put("department", entityPerson1.getDepartmentName());
        }
        return ok(Json.toJson(map));


    }

    public Result searchLocation() {
        return ok();
    }

    public Result findAllReviewers(String applicationId){
        List<Map<String, String>> list = new ArrayList<>();
        EntityEthicsApplication application = EntityEthicsApplication.GetApplication(EntityEthicsApplicationPK.fromString(applicationId));

        if (application != null){
            String pi = EntityPerson.getPersonById(application.getPiId()).getUserEmail();
            String prp = EntityPerson.getPersonById(application.getPiId()).getUserEmail();

            EntityPerson.findAllReviewers()
                    .stream()
                    .filter(entityPerson -> !entityPerson.getUserEmail().equals(pi) &&
                            !entityPerson.getUserEmail().equals(prp))
                    .forEach(p -> {
                        Map<String, String> map = new HashMap<>();
                        map.put("title", p.getUserTitle());
                        map.put("firstname", p.getUserFirstname());
                        map.put("lastname", p.getUserLastname());
                        map.put("dept", p.getDepartmentName());
                        map.put("faculty", p.getFacultyName());
                        map.put("email", p.getUserEmail());
                        list.add(map);
                    });
        }
        return ok(Json.toJson(list));
    }

    public Result javascriptRoutes(){
        return ok(
                JavaScriptReverseRouter.create("apiRoutes",
                        controllers.routes.javascript.APIController.getData(),
                        controllers.routes.javascript.APIController.searchPerson(),
                        controllers.routes.javascript.APIController.searchLocation(),
                        controllers.routes.javascript.APIController.findAllReviewers()
                )
        ).as("text/javascript");
    }
}
