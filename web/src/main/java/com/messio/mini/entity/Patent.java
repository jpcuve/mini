package com.messio.mini.entity;

import javax.persistence.*;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "patents", catalog = "mini")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("PT")
public class Patent extends Right {
    @Basic
    @Column(name = "application", length = 63)
    private String application;

    @Override
    public String getDescriptor() {
        return String.format("%s", application);
    }

    @Override
    public Domain getDomain() {
        return Domain.PATENT;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
