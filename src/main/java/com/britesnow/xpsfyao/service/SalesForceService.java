package com.britesnow.xpsfyao.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

import com.britesnow.snow.util.JsonUtil;
import com.britesnow.xpsfyao.dao.ContactDao;
import com.britesnow.xpsfyao.dao.IDao.SortOrder;
import com.britesnow.xpsfyao.entity.Contact;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SalesForceService {
    private  static final String SF_URL = "https://na15.salesforce.com/services/data/v28.0";
    private  static final String SF_QUERY_URL = SF_URL+"/query";
    @Inject
    private ContactDao contactDao;
    
    /**
     * @return JSONArray results
     *         Long result_count
     * @throws IOException 
     * @throws HttpException 
     * @throws JSONException 
     */
    public Map listContacts(String token, int pageIndex, int pageSize) throws HttpException, IOException, JSONException{
        Map resultMap = new HashMap();
        resultMap.put("result",contactDao.list(pageIndex, pageSize, "id", SortOrder.ASC));
        resultMap.put("result_count", contactDao.count());
        return resultMap;
    }

    public void syncContacts(String token){
    	 Map resultMap = new HashMap();
         String sql = "SELECT id,name, title FROM Contact";
         OAuthRequest oauth = new OAuthRequest(Verb.GET,SF_QUERY_URL);
         oauth.addHeader("Authorization", "Bearer "+token);
         oauth.addHeader("X-PrettyPrint", "1");
         oauth.addQuerystringParameter("q", sql);
         Map opts = JsonUtil.toMapAndList(oauth.send().getBody());
         resultMap.put("result", opts.get("records"));
         saveContactsFromSF(opts.get("records"));
         
    }
    
    public void saveContactsFromSF(Object contacts){
    	JSONArray m = JSONArray.fromObject(contacts);
    	Iterator itor = m.iterator();
    	while(itor.hasNext()){
    		JSONObject js = JSONObject.fromObject(itor.next());
    		Contact contact = new Contact();
    		contact.setContact_id(js.get("Id").toString());
    		contact.setName(js.get("Name").toString());
    		addOrUpdateContact(contact);
    	}
    }
    
    public Contact addOrUpdateContact(Contact contact){
    	contact = contactDao.setContactId(contact);
    	if(contact.getId()!=null){
    		contactDao.update(contact);
    	}else{
    		contactDao.save(contact);
    	}
    	return contact;
    }
}
