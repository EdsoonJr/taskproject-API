package com.edsonjunior.tasksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edsonjunior.tasksproject.models.User;

@Repository
public interface UserRepository extends JpaRepository<User , Long > {
    
}
