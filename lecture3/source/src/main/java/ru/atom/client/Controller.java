package ru.atom.client;

import ru.atom.model.Gender;
import ru.atom.model.Location;
import ru.atom.model.Person;

import java.util.Collections;
import java.util.List;
import java.util.Map;
//9.524 [qtp1608446010-12] INFO  ru.atom.server.auth.Authentication - New user 'Rail' registered
//21:07:07.708 [qtp1608446010-16] INFO  ru.atom.server.auth.Authentication - New user 'vladfedorenko' registered
 //       21:08:33.906 [qtp1608446010-12] INFO  ru.atom.server.auth.Authentication - New user 'Atlaster' registered


public class Controller {
    private RestClient client;


    public Controller(RestClient client, Gender lookingFor) {
        this.client = client;
    }

    public List<? extends Person> findYoungerThan29(Gender gender) {
        return Collections.emptyList();
    }

    public Map<Location, List<? extends Person>> groupByLocation(Gender gender) {
        return Collections.emptyMap();
    }
}