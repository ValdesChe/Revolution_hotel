package controllers;

import models.Bedroom;
import models.TypeBed;
import models.User;
import play.Logger;
import play.data.Form;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import java.util.Date;
import java.util.List;

import views.html.bedroom.create;
import  views.html.bedroom.utils.available;
import static play.data.Form.form;
import org.joda.time.*;
/**
 * CReate a new bedroom and managing
 * User: Valdor
 */
@Security.Authenticated(Secured.class)
public class Bedrooming extends Controller {


    /**
     * 
     *
     * @return 
     */
    public Result index() {
        String userEmail = ctx().session().get("email");
        User user = User.findByEmail(userEmail);
        List <TypeBed> typeBed = TypeBed.findByStatus((long) 1);
        String msgToDisplay = null;
        return ok(create.render(user , form(Bedrooming.newBedroom.class) ,typeBed , Bedroom.findAll(), Messages.get("info.none") ));
    }

    /**
     * 
     */
    public static class newBedroom {
        @Constraints.Required
        public Long indiceType;

        // Validation of the form
        public String validate(){
            if (isDefault(indiceType)){
                return "Problem with type || Status default value found";
            }

            return null;
        }

        private boolean isDefault( Long check) {
            return ( check == 0 );
        }

       /* private boolean isTypeExist( Long check) {
            return ( TypeBed.findByIndice(check) != null );
        }
        */
    }


    public static class newTypeBed{
        @Constraints.MinLength(4)
        @Constraints.MaxLength(20)
        @Constraints.Required
        public String libele;

        @Constraints.Required
        public Long price;


        public String validate(){
            if (isBlank(libele))
                return "Vide Trouvé xD";

            return null;
        }

    }


    // Verif method
    public static boolean isBlank(String input) {
        return (input == null || input.isEmpty() || input.trim().isEmpty());
    }


    public Result saveNewTypeBed(){
        return ok("Creating new TYpe of Bedroom ");
    }

    public Result saveNewBed(){
        Form<newBedroom> newBedroomForm = form(Bedrooming.newBedroom.class).bindFromRequest();

        if (newBedroomForm.hasErrors()){
            return badRequest("Error with NewBedroom FOrm");
        }

        Long typeBedId = newBedroomForm.get().indiceType;
        //Long StatusSet = newBedroomForm.get().status;

        try {
            Bedroom bedroom = new Bedroom();
            bedroom.u_id =  Long.valueOf(session("id"));
            bedroom.creation = new DateTime();
            bedroom.entrance = null;
            bedroom.lastmodify = new DateTime();
            bedroom.name = TypeBed.createNewBed(typeBedId);
            bedroom.status = Long.valueOf(1);
            bedroom.prevind = null;
            bedroom.save();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(Messages.get("error.technical"));
            return badRequest("Something Bad happened");
        }

        return ok(create.render( User.isConnected() , form(Bedrooming.newBedroom.class) ,TypeBed.findAll() , Bedroom.findAll(), Messages.get("info.none")));
    }






}
