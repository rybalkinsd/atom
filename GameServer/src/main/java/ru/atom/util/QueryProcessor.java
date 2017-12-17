package ru.atom.util;

import java.util.HashMap;

public class QueryProcessor {
    public static HashMap<String, String> process(String query) {
        HashMap<String, String> out = new HashMap<>();
        String[] params = query.split("&");
        for (String param : params) {
            String[] entry = param.split("=");
            out.put(entry[0],entry[1]);
        }
        return  out;
    }
}
