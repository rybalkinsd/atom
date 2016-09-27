package ru.atom.controller;

import ru.atom.model.Gender;
import ru.atom.model.Person;
import ru.atom.view.MainPageView;

/**
 * Created by s.rybalkin on 26.09.2016.
 */
public class Controller {

    private RestClient client;
    /**
     * You need full profile. This is a Mock
     */
    private Gender lookingFor;

    public Controller(RestClient client, Gender lookingFor) {
        this.client = client;
        this.lookingFor = lookingFor;
    }

    public String onNext() {
        Person person = client.next(lookingFor);
        return String.format(MainPageView.html,
                person.getImage().getUrl().toString(),
                person.getImage().getWidth(),
                person.getImage().getHight(),
                person.getName(),
                person.getAge(),
                person.getDesctiption(),
                person.getInstagramUrl().toString());
    }
}
