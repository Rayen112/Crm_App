import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { UserService } from '../../../core/services/user.service';
import { User } from '../../../core/models/user.model';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css',
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  isLoading = true;
  errorMessage = '';
  successMessage = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Impossible de charger la liste des utilisateurs.';
      },
    });
  }

  deleteUser(user: User): void {
    if (confirm(`Êtes-vous certain de vouloir supprimer l'utilisateur "${user.prenom} ${user.nom}" (${user.login}) ?`)) {
      this.userService.deleteUser(user.id).subscribe({
        next: () => {
          this.successMessage = `Utilisateur ${user.login} supprimé avec succès.`;
          this.loadUsers();
          setTimeout(() => (this.successMessage = ''), 4000);
        },
        error: (err) => {
          this.errorMessage = err.error?.message || "Erreur lors de la suppression de l'utilisateur.";
          setTimeout(() => (this.errorMessage = ''), 5000);
        },
      });
    }
  }
}
