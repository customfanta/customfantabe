package it.customfanta.be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    private String username;

    private String nome;
    private String profile;

    private String mail;
    private String password;

}
