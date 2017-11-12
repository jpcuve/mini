
import {Component, OnInit} from "@angular/core";
import {RemoteService} from "./remote.service";
import {Binder, Decision, Docket} from "./domain.type";
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
                this.query = query;
            });
        });
    }

    ngOnInit(): void {
    }
}

