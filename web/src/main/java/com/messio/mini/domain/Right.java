package com.messio.mini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "rights")
@Entity
@NamedQueries({
        @NamedQuery(name = Right.RIGHT_BY_IDS, query = "select distinct r.id, r from Right r where r.id in (:ids)"),
        @NamedQuery(name = Right.RIGHT_BY_BINDER_IDS, query = "select distinct r.id, r from Right r where r.binder.id in (:ids)")
})
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"binder", "patent", "trademark"})
public abstract class Right {
    public static final String RIGHT_BY_BINDER_IDS = "rightByBinderIds";
    public static final String RIGHT_BY_IDS = "rightByIds";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "domain")
    @Enumerated(EnumType.STRING)
    private Domain domain;
    @Basic
    @Column(name = "plaintiff")
    private boolean plaintiff;
    @ManyToOne
    @JoinColumn(name = "binder_id")
    private Binder binder;
    @Basic
    @Column(name = "binder_id", insertable = false, updatable = false, nullable = false)
    private Long binderId;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "right_images", joinColumns = { @JoinColumn(name = "right_id")})
    @Column(name = "image_id")
    private Set<String> imageIds;

    @Transient
    private String descriptor;

    public Right() {
    }

    protected Right(Binder binder, boolean plaintiff, Domain domain) {
        this.binder = binder;
        this.plaintiff = plaintiff;
        this.domain = domain;
    }

    public abstract String getDescriptor();

    public void setDescriptor(String descriptor){
        this.descriptor = descriptor;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(boolean opponent) {
        this.plaintiff = opponent;
    }

    public Binder getBinder() {
        return binder;
    }

    public void setBinder(Binder binder) {
        this.binder = binder;
    }

    public Long getBinderId() {
        return binderId;
    }

    public void setBinderId(Long binderId) {
        this.binderId = binderId;
    }

    public Set<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(Set<String> imageIds) {
        this.imageIds = imageIds;
    }
}
