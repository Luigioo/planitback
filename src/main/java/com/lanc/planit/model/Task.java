package com.lanc.planit.model;
/**
 * @Author Luigi Lin
 *
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int duration;//in seconds
    private Timestamp start;
    private Timestamp end;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser user;


}
