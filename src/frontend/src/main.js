import Vue from 'vue'
import App from './App.vue';
import axios from "./plugins/axios";
import vuetify from './plugins/vuetify'

Vue.prototype.$eventBus = new Vue();
Vue.prototype.$axios = axios
// 다른 컴포넌트에서는 import 없이 this.$axios로 사용가능

Vue.config.productionTip = false

new Vue({
  vuetify,
  render: h => h(App)
}).$mount('#app')
