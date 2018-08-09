package controllers.bedroom;

import models.TypeBed;
import play.mvc.Controller;
import play.mvc.Result;

public class TypeBedroomInfo extends Controller {
    public Result printInfoTypeBed(String typename){
        TypeBed typeBed = TypeBed.findByLibele(typename);
        if (typeBed != null){
            return ok("C est bon ");
        }
        return badRequest("Restricted area");
    }
}
