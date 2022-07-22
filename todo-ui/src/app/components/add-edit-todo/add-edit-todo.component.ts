import {Component, OnInit} from '@angular/core';
import {TodoService} from "../../services/todo.service";
import {Todo} from "../../models/todo.model";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from '@angular/common'
import { FormGroup,  FormBuilder,  Validators } from '@angular/forms';

@Component({
  selector: 'app-add-edit-todo',
  templateUrl: './add-edit-todo.component.html',
  styleUrls: ['./add-edit-todo.component.css']
})
export class AddEditTodoComponent {
  message = '';
  todo: Todo = AddEditTodoComponent.createTodo()
  todoForm: FormGroup;
  constructor(private todoService: TodoService,
              private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private formBuilder: FormBuilder) {
    this.todoForm = this.formBuilder.group({
      title: ['', Validators.required ],
      description: []
    });
  }

  ngOnInit(): void {
    if (this.route.snapshot.params["id"]) {
      this.getTodo(this.route.snapshot.params["id"]);
    }
  }

  getTodo(id: string): void {
    this.todoService.get(id)
      .subscribe({
        next: (data) => {
          this.todo = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  private static createTodo(): Todo {
    return {
      title: '',
      description: '',
      done: false
    };
  }

  saveTodo(): void {
    if (this.todo.id) {
      this.updateTodo();
    } else {
      this.insertTodo();
    }
  }

  private insertTodo() {
    const data = {
      title: this.todo.title,
      description: this.todo.description
    };

    this.todoService.create(data)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.router.navigate(['./todos']).then(_ => {});
        },
        error: (e) => console.error(e)
      });
  }

  updateTodo(): void {
    this.message = '';

    this.todoService.update(this.todo.id, this.todo)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message ? res.message : 'This todo was updated successfully!';
          this.back();
        },
        error: (e) => console.error(e)
      });
  }

  back(): void {
    this.location.back()
  }
}
