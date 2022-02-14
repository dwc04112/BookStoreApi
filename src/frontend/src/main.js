import Vue from "vue";
import App from './App.vue';
import axios from "./plugins/axios";
import vuetify from './plugins/vuetify';
import router from './router'
import "@/plugins/fontAwesomeIcon"


Vue.prototype.$eventBus = new Vue();
Vue.prototype.$axios = axios
// 다른 컴포넌트에서는 import 없이 this.$axios로 사용가능


Vue.config.productionTip = false

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
