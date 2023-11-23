package com.example.Cat.s.Blog.entity.users;

import com.example.Cat.s.Blog.entity.roles.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    private Long id;


    @Column(unique = true)
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role userRole;


}