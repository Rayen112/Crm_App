import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { TicketService } from '../../../core/services/ticket.service';
import { ClientService } from '../../../core/services/client.service';
import { AuthService } from '../../../core/services/auth.service';
import { Ticket, TicketPriority, TicketStatus } from '../../../core/models/ticket.model';
import { Client } from '../../../core/models/client.model';

@Component({
  selector: 'app-ticket-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './ticket-detail.component.html',
  styleUrl: './ticket-detail.component.css',
})
export class TicketDetailComponent implements OnInit {
  ticket: Ticket | null = null;
  client: Client | null = null;
  isLoading = true;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ticketService: TicketService,
    private clientService: ClientService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.loadTicket(Number(idParam));
    }
  }

  loadTicket(id: number): void {
    this.isLoading = true;
    this.ticketService.getTicketById(id).subscribe({
      next: (t) => {
        this.ticket = t;
        this.isLoading = false;
        if (t.clientId) {
          this.clientService.getClientById(t.clientId).subscribe({
            next: (c) => (this.client = c),
            error: () => {},
          });
        }
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Impossible de trouver le ticket demandé.';
      },
    });
  }

  changeStatus(newStatus: TicketStatus): void {
    if (!this.ticket) return;

    const updatePayload = {
      objet: this.ticket.objet,
      description: this.ticket.description,
      priorite: this.ticket.priorite,
      statut: newStatus,
      clientId: this.ticket.clientId,
      userId: this.ticket.userId,
    };

    this.ticketService.updateTicket(this.ticket.id, updatePayload).subscribe({
      next: (updated) => {
        this.ticket = updated;
      },
      error: () => {
        alert('Erreur lors du changement de statut.');
      },
    });
  }

  deleteTicket(): void {
    if (!this.ticket) return;
    if (confirm(`Confirmez-vous la suppression du ticket ${this.ticket.reference} ?`)) {
      this.ticketService.deleteTicket(this.ticket.id).subscribe({
        next: () => {
          this.router.navigate(['/tickets']);
        },
        error: () => {
          alert('Erreur lors de la suppression.');
        },
      });
    }
  }

  formatPriority(priority: TicketPriority): string {
    const map: Record<TicketPriority, string> = {
      FAIBLE: 'Faible',
      MOYENNE: 'Moyenne',
      HAUTE: 'Haute',
      CRITIQUE: 'Critique',
    };
    return map[priority] || priority;
  }

  formatStatus(status: TicketStatus): string {
    const map: Record<TicketStatus, string> = {
      NOUVEAU: 'Nouveau',
      EN_COURS: 'En cours',
      EN_ATTENTE: 'En attente',
      RESOLU: 'Résolu',
      FERME: 'Fermé',
    };
    return map[status] || status;
  }
}
