import axios from 'axios'
import { API_URL } from "./consts.js";
import { useUserStore } from "../stores/userStore.js";
import { User } from '@/models/base.js';

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

export async function apiLogin(loginDto) {
    const response = await noAuthInstance
        .post("/auth/login/", loginDto.toRepresentation())
        .catch(defaultApiExceptionHandler);

    let token = response.data["token"];
    let user = User.fromMap(response.data["user"]);
    return { user, token };
}

export async function apiRegister(registrationDto) {
    const response = await noAuthInstance
        .post("/auth/register/", registrationDto.toRepresentation())
        .catch(defaultApiExceptionHandler);

    let token = response.data["token"];
    let user = User.fromMap(response.data["user"]);
    return { user, token };
}

export async function apiFetchSpecialtyList() {
    const response = await instance.get("/specialty").catch(defaultApiExceptionHandler);

    const specialtyList = response.data;
    return specialtyList;
}

export async function apiFetchSpecialists(searchFilterDto) {
    const response = await instance.post("/specialist/find", searchFilterDto).catch(defaultApiExceptionHandler);
    return response.data;
}

export async function apiFetchSpecialist(id) {
    const response = await instance.get(`/specialist/${id}`).catch(defaultApiExceptionHandler);
    return response.data;
}

export async function apiFetchAppointments() {
    const response = await instance.get("/appointment/all").catch(defaultApiExceptionHandler);
    return response.data;
}

export async function apiCreateAppointment(createAppointmentDto) {
    await instance
        .post("/appointment/new", createAppointmentDto.toRepresentation())
        .catch(defaultApiExceptionHandler);
}

export async function apiApproveAppointment(appointmentId) {
    await instance
        .post(`/appointment/approve/${appointmentId}`)
        .catch(defaultApiExceptionHandler);
}

function defaultApiExceptionHandler(error) {
    if (error.response) {
        console.error(error.response);

        if (error.response.status === 401) {
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
