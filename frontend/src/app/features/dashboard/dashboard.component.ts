import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { DashboardService } from '../../core/services/dashboard.service';
import { AuthService } from '../../core/services/auth.service';
import { DashboardStats } from '../../core/models/dashboard.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  stats: DashboardStats = {
    totalTickets: 0,
    ticketsOuverts: 0,
    ticketsFermes: 0,
    totalUtilisateurs: 0,
  };

  isLoading = true;
  errorMessage = '';

  constructor(
    private dashboardService: DashboardService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.dashboardService.getStats().subscribe({
      next: (data) => {
        this.stats = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = 'Impossible de charger les statistiques du tableau de bord.';
      },
    });
  }
}
