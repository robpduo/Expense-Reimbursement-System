import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { IUser, Role } from "../Interfaces/IUser";
import { wrapper } from 'axios-cookiejar-support';
import { RType } from "../Interfaces/IReimbursement";


//Figure out our default state for this slice

interface UserSliceState {
    loading: boolean,
    error: boolean,
    user?: IUser,
    nullUser?: IUser,
    key: number,
}

const initialUserState: UserSliceState = {
    loading: false,
    error: false,
    key: 0
}

type Login = {
    username: string,
    password: string
}

type currentUser = {
    username: string
}

type updateInfo = {
    username: string,
    email: string,
    fName: string,
    lName: string,
    password: string,
    role?: Role | any
}

export const loginUser = createAsyncThunk(
    'user/login',
    async (credentials: Login, thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.post('http://localhost:8000/users/login', credentials);
            return {
                userId: res.data.userId,
                username: res.data.username,
                fName: res.data.fName,
                lName: res.data.lName,
                email: res.data.email,
                role: res.data.role
            }
        } catch (e) {
            return thunkAPI.rejectWithValue('something went wrong');
        }
    }
)

export const logoutUser = createAsyncThunk(
    'user/logout',
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.put('http://localhost:8000/users/logout');
            window.location.reload();
        } catch (e) {
        }
    }
)

export const newUser = createAsyncThunk(
    'user/get-user',
    async (register: updateInfo, thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.post('http://localhost:8000/users/get-user', register);

            if (res.data.username == null) {
                const createRes = await axios.post('http://localhost:8000/users/register', register);
                return {
                    key: 1 //new employee registered
                }
            }
            return {
                key: 2 //Username Exists
            }

        } catch (e) {
            return thunkAPI.rejectWithValue('something went wrong');
        }
    }
)

export const updateUser = createAsyncThunk(
    'user/update-info',
    async (accountInformation: updateInfo, thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.post('http://localhost:8000/users/update', accountInformation);
            return {
                userId: res.data.userId,
                username: res.data.username,
                fName: res.data.fName,
                lName: res.data.lName,
                email: res.data.email,
                role: res.data.role
            }
        } catch (e) {
            return thunkAPI.rejectWithValue('something went wrong');
        }
    }
)

//Create the slice
export const UserSlice = createSlice({
    name: "user",
    initialState: initialUserState,
    reducers: {
        toggleError: (state) => {
            state.error = !state.error;
        }
    },
    extraReducers: (builder) => {
        //This is where we would create our reducer logic
        builder.addCase(loginUser.pending, (state, action) => {
            state.loading = true;
        });
        builder.addCase(loginUser.fulfilled, (state, action) => {
            //The payload in this case, is the return from our asyncThunk from above
            state.user = action.payload;
            state.error = false;
            state.loading = false;
        });
        builder.addCase(loginUser.rejected, (state, action) => {
            state.error = true;
            state.loading = false;
        });

        builder.addCase(logoutUser.fulfilled, (state, action) => {
            //clear all user field
            state.user = state.nullUser;
            state.error = false;
            state.loading = false;
        });

        //This is where we would create our reducer logic
        builder.addCase(updateUser.pending, (state, action) => {
            state.loading = true;
        });
        builder.addCase(updateUser.fulfilled, (state, action) => {
            //The payload in this case, is the return from our asyncThunk from above
            state.user = action.payload;
            state.error = false;
            state.loading = false;
        });
        builder.addCase(updateUser.rejected, (state, action) => {
            state.error = true;
            state.loading = false;
        });

        builder.addCase(newUser.fulfilled, (state, action) => {
            state.key = action.payload.key;
            state.error = false;
            state.loading = false;
        });
    }
})

//If we had normal actions and reducers we would export them like this
export const { toggleError } = UserSlice.actions;

export default UserSlice.reducer;