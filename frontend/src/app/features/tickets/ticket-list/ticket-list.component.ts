import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { TicketService } from '../../../core/services/ticket.service';
import { ClientService } from '../../../core/services/client.service';
import { AuthService } from '../../../core/services/auth.service';
import { Ticket, TicketPriority, TicketStatus } from '../../../core/models/ticket.model';
import { Client } from '../../../core/models/client.model';

@Component({
  selector: 'app-ticket-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './ticket-list.component.html',
  styleUrl: './ticket-list.component.css',
})
export class TicketListComponent implements OnInit {
  tickets: Ticket[] = [];
  clients: Client[] = [];
  isLoading = true;
  errorMessage = '';
  successMessage = '';

  // Filter & Search state
  searchKeyword = '';
  filterStatus = '';
  filterPriority = '';
  filterClientId = '';
  filterDateDebut = '';
  filterDateFin = '';

  readonly statuses: { value: TicketStatus; label: string }[] = [
    { value: 'NOUVEAU', label: 'Nouveau' },
    { value: 'EN_COURS', label: 'En cours' },
    { value: 'EN_ATTENTE', label: 'En attente' },
    { value: 'RESOLU', label: 'Résolu' },
    { value: 'FERME', label: 'Fermé' },
  ];

  readonly priorities: { value: TicketPriority; label: string }[] = [
    { value: 'FAIBLE', label: 'Faible' },
    { value: 'MOYENNE', label: 'Moyenne' },
    { value: 'HAUTE', label: 'Haute' },
    { value: 'CRITIQUE', label: 'Critique' },
  ];

  constructor(
    private ticketService: TicketService,
    private clientService: ClientService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadTickets();
    this.loadClients();
  }

  loadTickets(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.ticketService.getAllTickets().subscribe({
      next: (data) => {
        this.tickets = data;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Impossible de charger la liste des tickets.';
      },
    });
  }

  loadClients(): void {
    this.clientService.getAllClients().subscribe({
      next: (data) => (this.clients = data),
      error: () => {},
    });
  }

  onSearch(): void {
    if (!this.searchKeyword.trim()) {
      this.loadTickets();
      return;
    }

    this.isLoading = true;
    this.ticketService.searchTickets(this.searchKeyword.trim()).subscribe({
      next: (data) => {
        this.tickets = data;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Erreur lors de la recherche des tickets.';
      },
    });
  }

  onFilterChange(): void {
    // Si dates renseignées
    if (this.filterDateDebut && this.filterDateFin) {
      this.isLoading = true;
      this.ticketService.filterByDate(this.filterDateDebut, this.filterDateFin).subscribe({
        next: (data) => {
          this.tickets = data;
          this.isLoading = false;
        },
        error: () => {
          this.isLoading = false;
          this.errorMessage = 'Erreur lors du filtrage par date.';
        },
      });
      return;
    }

    // Filtre par statut
    if (this.filterStatus) {
      this.isLoading = true;
      this.ticketService.getTicketsByStatus(this.filterStatus as TicketStatus).subscribe({
        next: (data) => {
          this.tickets = data;
          this.isLoading = false;
        },
        error: () => {
          this.isLoading = false;
          this.errorMessage = 'Erreur lors du filtrage par statut.';
        },
      });
      return;
    }

    // Filtre par priorité
    if (this.filterPriority) {
      this.isLoading = true;
      this.ticketService.getTicketsByPriority(this.filterPriority as TicketPriority).subscribe({
        next: (data) => {
          this.tickets = data;
          this.isLoading = false;
        },
        error: () => {
          this.isLoading = false;
          this.errorMessage = 'Erreur lors du filtrage par priorité.';
        },
      });
      return;
    }

    // Filtre par client
    if (this.filterClientId) {
      this.isLoading = true;
      this.ticketService.getTicketsByClient(Number(this.filterClientId)).subscribe({
        next: (data) => {
          this.tickets = data;
          this.isLoading = false;
        },
        error: () => {
          this.isLoading = false;
          this.errorMessage = 'Erreur lors du filtrage par client.';
        },
      });
      return;
    }

    // Aucun filtre actif
    this.loadTickets();
  }

  resetFilters(): void {
    this.searchKeyword = '';
    this.filterStatus = '';
    this.filterPriority = '';
    this.filterClientId = '';
    this.filterDateDebut = '';
    this.filterDateFin = '';
    this.loadTickets();
  }

  deleteTicket(ticket: Ticket): void {
    if (confirm(`Êtes-vous sûr de vouloir supprimer le ticket ${ticket.reference} ?`)) {
      this.ticketService.deleteTicket(ticket.id).subscribe({
        next: () => {
          this.successMessage = `Ticket ${ticket.reference} supprimé avec succès.`;
          this.loadTickets();
          setTimeout(() => (this.successMessage = ''), 4000);
        },
        error: () => {
          this.errorMessage = `Impossible de supprimer le ticket ${ticket.reference}.`;
          setTimeout(() => (this.errorMessage = ''), 4000);
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
