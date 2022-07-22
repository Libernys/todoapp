import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TodoService} from "../../services/todo.service";
import {Todo} from "../../models/todo.model";
import {Location} from "@angular/common";

@Component({
  selector: 'app-todo-detail',
  templateUrl: './todo-detail.component.html',
  styleUrls: ['./todo-detail.component.css']
})
export class TodoDetailComponent implements OnInit {

  @Input() todo: Todo = {
    title: '',
    description: '',
    done: false
  };

  message = '';

  constructor(
    private todoService: TodoService,
    private route: ActivatedRoute,
    private location: Location) { }

  ngOnInit(): void {
    if (this.route.snapshot.params["id"] && this.todo.title=="") {
      this.message = '';
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

  back() {
    this.location.back()
  }
}
