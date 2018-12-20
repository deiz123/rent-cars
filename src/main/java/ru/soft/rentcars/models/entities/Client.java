package ru.soft.rentcars.models.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "client")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;

    @OneToOne(mappedBy = "client")
    private Car car;

    public Client(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }
}
