package com.messio.mini.entity;

import javax.persistence.*;
import java.util.Locale;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jpc
 * Date: 4/20/13
 * Time: 9:23 AM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "users", catalog = "mini", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@NamedQueries({
        @NamedQuery(name = User.USER_BY_USERNAME, query = "select u from User u where u.username = :username")
})
@Entity
public class User {
    public static final String SYSTEM_NAME = "system";
    public static final String USER_BY_USERNAME = "user.byUsername";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "username", nullable = false, length = 64)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 64)
    private String password = "";
    @Basic
    @Column(name = "locale", nullable = false, length = 16)
    private String loc = "en";
    @Basic
    @Column(name = "expiry")
    private long expiry;
    @Basic
    @Column(name = "token", nullable = true, length = 64)
    private String token;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    public User(String username) {
        this.username = username;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Locale getLocale(){
        return Locale.forLanguageTag(loc);
    }

    public void setLocale(Locale locale){
        this.loc = locale.toLanguageTag();
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
