import {Component, OnInit} from "@angular/core";
import {RemoteService} from "./remote.service";
import {
    Binder, Copyright, Decision, DesignModel, Docket, DomainName, Party, Patent, Right,
    Trademark
} from "./domain.type";
import {BinderQueryModel} from "./form.type";

@Component({
    templateUrl: './query-view.component.html'
})
export class QueryViewComponent implements OnInit {
    query: BinderQueryModel;
    binders: Binder[];

    constructor(private remoteService: RemoteService){
        console.log('Main component starting now');
    }

    json(arg: any){
        return JSON.stringify(arg);
    }

    queryFormHandler(query: BinderQueryModel): void {
        this.remoteService.queryBinders(query).subscribe(ids => {
            console.log(JSON.stringify(ids));
            let idsAsString = ids.join(",");
            console.log(idsAsString);
            this.remoteService.getQueryViewModel(idsAsString).subscribe(m => {
                this.binders = [];
                for (let id in m.binders){
                    let binder: Binder = m.binders[id];
                    binder.dockets = [];
                    binder.parties = [];
                    binder.rights = [];
                    binder.trademarks = [];
                    this.binders.push(binder);
                }
                for (let id in m.dockets){
                    let docket: Docket = m.dockets[id];
                    docket.court = m.courts[docket.courtId];
                    docket.decisions = [];
                    m.binders[docket.binderId].dockets.push(docket);
                }
                for (let id in m.decisions){
                    let decision: Decision = m.decisions[id];
                    m.dockets[decision.docketId].decisions.push(decision);
                }
                for (let id in m.parties){
                    let party: Party = m.parties[id];
                    party.actor = m.actors[party.actorId];
                    m.binders[party.binderId].parties.push(party);
                }
                for (let id in m.rights){
                    let right: Right = m.rights[id];
                    m.binders[right.binderId].rights.push(right);
                }
                for (let id in m.binders) {
                    let binder: Binder = m.binders[id];
                    binder.trademarks = binder.rights
                        .filter(r => r.domain ==='TRADEMARK')
                        .reduce((ar: Trademark[], r: Trademark) => { ar.push(<Trademark>r); return ar; }, []);
                    binder.domainNames = binder.rights
                        .filter(r => r.domain ==='DOMAIN_NAME')
                        .reduce((ar: DomainName[], r: DomainName) => { ar.push(<DomainName>r); return ar; }, []);
                    binder.patents = binder.rights
                        .filter(r => r.domain ==='PATENT')
                        .reduce((ar: Patent[], r: Patent) => { ar.push(<Patent>r); return ar; }, []);
                    binder.designModels = binder.rights
                        .filter(r => r.domain ==='DESIGN_MODEL')
                        .reduce((ar: DesignModel[], r: DesignModel) => { ar.push(<DesignModel>r); return ar; }, []);
                    binder.copyrights = binder.rights
                        .filter(r => r.domain ==='COPYRIGHT')
                        .reduce((ar: Copyright[], r: Copyright) => { ar.push(<Copyright>r); return ar; }, []);
                }
                this.query = query;
            });
        });
    }

    ngOnInit(): void {
    }
}

