import {Component, Input} from "@angular/core";
import {Actor, Binder, Court, Decision, Docket, Party, Right} from "./domain.type";
import {RemoteService} from "./remote.service";

@Component({
    selector: 'app-binder-panel',
    template: `
        <p-panel toggleable="[true]" collapsed="[true]">
          <p-header>
            <i class="fa fa-book"></i>
            <span>{{binder.reference}}</span>
              <i *ngIf="binder.domains.indexOf('TRADEMARK') >= 0" class="fa fa-trademark"></i>
              <i *ngIf="binder.domains.indexOf('DOMAIN_NAME') >= 0" class="fa fa-at"></i>
              <i *ngIf="binder.domains.indexOf('PATENT') >= 0" class="fa fa-lightbulb-o"></i>
              <i *ngIf="binder.domains.indexOf('DESIGN_MODEL') >= 0" class="fa fa-cube"></i>
              <i *ngIf="binder.domains.indexOf('COPYRIGHT') >= 0" class="fa fa-copyright"></i>
              <i *ngIf="binder.domains.indexOf('UNFAIR_COMPETITION') >= 0" class="fa fa-compress"></i>
              <span>({{binder.area}})</span>
          </p-header>
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
                <li *ngFor="let trademark of binder.trademarks">
                    <app-trademark-panel [trademark]="trademark"></app-trademark-panel>
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
      <i *ngIf="party.plaintiff" class="fa fa-forward" style="color: firebrick;"></i>
      <i *ngIf="!party.plaintiff" class="fa fa-backward" style="color: forestgreen;"></i>
        <app-actor-panel [actor]="party.actor"></app-actor-panel>
    `
})
export class PartyPanelComponent {
    @Input('party')
    party: Party;
}

@Component({
    selector: 'app-right-panel',
    template: `
      <span>
        <i *ngIf="right.plaintiff" class="fa fa-forward" style="color: firebrick;"></i>
        <i *ngIf="!right.plaintiff" class="fa fa-backward" style="color: forestgreen;"></i>
        <i *ngIf="right.domain === 'TRADEMARK'" class="fa fa-trademark"></i>
        <i *ngIf="right.domain === 'DOMAIN_NAME'" class="fa fa-at"></i>
        <i *ngIf="right.domain === 'PATENT'" class="fa fa-lightbulb-o"></i>
        <i *ngIf="right.domain === 'DESIGN_MODEL'" class="fa fa-cube"></i>
        <i *ngIf="right.domain === 'COPYRIGHT'" class="fa fa-copyright"></i>
        <i *ngIf="right.domain === 'UNFAIR_COMPETITION'" class="fa fa-compress"></i>
      </span>
        <app-images-panel [ids]="right.imageIds"></app-images-panel>
    `
})
export class RightPanelComponent {
    @Input('right')
    right: Right;
}


@Component({
    selector: 'app-actor-panel',
    template: `
        <span title="{{actor.id}}">
          <i *ngIf="actor.person" class="fa fa-user"></i>
          <i *ngIf="!actor.person" class="fa fa-industry"></i>
          {{actor.name}}
        </span>
    `
})
export class ActorPanelComponent {
    @Input('actor')
    actor: Actor;
}

@Component({
    selector: 'app-docket-panel',
    template: `
        <span title="{{docket.id}}">
          <i class="fa fa-tag"></i>
          {{docket.reference}} (<app-court-panel [court]="docket.court"></app-court-panel>)
        </span>
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
        <span title="{{decision.id}}">
          <i class="fa fa-balance-scale"></i>
          {{decision.reference}}
        </span>
    `
})
export class DecisionPanelComponent {
    @Input('decision')
    decision: Decision;
}

@Component({
    selector: 'app-court-panel',
    template: `
        <span title="{{court.id}}">
          <i class="fa fa-institution"></i>
          {{court.name}}
        </span>
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
