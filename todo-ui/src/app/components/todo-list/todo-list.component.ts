import {Component, OnInit} from '@angular/core';
import {TodoService} from "../../services/todo.service";
import {Todo} from "../../models/todo.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent implements OnInit {
  todos?: Todo[];
  titleToSearch = '';
  lastTitleSearched = '';
  leftTodosCount = 0;
  todosDoneCount = 0;

  constructor(private todoService: TodoService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.retrieveTodos();
  }

  retrieveTodos(): void {
    this.todoService.getAll()
      .subscribe({
        next: (data) => {
          this.setTodos(data);
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  private setTodos(data: Todo[]) {
    this.todos = data;
    if(data) {
      this.leftTodosCount = data.filter(t => !t.done).length;
      this.todosDoneCount = data.length - this.leftTodosCount;
    } else {
      this.leftTodosCount = 0
      this.todosDoneCount = 0;
    }
  }

  refreshList(): void {
    if (this.lastTitleSearched) {
      this.searchByTitle();
    } else {
      this.retrieveTodos();
    }
  }

  updateDone(todo: Todo): void {
    this.todoService.update(todo.id, todo)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.refreshList();
        },
        error: (e) => {
          todo.done = !todo.done;
          console.error(e);
        }
      });
  }

  deleteTodo(id: any): void {
    this.todoService.delete(id)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.refreshList();
        },
        error: (e) => console.error(e)
      });
  }

  deleteTodos(): void {
    if (this.lastTitleSearched) {
      this.deleteByTitle();
    } else {
      this.deleteAllTodos();
    }
  }

  deleteAllTodos(): void {
    this.todoService.deleteAll()
      .subscribe({
        next: (res) => {
          console.log(res);
          this.refreshList();
        },
        error: (e) => console.error(e)
      });
  }

  searchByTitle(): void {
    this.lastTitleSearched = this.titleToSearch
    this.todoService.findByTitle(this.titleToSearch)
      .subscribe({
        next: (data) => {
          this.setTodos(data);
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  deleteByTitle(): void {
    this.todoService.deleteByTitle(this.lastTitleSearched)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.refreshList();
        },
        error: (e) => console.error(e)
      });
  }

  navigate(todo: Todo) {
    this.router.navigate(['./todos/' + todo.id]).then(_ => {});
  }

  removeCompleted() {
    this.todoService.deleteCompleted(this.lastTitleSearched)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.refreshList();
        },
        error: (e) => console.error(e)
      });
  }
}
