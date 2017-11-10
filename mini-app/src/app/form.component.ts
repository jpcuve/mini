import {Component, EventEmitter, Input, Output} from "@angular/core";
import {BinderQueryModel} from "./form";

@Component({
    selector: 'binder-query-form',
    template: `
        <form #queryForm="ngForm" #formSpy>
            {{diagnostic}}
            <br/>
            {{formSpy.className}}
            <div class="form-group">
                <label for="name" class="control-label">Reference</label>
                <input [(ngModel)]="model.reference" #spy type="text" class="form-control" id="name" name="name" required="required"/>
                <br/>
                {{spy.className}}
            </div>
            <div class="form-group">
                <label for="sex" class="control-label">Sex</label>
                <select [(ngModel)]="model.sex" class="form-control" id="sex" name="sex" required="required">
                    <option *ngFor="let s of sexes" [value]="s">{{s}}</option>
                </select>
            </div>
            <div class="has-warning">
                This is a warning
            </div>
            <button type="submit" class="btn btn-success" *ngIf="queryForm.valid" (click)="emit()">Search</button>
        </form>
    `
})
export class BinderQueryFormComponent {
    model: BinderQueryModel = new BinderQueryModel('1,2,3', 'male');
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
