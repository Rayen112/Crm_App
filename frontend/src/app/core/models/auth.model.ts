export interface LoginRequest {
  login: string;
  password: string;
}

export interface RegisterRequest {
  nom: string;
  prenom: string;
  email: string;
  login: string;
  password: string;
  telephone: string;
}

export interface LoginResponse {
  message: string;
  login: string;
  role: string;
  token: string;
}

export interface CurrentUser {
  login: string;
  role: string;
  token: string;
}
