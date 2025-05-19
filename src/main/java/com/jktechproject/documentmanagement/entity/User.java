package com.jktechproject.documentmanagement.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<UserRole> roles;


    public User(String username, String password, Set<UserRole> roles,String name) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.name = name;
    }

    public User(){

    }

    public Long getId()
    {
        return id;
    }
    public void setId(Long id) { this.id = id; }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }
    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
