import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { IReimbursement, RType } from "../Interfaces/IReimbursement";

interface ReimbursementSliceState {
    loading: boolean,
    error: boolean,
    reimbursement?: IReimbursement
}

const initialReimbursementState: ReimbursementSliceState = {
    loading: false,
    error: false
}

type Expense = {
    amount: number,
    description: string,
    type: RType
}

export const submitExpense = createAsyncThunk(
    'user/reimbursement',
    async(expenseInput: Expense, thunkAPI) => {
    try {
        axios.defaults.withCredentials = true;
        console.log("A: ", expenseInput.amount);
        const res = await axios.post(`http://localhost:8000/reimbursements/submit`, expenseInput);
        return expenseInput;
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
            console.log(state.reimbursement);
            state.error = false;
            state.loading = false;
        });

        builder.addCase(submitExpense.rejected, (state, action) => {
            state.error = true;
            state.loading = false;
        });
    }
})

//If we had normal actions and reducers we would export them like this
export const { toggleError } = ReimburseSlice.actions;

export default ReimburseSlice.reducer;