import Vue from 'vue'
import Layout from './components/layout/Layout.vue'
import iView from 'view-design'
import 'view-design/dist/styles/iview.css'
import  './assets/css/bin/bin.css'
import utils from './assets/js/utils/utils'
import apiPath from './assets/js/constant/ApiPath'
import routerConfig from './assets/js/router/routerConfig'
import vuexConfig from './assets/js/vuex/vuexConfig'

Vue.use(iView)



Vue.config.productionTip = false
Vue.prototype.utils = utils
Vue.prototype.API_PTAH = apiPath


const router = routerConfig.initRouterGenerate
const vuex = vuexConfig.initsStoreInstance



new Vue({
  router: router,
  store: vuex,
  render: h => h(Layout)
}).$mount('#app')
