import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Ticket, TicketPriority, TicketRequest, TicketStatus } from '../models/ticket.model';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private readonly apiUrl = `${environment.apiUrl}/tickets`;

  constructor(private http: HttpClient) {}

  getAllTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.apiUrl);
  }

  getTicketById(id: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.apiUrl}/${id}`);
  }

  createTicket(request: TicketRequest): Observable<Ticket> {
    return this.http.post<Ticket>(this.apiUrl, request);
  }

  updateTicket(id: number, request: TicketRequest): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.apiUrl}/${id}`, request);
  }

  deleteTicket(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getTicketsByStatus(status: TicketStatus): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}/status/${status}`);
  }

  getTicketsByPriority(priority: TicketPriority): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}/priority/${priority}`);
  }

  getTicketsByClient(clientId: number): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}/client/${clientId}`);
  }

  getTicketsByUser(userId: number): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}/user/${userId}`);
  }

  searchTickets(keyword: string): Observable<Ticket[]> {
    const params = new HttpParams().set('keyword', keyword);
    return this.http.get<Ticket[]>(`${this.apiUrl}/search`, { params });
  }

  filterByDate(dateDebut: string, dateFin: string): Observable<Ticket[]> {
    const params = new HttpParams()
      .set('dateDebut', dateDebut)
      .set('dateFin', dateFin);
    return this.http.get<Ticket[]>(`${this.apiUrl}/filter/date`, { params });
  }
}
