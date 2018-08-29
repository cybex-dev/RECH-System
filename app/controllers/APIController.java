package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.NMU.EntityDepartment;
import dao.NMU.EntityFaculty;
import dao.UserSystem.EntityPerson;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;

import java.util.ArrayList;
import java.util.List;
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
            default: break;
        }
        return list;
    }

    public Result searchPerson() {

        JsonNode criteriaNode = request().body().asJson();
        final List<EntityPerson> entityPersonList = new ArrayList<>();

        if (criteriaNode == null)
            return badRequest();

        criteriaNode.forEach(jsonNode -> {
                    entityPersonList.addAll(EntityPerson.find.all().stream().filter(entityPerson -> {
                        switch (jsonNode.textValue()) {
                            case "name": {
                                return entityPerson.getUserFirstname().contains(jsonNode.asText());
                            }

                            case "surname": {
                                return entityPerson.getUserLastname().contains(jsonNode.asText());
                            }

                            case "email": {
                                return entityPerson.getUserEmail().contains(jsonNode.asText());
                            }

                            case "phone": {
                                return entityPerson.getContactOfficeTelephone().contains(jsonNode.asText());
                            }

                            case "office": {
                                return entityPerson.getOfficeAddress().contains(jsonNode.asText());
                            }

                            default:
                                return false;
                        }
                    }).collect(Collectors.toList()));
                });
        return ok(Json.toJson(entityPersonList));

    }

    public Result searchLocation() {
        return ok();
    }

    public Result javascriptRoutes(){
        return ok(
                JavaScriptReverseRouter.create("apiRoutes",
                        controllers.routes.javascript.APIController.getData(),
                        controllers.routes.javascript.APIController.searchPerson(),
                        controllers.routes.javascript.APIController.searchLocation()
                )
        ).as("text/javascript");
    }
}
