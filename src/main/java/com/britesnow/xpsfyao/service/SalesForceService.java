package com.britesnow.xpsfyao.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

import com.britesnow.snow.util.JsonUtil;
import com.google.inject.Singleton;

@Singleton
public class SalesForceService {
    private  static final String SF_URL = "https://na15.salesforce.com/services/data/v28.0";
    private  static final String SF_QUERY_URL = SF_URL+"/query";
    
    
    /**
     * @return JSONArray results
     *         Long result_count
     * @throws IOException 
     * @throws HttpException 
     * @throws JSONException 
     */
    public Map listContacts(String token, int pageIndex, int pageSize) throws HttpException, IOException, JSONException{
        Map resultMap = new HashMap();
        String sql = "SELECT name, title FROM Contact LIMIT 100";
        OAuthRequest oauth = new OAuthRequest(Verb.GET,SF_QUERY_URL);
        oauth.addHeader("Authorization", "Bearer "+token);
        oauth.addHeader("X-PrettyPrint", "1");
        oauth.addQuerystringParameter("q", sql);
        Map opts = JsonUtil.toMapAndList(oauth.send().getBody());
        resultMap.put("result", opts.get("records"));
        resultMap.put("result_count", 20);
        return resultMap;
    }

}
