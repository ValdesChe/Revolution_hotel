package controllers;

import models.TypeBed;
import models.User;
import org.joda.time.DateTime;
import play.mvc.Controller;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


/**
 * CReate a new Shortcut
 * User: Valdor
 * Date : 31 - 01 -2018 20:36 Tetouan
 */
@Security.Authenticated(Secured.class)
public class Shortcutering extends Controller {

    public  Result  NewBedTYpe  (String typeName , String typePri){
        Long typePrice = Long.valueOf(typePri).longValue();
        if (isBlank(typeName) || (typePrice < 0) )
            return badRequest("erreur dans votre formulaire ....");

        try {

            TypeBed typeBed = new TypeBed();
            typeBed.title = typeName;
            typeBed.price = typePrice;
            typeBed.status = 1;
            typeBed.creation = new DateTime();
            typeBed.u_id = User.isConnected().id;
            typeBed.numbers = Long.valueOf(0);

            typeBed.save();
        }
        catch (Exception e){
            System.out.println("Erreur de derniere minute");
        }

       return ok("Correctement enregistré");

    }


    private boolean isBlank(String input) {
        return (input == null || input.isEmpty() || input.trim().isEmpty());
    }




}
