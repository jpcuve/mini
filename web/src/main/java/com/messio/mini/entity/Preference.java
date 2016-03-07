package com.messio.mini.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jpc
 * Date: 4/23/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "preferences", catalog = "mini", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "parent_id", "name" }))
@Entity
public class Preference {
    public static final Logger LOG = LoggerFactory.getLogger(Preference.class);
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "user_id")
    private long userId;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Preference parent;
    @OneToMany(mappedBy = "parent")
    private List<Preference> children;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "preference_properties", joinColumns = @JoinColumn(name = "preference_id"))
    @MapKeyColumn(name = "property_key")
    @Column(name = "property_value")
    private Map<String, String> properties;
    @Transient
    private Map<String, String> cache = new HashMap<>();

    public Preference() {
    }

    public Preference(Preference parent, long userId, String name) {
        this.name = name;
        this.userId = userId;
        this.parent = parent;
    }

    @Transient
    public String getPath(){
        final StringBuilder sb = new StringBuilder();
        for (Preference current = this; current != null; current = current.getParent()){
            final String currentName = current.getName();
            sb.insert(0, currentName);
            if (currentName.length() > 0 || sb.length() == 0) sb.insert(0, '/');
        }
        return sb.toString();
    }

    @Transient
    public Map<String, String> getCache() {
        return cache;
    }

    @Transient
    public Collection<String> getPropertyKeys(){
        final List<String> propertyKeys = new ArrayList<>(getProperties().keySet());
        Collections.sort(propertyKeys);
        return propertyKeys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Preference getParent() {
        return parent;
    }

    public void setParent(Preference parent) {
        this.parent = parent;
    }

    public List<Preference> getChildren() {
        return children;
    }

    public void setChildren(List<Preference> children) {
        this.children = children;
    }
}
