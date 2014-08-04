package org.rayjars.appdirect;

import org.rayjars.appdirect.xml.beans.Event;
import org.rayjars.appdirect.xml.beans.Payload;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public final class TestHelper {

    private TestHelper() {
    }

    public static Event emptyEvent() {
        Event event = new Event();
        Payload payload = new Payload();
        event.setPayload(payload);
        return event;
    }

    public static String urlFromClasspath(String fileName){
        return TestHelper.class.getResource("/"+fileName).toString();
    }


    public static Map<String, String> splitQuery(String url) throws UnsupportedEncodingException, MalformedURLException {
        return splitQuery(new URL(url));
    }

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

}
