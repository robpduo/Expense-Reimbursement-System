import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { stat } from "fs";
import { useState } from "react";
import { ViewAllResolved } from "../Components/ViewAllResolved/ViewAllResolved";
import { IReimbursement, RType, Status } from "../Interfaces/IReimbursement";
import { IUser } from "../Interfaces/IUser";

let toRemove: number; //used to determine which reimbursement id should be deleted from the array so that the page updates correctly 
let pageSource: string; //used to determine if approve and delete icons will render, otherwise icons will render on all pages

/*filter functions*/
let filterType: RType;
let filterId: number;
let filterAuthor: number;

interface ReimbursementSliceState {
    loading: boolean,
    error: boolean,
    reimbursement?: IReimbursement,
    reimbursements?: IReimbursement[],
    source: string
}

const initialReimbursementState: ReimbursementSliceState = {
    loading: false,
    error: false,
    source: ""
}

type Expense = {
    amount: number,
    description: string,
    type: RType
}

type Request = {
    id?: number,
    status: Status
}

export const submitExpense = createAsyncThunk(
    'user/reimbursement',
    async (expenseInput: Expense, thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.post(`http://localhost:8000/reimbursements/submit`, expenseInput);

            return expenseInput;
        } catch (error) {
            console.log(error);
        }
    }
)

export const viewPending = createAsyncThunk(
    'user/pending',
    async (thunkAPI) => {

        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get(`http://localhost:8000/reimbursements/view-pending`);
            return (res.data);
        } catch (error) {
            console.log(error);
        }
    }
)

export const viewAllPending = createAsyncThunk(
    'user/view-all-pending',
    async (thunkAPI) => {

        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get(`http://localhost:8000/reimbursements/view-all-pending`);
            return (res.data);
        } catch (error) {
            console.log(error);
        }
    }
)

export const resolveRequest = createAsyncThunk(
    'user/update',
    async (decision: Request, thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.put(`http://localhost:8000/reimbursements/update`, decision);
        } catch (error) {
            console.log(error);
        }
    }
)

export const modifyToRemove = createAsyncThunk(
    'user/toRemove',
    function (reimburseId: RType) {
        toRemove = reimburseId;
    }
)

export const modifySource = createAsyncThunk(
    'user/source',
    function (source: string) {
        return (source)
    }
)

export const viewPastTickets = createAsyncThunk(
    'user/view-past-tickets',
    async (thunkAPI) => {

        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get(`http://localhost:8000/reimbursements/view-past`);
            return (res.data);
        } catch (error) {
            console.log(error);
        }
    }
)


export const viewAllResolved = createAsyncThunk(
    'user/view-all-resolved',
    async (thunkAPI) => {

        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get(`http://localhost:8000/reimbursements/view-all-resolved`);
            return (res.data);
        } catch (error) {
            console.log(error);
        }
    }
)

export const modFilterType = createAsyncThunk(
    'user/filterByType',
    function (type: number) {
        filterType = type;
    }
)

export const modFilterId = createAsyncThunk(
    'user/filterById',
    function (id: number) {
        filterId = id;
    }
)

export const modAuthorId = createAsyncThunk(
    'user/filterByAuthor',
    function (author: number) {
        filterAuthor = author;
    }
)
//Create the slice
export const ReimburseSlice = createSlice({
    name: "reimbursement",
    initialState: initialReimbursementState,
    reducers: {
        toggleError: (state) => {
            state.error = !state.error;
        }
    },
    extraReducers: (builder) => {
        //This is where we would create our reducer logic
        builder.addCase(submitExpense.pending, (state, action) => {
            state.loading = true;
        });

        builder.addCase(submitExpense.fulfilled, (state, action) => {
            //The payload in this case, is the return from our asyncThunk from above
            state.reimbursement = action.payload;
            state.error = false;
            state.loading = false;
        });

        builder.addCase(submitExpense.rejected, (state, action) => {
            state.error = true;
            state.loading = false;
        });
        //End of Submit Expense

        builder.addCase(viewPending.fulfilled, (state, action) => {
            //The payload in this case, is the return from our asyncThunk from above
            state.reimbursements = action.payload;
            state.error = false;
            state.loading = false;
        });
        //End of View Pending

        builder.addCase(viewAllPending.fulfilled, (state, action) => {
            state.reimbursements = action.payload;
            state.error = false;
            state.loading = false;
        });
        //End of View All Pending

        builder.addCase(resolveRequest.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
        });

        builder.addCase(modifyToRemove.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            state.reimbursements = state.reimbursements?.filter((stillPending) => stillPending.id !== toRemove);
        });
        //End of Resolve Request

        builder.addCase(viewPastTickets.fulfilled, (state, action) => {
            state.reimbursements = action.payload;
            state.error = false;
            state.loading = false;
        });

        builder.addCase(viewPastTickets.rejected, (state, action) => {
            state.error = true;
            state.loading = false;
        });

        builder.addCase(viewPastTickets.pending, (state, action) => {
            state.loading = true;
        });
        //End of Past Tickets

        builder.addCase(viewAllResolved.fulfilled, (state, action) => {
            state.reimbursements = action.payload;
            state.error = false;
            state.loading = false;
        });
        //End of View All Resolved

        builder.addCase(modifySource.fulfilled, (state, action) => {
            //The payload in this case, is the return from our asyncThunk from above
            state.source = action.payload;
            state.error = false;
            state.loading = false;
        });
        //End of View All Resolved

        builder.addCase(modFilterType.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            let convertEnum: any;
            let tempList: any | IReimbursement[] = new Array();
            let counter: number = 0;
            let size: any = state.reimbursements?.length;

            for (let i: number = 0; i < size; i++) {
                convertEnum = state.reimbursements?.at(i)?.type.valueOf();
                if (convertEnum == RType[filterType]) {
                    tempList[counter] = state.reimbursements?.at(i);
                    counter++;
                }
            }

            state.reimbursements = tempList;
        });

        builder.addCase(modFilterId.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            state.reimbursements = state.reimbursements?.filter((filter) => filter.id === filterId);
        });

        builder.addCase(modAuthorId.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            state.reimbursements = state.reimbursements?.filter((filter) => filter.author?.userId === filterAuthor);
        });
    }
})

// //If we had normal actions and reducers we would export them like this
export const { toggleError } = ReimburseSlice.actions;
export default ReimburseSlice.reducer;