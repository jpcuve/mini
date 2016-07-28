package com.messio.mini.session;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;

/**
 * Created by jpc on 7/27/16.
 */
@Stateless
public class GateKeeper  {
    private static final Logger LOGGER = LoggerFactory.getLogger(GateKeeper.class);

    public boolean checkClientId(String clientId){
        return true;
    }

    public boolean checkClientSecret(String clientSecret){
        return true;
    }

    public boolean checkAuthorizationCode(String authorizationCode){
        return true;
    }

    public boolean checkPassword(String username, String password){
        return true;
    }
}
