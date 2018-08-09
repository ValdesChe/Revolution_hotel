package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.dashboard.index;

/**
 * User: Valdor
 * Date: 30/01/2018
 */
@Security.Authenticated(Secured.class)
public class Dashboard extends Controller {

    public Result index() {
        return ok(index.render(User.findByEmail(ctx().session().get("email")) , null ,Form.form(Customering.NewTempCustomer.class)));
    }
}
