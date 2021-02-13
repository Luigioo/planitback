package com.lanc.planit.control;

import com.lanc.planit.model.MyUser;
import com.lanc.planit.model.Plan;
import com.lanc.planit.model.Task;
import com.lanc.planit.service.MyUserService;
import com.lanc.planit.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import javax.xml.ws.Response;
import java.awt.print.Printable;
import java.net.URI;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class PlanController {

    @Autowired
    private PlanService planService;

    @Autowired
    private MyUserService uService;

//    //findById
//    @GetMapping("/plan/{id}")
//    public Plan findPlan(@PathVariable Integer id){
//        Optional ret = planService.find(id);
//        return ret.isPresent()?(Plan)ret.get():null;
//    }


    //find all
    @GetMapping("/plan")
    public ResponseEntity<List<Plan>> findAllPlans(Principal principal){
        MyUser user = (MyUser) uService.loadUserByUsername(principal.getName());
        List<Plan> ret = new LinkedList<Plan>();
        Iterable<Plan> plandata = planService.findAllByUserId(user.getId());
        for(Plan p:plandata){
            p.setUser(null);
            ret.add(p);
        }
        return ResponseEntity.ok(ret);
    }

    //add/update
    @PostMapping("/plan")
    public ResponseEntity<Plan> insertPlan(@RequestBody Plan p, Principal principal){
        MyUser user = (MyUser) uService.loadUserByUsername(principal.getName());
        p.setUser(user);
        p.setId(null);
        Plan insertedPlan = planService.add(p);
        if (insertedPlan == null) {
            return ResponseEntity.notFound().build();
        } else {

            return ResponseEntity.ok(insertedPlan);
        }
    }
    @PutMapping("/plan")
    public ResponseEntity<Object> putTask(@RequestBody Plan p, Principal principal){
        MyUser user = (MyUser) uService.loadUserByUsername(principal.getName());
        p.setUser(user);
        Optional<Plan> poc = planService.find(p.getId());

        if(!poc.isPresent()){
            return ResponseEntity.notFound().build();
        }

        if(poc.get().getUser().getId()!=user.getId()){
            return new ResponseEntity<Object>("User IDs don't match", HttpStatus.CONFLICT);
        }

        Plan insertedPlan = planService.add(p);
        if (insertedPlan == null) {
            return ResponseEntity.notFound().build();
        } else {

            return ResponseEntity.ok("updated plan");
        }
    }

    //delete
    @DeleteMapping("/plan/{id}")
    public ResponseEntity<Object> deletePlan(@PathVariable Integer id, Principal principal){
        MyUser user = (MyUser) uService.loadUserByUsername(principal.getName());
        Optional<Plan> p = planService.find(id);

        if(!p.isPresent()){
            return new ResponseEntity<Object>("Object not found", HttpStatus.NOT_FOUND);
        }
        if(p.get().getUser().getId()!=user.getId()){
            //if not the same user
            return new ResponseEntity<Object>("User IDs do not match", HttpStatus.CONFLICT);
        }

        planService.delete(id);
        return new ResponseEntity<Object>("Object is deleted", HttpStatus.OK);
    }

}
