package com.capgemini.todoweb;

import com.capgemini.todoweb.data.TodoRepository;
import com.capgemini.todoweb.models.TodoModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public CommandLineRunner dataLoader(TodoRepository repository) {
        return args -> {
            repository.save(new TodoModel("Create TODO list", "Each todo could have, at minimal, a related state and title. \n" +
                    "Some hard-coded todos will be initialized in this context to demonstrate the tool.", true));
            repository.save(new TodoModel("Allow to change TODO state", "When a todo is done, it should be placed at the bottom of the list and should be crossed out", false));
            repository.save(new TodoModel("Show detail of a TODO", "We can click on a todo (by any way) to access the details view of the todo\n" +
                    "The todo could be accessed via a unique URL", false));
            repository.save(new TodoModel("Allow to add a new TODO", "The todo title is required\n" +
                    "The todo description can be empty\n" +
                    "The newly created todo has to be on top of the list of todos\n" +
                    "You are free to choose the design / interaction ", false));

        };
    }
}
