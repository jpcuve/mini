package com.messio.mini.entity;

import javax.persistence.*;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "trademarks", catalog = "mini")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("TM")
public class Trademark extends Right {
    @Basic
    @Column(name = "name", length = 255)
    private String name;

    @Override
    public String getDescriptor() {
        return String.format("%s", name);
    }

    @Override
    public Domain getDomain() {
        return Domain.TRADEMARK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
