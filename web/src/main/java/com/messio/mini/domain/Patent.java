package com.messio.mini.domain;

import javax.persistence.*;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "patents")
@Entity
public class Patent extends Right {
    @Basic
    @Column(name = "application", length = 63)
    private String application;

    public Patent() {
    }

    public Patent(Binder binder, boolean opponent, String application) {
        super(binder, opponent, Domain.PATENT);
        this.application = application;
    }

    @Override
    public String getDescriptor() {
        return String.format("%s", application);
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
