import { defineStore } from "pinia";
import { apiLogin, apiRegister } from "../services/api.js";
import {
    clearTokenInStorage,
    clearUserInStorage,
    getTokenFromStorage,
    getUserFromStorage,
    storeTokenInStorage,
    storeUserInStorage
} from "../services/localData.js";

export const useUserStore = defineStore('userStore', {
    state: () => ({
        token: getTokenFromStorage(),
        user: getUserFromStorage(),
        requestData: {
            loading: false,
            error: null
        }
    }),
    actions: {
        async login(email, password) {
            this.loading = true;
            this.clearError();

            try {
                const data = await apiLogin(email, password);
                storeTokenInStorage(data["token"]);
                this.token = data["token"];
                this.setUser(data["user"]);
            } catch (error) {
                this.setError(error);
            }

            this.loading = false;
        },
        async register(email, fullName, password, passwordRepeat) {
            this.loading = true;
            this.clearError();

            try {
                const data = await apiRegister(email, fullName, password, passwordRepeat);
                storeTokenInStorage(data["token"]);
                this.token = data["token"];
                this.setUser(data["user"]);
            } catch (error) {
                this.setError(error);
            }

            this.loading = false;
        },
        setUser(user) {
            this.user = user;
            storeUserInStorage(user);
        },
        setError(error) {
            this.requestData.error = error
        },
        clearError() {
            this.requestData.error = null
        },
        logout() {
            clearUserInStorage();
            clearTokenInStorage();
            this.$reset();
        }
    },
    getters: {
        isAuthenticated(state) {
            return state.user !== null;
        }
    }
})
