package ru.atom.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.atom.model.Gender;
import ru.atom.model.Person;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by s.rybalkin on 03.10.2016.
 */
public class RestClientImpl implements RestClient {
    private static final String PROTOCOL = "http";
    private static final String HOST = "127.0.0.1";
    private static final String PORT = "8080";
    private static final HttpClient client = HttpClientBuilder.create().build();

    @Override
    public Collection<? extends Person> getBatch(Gender gender) {
        return null;
    }
}
