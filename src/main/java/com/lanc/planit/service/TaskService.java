package com.lanc.planit.service;
/**
 * @Author Luigi Lin
 * This is the algorithm part
 */
import com.lanc.planit.dao.TaskRepo;
import com.lanc.planit.model.Plan;
import com.lanc.planit.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepo repo;

    public Task add(Task p) {
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Iterable<Task> findAllByUserId(long id){
        return repo.findAllByUserId(id);
    }

    public Optional<Task> find(Long id){ return repo.findById(id); }

    public List<Plan>[] getOptions(Task task, List<Plan> plans){
        //get a clean list of plans with only the ones within task time
        return algo(task, plans);
    }
    private List<Plan>[] algo(Task task, List<Plan> plans){
        Timestamp start = task.getStart();
        Timestamp end = task.getEnd();

        //get empty slots
        List<Plan> emptySlots = new ArrayList<>();
        emptySlots.add(conPlan(start, end));
        Plan[] safe = plans.toArray(new Plan[]{});
        for(int i=safe.length-1;i>=0;i--){
            Plan s = safe[i];
            Plan[] empty = emptySlots.toArray(new Plan[]{});
            //not in the range
            if(afterE(s.getStart(), end)||beforeE(s.getEnd(), start)){
                continue;
            }
            for(Plan p:empty){
                //not in range
                if(afterE(p.getStart(), s.getEnd())||beforeE(p.getEnd(), s.getStart())){
                    continue;
                }
                //overlap
                if(beforeE(p.getStart(), s.getStart())){
                    //
                    emptySlots.add(conPlan(p.getStart(), s.getStart()));
                }
                if(p.getEnd().after(s.getEnd())){
                    //
                    emptySlots.add(conPlan(s.getEnd(), p.getEnd()));
                }
                //remove
                emptySlots.remove(p);
            }
        }
        long availTime = 0;
        for(Plan e:emptySlots.toArray(new Plan[]{})){
            long length = e.getEnd().getTime()-e.getStart().getTime();
            if(e.getEnd().getTime()-e.getStart().getTime()<5*60000){
                //smaller than 5 mins will be ignored
                emptySlots.remove(e);
            }else{
                availTime+=length;
            }
        }
        long duration = (long)task.getDuration()*60*60000;
        if(availTime<duration){
            System.out.println("not enough time");
            return null;
        }

        emptySlots = sortPlans(emptySlots);
//        pps(emptySlots);
//        System.out.println("----------");
        List<Plan> r1 = algo1(duration, emptySlots);
        List<Plan> r2 = algo2(duration, emptySlots);

        return new List[]{r1,r2};
    }

    private List<Plan> sortPlans(List<Plan> plans){
        List<Plan> ret = new ArrayList<>();
        while(plans.size()>0){
            Plan low = plans.get(0);
            for(Plan p:plans){
                if(p.getStart().getTime()<low.getStart().getTime()){
                    low = p;
                }
            }
            ret.add(low);
            plans.remove(low);
        }
        return ret;
    }

    private List<Plan> algo1(long duration, List<Plan> empty){
        List<Plan> ret = new ArrayList<>();
        for(Plan e:empty){
            long length = e.getEnd().getTime()-e.getStart().getTime();
            if(length<duration){
                duration-=length;
                ret.add(e);
            }else if(length>=duration){
                Timestamp newend = new Timestamp(e.getStart().getTime()+duration);
                ret.add(conPlan(e.getStart(), newend));
                break;
            }
        }
        return ret;
    }
    private List<Plan> algo2(long duration, List<Plan> empty){
        List<Plan> ret = new ArrayList<>();
        long[] times = new long[empty.size()];
        for(int i=0;i<times.length;i++){
            times[i] = calcLength(empty.get(i));
        }

        while(true){
            //find lowest
            boolean first = true;
            long low=0;
            int length = 0;
            for(long t:times){
                if(t>0){
                    length++;
                    if(first){
                        first=false;
                        low = t;
                    }else{
                        if(low>t){
                            low = t;
                        }
                    }
                }
            }
            if(length==0){
                break;
            }
            long tol = length*low;
            long piece = duration/length;
            if(tol>=duration){
                //enough time
                for(int i=times.length-1;i>=0;i--){
                    if(times[i]>0){
                        times[i]-=piece;
                    }
                }
                break;
            }else{
                for(int i=times.length-1;i>=0;i--){
                    if(times[i]>0){
                        times[i]-=low;
                    }
                }
                duration-=low*length;
            }

        }

        for(int i=0;i<empty.size();i++){
            Timestamp newend = new Timestamp(empty.get(i).getEnd().getTime()-times[i]);
            ret.add(conPlan(empty.get(i).getStart(), newend));
        }



        return ret;
    }

    private long calcLength(Plan e){
        return e.getEnd().getTime()-e.getStart().getTime();
    }

    private boolean beforeE(Timestamp a, Timestamp b){
        return a.before(b)||a.equals(b);
    }
    private boolean afterE(Timestamp a, Timestamp b){
        return a.after(b)||a.equals(b);
    }
    private void pps(List<Plan> ps){
        for(Plan p:ps){
            System.out.println(p);
        }
    }


    private Plan conPlan(Timestamp start, Timestamp end){
        Plan ret = new Plan();
        ret.setStart(start);
        ret.setEnd(end);
        return ret;
    }
}
