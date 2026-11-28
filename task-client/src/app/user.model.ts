export interface User {
  id:number;
  username:string
}

export type UserRole = "EMPLOYEE" | "MANAGER" | "ADMIN";

export interface CreateUserRequest {
  username: string;
  password: string;
  email: string;
  role: UserRole;
}
