package com.capgemini.todoweb.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 *  The <b>TodoModel</b> class contains the TODOs content.
 */
@Data
@Entity
@NoArgsConstructor
public class TodoModel {

    public TodoModel(String title, String description, boolean done) {
        this.title = title;
        this.description = description;
        this.done = done;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "The title can not be null")
    @Column(length = 50)
    private String title;

    @Column(length = 2000)
    private String description;

    private Date doneAt = null;

    private Date updatedAt = new Date();
    @Type(type= "org.hibernate.type.NumericBooleanType")
    private boolean done = false;
}
