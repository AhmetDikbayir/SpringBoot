package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "firstname cannot be null...")
    @NotBlank(message = "firstname cannot be white space...")
    @Size(min = 2, max = 25, message = "Firstname '${ValidatedValue}' must be between {min} and {max} chars")
    @Column(nullable = false, length = 25)
    private String name;

    @NotNull(message = "lastname cannot be null...")
    @NotBlank(message = "lastname cannot be white space...")
    @Size(min = 2, max = 25, message = "Firstname '${ValidatedValue}' must be between {min} and {max} chars")
    @Column(nullable = false, length = 25)
    private String lastName;

    private Integer grade;

    @Column(nullable = false, length = 50, unique = true)
    @Email(message = "Provide valid email")
    private String email;

    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @Setter(AccessLevel.NONE)
    private LocalDateTime createDate = LocalDateTime.now();

    @OneToMany(mappedBy = "student")
    private List<Book> book = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
