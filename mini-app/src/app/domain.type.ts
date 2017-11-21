export interface TreeNode {
  children: TreeNode[];
}

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
  rights: Right[];
  trademarks: Trademark[];
  domainNames: DomainName[];
  patents: Patent[];
  designModels: DesignModel[];
  copyrights: Copyright[];
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
  domain: string;
  plaintiff: boolean;
  imageIds: string[];
}

export interface Trademark extends Right {
  name: string;
  type: string;
}

export interface DomainName extends Right {
  name: string;
}

export interface Patent extends Right {
  application: string;
}

export interface DesignModel extends Right {
  name: string;
  registration: string;
}

export interface Copyright extends Right {
    name: string;
    type: string;h
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
  level: string;
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
