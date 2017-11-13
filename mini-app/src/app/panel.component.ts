import {Component, Input} from "@angular/core";
import {Actor, Binder, Court, Decision, Docket, Party, Right} from "./domain.type";
import {RemoteService} from "./remote.service";

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
                <li *ngFor="let right of binder.rights">
                    <app-right-panel [right]="right"></app-right-panel>
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
        <app-actor-panel [actor]="party.actor"></app-actor-panel>
        <span *ngIf="party.plaintiff">(Plaintiff)</span>
        <span *ngIf="!party.plaintiff">(Defendant)</span>
    `
})
export class PartyPanelComponent {
    @Input('party')
    party: Party;
}

@Component({
    selector: 'app-right-panel',
    template: `
        <span>{{right.discriminator}}</span>
        <app-images-panel [ids]="right.imageIds"></app-images-panel>
        <span *ngIf="right.plaintiff">(Plaintiff)</span>
        <span *ngIf="!right.plaintiff">(Defendant)</span>
    `
})
export class RightPanelComponent {
    @Input('right')
    right: Right;
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

@Component({
    selector: 'app-images-panel',
    template: `
        <img *ngFor="let id of ids" src="{{remoteService.server}}/asset?uuid={{id}}"/> <!-- TODO url -->
    `
})
export class ImagesPanelComponent {
    constructor(public remoteService: RemoteService){

    }

    @Input('ids')
    ids: string[];
}
