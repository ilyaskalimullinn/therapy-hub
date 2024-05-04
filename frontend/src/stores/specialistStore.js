import { defineStore } from "pinia";
import { RequestData } from "@/models/util.js";
import { apiFetchSpecialists } from "@/services/api";

export const useSpecialistStore = defineStore('specialistStore', {
    state: () => ({
        specialists: [],
        requestData: new RequestData(false, null),
    }),
    actions: {
        async fetchSpecialists(searchFilterDto) {
            this.requestData.startLoading();

            try {
                this.specialists = await apiFetchSpecialists(searchFilterDto);
            } catch (error) {
                this.requestData.setError(error);
            }

            this.requestData.stopLoading();
        }
    }
})
