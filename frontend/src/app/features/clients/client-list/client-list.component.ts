import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ClientService } from '../../../core/services/client.service';
import { AuthService } from '../../../core/services/auth.service';
import { Client } from '../../../core/models/client.model';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './client-list.component.html',
  styleUrl: './client-list.component.css',
})
export class ClientListComponent implements OnInit {
  clients: Client[] = [];
  isLoading = true;
  errorMessage = '';
  successMessage = '';

  constructor(
    private clientService: ClientService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.clientService.getAllClients().subscribe({
      next: (data) => {
        this.clients = data;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Impossible de charger la liste des clients.';
      },
    });
  }

  deleteClient(client: Client): void {
    if (confirm(`Confirmez-vous la suppression du client "${client.raisonSociale || client.nom}" ?`)) {
      this.clientService.deleteClient(client.id).subscribe({
        next: () => {
          this.successMessage = `Client supprimé avec succès.`;
          this.loadClients();
          setTimeout(() => (this.successMessage = ''), 4000);
        },
        error: () => {
          this.errorMessage = 'Impossible de supprimer ce client (il possède peut-être des tickets associés).';
          setTimeout(() => (this.errorMessage = ''), 5000);
        },
      });
    }
  }
}
