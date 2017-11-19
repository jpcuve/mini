package com.messio.mini.domain;

import javax.persistence.*;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "design_models")
@Entity
public class DesignModel extends Right {
    @Basic
    @Column(name = "name", length = 255)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "registration")
    private DesignRegistration registration;

    public DesignModel() {
    }

    public DesignModel(Binder binder, boolean opponent, String name, DesignRegistration registration) {
        super(binder, opponent, Domain.DESIGN_MODEL);
        this.name = name;
        this.registration = registration;
    }

    @Override
    public String getDescriptor() {
        return String.format("%s", name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DesignRegistration getRegistration() {
        return registration;
    }

    public void setRegistration(DesignRegistration registration) {
        this.registration = registration;
    }
}
