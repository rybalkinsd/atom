package ru.atom.server.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.atom.model.Person;

import java.util.Arrays;
import java.util.List;

/**
 * Created by s.rybalkin on 03.10.2016.
 */
public class PersonBatchHolder {
    private static final ObjectMapper mapper = new ObjectMapper();

    @JsonSerialize
    private List<? extends Person> persons;

    public static <T extends Person> PersonBatchHolder of(T ... ts) {
        return new PersonBatchHolder(Arrays.asList(ts));
    }

    public String writeJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

    private PersonBatchHolder(List<? extends Person> persons) {
        this.persons = persons;
    }
}
