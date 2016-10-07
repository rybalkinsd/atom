package ru.atom.model.person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Holder class.
 * Important for client - server data transfer
 */
public class PersonBatchHolder {
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    private List<? extends Person> persons;

    public static <T extends Person> PersonBatchHolder of(T ... ts) {
        return new PersonBatchHolder(Arrays.asList(ts));
    }

    public static PersonBatchHolder readJson(String json) throws IOException {
        return mapper.readValue(json, PersonBatchHolder.class);
    }

    public String writeJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

    private PersonBatchHolder(List<? extends Person> persons) {
        this.persons = persons;
    }

    /**
     * requested by jackson
     * */
    private PersonBatchHolder() { }

    public List<? extends Person> getPersons() {
        return persons;
    }
}
