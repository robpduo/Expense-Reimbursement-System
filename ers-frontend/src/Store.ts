import { configureStore } from "@reduxjs/toolkit";

//We would import our reducers from the slices we have to create
import userReducer from "./Slices/UserSlice";
import reimburseReducer from "./Slices/ReimbursementSlice";
import employeesReducer from "./Slices/EmployeesSlice";

export const userStore = configureStore({
    reducer: {
        user: userReducer,
        reimburser: reimburseReducer,
        employees: employeesReducer
    }
});

//We must export these two things to make our lives easier later
export type RootState = ReturnType<typeof userStore.getState>;
export type AppDispatch = typeof userStore.dispatch;