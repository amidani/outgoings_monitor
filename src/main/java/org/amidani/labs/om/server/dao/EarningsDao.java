package org.amidani.labs.om.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.logging.Logger;

import org.amidani.labs.om.server.model.Earning;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.Query;

@Repository
public class EarningsDao {
	Logger log = Logger.getLogger(this.getClass().getName());
	
	static {
        ObjectifyService.register(Earning.class);
    }
	
	public List<Earning> getEarnings(){
		log.info("DAO : Get earnings");
		Query<Earning> articlesList = ofy().load().type(Earning.class);
		log.info("DAO : Earnings retrieved successfuly");
		return articlesList.list();
	}
	
	public long persistEarning(Earning earning){
		log.info("DAO : Add new earnings");
		Key<Earning> key = ofy().save().entity(earning).now();    // async without the now();
		log.info("DAO : Earnings persisted successfuly with key=["+key.getId()+"]");
		return key.getId();
	}
	
	public void removeEarning(Long id) throws Exception{
		log.info("DAO : Delete earnings");
		try{
			ofy().delete().type(Earning.class).id(id);
		}catch(Exception e){throw new Exception("Unable to delete earnings with id = ["+id+"]", e.getCause());}
	}

}
