import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { IUser } from "../Interfaces/IUser";

interface EmployeesSliceState {
    loading: boolean,
    error: boolean,
    employees?: IUser[]
}

const initialEmployeesState: EmployeesSliceState = {
    loading: false,
    error: false,
}

export const getAllEmployees = createAsyncThunk(
    'user/view-all-employees',
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get('http://localhost:8000/users/view-all-employees');
            return res.data;
        } catch (error) {
            console.log(error);
        }
    }
)

export const EmployeesSliceState = createSlice({
    name: "employees",
    initialState: initialEmployeesState,
    reducers: {
        toggleError: (state) => {
            state.error = !state.error;
        }
    },
    extraReducers: (builder) => {
        builder.addCase(getAllEmployees.fulfilled, (state, action) => {
            state.loading = false;
            state.error = false;
            state.employees = action.payload;
        })
    }
})

export const {toggleError} = EmployeesSliceState.actions;

export default EmployeesSliceState.reducer;