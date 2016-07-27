package com.messio.mini.session;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.ejb.Stateless;

/**
 * Created by jpc on 7/27/16.
 */
@Stateless
public class GateKeeper implements OAuthIssuer {
    @Override
    public String accessToken() throws OAuthSystemException {
        return null;
    }

    @Override
    public String authorizationCode() throws OAuthSystemException {
        return null;
    }

    @Override
    public String refreshToken() throws OAuthSystemException {
        return null;
    }
}
