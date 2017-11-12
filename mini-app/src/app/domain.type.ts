export interface Actor {
  id: number;
  name: string;
}

export interface Binder {
  id: number;
  reference: string;
  first_action?: string;
  withdrawal: boolean;
  settled: boolean;
  domains: string[];
  dockets: Docket[];
  parties: Party[];
}

export interface Party {
  actorId: number;
  binderId: number;
  defendant: boolean;
  actor: Actor;
}

export interface Court {
  id: number;
  parentId?: number;
  name: string;
}

export interface Right {
  id: number;
  discriminator: string;
  opponent: boolean;
  imageIds: number[];
}

export interface Trademark extends Right {
  name: string;
}

export interface Patent extends Right {
  application: string;
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
