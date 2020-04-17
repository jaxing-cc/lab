import Vue from 'vue'
import App from './App.vue'
import router from './router'
import  ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/global.css'
import axios from 'axios'
//配置axios
axios.defaults.baseURL='http://localhost:8081/'//请求根路径

//加上token请求头 预处理
axios.interceptors.request.use(
  config => {
    if (sessionStorage.getItem('token')) {
      config.headers.token = sessionStorage.getItem('token');
    }

    return config;
  },
  error => {
    return Promise.reject(error);
  });
Vue.prototype.$http=axios;

Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small',zIndex: 3000})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
