package com.messio.mini.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.messio.mini.util.Node;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by jpc on 10/14/14.
 */
@Table(name = "courts", catalog = "mini", uniqueConstraints = @UniqueConstraint(columnNames = { "parent_id", "name" }))
@Entity
@JsonIgnoreProperties({"parent", "children"})
public class Court implements Node<Long, Court> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name", length = 255, nullable = false, unique = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Court parent;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Collection<Court> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Court getParent() {
        return parent;
    }

    public void setParent(Court parent) {
        this.parent = parent;
    }

    public Collection<Court> getChildren() {
        return children;
    }

    public void setChildren(Collection<Court> children) {
        this.children = children;
    }
}
