package com.perrine.portfolio.model;


import javax.persistence.*;

@Entity
@Table(name = "T_TAG")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    public Tag() {}

    public Tag(String tagName) {
        this.name = tagName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}