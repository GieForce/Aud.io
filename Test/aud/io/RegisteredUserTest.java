package aud.io;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegisteredUserTest {

    String email;
    String password;
    String nickname;
    RegisteredUser user;

    @Before
    public void setUp() {
        email = "dhrlaaboudi@gmail.com";
        password = "boterkoek";
        nickname = "Hich";
        user = new RegisteredUser(email, nickname, password);
    }

    @Test
    public void testConstructor() {
        RegisteredUser userexpected = new RegisteredUser("dhrlaaboudi@gmail.com", "Hich", "boterkoek");
        assertEquals(userexpected.getEmail(), user.getEmail());
        assertEquals(userexpected.getNickname(), user.getNickname());

    }

    @Test
    public void testSetters(){
        user.setNickname("dope");
        assertEquals("dope", user.getNickname());

        user.setEmail("jantje@live.nl");
        assertEquals("jantje@live.nl", user.getEmail());
    }

    @Test
    public void checkLogin() {
        assertTrue(user.checkLogin("dhrlaaboudi@gmail.com", "Hich"));
    }

}