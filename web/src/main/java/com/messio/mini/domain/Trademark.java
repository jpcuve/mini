package com.messio.mini.domain;

import javax.persistence.*;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "trademarks")
@Entity
public class Trademark extends Right {
    @Basic
    @Column(name = "name", length = 255)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TrademarkType type;

    public Trademark() {
    }

    public Trademark(Binder binder, boolean opponent, String name, TrademarkType type) {
        super(binder, opponent, Domain.TRADEMARK);
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

    public TrademarkType getType() {
        return type;
    }

    public void setType(TrademarkType type) {
        this.type = type;
    }
}
