
import {Actor, Binder, Court, Decision, Docket, Party} from "./domain.type";

export interface QueryViewModel {
    binders: {[id: string]: Binder};
    dockets: {[id: string]: Docket};
    decisions: {[id: string]: Decision};
    courts: {[id: string]: Court};
    parties: {[id: string]: Party};
    actors: {[id: string]: Actor};
}
