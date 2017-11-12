
import {Binder, Court, Decision, Docket} from "./domain.type";

export interface QueryViewModel {
    binders: {[id: string]: Binder};
    dockets: {[id: string]: Docket};
    decisions: {[id: string]: Decision};
    courts: {[id: string]: Court};
}
