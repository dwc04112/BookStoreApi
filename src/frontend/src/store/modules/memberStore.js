import axios from "axios";

const userData = () => {
    return {
        mid : void 0,
        nickName: void 0,
        fullName: void 0,
        phoneNum: void 0,
        profilePicture : void 0
    }
}

const memberStore = {
    state: {
        loginState : false,
        email: '',
        token: '',
        userData : userData(),
    },

    mutations: {
        loginData: function (state, data) {
            state.email = data.email
            state.token = data.token
            state.loginState = true
            console.log("after email? : ", state.email)
        },
        initData (state){
            state.loginState = false;
            state.email = void 0;
            state.token = void 0;
            state.userData = userData();
            console.log(state)
        },

        putProfile: function (state, data){
          state.userData.nickName = data.nickName
          state.userData.profilePicture = require('@/assets/profile_imgs/'+data.profilePicture)
          console.log(state.userData)
        },

        putUserInfo: function (state, data){
            state.userData.fullName = data.fullName
            state.userData.nickName = data.nickName
            state.userData.mid = data.mid
            state.userData.phoneNum = data.phoneNum
            state.userData.profilePicture = require('@/assets/profile_imgs/'+data.profilePicture)
        },
    },

    actions: {
        login ({commit, dispatch}, payload){
            commit('initData')
            let data = {};
            data.email = payload.email
            data.token = payload.token
            commit('loginData', data)

            dispatch('getUserInfo', data.email)
        },

        logout({commit}){
            axios.post("/logout")
                .then(response => {
                    commit('initData')
                    console.log(response.data+"로그아웃처리")
                })
                .catch(error =>{
                    console.log(error.response);
                })
        },

        getUserInfo: function ({commit}, payload) {
            let data = {};
            data.email = payload
            axios.post('/user/info', JSON.stringify(data), {
                headers: {
                    "Content-Type": `application/json`,
                },
            })
                .then((res) => {
                    console.log(res.data)
                    data.nickName = res.data.nickName
                    data.fullName = res.data.fullName
                    data.mid = res.data.mid
                    data.phoneNum = res.data.phoneNum
                    data.profilePicture = res.data.profilePicture
                    commit('putUserInfo', data)
                })
                .catch((error) => {
                    console.log(error.res)
                })
        },

/*
        updateProfile: function ({commit}, payload) {
            let data = {};
            data.nickName = payload.nickName;
            data.profilePicture = payload.profilePicture;
            commit('putProfile', data)
        }

 */



    },
}
export default memberStore;