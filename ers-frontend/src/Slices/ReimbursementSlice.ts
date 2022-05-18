import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { IReimbursement, RType } from "../Interfaces/IReimbursement";

interface ReimbursementSliceState {
    loading: boolean,
    error: boolean,
    reimbursement?: IReimbursement
    reimbList?: IReimbursement[]
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
    'users/get',
    async(expenseInput: Expense, thunkAPI) => {
    try {
        const res = await axios.post(`http://localhost:8000/users/submit`, expenseInput);

        return {
            amount: 1300,
            description: "kfc",
            type: "FOOD"
        }
    } catch (error) {
        console.log(error);
    }
}
)

//Create the slice
export const UserSlice = createSlice({
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
            //state.user = action.payload;
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
export const { toggleError } = UserSlice.actions;

export default UserSlice.reducer;