import axios from "axios";

const userData = () => {
    return {
        mid : void 0,
        nickName: void 0,
        fullName: void 0,
        phoneNum: void 0,
        profilePicture : void 0,
        userRule : void 0,
    }
}

const loginData = () => {
    return {
        loginState: false,
        email: void 0,
        token: void 0,
    }
}

const member = {
    state: {
        loginData: loginData(),
        userData: userData(),
    },

    mutations: {
        loginData: function (state, data) {
            state.loginData.email = data.email
            state.loginData.token = data.token
            state.loginData.loginState = true
            console.log("log State : ", state.loginData)
        },


        initData (state){
            state.userData = userData();
            state.loginData = loginData();
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
            state.userData.userRule = data.userRule
            console.log("putUserInfo : " + state)
        },
    },

    actions: {

        login ({commit, dispatch}, payload){
            let data = {};
            data.email = payload.email
            data.token = payload.token
            return new Promise((resolve) => {
                setTimeout(() => {
                    commit('loginData', data);
                    resolve()
                }, 1000)
            }).then(()=> dispatch('getUserInfo', data.email))
        },

        logout({commit}){
            axios.post("/logout")
                .then(() => {
                    return new Promise((resolve) => {
                        setTimeout(() => {
                            commit('initData');
                            resolve()
                        }, 1000)
                    })
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
                    data.userRule = res.data.userRule
                    return new Promise((resolve) => {
                        setTimeout(() => {
                            commit('putUserInfo', data);
                            resolve()
                        }, 1000)
                    })
                })
                .catch((error) => {
                    console.log(error.res)
                })
        },

        updateProfile: function ({commit}, payload) {
            let data = {};
            data.nickName = payload.nickName;
            data.profilePicture = payload.profilePicture;

            return new Promise((resolve) => {
                setTimeout(() => {
                    commit('putProfile', data);
                    resolve()
                }, 1000)
            })
        },

        loginCheck_401: function ({commit}){
                return new Promise((resolve) => {
                    setTimeout(() => {
                        commit('initData');
                        resolve()
                    }, 1000)
                }
            )
        },



    },
}


export default member;