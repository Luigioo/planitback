package com.lanc.planit.model;
/**
 * @Author Luigi Lin
 *
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser user;

    private String name;
    private Timestamp start;
    private Timestamp end;
    private boolean allday;

}
