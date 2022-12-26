package com.vti.testing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "`Account`")
@Data
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "`password`", length = 800, nullable = false)
    private String password;

    @Column(name = "`first_name`", length = 50, nullable = false)
    private String firstName;

    @Column(name = "`last_name`", length = 50, nullable = false)
    private String lastName;

    @Formula("CONCAT(first_name,' ', last_name)")
    private String fullName;

    @Column(name = "`role`", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(
            columnDefinition = "department_id", referencedColumnName = "id", nullable = false
    )
    private Department department;

    @PrePersist
    public void prePersist() {
        password = new BCryptPasswordEncoder().encode("123456");
    }
}
