package org.web.resumeandjobtracking.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Entity
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable=false)
    private String FirstName ;
    @Column(nullable = false)
    private String LastName;
    @Column(nullable=false)
    private String password;
    @Column(unique=true,nullable=false)
    private String email;

}
