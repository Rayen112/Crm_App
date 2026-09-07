import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';

import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { TicketListComponent } from './features/tickets/ticket-list/ticket-list.component';
import { TicketFormComponent } from './features/tickets/ticket-form/ticket-form.component';
import { TicketDetailComponent } from './features/tickets/ticket-detail/ticket-detail.component';
import { UserListComponent } from './features/users/user-list/user-list.component';
import { UserFormComponent } from './features/users/user-form/user-form.component';
import { ClientListComponent } from './features/clients/client-list/client-list.component';
import { ClientFormComponent } from './features/clients/client-form/client-form.component';

export const routes: Routes = [
  // Public Auth routes
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Protected Dashboard
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },

  // Protected Tickets
  { path: 'tickets', component: TicketListComponent, canActivate: [authGuard] },
  { path: 'tickets/new', component: TicketFormComponent, canActivate: [authGuard] },
  { path: 'tickets/:id', component: TicketDetailComponent, canActivate: [authGuard] },
  { path: 'tickets/:id/edit', component: TicketFormComponent, canActivate: [authGuard] },

  // Protected Clients
  { path: 'clients', component: ClientListComponent, canActivate: [authGuard] },
  { path: 'clients/new', component: ClientFormComponent, canActivate: [authGuard] },
  { path: 'clients/:id/edit', component: ClientFormComponent, canActivate: [authGuard] },

  // Protected Users (Admin only)
  { path: 'users', component: UserListComponent, canActivate: [authGuard, adminGuard] },
  { path: 'users/new', component: UserFormComponent, canActivate: [authGuard, adminGuard] },
  { path: 'users/:id/edit', component: UserFormComponent, canActivate: [authGuard, adminGuard] },

  // Fallbacks
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: '**', redirectTo: 'dashboard' },
];
