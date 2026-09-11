import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CurrentUser, LoginRequest, LoginResponse, RegisterRequest } from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiUrl = `${environment.apiUrl}/auth`;
  private readonly USER_KEY = 'crm_current_user';

  private readonly currentUserSignal = signal<CurrentUser | null>(this.loadStoredUser());

  readonly currentUser = this.currentUserSignal.asReadonly();
  readonly isAuthenticated = computed(() => !!this.currentUserSignal());
  readonly isAdmin = computed(() => this.currentUserSignal()?.role === 'ADMIN');
  readonly userRole = computed(() => this.currentUserSignal()?.role ?? '');
  readonly userLogin = computed(() => this.currentUserSignal()?.login ?? '');
  readonly currentUserId = computed(() => this.currentUserSignal()?.id ?? null);

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap((res) => {
        const user: CurrentUser = {
          id: res.id,
          nom: res.nom,
          prenom: res.prenom,
          email: res.email,
          login: res.login,
          role: res.role,
          token: res.token,
        };
        this.saveUser(user);
      })
    );
  }

  register(data: RegisterRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/register`, data).pipe(
      tap((res) => {
        const user: CurrentUser = {
          id: res.id,
          nom: res.nom,
          prenom: res.prenom,
          email: res.email,
          login: res.login,
          role: res.role,
          token: res.token,
        };
        this.saveUser(user);
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.USER_KEY);
    this.currentUserSignal.set(null);
  }

  getToken(): string | null {
    return this.currentUserSignal()?.token ?? null;
  }

  private saveUser(user: CurrentUser): void {
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
    this.currentUserSignal.set(user);
  }

  private loadStoredUser(): CurrentUser | null {
    const raw = localStorage.getItem(this.USER_KEY);
    if (!raw) return null;
    try {
      return JSON.parse(raw) as CurrentUser;
    } catch {
      localStorage.removeItem(this.USER_KEY);
      return null;
    }
  }
}

