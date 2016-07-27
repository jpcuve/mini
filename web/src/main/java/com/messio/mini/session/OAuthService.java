package com.messio.mini.session;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by jpc on 7/27/16.
 */
@Singleton(name = "mini/oauth-service")
@Lock(LockType.READ)
@Path("/oauth")
public class OAuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthService.class);
    private static final String INVALID_CLIENT_DESCRIPTION = "Client authentication failed (e.g., unknown client, no client authentication included, or unsupported authentication method).";
    @Context
    private HttpServletRequest request;
    @Inject
    private OAuthIssuer issuer;

    @GET
    @Path("/authz")
    public Response authorize() throws URISyntaxException, OAuthSystemException {
        OAuthAuthzRequest oauthRequest = null;
        try {
            oauthRequest = new OAuthAuthzRequest(request);
            //build response according to response_type
            final ResponseType responseType = ResponseType.valueOf(oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE));
            final OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
            switch(responseType){
                case CODE:
                    builder.setCode(issuer.authorizationCode());
                    break;
                case TOKEN:
                    builder.setAccessToken(issuer.accessToken()).setTokenType(OAuth.DEFAULT_TOKEN_TYPE.toString()).setExpiresIn(3600L);
                    break;
            }
            final String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            URI url = new URI(response.getLocationUri());
            return Response.status(response.getResponseStatus()).location(url).build();
        } catch (OAuthProblemException e) {
            final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);
            final String redirectUri = e.getRedirectUri();
            if (redirectUri == null || redirectUri.length() == 0) {
                throw new WebApplicationException(responseBuilder.entity("OAuth callback url needs to be provided by client!!!").build());
            }
            final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e).location(redirectUri).buildQueryMessage();
            final URI location = new URI(response.getLocationUri());
            return responseBuilder.location(location).build();
        }
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("/token")
    public Response token() throws OAuthSystemException {

        OAuthTokenRequest oauthRequest = null;
        try {
            oauthRequest = new OAuthTokenRequest(request);

/*
            // check if clientid is valid
            if (!Common.CLIENT_ID.equals(oauthRequest.getClientId())) {
                OAuthResponse response =
                        OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                                .setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription(INVALID_CLIENT_DESCRIPTION)
                                .buildJSONMessage();
                return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
            }

            // check if client_secret is valid
            if (!Common.CLIENT_SECRET.equals(oauthRequest.getClientSecret())) {
                OAuthResponse response =
                        OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT).setErrorDescription(INVALID_CLIENT_DESCRIPTION)
                                .buildJSONMessage();
                return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
            }

            // do checking for different grant types
            if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
                    .equals(GrantType.AUTHORIZATION_CODE.toString())) {
                if (!Common.AUTHORIZATION_CODE.equals(oauthRequest.getParam(OAuth.OAUTH_CODE))) {
                    OAuthResponse response = OAuthASResponse
                            .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.TokenResponse.INVALID_GRANT)
                            .setErrorDescription("invalid authorization code")
                            .buildJSONMessage();
                    return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
                }
            } else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
                    .equals(GrantType.PASSWORD.toString())) {
                if (!Common.PASSWORD.equals(oauthRequest.getPassword())
                        || !Common.USERNAME.equals(oauthRequest.getUsername())) {
                    OAuthResponse response = OAuthASResponse
                            .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.TokenResponse.INVALID_GRANT)
                            .setErrorDescription("invalid username or password")
                            .buildJSONMessage();
                    return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
                }
            } else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
                    .equals(GrantType.REFRESH_TOKEN.toString())) {
                // refresh token is not supported in this implementation
                OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_GRANT)
                        .setErrorDescription("invalid username or password")
                        .buildJSONMessage();
                return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
            }
*/

            final OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(issuer.accessToken()).setTokenType(OAuth.DEFAULT_TOKEN_TYPE.toString()).setExpiresIn("3600").buildJSONMessage();
            return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
        } catch (OAuthProblemException e) {
            final OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e).buildJSONMessage();
            return Response.status(res.getResponseStatus()).entity(res.getBody()).build();
        }
    }

}
