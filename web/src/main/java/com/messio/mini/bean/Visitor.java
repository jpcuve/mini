package com.messio.mini.bean;

import com.messio.mini.entity.Role;
import com.messio.mini.entity.User;
import com.messio.mini.web.JsfUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.EnumSet;
import java.util.Locale;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: jpc
 * Date: 4/25/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
@SessionScoped
public class Visitor implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Visitor.class);
    private static final long PASSWORD_TOKEN_EXPIRY_DELAY = 10 * 60 * 1000L;
    private String username;
    private String password;
    private String token;
    private EnumSet<Role> roles = EnumSet.noneOf(Role.class);
    private Principal principal;
/*
    @EJB
    private UserDao userDao;
*/
    @Inject
    private Event<MailEvent> mailEventEmitter;

    public String login(){
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try{
            req.login(username, password);
            LOGGER.info("Login successful: {}", username);
            this.principal = req.getUserPrincipal();
            roles.clear();
            for (final Role role: Role.values()) if (req.isUserInRole(role.toString())) roles.add(role);
            LOGGER.info("Roles: {}", roles);
        } catch(ServletException x){
            LOGGER.error("Login failed", x);
        }
        return null;
    }

    public String logout(){
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        this.principal = null;
        this.roles.clear();
        try{
            req.logout();
            LOGGER.info("Logout successful");
        } catch(ServletException x){
            LOGGER.error("Logout failed", x);
        }
        return "index";
    }

    public String forgot() throws IOException, MessagingException {
        LOGGER.debug("forgot");
        if (username != null && username.length() > 0){
/*
            final User user = userDao.findOne(username);
            if (user != null){
                LOGGER.debug("user exists, generating password change token: " + username);
                this.token = UUID.randomUUID().toString();
                userDao.updateToken(user.getId(), System.currentTimeMillis() + PASSWORD_TOKEN_EXPIRY_DELAY, token);
                final DataSource dataSource = JsfUtil.getViewDataSource(FacesContext.getCurrentInstance(), "/mail/reset-password.xhtml");
                final MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setDataHandler(new DataHandler(dataSource));
                mailEventEmitter.fire(new MailEvent(mimeBodyPart));
            }
*/
        }
        return null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLogged(){
        return principal != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public EnumSet<Role> getRoles() {
        return roles;
    }

    public boolean isAdministrator(){
        return roles.contains(Role.ADMINISTRATOR);
    }

    public boolean isDeveloper(){
        return roles.contains(Role.DEVELOPER);
    }

    public Locale getLocale() {
        return Locale.ENGLISH;
    }
}
