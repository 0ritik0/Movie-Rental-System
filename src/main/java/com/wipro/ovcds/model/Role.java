package com.wipro.ovcds.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "roles" )
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
     
    private String name;
    public Integer getId() {
        return id;
    }
     
   
}
