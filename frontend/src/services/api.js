import axios from 'axios'
import { API_URL } from "./consts.js";
import { useUserStore } from "../stores/userStore.js";

const instance = axios.create({
    baseURL: API_URL
})

const noAuthInstance = axios.create({
    baseURL: API_URL
})

instance.interceptors.request.use(function (config) {
    const userStore = useUserStore();
    if (userStore.token) {
        config.headers['Authorization'] = `Bearer ${userStore.token}`;
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});

export async function apiLogin(email, password) {
    const response = await noAuthInstance.post("/auth/login/", {
        username: email,
        password
    }).catch(defaultApiExceptionHandler);

    return response.data;
}

export async function apiRegister(email, fullName, password, passwordRepeat) {
    const response = await noAuthInstance.post("/auth/register/", {
        fullName,
        username: email,
        password,
        passwordRepeat
    }).catch(defaultApiExceptionHandler);

    return response.data;
}

function defaultApiExceptionHandler(error) {
    if (error.response) {
        console.error(error.response);

        if (error.response.status === 403) {
            let err = new Error("Error with authentication, please retry logging in")
            err.statusCode = error.response.status;
            throw err;
        }

        let err = new Error(error.response.data.detail || "Unknown error");
        err.statusCode = error.response.status;
        throw err;
    } else {
        console.error('Unknown error: ', error.message);
        throw new Error("Unknown error, please try again");
    }
}
