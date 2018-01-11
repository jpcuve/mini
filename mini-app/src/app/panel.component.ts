import {Component, Input} from "@angular/core";
import {
  Actor, Binder, Copyright, Court, Decision, DesignModel, Docket, DomainName, Party, Patent, Right,
  Trademark
} from "./domain.type";
import {RemoteService} from "./remote.service";

@Component({
  selector: 'app-binder-panel',
  templateUrl: 'panel/binder-panel.component.html'
})
export class BinderPanelComponent {
  @Input('binder')
  binder: Binder;
}

@Component({
  selector: 'app-party-panel',
  templateUrl: 'panel/party-panel.component.html'
})
export class PartyPanelComponent {
  @Input('party')
  party: Party;
}

@Component({
  selector: 'app-right-panel',
  templateUrl: 'panel/right-panel.component.html'
})
export class RightPanelComponent {
  @Input('right')
  right: Right;
}

@Component({
  selector: 'app-trademark-panel',
  templateUrl: 'panel/trademark-panel.component.html'
})
export class TrademarkPanelComponent {
  @Input('trademark')
  trademark: Trademark;
}

@Component({
  selector: 'app-domain-name-panel',
  templateUrl: 'panel/domain-name-panel.component.html'
})
export class DomainNamePanelComponent {
  @Input('domainName')
  domainName: DomainName;
}

@Component({
  selector: 'app-patent-panel',
  templateUrl: 'panel/patent-panel.component.html'
})
export class PatentPanelComponent {
  @Input('patent')
  patent: Patent;
}

@Component({
  selector: 'app-design-model-panel',
  templateUrl: 'panel/design-model-panel.component.html'
})
export class DesignModelPanelComponent {
  @Input('designModel')
  designModel: DesignModel;
}

@Component({
  selector: 'app-copyright-panel',
  templateUrl: 'panel/copyright-panel.component.html'
})
export class CopyrightPanelComponent {
  @Input('copyright')
  copyright: Copyright;
}

@Component({
  selector: 'app-actor-panel',
  templateUrl: 'panel/actor-panel.component.html'
})
export class ActorPanelComponent {
  @Input('actor')
  actor: Actor;
}

@Component({
  selector: 'app-docket-panel',
  templateUrl: 'panel/docket-panel.component.html'
})
export class DocketPanelComponent {
  @Input('docket')
  docket: Docket;
}

@Component({
  selector: 'app-decision-panel',
  templateUrl: 'panel/decision-panel.component.html'
})
export class DecisionPanelComponent {
  @Input('decision')
  decision: Decision;
}

@Component({
  selector: 'app-day-panel',
  templateUrl: 'panel/day-panel.component.html'
})
export class DayPanelComponent {
  @Input('day')
  day: Date;
}

@Component({
  selector: 'app-court-panel',
  templateUrl: 'panel/court-panel.component.html'
})
export class CourtPanelComponent {
  @Input('court')
  court: Court;
}

@Component({
  selector: 'app-images-panel',
  templateUrl: 'panel/images-panel.component.html'
})
export class ImagesPanelComponent {
  constructor(public remoteService: RemoteService) {

  }

  @Input('ids')
  ids: string[];
}
