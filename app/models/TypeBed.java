package models;

import models.utils.AppException;
import models.utils.Hash;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import play.data.format.Formats;
import play.data.validation.Constraints;
import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Entity
public class TypeBed extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public Long u_id;


    @Constraints.Required
    public  String  title ;

    @Constraints.Required
    public Long price;


    @Constraints.Required
    public Integer status;

    @Constraints.Required
    public Long numbers;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Constraints.Required
    public DateTime creation;

    // -- Queries (long id, user.class)
    public static Model.Finder<Long, TypeBed> find = new Model.Finder<Long, TypeBed>(Long.class, TypeBed.class);

    // Retrive type from a title
    public static TypeBed findByLibele(String lib){
        TypeBed type =  find.where().like("title","%"+lib+"%").findUnique();
        return type;
    }


    // Retrive type from an interval of price
    public static List<TypeBed> findByInterval(Long inf , Long sup){
        ArrayList <TypeBed> type = new ArrayList<>();
        type = (ArrayList<TypeBed>) find.where().gt("price",inf).ge("price",sup).findList();
        return type;
    }


    // Retrive Alltype from nothing
    public static List<TypeBed> findAll(){
        List <TypeBed> type = null;
        type =  find.where().orderBy("title ASC").findList();
        return type;
    }

    // Retrive by Status
    public static List<TypeBed> findByStatus(Long status){
        List <TypeBed> type = null;
        type =  find.where().eq("status",status).findList();
        return type;
    }


    public static List<TypeBed> findByUser(Long u_id){
        List<TypeBed> typeBed;
        typeBed = find.where().eq("u_id",u_id).findList();
        return typeBed;
    }


    public static TypeBed findByIndice(Long typ_id){
        TypeBed typeBed ;
        typeBed = find.where().eq("id"  , typ_id).findUnique();
        return typeBed;
    }


    public static String createNewBed(Long typeBedId) {
        String prefix;
        // We manage manually but it has to be auto

        ArrayList<String> prefixes = new ArrayList<>();
        List<TypeBed> typesBeds = TypeBed.findByStatus((long) 1);
        for ( TypeBed typ : typesBeds){
            // Getting the 3 First characters
           prefixes.add(typ.title.substring(0,2).toUpperCase()+typ.id+"."+(typ.numbers+1));
        }

        prefix = prefixes.get((int) (typeBedId-1));


        // Here we increment the number of beds
        TypeBed updater = TypeBed.findByIndice(typeBedId);
        updater.numbers +=1;
        updater.save();


        return prefix;


    }








}
