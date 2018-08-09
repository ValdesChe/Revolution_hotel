package controllers;

import models.User;
import models.utils.AppException;
import models.utils.Hash;
import play.Logger;
import play.data.Form;
import play.data.DynamicForm;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index.index;


import static play.data.Form.form;

/**
 * Login and Logout.
 * User: V@ldors
 */

public class Application extends Controller {

    public static Result GO_HOME = redirect(
            routes.Application.index()
    );

    public static Result GO_DASHBOARD = redirect(
            routes.Dashboard.index()
    );

    /**
     * Display the login page or dashboard if connected
     *
     * @return login page or dashboard
     */
    public Result index() {
        // Check that the email matches a confirmed user before we redirect.
        // We catch session var ...
        String email = ctx().session().get("email");
        if (email != null) {
            User user = User.findByEmail(email);
            if (user != null && user.validated) {
                return GO_DASHBOARD;
            } else {
                // Logger.debug("Clearing invalid session credentials");
                session().clear();
            }
        }

        return ok(index.render(form(Login.class)));
    }

    /**
     * Login class used by Login Form.
     */
    public static class Login {

        // public String email;
        @Constraints.Required
        @Constraints.Email
        @Constraints.MinLength(10)
        @Constraints.MaxLength(30)
        public String email;

        @Constraints.Required
        @Constraints.MinLength(10)
        @Constraints.MaxLength(20)
        public String password;

        /**
         * Validate the authentication.
         *
         * @return null if validation ok, string with details otherwise
         */
        public String validate() {

            User user = null;
            try {
                //System.out.println(Hash.createPassword(password));
                user = User.authenticate(email, password);
            } catch (AppException e) {
                return Messages.get("error.technical");
            }
            if (user == null) {
                return Messages.get("invalid.user.or.password");
            }

            else if (!user.validated) {
                return Messages.get("account.not.validated.check.mail");
            }
            return null;
        }

    }

    // signUp manager ...
    public static class Register {

        @Constraints.Required
        public String email;

        @Constraints.Required
        public String fullname;

        @Constraints.Required
        public String inputPassword;

        /**
         * Validate the authentication.
         *
         * @return null if validation ok, string with details otherwise
         */
        public String validate() {
            if (isBlank(email)) {
                return "Email is required";
            }

            if (isBlank(fullname)) {
                return "Full name is required";
            }

            if (isBlank(inputPassword)) {
                return "Password is required";
            }

            return null;
        }

        private boolean isBlank(String input) {
            return input == null || input.isEmpty() || input.trim().isEmpty();
        }
    }

    /**
     * Handle login form submission.
     *
     * @return Dashboard if auth OK or login form if auth KO
     */
    public Result authenticate() throws AppException {
        Form<Application.Login> loginForm = form(Login.class).bindFromRequest();

        // Form<Register> registerForm = form(Register.class);


        if (loginForm.hasErrors()) {
            try {
                System.out.println(Hash.createPassword(loginForm.get().password));
            }
            catch (Exception e){

            }
            return GO_HOME;
        }

        String email = loginForm.get().email;
        session("email", email);

        User user = User.findByEmail(email);
        session("id", user.id.toString());
        return GO_DASHBOARD;
    }

    /**
     * Logout and clean the session.
     *
     * @return Index page
     */
    /**
     * Logs out (only for authenticated users) and returns them to the Index page.
     * @return A redirect to the Index page.
     */
    @Security.Authenticated(Secured.class)
    public Result logout() {
        session().clear();
        flash("success", Messages.get("youve.been.logged.out"));
        return GO_HOME;
    }

}