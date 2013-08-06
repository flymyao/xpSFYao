package com.britesnow.xpsfyao.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.britesnow.xpsfyao.entity.Contact;

public class ContactDao extends BaseHibernateDao<Contact>{

	  public boolean existContact(String contact_id) {
		  Map m = new HashMap();
		  m.put("contact_id", contact_id);
		  Long c =count(m);
		  return c>0L;
	    }
	  
	  public Contact setContactId(Contact contact){
	        List<Contact> contacts = search("from Contact c where c.contact_id = ?", contact.getContact_id());
	        if (contacts.size() == 1) {
	        	contacts.get(0).setName(contact.getName());
	        	return contacts.get(0);
	        }
	        return contact;
	  }
}
