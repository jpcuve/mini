package com.messio.mini.bean;

import com.messio.mini.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jpc on 11/4/14.
 */
@Named
@RequestScoped
public class Password {
    private static final Logger LOGGER = LoggerFactory.getLogger(Password.class);
    private String token;
    private String password;
    @Inject
    private Visitor visitor;
/*
    @EJB
    private UserDao userDao;
*/

    public String change(){
/*
        final User user = userDao.findOneByPasswordToken(token);
        if (user != null){
            userDao.updatePassword(user.getId(), password);
            LOGGER.debug("changed password for user: {}", user.getUsername());
            visitor.setUsername(user.getUsername());
            visitor.setPassword(password);
            visitor.setToken(null);
        }
*/
        return "index";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
}
