
import {Component, OnInit} from "@angular/core";
import {RemoteService} from "./remote.service";
import {Binder, Docket} from "./domain.type";
import {QueryViewModel} from "./model.type";
import {BinderQueryModel} from "./form.type";

@Component({
    templateUrl: './query-view.component.html'
})
export class QueryViewComponent implements OnInit {
    model: QueryViewModel = { binders:[], dockets:[], decisions:[], courts:[]};
    query: BinderQueryModel;

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
                this.model = m;
                const map: {[key: string]: any} = {};
                m.binders.forEach(binder => {
                    binder.dockets = [];
                    map['b' + binder.id] = binder;
                });
                m.courts.forEach(court => {
                    map['c' + court.id] = court;
                });
                m.dockets.forEach(docket => {
                    docket.decisions = [];
                    map['o' + docket.id] = docket;
                    docket.court = map['c' + docket.courtId];
                    (map['b' + docket.binderId] as Binder).dockets.push(docket);
                });
                m.decisions.forEach(decision => {
                    map['d' + decision.id] = decision;
                    (map['o' + decision.docketId] as Docket).decisions.push(decision);
                });
                this.query = query;
            });
        });
    }

    ngOnInit(): void {
    }
}

