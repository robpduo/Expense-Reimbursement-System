import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { IUser } from "../Interfaces/IUser";
import { wrapper } from 'axios-cookiejar-support';


//Figure out our default state for this slice

interface UserSliceState {
    loading: boolean,
    error: boolean,
    user?: IUser,
    nullUser?: IUser
}

const initialUserState: UserSliceState = {
    loading: false,
    error: false
}

type Login = {
    username: string,
    password: string
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
            axios.defaults.withCredentials = false;
            const res = await axios.put('http://localhost:8000/users/logout');
        } catch (e) {
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
            console.log(state.user);
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
    }
})

//If we had normal actions and reducers we would export them like this
export const { toggleError } = UserSlice.actions;

export default UserSlice.reducer;