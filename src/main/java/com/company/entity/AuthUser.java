package com.company.entity;

import com.company.enums.Role;
import com.company.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"subscribeLocations"})
@Builder
@EqualsAndHashCode(callSuper = false, exclude = {"subscribeLocations"})
@NamedQueries({
        @NamedQuery(
                name = "findByEmail",
                query = "select u from AuthUser u where u.email = :email"
        )
})
public class AuthUser extends Auditable {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar default 'ROLE_USER'")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar default 'ACTIVE'")
    private Status status;

    @ManyToMany(
            mappedBy = "subscribeUsers",
            fetch = FetchType.EAGER
    )
    private List<Location> subscribeLocations;
}
