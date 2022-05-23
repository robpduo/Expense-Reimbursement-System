import { IUser } from "./IUser"

export interface IReimbursement {
    id?: number,
    amount: number,
    submittedDate?: Date,
    resolvedDate?: Date, 
    description: string, 
    author?: IUser,
    resolver?: IUser,
    status?: Status,
    type: RType
}

export enum Status {
    APPROVED = 1,  
    DENIED = 2,
    PENDING = 3
}

export enum RType {
    BLANK = 0,
    LODGING = 1,
    TRAVEL = 2,
    FOOD = 3,
    OTHER = 4
}

