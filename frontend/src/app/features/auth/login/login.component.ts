import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginData = {
    login: '',
    password: '',
  };

  showPassword = false;
  isLoading = false;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/dashboard']);
    }
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit(): void {
    if (!this.loginData.login || !this.loginData.password) {
      this.errorMessage = 'Veuillez saisir votre identifiant/email et votre mot de passe.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login(this.loginData).subscribe({
      next: () => {
        this.isLoading = false;
        // Redirection vers la liste des tickets comme demandé dans le CDC (Page 2)
        this.router.navigate(['/tickets']);
      },
      error: (err) => {
        this.isLoading = false;
        if (err.status === 401 || err.status === 403) {
          this.errorMessage = 'Identifiant ou mot de passe incorrect.';
        } else if (err.status === 404) {
          this.errorMessage = 'Utilisateur non trouvé.';
        } else {
          this.errorMessage = 'Erreur de connexion au serveur. Veuillez vérifier que le backend est démarré.';
        }
      },
    });
  }
}
