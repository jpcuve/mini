package com.messio.mini;

import com.messio.mini.session.OAuthService;
import com.messio.mini.session.RESTService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jpc on 7/04/2016.
 */
@ApplicationPath("/api")
public class ApplicationConfiguration extends Application {
    public Set<Class<?>> getClasses(){
        return new HashSet<>(Arrays.asList(RESTService.class, OAuthService.class));
    }
}
