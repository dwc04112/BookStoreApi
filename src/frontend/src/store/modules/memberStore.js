import axios from "axios";


const memberStore = {
    state: {
        email: '',
        token: '',
    },

    mutations: {
        login: function (state, data) {
            state.email = data.email
            state.token = data.token
            console.log("after email? : ", state.email)
        },
        resetState: function (state){
            console.log(state)
        }
    },

    actions: {
        login ({commit}, payload){
            console.log("store :: "+payload)
            let data = {};
            data.email = JSON.parse(atob(payload.token.split('.')[1])).jti;
            data.token = payload.token
            commit('login', data)

        },

        logout({commit}){
            axios.post("/logout")
                .then(response => {
                    console.log(response.data + "로그아웃 처리");
                    commit('resetState')
                })
                .catch(error =>{
                    console.log(error.response);
                })
        },


    },
}
export default memberStore;