package com.lanc.planit.control;

import com.lanc.planit.model.Plan;
import com.lanc.planit.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.xml.ws.Response;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:8080")
public class PlanController {

    @Autowired
    private PlanService planService;

    //findById
    @GetMapping("/plan/{id}")
    public Plan findPlan(@PathVariable Integer id){
        Optional ret = planService.find(id);
        return ret.isPresent()?(Plan)ret.get():null;
    }

    //find all
    @GetMapping("/plan")
    public ResponseEntity<List<Plan>> findAllPlans(){
        List<Plan> ret = new LinkedList<Plan>();
        Iterable<Plan> plandata = planService.findAll();
        for(Plan p:plandata){
            ret.add(p);
        }
        return ResponseEntity.ok(ret);
    }

    //add/update
    @PostMapping("/plan")
    public ResponseEntity<Plan> insertPlan(@RequestBody Plan p){
        Plan insertedPlan = planService.add(p);
        if (insertedPlan == null) {
            return ResponseEntity.notFound().build();
        } else {
//            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                    .path("/1")
//                    .buildAndExpand(insertedPlan.getId())
//                    .toUri();

            return ResponseEntity.ok(insertedPlan);
        }
    }

    //delete
    @DeleteMapping("/plan/{id}")
    public ResponseEntity<Object> deletePlan(@PathVariable Integer id){
        if(!planService.find(id).isPresent()){
            return new ResponseEntity<Object>("Object not found", HttpStatus.BAD_REQUEST);
        }
        planService.delete(id);
        return new ResponseEntity<Object>("Object is deleted", HttpStatus.OK);
    }

}
