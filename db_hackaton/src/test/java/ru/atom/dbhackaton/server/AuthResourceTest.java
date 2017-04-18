package ru.atom.dbhackaton.server;

import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;

/**
 * Created by pavel on 17.04.17.
 */
public class AuthResourceTest {
    @Before
    public void setUp() throws Exception {
        AuthServer.startServer();
    }



    @After
    public void setDown() throws Exception {
        AuthServer.stopServer();
    }
}
