import axios from "axios";


const memberStore = {
    state: {
        mid : '',
        email: '',
        token: '',
        nickName: '',
        fullName: '',
    },

    mutations: {
        loginData: function (state, data) {
            state.email = data.email
            state.token = data.token
            console.log("after email? : ", state.email)
        },
        resetState: function (state){
            console.log(state)
        },
        putUserInfo: function (state, data){
            state.fullName = data.fullName
            state.nickName = data.nickName
            state.mid = data.mid
            console.log(state)
        },

    },

    actions: {
        login ({commit, dispatch}, payload){
            console.log("store :: "+payload)
            let data = {};
            data.email = JSON.parse(atob(payload.token.split('.')[1])).jti;
            data.token = payload.token
            commit('loginData', data)
            dispatch('getUserInfo', data.email)
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

        getUserInfo({commit}, context){
            let data={};
            data.email = context
            axios.post('/user/info', JSON.stringify({"email":data.email}), {
                headers: {
                    "Content-Type": `application/json`,
                },
            })
                .then((res) => {
                    console.log(res.data)
                    data.nickName = res.data.nickName
                    data.fullName = res.data.fullName
                    data.mid = res.data.mid
                    commit('putUserInfo', data)
                })
                .catch((error)=>{
                    console.log(error.res)
                })
        }

    },
}
export default memberStore;