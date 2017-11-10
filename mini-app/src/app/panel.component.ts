import {Component, Input} from "@angular/core";
import {Binder, Decision, Docket} from "./domain";

@Component({
    selector: 'binder-panel',
    template: `
        <span>b{{binder.id}}: {{binder.reference}}</span>
        <ul>
            <li *ngFor="let docket of binder.dockets">
                <docket-panel [docket]="docket"></docket-panel>
            </li>
        </ul>
    `
})
export class BinderPanelComponent {
    @Input('binder')
    binder: Binder;
}

@Component({
    selector: 'docket-panel',
    template: `
        <span>o{{docket.id}}: {{docket.reference}}</span>
        <ul>
            <li *ngFor="let decision of docket.decisions">
                <decision-panel [decision]="decision"></decision-panel>
            </li>
        </ul>
    `
})
export class DocketPanelComponent {
    @Input('docket')
    docket: Docket;
}

@Component({
    selector: 'decision-panel',
    template: `
        <span>d{{decision.id}}: {{decision.reference}}</span>
    `
})
export class DecisionPanelComponent {
    @Input('decision')
    decision: Decision;
}
