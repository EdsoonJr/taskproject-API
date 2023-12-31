package com.edsonjunior.tasksproject.services;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edsonjunior.tasksproject.models.User;
import com.edsonjunior.tasksproject.repositories.UserRepository;
import com.edsonjunior.tasksproject.services.exceptions.DataBindingViolationException;
import com.edsonjunior.tasksproject.services.exceptions.ObjectNotFoundException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User findById(long id) {
        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário Não Encontrado Id:"
                + id + ", Tipo: " + user.getClass().getName()));
    }

    public List<User> findAll(){
        List<User> users = this.userRepository.findAll();
        return users; 
    }

    @Transactional //? Maior controle na inserção no banco de dados 
    public User create(User obj){
        obj.setId(null); //? Garantir que crie outro usuario com um novo id
        obj = userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User Update(User obj){
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());

        return this.userRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);

        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possivel excluir, pois há relacionamentos");
        }
    }
}
