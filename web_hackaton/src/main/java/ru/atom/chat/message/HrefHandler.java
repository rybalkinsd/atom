package ru.atom.chat.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HrefHandler {
    public static String URL_REGEXP =
            "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]" +
                    "+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))" +
                    "+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))";

    public static Pattern URL_PATTERN = Pattern.compile(URL_REGEXP);

    public static String handleHref(String string) {
        String local = string.toLowerCase();
        Matcher matcher = URL_PATTERN.matcher(local);
        String result = "";
        int start = 0;
        for (; matcher.find(); ) {
            result = result + string.substring(start, matcher.start())
                    + "<a href=\"" + matcher.group() + "\">"
                    + string.substring(matcher.start(), matcher.end()) + "</a>";
            start = matcher.end();
        }

        return result.length() == 0 ? string : result;
    }
}
