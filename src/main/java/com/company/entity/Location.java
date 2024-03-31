package com.company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"subscribeUsers"})
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"subscribeUsers"})
public class Location extends Auditable {

    private String name;
    private double latitude;
    private double longitude;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "location_user",
            joinColumns = {@JoinColumn(name = "location_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<AuthUser> subscribeUsers;
}
