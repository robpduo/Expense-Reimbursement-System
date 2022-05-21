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
    APPROVED,   //Starts at zero even if you assign the first one as 1
    DENIED,
    PENDING
}

export enum RType {
    LODGING,
    TRAVEL,
    FOOD,
    OTHER,
    BLANK
}