export interface Client {
  id: number;
  raisonSociale?: string;
  nom: string;
  telephone: string;
  email: string;
}

export interface ClientRequest {
  raisonSociale?: string;
  nom: string;
  telephone: string;
  email: string;
}

