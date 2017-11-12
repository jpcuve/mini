import {Component, Input} from "@angular/core";
import {Actor, Binder, Court, Decision, Docket, Party} from "./domain.type";

@Component({
    selector: 'app-binder-panel',
    template: `
        <p-panel header="b{{binder.id}}: {{binder.reference}}" toggleable="[true]" collapsed="[true]">
            <ul>
                <li *ngFor="let party of binder.parties">
                    <app-party-panel [party]="party"></app-party-panel>
                </li>
            </ul>
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
    selector: 'app-party-panel',
    template: `
        <span>{{party.defendant}}:  <app-actor-panel [actor]="party.actor"></app-actor-panel></span>
    `
})
export class PartyPanelComponent {
    @Input('party')
    party: Party;
}

@Component({
    selector: 'app-actor-panel',
    template: `
        <span>a{{actor.id}}: {{actor.name}}</span>
    `
})
export class ActorPanelComponent {
    @Input('actor')
    actor: Actor;
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

