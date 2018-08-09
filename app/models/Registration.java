package models;


import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

public class Registration extends Model {

    // pk of field 
    @Constraints.Required
    public Long ind;

    // Customer id
    @Constraints.Required
    public Long cu_mat;

    // date id
    @Constraints.Required
    public Long da_id;

   // user  id
    @Constraints.Required
    public Long u_id;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Constraints.Required
    public DateTime comein = new DateTime();

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Constraints.Required
    public DateTime comeout = new DateTime();

    @Constraints.Required
    public int duration ;

    @Constraints.Required
    public String withs ;



    // -- Queries (long id, user.class)
    public static Model.Finder<Long, Customer> find = new Model.Finder<>(Long.class, Customer.class);




    /**
     * Retrieve a customer from an id.
     *
     * @param id customerID to search
     * @return a user
     */
    public static Customer findByID(long id) {
        Customer customer = null;

        customer = find.where().eq("id", id).findUnique();
        return customer ;
    }
}
