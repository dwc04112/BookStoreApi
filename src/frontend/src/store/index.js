import Vue from 'vue';
import Vuex from "vuex";
import createPersistedState from 'vuex-persistedstate'
import toOrderStore from "@/store/modules/toOrderStore";
import member from "@/store/modules/member";


Vue.use(Vuex);

export const store = new Vuex.Store({
    modules: {
        member : member,
        toOrderStore : toOrderStore,
    },

    plugins: [createPersistedState({
        paths: ["member","toOrderStore"]
    })]

});