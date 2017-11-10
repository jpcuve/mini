/**
 * Created by jpc on 6/1/17.
 */
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Http} from "@angular/http";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {QueryViewModel} from "./model";
import {BinderQueryModel} from "./form";


@Injectable()
export class RemoteService {
    base: string;

    constructor(private http: Http) {
        const w: Window = <Window> window;
        const cs: string[] = (parseInt(w.location.port) < 8080 ? ['http://', w.location.hostname, ':8080'] : []);
        this.base = cs.concat(['/api']).join('');
        console.info('base:', this.base);
    }

    queryBinders(model: BinderQueryModel): Observable<number[]> {
        return this.http.post(this.base + '/binders/query', model).map(m => m.json() as number[]);
    }

    getQueryViewModel(commaSeparatedIds: string): Observable<QueryViewModel> {
        return this.http.get(this.base + '/binders/' + commaSeparatedIds).map(m => m.json() as QueryViewModel);
    }
}
