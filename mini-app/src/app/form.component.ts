import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BinderQueryModel} from './form.type';

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
          <button pButton type="button" label="Clear" icon="fa-cog" class="ui-button-secondary" (click)="clear()"></button>
          <button pButton type="button" *ngIf="queryForm.valid" (click)="emit()" label="Search"
                  icon="fa-search"></button>
        </div>
      </div>
      <br/>
    </form>
  `
})
export class BinderQueryFormComponent {
  model: BinderQueryModel = new BinderQueryModel('i', 'male');
  @Output('handler')
  handler: EventEmitter<BinderQueryModel> = new EventEmitter();
  sexes: string[] = ['male', 'female'];

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
