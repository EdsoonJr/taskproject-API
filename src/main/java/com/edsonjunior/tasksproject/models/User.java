package com.edsonjunior.tasksproject.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = User.TABLE_NAME)
@Getter //? Gerador de getters and setters
@Setter
@AllArgsConstructor //? Gerador de todos Construtor
@NoArgsConstructor //? Gerador de Construtor vazio
@EqualsAndHashCode

public class User {

    //! Regra de negocio para user não atualizar seu ursername na criação
    public interface CreateUser {}
    public interface UpdateUser {}


    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //* Id AutoIncremment
    @Column( name = "id" , unique = true)
    private Long id;

    @Column( name = "username" , length = 100 , nullable = false ,unique = true)
    @NotNull(groups = CreateUser.class) //? Notações java validation,verificação se o usuário vazio 
    @NotEmpty(groups = CreateUser.class)//? Criação de usuário não pode ser vazio
    @Size (groups = CreateUser.class , min = 2,max = 100)//? Tamanho máximo e minimo de entrada 
    private String username;

    @JsonProperty( access = Access.WRITE_ONLY) //? Apenas escrita da senha
    @Column( name = "password" , length = 60 , nullable = false)
    @NotNull(groups = {CreateUser.class , UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class , UpdateUser.class})
    @Size (groups = {CreateUser.class , UpdateUser.class} ,min = 8 , max = 60)
    private String password;


    
    @OneToMany(mappedBy = "user")
    @JsonBackReference
    //@JsonIgnore
    private List<Task> tasks = new ArrayList<Task> ();

    
}
