import axios from 'axios';
import {store} from "@/store";
import router from "@/router";




// Add a request interceptor
axios.interceptors.request.use(function (config) {
    // Do something before request is sent
    if(document.URL.match("test3")){
        console.log("인증이 필요없는 url : " + document.URL)
        return config;
    }
    if(store.state.member.loginData.token==null){
        return config
    }


    if(config.url.match("kakao")){
        console.log("kakao Api Request")
        return config;
    }

    config.headers.Authorization = "Bearer "+store.state.member.loginData.token;
    //this.$router.go(0); //새로고침
    return config;
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
}, function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    // console.log("request "+error)
    console.log("response1 " + error)
    console.log("response "+ error.response.data.path)
    if(error.response.data.path === "/authenticate"){
        console.log("exception Login page")
        alert("로그인이 만료되어 로그아웃 처리 됩니다.")
    }
    else if(error.response.status===401) {
        store.dispatch('loginCheck_401').then(()=>{
            router.push({path: '/login'}).then(() => (alert("로그인이 필요한 페이지입니다.")))
        })
    }
    return Promise.reject(error);
});


export default axios;