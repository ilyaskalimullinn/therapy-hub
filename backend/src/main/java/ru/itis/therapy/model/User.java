package ru.itis.therapy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Data
@ToString(exclude = {"specialityList", "clientReviews", "specialistReviews"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String password;

    @Column(name = "created_at")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserAuthority authority;

    @Column(name = "specialist_bio")
    private String specialistBio;

    @Column(name = "specialist_appointment_price")
    private Integer specialistAppointmentPrice;

    @Column(name = "specialist_avg_rating")
    private Double specialistAvgRating;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> clientReviews;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> specialistReviews;

    @ManyToMany
    @JoinTable(
            name = "user_speciality",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private List<Speciality> specialityList;

    public enum UserRole {
        SPECIALIST,
        CLIENT
    }

    public enum UserAuthority {
        ADMIN,
        DEFAULT
    }
}
