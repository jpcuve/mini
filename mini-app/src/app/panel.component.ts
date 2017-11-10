import {Component, Input} from "@angular/core";
import {Binder, Court, Decision, Docket} from "./domain";

@Component({
    selector: 'app-binder-panel',
    template: `
        <p-panel header="b{{binder.id}}: {{binder.reference}}" toggleable="[true]" collapsed="[true]">
          <ul>
            <li *ngFor="let docket of binder.dockets">
              <app-docket-panel [docket]="docket"></app-docket-panel>
            </li>
          </ul>
        </p-panel>
    `
})
export class BinderPanelComponent {
    @Input('binder')
    binder: Binder;
}

@Component({
    selector: 'app-docket-panel',
    template: `
        <span>o{{docket.id}}: {{docket.reference}} (<app-court-panel [court]="docket.court"></app-court-panel>)</span>
        <ul>
            <li *ngFor="let decision of docket.decisions">
                <app-decision-panel [decision]="decision"></app-decision-panel>
            </li>
        </ul>
    `
})
export class DocketPanelComponent {
    @Input('docket')
    docket: Docket;
}

@Component({
    selector: 'app-decision-panel',
    template: `
        <span>d{{decision.id}}: {{decision.reference}}</span>
    `
})
export class DecisionPanelComponent {
    @Input('decision')
    decision: Decision;
}

@Component({
    selector: 'app-court-panel',
    template: `
        <span>c{{court.id}}: {{court.name}}</span>
    `
})
export class CourtPanelComponent {
    @Input('court')
    court: Court;
}

