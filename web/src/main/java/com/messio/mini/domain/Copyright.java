package com.messio.mini.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "copyrights")
@Entity
public class Copyright extends Right {
    @Basic
    @Column(name = "name", length = 255)
    private String name;

    public Copyright() {
    }

    public Copyright(Binder binder, boolean opponent, String name) {
        super(binder, Domain.COPYRIGHT, opponent);
        this.name = name;
    }

    @Override
    public String getDescriptor() {
        return String.format("%s", name);
    }

    @Override
    public Domain getDomain() {
        return Domain.COPYRIGHT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
