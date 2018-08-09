package controllers;

import controllers.customer.CreateRegistration;
import models.*;
import org.joda.time.DateTime;
import play.Logger;
import play.data.Form;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import views.html.customer.customer;
import views.html.customer.finalizeCustomer;
import views.html.customer.multiIncome;
import views.html.dashedboard;
import java.util.Date;
import java.util.List;

import static play.data.Form.form;

/**
 * CReate a new customer and managing
 * User: Valdor
 * Date : 26 - 01 -2018 06:07 Tetouan
 */
@Security.Authenticated(Secured.class)
public class Customering extends Controller {
    /**
     * @return
     */
    public Result index() {
        List<Bedroom> beds = null;
        String userMail = ctx().session().get("email");

        User user = User.findByEmail(userMail);
        beds = (List<Bedroom>) Bedroom.findBystatus(1);

        return ok(customer.render(user,beds, form(Customering.NewTempCustomer.class) , TypeBed.findAll(), "nothing"));
    }

    /**
     * NewCustomer class used by Customer Form.
     */
    public static class NewCustomer {

        @Constraints.Required
        public String mat;

        @Constraints.Required
        public String gender;


        @Constraints.Required
        public String email;

        @Constraints.Required
        public String name;

        @Constraints.Required
        public String tel;

        /**
         * Validate the authentication.
         *
         * @return null if validation ok, string with details otherwise
         */
        public String validate() {
            // if()
            return null;
        }

    }

    /**
     * NewRegistrator class used by Registrator Form.
     */
    public static class Registrator {

        @Constraints.Required
        public long id;

        @Constraints.Required
        public long mat;

        @Constraints.Required
        public long u_id;

        @Constraints.Required
        public String gender;

        @Constraints.Required
        public String email;

        @Constraints.Required
        public String name;

        @Constraints.Required
        public long tel;

        @Constraints.Required
        public String country;

        @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
        @Constraints.Required
        public Date creation = new Date();

        /**
         * Validate the authentication.
         *
         * @return null if validation ok, string with details otherwise
         */
        public String validate() {

            return null;
        }

    }

    /**
     * We save temp customer
     * NewCustomer class used by Customer Form.
     */
    public static class NewTempCustomer {
        @Constraints.MinLength(5)
        @Constraints.MaxLength(15)
        @Constraints.Required
        public String mat;

        @Constraints.Required
        public String country;

        /**
         * Validate the authentication.
         *
         * @return null if validation ok, string with details otherwise
         */
        public String validate() {
            if (isBlank(mat) ) {
                return "Mat is required";
            }

            if (isBlank(country)) {
                return "Country name is required";
            }


            return null;
        }

        private boolean isBlank(String input) {
            return (input == null || input.isEmpty() || input.trim().isEmpty());
        }

    }

    // Get tmp customer
    public Result saveTempo() {

        //Form<Customering.NewTempCustomer> cusTmpForm = form(NewTempCustomer.class).bindFromRequest();

        Form<Customering.NewTempCustomer> cusTmpForm = form(NewTempCustomer.class).bindFromRequest();
        // User users = User.isConnected();


        if (cusTmpForm.hasErrors()) {
            return badRequest("Une erreur dans votre formulaire de saisie tmpCustomer");
        }

        // if there s no error ...
        else {
            List<Bedroom> beds = Bedroom.findAll();

            //  For improvement we can just pass directly mat and country, not necessary An Object
            Customer customer = new Customer();
            customer.mat = cusTmpForm.get().mat;
            customer.country =  cusTmpForm.get().country;

            // Check if the customer exists or  not
            Boolean exist = Customer.checkBeforeIfExist(customer.mat, customer.country);

            if ( !exist){

                String UserMail = session("email");
                User user = User.findByEmail(UserMail) ;

                customer.setU_id(user.id);
                customer.setCreation( new DateTime());

                // We can save tmp and update after
                customer.save();

                return ok(finalizeCustomer.render(user , customer ,  "nothing"));
            }


            // He exist
            else {

                //Form<Customering.NewCustomer> regForm = null;
                return ok("Il existe");

            }

        }
    }

    public Result saveNew() {

        Form<Customering.NewCustomer> registerForm = form(Customering.NewCustomer.class).bindFromRequest();

        String UserMail = session("email");
        User user = User.findByEmail(UserMail);
        if (registerForm.hasErrors()) {
            return badRequest(customer.render( user , Bedroom.findByReIND(1), form(Customering.NewTempCustomer.class) ,  TypeBed.findAll(), " Error Customer cannot be saved ! ")) ;
        }

        // if there s no error ...


        try {

            Customer customer = Customer.findByMat(registerForm.get().mat);

            customer.u_id = user.id ;
            customer.email = registerForm.get().email;
            customer.gender = registerForm.get().gender;
            customer.tel = registerForm.get().tel;
            customer.name = registerForm.get().name;

            customer.save();

            return ok(views.html.customer.customer.render(user , Bedroom.findBystatus(1), form(Customering.NewTempCustomer.class), TypeBed.findAll(), "Correctly saved " ));

        } catch (Exception e) {
            Logger.error("Signup.save error", e);
            flash("error", Messages.get("error.technical"));
            return ok(views.html.customer.customer.render(user , Bedroom.findBystatus(1), form(Customering.NewTempCustomer.class) , TypeBed.findAll(), Messages.get("error.technical")));

        }


    }






    public Result validate(){
        return ok("Validation Step ");
    }


}