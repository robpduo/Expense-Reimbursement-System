import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { stat } from "fs";
import { IReimbursement, RType, Status } from "../Interfaces/IReimbursement";
import { IUser } from "../Interfaces/IUser";

interface ReimbursementSliceState {
    loading: boolean,
    error: boolean,
    toggleChange: boolean,
    reimbursement?: IReimbursement,
    reimbursements?: IReimbursement[]
}

const initialReimbursementState: ReimbursementSliceState = {
    loading: false,
    error: false,
    toggleChange: false
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

        builder.addCase(viewPending.fulfilled, (state, action) => {
            //The payload in this case, is the return from our asyncThunk from above
            state.reimbursements = action.payload;
            state.error = false;
            state.loading = false;
        });

        builder.addCase(viewAllPending.fulfilled, (state, action) => {
            state.reimbursements = action.payload;
            state.error = false;
            state.loading = false;
        })

        builder.addCase(resolveRequest.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            state.toggleChange = !state.toggleChange;
        })
    }
})

//If we had normal actions and reducers we would export them like this
export const { toggleError } = ReimburseSlice.actions;

export default ReimburseSlice.reducer;