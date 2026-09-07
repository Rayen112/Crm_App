export type TicketPriority = 'FAIBLE' | 'MOYENNE' | 'HAUTE' | 'CRITIQUE';

export type TicketStatus = 'NOUVEAU' | 'EN_COURS' | 'EN_ATTENTE' | 'RESOLU' | 'FERME';

export interface Ticket {
  id: number;
  reference: string;
  objet: string;
  description: string;
  priorite: TicketPriority;
  statut: TicketStatus;
  dateCreation: string;
  dateModification?: string;
  clientId: number;
  clientName?: string;
  userId: number;
  userName?: string;
}

export interface TicketRequest {
  objet: string;
  description: string;
  priorite: TicketPriority;
  statut: TicketStatus;
  clientId: number;
  userId: number;
}
