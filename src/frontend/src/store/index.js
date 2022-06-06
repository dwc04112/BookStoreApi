import Vue from 'vue';
import Vuex from "vuex";
import memberStore from "@/store/modules/memberStore";
import createPersistedState from 'vuex-persistedstate'
import toOrderStore from "@/store/modules/toOrderStore";


Vue.use(Vuex);

export const store = new Vuex.Store({
    modules: {
        memberStore : memberStore,
        toOrderStore : toOrderStore,
    },

    plugins: [createPersistedState({
        paths: ["memberStore","toOrderStore"]
    })]

});