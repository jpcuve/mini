import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BinderQueryModel} from './form.type';
import {TreeNode} from "primeng/primeng";
import {RemoteService} from "./remote.service";

@Component({
  selector: 'app-binder-query-form',
  template: `
    <form class="ui-widget" #queryForm="ngForm" #formSpy name="binderQueryForm">
      <div class="ui-g">
          <div class="ui-g-12">
              <span>{{diagnostic}}</span>
          </div>
        <div class="ui-g-12">
          <span class="ui-float-label">
            <input name="reference" type="text" size="30" pInputText [(ngModel)]="model.reference"/> 
            <label for="float-input">Reference</label>
          </span>
        </div>
        <div class="ui-g-12">
          <p-tree [value]="courts"></p-tree>
        </div>
        <div class="ui-g-12">
          <p-tree [value]="pols"></p-tree>
        </div>
        <div class="ui-g-12">
          <button pButton type="button" label="Clear" icon="fa-cog" class="ui-button-secondary" (click)="clear()"></button>
          <button pButton type="button" *ngIf="queryForm.valid" (click)="emit()" label="Search"
                  icon="fa-search"></button>
        </div>
      </div>
      <br/>
    </form>
  `
})
export class BinderQueryFormComponent implements OnInit {
  model: BinderQueryModel = new BinderQueryModel('i', 'male');
  @Output('handler')
  handler: EventEmitter<BinderQueryModel> = new EventEmitter();
  sexes: string[] = ['male', 'female'];
  courts: TreeNode[] = [{ label: 'A', children:[{ label: 'B'}, {label: 'C'}] }];
  pols: TreeNode[] = [];

  constructor(private remoteService: RemoteService){
  }

  ngOnInit(): void {
    this.remoteService.getPols().subscribe(pols => {
      let map: { [key: string]: TreeNode } = {};
      pols.forEach(pol => {
        let node: TreeNode = {label: pol.name, children: []};
        map[pol.id] = node;
        if (!pol.parentId){
          this.pols.push(node);
        }
      });
      pols.filter(n => n.parentId).forEach(n => map[n.parentId].children.push(map[n.id]));
    });
  }


  clear(): void {
      this.model.clear();
  }

  emit(): void {
    this.handler.emit(this.model);
  }

  get diagnostic(): string {
    return JSON.stringify(this.model);
  }
}
