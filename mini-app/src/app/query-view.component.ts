
import {Component, OnInit} from "@angular/core";
import {RemoteService} from "./remote.service";
import {Binder, Decision, Docket, Party, Right} from "./domain.type";
import {QueryViewModel} from "./model.type";
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
                this.query = query;
            });
        });
    }

    ngOnInit(): void {
    }
}

