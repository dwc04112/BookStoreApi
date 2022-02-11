import Vue from 'vue';
import Vuex from "vuex";
import memberStore from "@/store/modules/memberStore";
import createPersistedState from 'vuex-persistedstate'


Vue.use(Vuex);

export const store = new Vuex.Store({
    modules: {
        memberStore : memberStore
    },

    plugins: [createPersistedState({
        paths: ["memberStore"]
    })]

});