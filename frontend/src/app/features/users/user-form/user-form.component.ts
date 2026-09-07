import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UserService } from '../../../core/services/user.service';
import { UserRequest } from '../../../core/models/user.model';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.css',
})
export class UserFormComponent implements OnInit {
  isEditMode = false;
  userId: number | null = null;

  userData: UserRequest = {
    nom: '',
    prenom: '',
    email: '',
    login: '',
    password: '',
    telephone: '',
    roleId: 2, // Default: 2 = AGENT_CRM, 1 = ADMIN
  };

  isLoading = false;
  isSaving = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.userId = Number(idParam);
      this.loadUser(this.userId);
    }
  }

  loadUser(id: number): void {
    this.isLoading = true;
    this.userService.getUserById(id).subscribe({
      next: (u) => {
        this.userData = {
          nom: u.nom,
          prenom: u.prenom,
          email: u.email,
          login: u.login,
          password: '', // Non renvoyé par sécurité
          telephone: u.telephone,
          roleId: u.roleId || 2,
        };
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = "Impossible de charger les données de l'utilisateur.";
      },
    });
  }

  onSubmit(): void {
    if (!this.isEditMode && !this.userData.password) {
      this.errorMessage = 'Le mot de passe est obligatoire pour la création.';
      return;
    }

    this.isSaving = true;
    this.errorMessage = '';

    if (this.isEditMode && this.userId) {
      this.userService.updateUser(this.userId, this.userData).subscribe({
        next: () => {
          this.isSaving = false;
          this.router.navigate(['/users']);
        },
        error: (err) => {
          this.isSaving = false;
          this.errorMessage = err.error?.message || "Erreur lors de la mise à jour de l'utilisateur.";
        },
      });
    } else {
      this.userService.createUser(this.userData).subscribe({
        next: () => {
          this.isSaving = false;
          this.router.navigate(['/users']);
        },
        error: (err) => {
          this.isSaving = false;
          this.errorMessage = err.error?.message || "Erreur lors de la création de l'utilisateur.";
        },
      });
    }
  }
}
