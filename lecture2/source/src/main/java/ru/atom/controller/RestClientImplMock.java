package ru.atom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.atom.model.Gender;

import static ru.atom.model.Gender.*;

/**
 * Created by s.rybalkin on 27.09.2016.
 */
public class RestClientImplMock implements RestClient {

    private static final String FRY_JSON =
            "{" +
                    "\"id\":\"44d91146-a6b7-45fa-888d-bad4ad0301e0\"," +
                    "\"gender\":\"MALE\"," +
                    "\"name\":\"Stephen\"," +
                    "\"age\":59," +
                    "\"location\":{" +
                        "\"latitude\":51.5287718," +
                        "\"longitude\":-0.2416814" +
                    "}," +
                    "\"desctiption\":\"Actor and writer currently working on new TV comedy.\"," +
                    "\"image\":{" +
                        "\"url\":\"http://hitgid.com/images/%D1%81%D1%82%D0%B8%D0%B2%D0%B5%D0%BD-%D1%84%D1%80%D0%B0%D0%B9-2.jpg\"," +
                        "\"width\":360," +
                        "\"height\":288}," +
                    "\"instagramUrl\":\"https://www.instagram.com/stephenfryactually/\"" +
            "}";

    private static final String LADY_JSON =
            "{" +
                    "\"id\":\"a2312795-d8c7-45fa-888d-bad4ad0301e0\"," +
                    "\"gender\":\"FEMALE\"," +
                    "\"name\":\"Lenna\"," +
                    "\"age\":65," +
                    "\"location\":{" +
                    "\"latitude\":59.3260668," +
                    "\"longitude\":17.8474656" +
                    "}," +
                    "\"desctiption\":\"I Like Compression!\"," +
                    "\"image\":{" +
                    "\"url\":\"https://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png\"," +
                    "\"width\":512," +
                    "\"height\":512}," +
                    "\"instagramUrl\":\"https://www.instagram.com/lenasoderberg/\"" +
                    "}";

    @Override
    public String next(Gender gender) {
        if (gender == FEMALE) {
            return LADY_JSON;
        } else {
            return FRY_JSON;
        }
    }
}
