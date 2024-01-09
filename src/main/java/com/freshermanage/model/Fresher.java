package be.fresher_manage.model.dto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Fresher {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "language")
    private String language;
    @Column(name = "email")
    private String email;
    @Column(name = "point1")
    private int point1;
    @Column(name = "point2")
    private int point2;
    @Column(name = "point3")
    private int point3;
    @ManyToOne
    private Center centerId;
}
