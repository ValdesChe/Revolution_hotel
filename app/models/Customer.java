package models;

import com.avaje.ebeaninternal.server.lib.util.Str;
import models.utils.AppException;
import models.utils.Hash;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import com.avaje.ebean.Model;

import play.data.validation.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.*;

/**
 * User: Valdor
 * Date: 16/07/17
 */
@Entity
public class Customer extends Model {
    @Id
    @Constraints.Required
    public Long id;

    @Constraints.Required
    public String mat ;

    @Constraints.Required
    public Long u_id;

    @Constraints.Required
    public String gender ;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String tel;

    @Constraints.Required
    public String country;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Constraints.Required
    public DateTime creation ;

    // -- Queries (long id, user.class)
    public static Model.Finder<Long, Customer> find = new Model.Finder<>(Long.class, Customer.class);


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public Long getU_id() {
        return u_id;
    }

    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DateTime getCreation() {
        return creation;
    }

    public void setCreation(DateTime creation) {
        this.creation = creation;
    }

    /**
     * Retrieve a customer from an id.
     *
     * @param id customerID to search
     * @return a user
     */
    public static Customer findByID(Long id) {
        Customer customer = null;

        customer = find.where().eq("id", id).findUnique();
        return customer ;
    }

    /**
     * Retrieve a customer from comout.
     *
     * @param
     * @return a customer
     */
    // public static User findByComout(Date comout ) {
    // return find.where().eq("comout", comout).findA();
    //}

    /**
     * Retrieves a customer from a UserID.
     *
     * @param Uid the UserID ...
     * @return a customer if the UserID is found, null otherwise.
     */
    public static List<Customer> findByUserID(Long Uid) {
        ArrayList bt = new  ArrayList();

        return find.where().eq("u_id", Uid).findList();
    }

    /**
     *
     *
     * @param
     * @param
     * @return
     */
     public static Customer findByName(String name ){
         Customer cust = new Customer();
         cust = find.where().eq("name",name).findUnique();

         return cust;
     }


    /**
     *
     *
     * @param
     * @param
     * @return
     */
     public static Customer findByMat(String ID ){
         Customer cust = new Customer();
         cust = find.where().eq("mat",ID).findUnique();

         return cust;
     }


    /**
     *
     *
     * @param
     * @param
     * @return
     */
     public static Customer findByMat_Country( String mat , String country) {
         Customer cust = find.where().eq("country",country).eq("mat", mat).findUnique();
         return cust;
     }

    /**
     * Check if the customer is registred in customer table.
     *
     * @param customer     the info send
     * @return Index if there was a problem, null otherwise
     */
    public static Boolean  checkBeforeIfExist( String mat , String country) {
        // Check unique customer mat(*) and name(..)
        Customer custo = findByMat_Country(mat,country);
        if ((custo != null)) {
            // flash("info", "LE client etait d abord present ...");
            return true;
        }
        return false;
    }




}
