


let frameUtil = {}


frameUtil.getCurrentLoginUserInfo = (store) => {
  window.addEventListener('message',  (e) => {
      if (typeof(e.data.loadCurrentLoginUserInfo)  != 'undefined') {
        store.commit('loadCurrentLoginUserInfo',e.data.loadCurrentLoginUserInfo) 
      }
    },
    false
  )
}


export default frameUtil
