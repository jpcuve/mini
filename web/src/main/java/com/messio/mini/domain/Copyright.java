package com.messio.mini.domain;

import javax.persistence.*;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "copyrights")
@Entity
public class Copyright extends Right {
    @Basic
    @Column(name = "name", length = 255)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CopyrightType type;

    public Copyright() {
    }

    public Copyright(Binder binder, boolean opponent, String name, CopyrightType type) {
        super(binder, opponent, Domain.COPYRIGHT);
        this.name = name;
        this.type = type;
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

    public CopyrightType getType() {
        return type;
    }

    public void setType(CopyrightType type) {
        this.type = type;
    }
}
