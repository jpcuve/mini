/**
 * Created by jpc on 6/1/17.
 */
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {QueryViewModel} from "./model.type";
import {BinderQueryModel} from "./form.type";
import {Court, Decision, Pol} from "./domain.type";
import {HttpClient} from "@angular/common/http";


@Injectable()
export class RemoteService {
    server: string;
    base: string;

    constructor(private httpClient: HttpClient) {
        const w: Window = <Window> window;
        const cs: string[] = (+w.location.port < 8080 ? ['http://', w.location.hostname, ':8080'] : []);
        this.server = cs.join('');
        this.base = cs.concat(['/api']).join('');
        window.console.info('base:', this.base);
    }

    queryBinders(model: BinderQueryModel): Observable<number[]> {
        return this.httpClient.post(this.base + '/binders/query', model).map(m => m as number[]);
    }

    getQueryViewModel(commaSeparatedIds: string): Observable<QueryViewModel> {
        return this.httpClient.get(this.base + '/binders/' + commaSeparatedIds).map(m => m as QueryViewModel);
    }

    getCourts(): Observable<Court[]> {
      return this.httpClient.get(this.base + '/courts').map(r => r as Court[]);
    }

    getPols(): Observable<Pol[]> {
      return this.httpClient.get(this.base + '/pols').map(r => r as Pol[]);
    }

    getDecision(id: number): Observable<Decision> {
      return this.httpClient.get(this.base + '/decision/' + id).map(r => r as Decision);
    }
}
