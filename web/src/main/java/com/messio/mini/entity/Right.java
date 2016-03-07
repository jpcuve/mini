package com.messio.mini.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "rights", catalog = "mini")
@Entity
@DiscriminatorColumn(name = "discriminator", length = 2)
@JsonIgnoreProperties({"binder", "patent", "trademark"})
public abstract class Right {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "discriminator", insertable = false, updatable = false)
    private String discriminator;
    @Basic
    @Column(name = "opponent")
    private boolean opponent;
    @Basic
    @Column(name = "image_ids", length = 1024)
    private String imageIds;
    @ManyToOne
    @JoinColumn(name = "binder_id")
    private Binder binder;
    @Basic
    @Column(name = "target_id")
    private Long targetId;
    @OneToOne
    @JoinColumn(name = "target_id", insertable = false, updatable = false)
    private Trademark trademark;
    @OneToOne
    @JoinColumn(name = "target_id", insertable = false, updatable = false)
    private Patent patent;

    @Transient
    private String descriptor;
    @Transient
    private Domain domain;

    public Right() {
    }

    protected Right(Binder binder, boolean opponent) {
        this.binder = binder;
        this.opponent = opponent;
    }

    public abstract String getDescriptor();

    public void setDescriptor(String descriptor){
        this.descriptor = descriptor;
    }

    public abstract Domain getDomain();

    public void setDomain(Domain domain){
        this.domain = domain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public boolean isOpponent() {
        return opponent;
    }

    public void setOpponent(boolean opponent) {
        this.opponent = opponent;
    }

    public List<String> getImageIds(){
        final List<String> list = new ArrayList<>();
        if (this.imageIds != null){
            final StringTokenizer st = new StringTokenizer(imageIds, "|");
            while (st.hasMoreTokens()){
                list.add(st.nextToken());
            }
        }
        return list;
    }

    public void setImageIds(List<String> imageIds){
        this.imageIds = imageIds == null ? null : imageIds.stream().collect(Collectors.joining("|"));
    }

    public Binder getBinder() {
        return binder;
    }

    public void setBinder(Binder binder) {
        this.binder = binder;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Trademark getTrademark() {
        return trademark;
    }

    public void setTrademark(Trademark trademark) {
        this.trademark = trademark;
    }

    public Patent getPatent() {
        return patent;
    }

    public void setPatent(Patent patent) {
        this.patent = patent;
    }
}
