package controllers.customer;

import controllers.Bedrooming;
import controllers.Customering;
import models.Bedroom;
import models.Customer;
import models.Registration;
import models.User;
import play.Logger;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.bedroom.create;
import views.html.customer.customer;
import views.html.customer.multiIncome;
import views.html.dashboard.index;

import java.util.Date;

import static play.data.Form.form;

public class CreateRegistration extends Controller {
    /**
     * Save the new user.
     *
     * @return Successfull page or created form if bad
     */

    public Result registrate() {
        Form<Customering.Registrator> registratorForm = form(Customering.Registrator.class).bindFromRequest();
        String UserID = session("email");
        User user = User.findByEmail(UserID);
        if (registratorForm.hasErrors()) {
            return badRequest(index.render(User.findByEmail(ctx().session().get("email")) , null ,Form.form(Customering.NewTempCustomer.class)));
        }

        // if there s no error ...
        Customering.Registrator register = registratorForm.get();


        try {
            Registration regis = new Registration();
            regis.u_id = user.id;

            regis.save();

            return ok(index.render(user,null , form(Customering.NewTempCustomer.class)));

        } catch (Exception e) {
            Logger.error("Signup.save error", e);
            flash("error", Messages.get("error.technical"));

        }
        return ok(index.render(user,null , form(Customering.NewTempCustomer.class)));
    }




}
