package controllers.bedroom;

import models.Bedroom;
import play.mvc.Controller;
import play.mvc.Result;

public class BedroomInfo extends Controller {

    public Result printBedInfo ( Long id){
        Bedroom bed = Bedroom.findByID(id);
        if( bed != null ){

            return ok("Ok Bien recu ! Ici les info de la chambre id = "+id);
        }
        return badRequest("Ohhhh id incorrect ! Ne tente pas ");
    }


    public Result printInfo ( Long id){
       return printBedInfo(id);
    }

}
