package com.edsonjunior.tasksproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edsonjunior.tasksproject.models.Task;
import com.edsonjunior.tasksproject.models.User;
import com.edsonjunior.tasksproject.repositories.TaskRepository;

@Service
public class TaskService {

    @Autowired
    public TaskRepository taskRepository;
    @Autowired
    public UserService userService;

    public Task findById(long id){
        Optional<Task> task = this.taskRepository.findById(id);

        return task.orElseThrow(() -> new RuntimeException("Tarefa Não Encontrado Id:"
                + id + ", Tipo: " + task.getClass().getName()));
    }

    public List<Task> findAllByUserId(Long userId){
        List<Task> tasks = taskRepository.findByUser_Id(userId);
        return tasks;
        
    }

    @Transactional
    public Task create(Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        this.taskRepository.save(obj);
        return obj;

    }

    @Transactional
    public Task update(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(long id){
        findById(id);

        try {
            this.taskRepository.deleteById(null);
        } catch (Exception e) {
            throw new RuntimeException("Não é possivel excluir, pois há relacionamentos");
        }
    }
    
}
