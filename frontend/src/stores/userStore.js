import { defineStore } from "pinia";
import { apiLogin, apiRegister, apiDeleteProfile, apiUpdateProfile } from "../services/api.js";
import {
    clearTokenInStorage,
    clearUserInStorage,
    getTokenFromStorage,
    getUserFromStorage,
    storeTokenInStorage,
    storeUserInStorage
} from "../services/localData.js";
import { RequestData } from "@/models/util.js";

export const useUserStore = defineStore('userStore', {
    state: () => ({
        token: getTokenFromStorage(),
        user: getUserFromStorage(),
        requestData: new RequestData(false, null),
    }),
    actions: {
        async login(loginDto) {
            this.requestData.startLoading();

            try {
                let { user, token } = await apiLogin(loginDto);
                storeTokenInStorage(token);
                this.token = token;
                this.setUser(user);
            } catch (error) {
                this.requestData.setError(error);
            }

            this.requestData.stopLoading();
        },
        async register(registrationDto) {
            this.requestData.startLoading();

            try {
                let {user, token} = await apiRegister(registrationDto);
                storeTokenInStorage(token);
                this.token = token;
                this.setUser(user);
            } catch (error) {
                this.requestData.setError(error);
            }

            this.requestData.stopLoading();
        },
        setUser(user) {
            this.user = user;
            storeUserInStorage(user);
        },
        logout() {
            clearUserInStorage();
            clearTokenInStorage();
            this.$reset();
        },
        async deleteProfile() {
            this.requestData.startLoading();

            try {
                await apiDeleteProfile();
                this.logout();
            } catch (error) {
                this.requestData.setError(error);
            }

            this.requestData.stopLoading();
        },
        async updateProfile(updateProfileDto, specialties) {
            this.requestData.startLoading();

            try {
                await apiUpdateProfile(updateProfileDto);

                this.user.fullName = updateProfileDto.fullName;
                this.user.specialties = specialties;
                this.user.bio = updateProfileDto.bio;
                this.user.price = updateProfileDto.price;

                this.setUser(this.user);
            } catch (error) {
                this.requestData.setError(error);
            }


            this.requestData.stopLoading();
        }
    },
    getters: {
        isAuthenticated(state) {
            return state.user !== null;
        },
        prettyRole(state) {
            if (state.user.role === "SPECIALIST") {
                return "Специалист"
            }
            return "Клиент"
        }
    }
})
