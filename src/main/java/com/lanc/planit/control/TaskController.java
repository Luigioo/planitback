package com.lanc.planit.control;

import com.lanc.planit.model.MyUser;
import com.lanc.planit.model.Plan;
import com.lanc.planit.model.Task;
import com.lanc.planit.service.MyUserService;
import com.lanc.planit.service.PlanService;
import com.lanc.planit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private PlanService planService;

    @Autowired
    private MyUserService uService;


    @GetMapping("/task")
    public ResponseEntity<List<Task>> findAllTasks(Principal principal){
        MyUser user = (MyUser) uService.loadUserByUsername(principal.getName());
        List<Task> ret = new LinkedList<Task>();
        Iterable<Task> plandata = taskService.findAllByUserId(user.getId());
        for(Task t:plandata){
            t.setUser(null);
            ret.add(t);
        }
        return ResponseEntity.ok(ret);
    }

    @PostMapping("/task")
    public ResponseEntity<List<Plan>[]> insertTask(@RequestBody Task task, Principal principal){
        System.out.println("ola");
        MyUser user = (MyUser) uService.loadUserByUsername(principal.getName());
        task.setUser(user);
        task.setId(null);
        Task insertedTask = taskService.add(task);
        if (insertedTask == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<Plan> ret = new ArrayList<>();
            Iterable<Plan> plandata = planService.findAllByUserId(user.getId());
            for(Plan t:plandata){
                t.setUser(null);
                ret.add(t);
            }
            List[] options = taskService.getOptions(task, ret);
        if(options==null){
            return ResponseEntity.badRequest().build();
        }

            return ResponseEntity.ok(options);
        }
    }
    @PutMapping("/task")
    public ResponseEntity<Object> putTask(@RequestBody Task task, Principal principal){

        MyUser user = (MyUser) uService.loadUserByUsername(principal.getName());
        task.setUser(user);
        Optional<Task> toc = taskService.find(task.getId());

        if(!toc.isPresent()){
            return ResponseEntity.notFound().build();
        }

        if(toc.get().getUser().getId()!=user.getId()){
            return new ResponseEntity<Object>("User IDs don't match", HttpStatus.CONFLICT);
        }

        Task insertedTask = taskService.add(task);
        if (insertedTask == null) {
            return ResponseEntity.notFound().build();
        } else {

            return ResponseEntity.ok("updated task");
        }
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Object> deletePlan(@PathVariable Long id, Principal principal){
        MyUser user = (MyUser) uService.loadUserByUsername(principal.getName());
        Optional<Task> t = taskService.find(id);

        if(!t.isPresent()){
            return new ResponseEntity<Object>("Object not found", HttpStatus.NOT_FOUND);
        }
        if(t.get().getUser().getId()!=user.getId()){
            //if not the same user
            return new ResponseEntity<Object>("User IDs do not match", HttpStatus.CONFLICT);
        }

        taskService.delete(id);
        return new ResponseEntity<Object>("Object is deleted", HttpStatus.OK);
    }

}
