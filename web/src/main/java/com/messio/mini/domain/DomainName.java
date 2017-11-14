package com.messio.mini.domain;

import javax.persistence.*;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "domain_names")
@Entity
public class DomainName extends Right {
    @Basic
    @Column(name = "name", length = 255)
    private String name;

    public DomainName() {
    }

    public DomainName(Binder binder, boolean opponent, String name) {
        super(binder, Domain.DOMAIN_NAME, opponent);
        this.name = name;
    }

    @Override
    public String getDescriptor() {
        return String.format("%s", name);
    }

    @Override
    public Domain getDomain() {
        return Domain.DOMAIN_NAME;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
