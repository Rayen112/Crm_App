import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { TicketService } from '../../../core/services/ticket.service';
import { ClientService } from '../../../core/services/client.service';
import { UserService } from '../../../core/services/user.service';
import { AuthService } from '../../../core/services/auth.service';
import { TicketPriority, TicketRequest, TicketStatus } from '../../../core/models/ticket.model';
import { Client } from '../../../core/models/client.model';
import { User } from '../../../core/models/user.model';

@Component({
  selector: 'app-ticket-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './ticket-form.component.html',
  styleUrl: './ticket-form.component.css',
})
export class TicketFormComponent implements OnInit {
  isEditMode = false;
  ticketId: number | null = null;
  ticketRef = '';

  ticketData: TicketRequest = {
    objet: '',
    description: '',
    priorite: 'MOYENNE',
    statut: 'NOUVEAU',
    clientId: 0,
    userId: 1, // default or assigned
  };

  clients: Client[] = [];
  users: User[] = [];

  isLoading = false;
  isSaving = false;
  errorMessage = '';

  readonly priorities: { value: TicketPriority; label: string }[] = [
    { value: 'FAIBLE', label: 'Faible' },
    { value: 'MOYENNE', label: 'Moyenne' },
    { value: 'HAUTE', label: 'Haute' },
    { value: 'CRITIQUE', label: 'Critique' },
  ];

  readonly statuses: { value: TicketStatus; label: string }[] = [
    { value: 'NOUVEAU', label: 'Nouveau' },
    { value: 'EN_COURS', label: 'En cours' },
    { value: 'EN_ATTENTE', label: 'En attente' },
    { value: 'RESOLU', label: 'Résolu' },
    { value: 'FERME', label: 'Fermé' },
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ticketService: TicketService,
    private clientService: ClientService,
    private userService: UserService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadClients();
    this.loadUsers();

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.ticketId = Number(idParam);
      this.loadTicket(this.ticketId);
    }
  }

  loadClients(): void {
    this.clientService.getAllClients().subscribe({
      next: (data) => {
        this.clients = data;
        if (!this.isEditMode && this.clients.length > 0) {
          this.ticketData.clientId = this.clients[0].id;
        }
      },
      error: () => {},
    });
  }

  loadUsers(): void {
    if (!this.authService.isAdmin()) {
      const myId = this.authService.currentUserId();
      if (myId) {
        this.ticketData.userId = myId;
      }
      return;
    }

    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
        if (!this.isEditMode && this.users.length > 0) {
          const currentLogin = this.authService.userLogin();
          const me = this.users.find((u) => u.login === currentLogin);
          this.ticketData.userId = me ? me.id : this.users[0].id;
        }
      },
      error: () => {
        const myId = this.authService.currentUserId();
        if (myId) {
          this.ticketData.userId = myId;
        }
      },
    });
  }

  loadTicket(id: number): void {
    this.isLoading = true;
    this.ticketService.getTicketById(id).subscribe({
      next: (t) => {
        this.ticketRef = t.reference;
        this.ticketData = {
          objet: t.objet,
          description: t.description,
          priorite: t.priorite,
          statut: t.statut,
          clientId: t.clientId,
          userId: t.userId,
        };
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Impossible de charger le ticket.';
      },
    });
  }

  onSubmit(): void {
    if (!this.ticketData.objet || !this.ticketData.description || !this.ticketData.clientId) {
      this.errorMessage = 'Veuillez remplir tous les champs obligatoires.';
      return;
    }

    this.isSaving = true;
    this.errorMessage = '';

    if (this.isEditMode && this.ticketId) {
      this.ticketService.updateTicket(this.ticketId, this.ticketData).subscribe({
        next: () => {
          this.isSaving = false;
          this.router.navigate(['/tickets']);
        },
        error: () => {
          this.isSaving = false;
          this.errorMessage = 'Erreur lors de la mise à jour du ticket.';
        },
      });
    } else {
      this.ticketService.createTicket(this.ticketData).subscribe({
        next: () => {
          this.isSaving = false;
          this.router.navigate(['/tickets']);
        },
        error: () => {
          this.isSaving = false;
          this.errorMessage = 'Erreur lors de la création du ticket.';
        },
      });
    }
  }
}

