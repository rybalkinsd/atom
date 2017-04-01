package ru.atom.rk1.server;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RegisterTest.class,
        RegisterTest2.class,
        TokenTest.class,
        LoginTest.class,
        LoginTest2.class,
        LogoutTest.class,
        UsersTest.class
    })
public class AllTestsSuit {
}
