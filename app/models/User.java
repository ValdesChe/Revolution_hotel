package models;

import models.utils.AppException;
import models.utils.Hash;
import play.data.format.Formats;
import play.data.validation.Constraints;
import com.avaje.ebean.Model;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

import static play.mvc.Controller.session;

/**
 * User: Valdes Che
 * Date: 26 - 01 - 2018 06:06
 * @Location Tetouan - Morocco
 */
@Entity
public class User extends Model {

    @Id
    public Long id;

    
    @Constraints.Required
    @Formats.NonEmpty
    @Column(unique = true)
    public String email;

    @Constraints.Required
    @Formats.NonEmpty
    @Column(unique = true)
    public String fullname;

    @Constraints.Required
    public String typeu_id ;

    // public String confirmationToken;

    @Constraints.Required
    @Formats.NonEmpty
    public String passwordHash;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date dateCreation   = new Date();

    @Formats.NonEmpty
    public Boolean validated ;

    // -- Queries (long id, user.class)
    public static Model.Finder<Long, User> find = new Model.Finder<Long, User>(Long.class, User.class);


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTypeu_id() {
        return typeu_id;
    }

    public void setTypeu_id(String typeu_id) {
        this.typeu_id = typeu_id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }


    /**
     * The connected user
     *
     */

    public static User isConnected(){
        String UserMail = session("email");
        User user = User.findByEmail(UserMail) ;
        return user;
    }


    /**
     * Retrieve a user from an email.
     *
     * @param email email to search
     * @return a user
     */
    public static User findByEmail(String email) {
        User user = null;

        user = find.where().eq("email", email).findUnique();
        return user ;
    }


    /**
     * Retrieve a user from a fullname.
     *
     * @param fullname Full name
     * @return a user
     */
    public static User findByFullname(String fullname) {
        return find.where().eq("fullname", fullname).findUnique();
    }


    /**
     * Retrieves a user from a confirmation token.
     *
     * @param token the confirmation token to use.
     * @return a user if the confirmation token is found, null otherwise.
     *
    public static User findByConfirmationToken(String token) {
        return find.where().eq("confirmationToken", token).findUnique();
    }

    */



    /**
     * Authenticate a User, from a email and clear password.
     *
     * @param email         email
     * @param clearPassword clear password
     * @return User if authenticated, null otherwise
     * @throws AppException App Exception
     */
    public static User authenticate(String email, String clearPassword) throws AppException {

        // get the user with email only to keep the salt password
        User user = find.where().eq("email", email).findUnique();
        if (user != null) {
            // get the hash password from the salt + clear password

            if (Hash.checkPassword(clearPassword, user.passwordHash)) {
                return user;
            }
        }
        return null;
    }

    public void changePassword(String password) throws AppException {
        this.passwordHash = Hash.createPassword(password);
        this.save();
    }

    /**
     * Confirms an account.
     *
     * @return true if confirmed, false otherwise.
     * @throws AppException App Exception
     **/
    public static boolean confirm(User user) throws AppException {
        if (user == null) {
            return false;
        }

        // user.confirmationToken = null;
        user.validated = true;
        user.save();
        return true;
    }



}
