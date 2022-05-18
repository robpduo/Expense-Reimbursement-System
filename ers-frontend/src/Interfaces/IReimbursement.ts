import { IUser } from "./IUser"

export interface IReimbursement {
    postId: number,
    postedDate: Date,
    content: string,
    postUser: IUser

    id: number, 
    amount: number,
    submittedDate: Date,
    resolvedDate: Date, 
    description: string, 
    author: IUser,
    resolver: IUser,
    status: Status,
    type: RType
}

export enum Status {
    APPROVED = 1,
    DENIED,
    PENDING
}

export enum RType {
    LODGING = 1,
    TRAVEL,
    FOOD,
    OTHER
}