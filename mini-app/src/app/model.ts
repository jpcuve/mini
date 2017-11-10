
import {Binder, Court, Decision, Docket} from "./domain";

export interface QueryViewModel {
    binders: Binder[];
    dockets: Docket[];
    decisions: Decision[];
    courts: Court[];
}
