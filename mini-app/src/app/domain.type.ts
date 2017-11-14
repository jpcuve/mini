export interface Actor {
  id: number;
  name: string;
  person: boolean;
}

export interface Binder {
  id: number;
  reference: string;
  area: string;
  first_action?: string;
  withdrawal: boolean;
  settled: boolean;
  domains: string[];
  dockets: Docket[];
  parties: Party[];
  rights: Right[]
}

export interface Party {
  actorId: number;
  binderId: number;
  plaintiff: boolean;
  actor: Actor;
}

export interface Court {
  id: number;
  parentId?: number;
  name: string;
}

export interface Right {
  id: number;
  binderId: number;
  discriminator: string;
  plaintiff: boolean;
  imageIds: string[];
}

export interface Trademark extends Right {
  name: string;
}

export interface DomainName extends Right {
  name: string;
}

export interface Patent extends Right {
  application: string;
}

export interface DesignModel extends Right {
  name: string;
}

export interface Pol {
  id: number;
  name: string;
  parentId?: number;
}

export interface Docket {
  id: number;
  binderId: number;
  courtId: number;
  reference: string;
  court: Court;
  decisions: Decision[];
}

export interface Decision {
  id: number;
  docketId: number;
  reference: string;
  judgmentDate: string;
  recordNature?: string;
  refusal: boolean;
  citable: boolean;
  winner?: string;
}

export interface Document {
  id: number;
  decisionId: number;
  reference: string;
  lang: string;
}

export interface Honor {
  id: number;
  decisionId: number;
  rightId: number;
  validity: string;
}

export interface Analysis {
  id: number;
}
