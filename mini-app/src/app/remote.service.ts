/**
 * Created by jpc on 6/1/17.
 */
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Http} from "@angular/http";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {Domain, Person, Product, Role, Territory} from "./domain";


export interface RoleViewModel {
    territories: Territory[];
    role: Role;
}

export interface TerritoryViewModel {
    roles: Role[];
    territory: Territory;
}

@Injectable()
export class RemoteService {
    base: string;

    constructor(private http: Http) {
        let w: Window = <Window> window;
        let cs: string[] = (parseInt(w.location.port) < 8080 ? ['http://', w.location.hostname, ':8080'] : []);
        this.base = cs.concat(['/api']).join('');
        console.info('base:', this.base);
    }

    getPersons(): Observable<Person[]> {
        return this.http.get(this.base + '/persons').map(r => r.json() as Person[]);
    }

    getDomains(): Observable<Domain[]> {
        return this.http.get(this.base + '/domains').map(r => r.json() as Domain[]);
    }

    getProducts(): Observable<Product[]> {
        return this.http.get(this.base + '/products').map(r => r.json() as Product[]);
    }

    getRoles(): Observable<Role[]> {
        return this.http.get(this.base + '/roles').map(r => r.json() as Role[]);
    }

    getTerritories(): Observable<Territory[]> {
        return this.http.get(this.base + '/territories').map(t => t.json() as Territory[]);
    }

    getRoleViewModel(id: string): Observable<RoleViewModel> {
        return this.http.get(this.base + '/roles/' + id).map(m => m.json() as RoleViewModel);
    }

    getTerritoryViewModel(id: string): Observable<TerritoryViewModel> {
        return this.http.get(this.base + '/territories/' + id).map(m => m.json() as TerritoryViewModel);
    }

}
