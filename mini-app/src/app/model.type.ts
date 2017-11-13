
import {
  Actor, Binder, Court, Decision, DesignModel, Docket, DomainName, Party, Patent, Right,
  Trademark
} from "./domain.type";

export interface QueryViewModel {
    binders: {[id: string]: Binder};
    dockets: {[id: string]: Docket};
    decisions: {[id: string]: Decision};
    courts: {[id: string]: Court};
    parties: {[id: string]: Party};
    actors: {[id: string]: Actor};
    rights: {[id: string]: Right};
    trademarks: {[id: string]: Trademark};
    domainNames: {[id: string]: DomainName};
    patents: {[id: string]: Patent};
    designModels: {[id: string]: DesignModel};
}
