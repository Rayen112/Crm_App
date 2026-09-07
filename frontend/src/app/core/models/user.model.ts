export interface User {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  login: string;
  telephone: string;
  roleId: number;
  roleName: string;
}

export interface UserRequest {
  nom: string;
  prenom: string;
  email: string;
  login: string;
  password?: string;
  telephone: string;
  roleId: number;
}
