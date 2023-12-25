package com.freshermanage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Long id;
    @Column(name = "FullName", nullable = false)
    private String fullName;
    @Column(name = "UserName", unique = true, nullable = false)
    private String userName;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column(name = "Email", unique = true, nullable = false)
    private String email;
    @Column(name = "UserStatus")
    private boolean userStatus;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Users_Role",joinColumns = @JoinColumn(name = "UserId"), inverseJoinColumns = @JoinColumn(name = "RoleId"))
    private Set<Roles> listRole = new HashSet<>();
}
