package models;

import com.avaje.ebeaninternal.server.lib.util.Str;
import models.Bedroom;
import models.Customer;

import models.utils.AppException;
import models.utils.Hash;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import com.avaje.ebean.Model;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Valdes
 * Date: 25/07/17
 */
@Entity
public class Bedroom extends Model {

    @Id
    public Long id;


    @Constraints.Required
    public Long re_ind ;

    @Constraints.Required
    public Long u_id ;

    @Constraints.Required
    public  String  name ;

    @Constraints.Required
    public Long prevind;


    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Constraints.Required
    public DateTime lastmodify;

    @Constraints.Required
    public Long status;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Constraints.Required
    public DateTime entrance ;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Constraints.Required
    public DateTime creation;

    // -- Queries (long id, user.class)
    public static Model.Finder<Long, Bedroom> find = new Model.Finder<Long, Bedroom>(Long.class, Bedroom.class);



    /**
     * Retrieve
     *
     * @param  to search
     * @return a
     */
    public static Bedroom findByUID(String u_id) {
       Bedroom bedroom = null;

        bedroom = find.where().eq("u_id", u_id).findUnique();
        return bedroom ;
    }

    public static Bedroom findByID(long id) {
       Bedroom bedroom = null;

        bedroom = find.where().eq("id", id).findUnique();
        return bedroom ;
    }

    public static List<Bedroom> findAll() {
        List<Bedroom> bedroom = null;
        bedroom = find.where().orderBy("name").findList();
        return bedroom ;
    }

    public static List<Bedroom> findByCreated(Date date) {
        ArrayList<Bedroom> bedroom = null;
        bedroom = (ArrayList<Bedroom>) find.where().eq("creation", date).findList();
        return bedroom ;
    }

    public static List<Bedroom>  findByEntrance(Date date) {
        List<Bedroom> bedroom = null;
        bedroom = find.where().eq("entrance", date).findList();
        return bedroom ;
    }

    // Register indice
    public static List<Bedroom> findByReIND(long ind) {
        List<Bedroom> bedroom = null;

        bedroom = find.where().eq("re_ind", ind).findList();
        return bedroom ;
    }


    //
    public static List<Bedroom> findBystatus(int ind) {
        List<Bedroom> bedroom = null;
        bedroom = find.where().eq("status",ind ).findList();
        return bedroom ;
    }

    // Give me the type of a bedroom from id ...
    public static  long getTypeFrom ( long id ){
        Bedroom bed =  new Bedroom();
        bed = find.where().eq("id",id).findUnique();
        return bed.id;
    }


    /**
     *
     *
     * @param
     * @param
     * @return
     * @throws AppException App Exception
     */
    public static Bedroom authenticate(long id) throws AppException {

        // get the bedroom with id only to keep
        Bedroom bedroom = findByID(id);
        //
        if (bedroom.status == 1) {
            return bedroom;
        }
        return null;
    }


}
