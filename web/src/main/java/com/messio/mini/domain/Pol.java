package com.messio.mini.domain;

import com.messio.mini.util.Node;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by jpc on 10/14/14.
 */
@Table(name = "pols", uniqueConstraints = @UniqueConstraint(columnNames = { "parent_id", "name" }))
@NamedQueries({
        @NamedQuery(name = Pol.POL_BY_PARENT_BY_NAME, query = "select p from Pol p where p.parent = :parent and p.name = :name")
})
@Entity
public class Pol implements Node<Long, Pol> {
    public static final String POL_BY_PARENT_BY_NAME = "pol.byParentByName";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name", length = 255, nullable = false, unique = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Pol parent;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Collection<Pol> children;

    public Pol() {
    }

    public Pol(Pol parent, String name) {
        this.parent = parent;
        this.name = name;
    }

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

    public Pol getParent() {
        return parent;
    }

    public void setParent(Pol parent) {
        this.parent = parent;
    }

    public Collection<Pol> getChildren() {
        return children;
    }

    public void setChildren(Collection<Pol> children) {
        this.children = children;
    }
}
