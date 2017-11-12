
import {Actor, Binder, Court, Decision, Docket, Party, Patent, Right, Trademark} from "./domain.type";

export interface QueryViewModel {
    binders: {[id: string]: Binder};
    dockets: {[id: string]: Docket};
    decisions: {[id: string]: Decision};
    courts: {[id: string]: Court};
    parties: {[id: string]: Party};
    actors: {[id: string]: Actor};
    rights: {[id: string]: Right};
    trademarks: {[id: string]: Trademark};
    patents: {[id: string]: Patent};
}
