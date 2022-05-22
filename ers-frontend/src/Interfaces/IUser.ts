export interface IUser {
    userId: number,
    username: string, 
    fName: string,
    lName: string,
    email: string,
    role: Role
}

export enum Role {
    MANAGER = 1,
    EMPLOYEE = 2
}