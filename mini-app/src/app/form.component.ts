import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BinderQueryModel} from './form.type';

@Component({
  selector: 'app-binder-query-form',
  template: `
    <form #queryForm="ngForm" #formSpy name="binderQueryForm">
      {{diagnostic}}
      <br/>
      {{formSpy.className}}
      <div class="ui-g ui-fluid">
        <div class="ui-g-12 ui-md-4">
          <span class="ui-float-label">
            <input id="float-input" type="text" size="30" pInputText [(ngModel)]="model.reference"/> 
            <label for="float-input">Reference</label>
          </span>
        </div>
        <div class="ui-g-12 ui-md-4">
          <button pButton="" type="button" label="Clear" icon="fa-cog" class="ui-button-secondary"></button>
          <button pButton="" type="button" *ngIf="queryForm.valid" (click)="emit()" label="Search"
                  icon="fa-search"></button>
        </div>
      </div>
      <br/>
    </form>
  `
})
export class BinderQueryFormComponent {
  model: BinderQueryModel = new BinderQueryModel('dec', 'male');
  @Output('handler')
  handler: EventEmitter<BinderQueryModel> = new EventEmitter();
  sexes: string[] = ['male', 'female'];

  emit(): void {
    this.handler.emit(this.model);
  }

  get diagnostic(): string {
    return JSON.stringify(this.model);
  }
}
