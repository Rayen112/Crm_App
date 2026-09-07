import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ClientService } from '../../../core/services/client.service';
import { ClientRequest } from '../../../core/models/client.model';

@Component({
  selector: 'app-client-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './client-form.component.html',
  styleUrl: './client-form.component.css',
})
export class ClientFormComponent implements OnInit {
  isEditMode = false;
  clientId: number | null = null;

  clientData: ClientRequest = {
    raisonSociale: '',
    nom: '',
    telephone: '',
    email: '',
  };

  isLoading = false;
  isSaving = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.clientId = Number(idParam);
      this.loadClient(this.clientId);
    }
  }

  loadClient(id: number): void {
    this.isLoading = true;
    this.clientService.getClientById(id).subscribe({
      next: (c) => {
        this.clientData = {
          raisonSociale: c.raisonSociale || '',
          nom: c.nom,
          telephone: c.telephone,
          email: c.email,
        };
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Impossible de charger les données du client.';
      },
    });
  }

  onSubmit(): void {
    if (!this.clientData.nom || !this.clientData.telephone || !this.clientData.email) {
      this.errorMessage = 'Veuillez remplir tous les champs obligatoires.';
      return;
    }

    this.isSaving = true;
    this.errorMessage = '';

    if (this.isEditMode && this.clientId) {
      this.clientService.updateClient(this.clientId, this.clientData).subscribe({
        next: () => {
          this.isSaving = false;
          this.router.navigate(['/clients']);
        },
        error: (err) => {
          this.isSaving = false;
          this.errorMessage = err.error?.message || 'Erreur lors de la mise à jour du client.';
        },
      });
    } else {
      this.clientService.createClient(this.clientData).subscribe({
        next: () => {
          this.isSaving = false;
          this.router.navigate(['/clients']);
        },
        error: (err) => {
          this.isSaving = false;
          this.errorMessage = err.error?.message || 'Erreur lors de la création du client.';
        },
      });
    }
  }
}
