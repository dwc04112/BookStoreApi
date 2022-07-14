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
            state.userData.profilePicture = require('@/../../../profile_img/'+data.profilePicture)
            console.log(state.userData)
        },
        putUserSimpleInfo: function (state, data){
            state.userData.fullName = data.fullName
            state.userData.nickName = data.nickName
            state.userData.mid = data.mid
            state.userData.phoneNum = data.phoneNum
            state.userData.userRule = data.userRule
            console.log("no profilePic : " + state)
        },

        putUserInfo: function (state, data){
            state.userData.fullName = data.fullName
            state.userData.nickName = data.nickName
            state.userData.mid = data.mid
            state.userData.phoneNum = data.phoneNum
            state.userData.profilePicture = require('@/../../../profile_img/'+data.profilePicture)
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
                }, 500)
            }).then(()=> dispatch('getUserInfo', data.email))
        },
/*
        logout({commit}){
            console.log("logout page")
            return new Promise((resolve) => {
                setTimeout(() => {
                    axios.post("/logout")
                        .then(() => {
                           console.log("success logout")
                        }).catch(error =>{
                            console.log("error : " + error.response);
                        }).then(()=> commit('initData'))
                        resolve()
                }, 1000)
            })
        },

 */
        logout({commit}){
            return new Promise((resolve) => {
                setTimeout(() => {
                    commit('initData')
                    resolve()
                }, 500)
            }).then(()=> console.log("success logout :: member Store"))
        },


        getUserInfo: function ({commit}, payload) {

            return new Promise((resolve) => {
                setTimeout(() => {

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
                        data.userRule = res.data.userRule
                        data.profilePicture = res.data.profilePicture
                    }).catch((error) => {
                        console.log(error.res)
                    }).then(()=>{
                        if(data.profilePicture==null|| data.profilePicture===""){
                            commit('putUserSimpleInfo', data);
                        }else{
                            commit('putUserInfo', data);
                        }
                    })
                    resolve()
                }, 1000)
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
                }, 2000)
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