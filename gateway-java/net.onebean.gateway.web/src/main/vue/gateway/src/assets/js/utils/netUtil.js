import axios from 'axios'
import { Message } from 'view-design'

let cancel, promiseArr = {}
const CancelToken = axios.CancelToken

//请求拦截器
axios.interceptors.request.use(
  config => {
    //发起请求时，取消掉当前正在进行的相同请求
    if (promiseArr[config.url]) {
      promiseArr[config.url]('cancel')
      promiseArr[config.url] = cancel
    } else {
      promiseArr[config.url] = cancel
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

//响应拦截器即异常处理
axios.interceptors.response.use(
  response => {
    return response
  },
  err => {
    if (err && err.response) {
      switch (err.response.status) {
        case 400:
          err.message = '错误请求'
          break
        case 401:
          err.message = '未授权，请重新登录'
          break
        case 403:
          err.message = '拒绝访问'
          break
        case 404:
          err.message = '请求错误,未找到该资源'
          break
        case 405:
          err.message = '请求方法未允许'
          break
        case 408:
          err.message = '请求超时'
          break
        case 500:
          err.message = '服务器端出错'
          break
        case 501:
          err.message = '网络未实现'
          break
        case 502:
          err.message = '网络错误'
          break
        case 503:
          err.message = '服务不可用'
          break
        case 504:
          err.message = '网络超时'
          break
        case 505:
          err.message = 'http版本不支持该请求'
          break
        default:
          err.message = `连接错误${err.response.status}`
      }
    } else {
      if (err.message != 'cancel') {
        err.message = '连接到服务器失败'
      }

    }
    if (err.message != 'cancel') {
      Message.error(err.message)
    }
    return Promise.resolve(err.response)
  }
)

axios.defaults.baseURL = process.env.VUE_APP_API_ADDRESS
//设置默认请求头

axios.defaults.headers = {
  'Content-Type': 'application/json',
  'Accept': 'application/json'
}
axios.defaults.timeout = 50000

export default {
  postAsync: async function (url, param) {
    const response = await axios({
      method: 'post',
      url,
      data: param,
      cancelToken: new CancelToken(c => {
        cancel = c
      })
    })
    if (typeof (response) == 'undefined') {
      console.log('response is undefined,may be the request is cancelled')
      return
    }
    if (response.data.errCode != '0') {
      if (typeof (response.data.errCode) != 'undefined') {
        Message.error(`请求失败 错误码 ${response.data.errCode} 错误信息: ${response.data.errMsg}`)
      }
    }
    return response.data.data
  },
  post: function (url, param, callback, errCallBack) {
    const hedaer = {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    }
    axios({
      headers: hedaer,
      method: 'post',
      url,
      data: param,
      cancelToken: new CancelToken(c => {
        cancel = c
      })
    }).then(response => {
      if (typeof (response) == 'undefined') {
        console.log('response is undefined,may be the request is cancelled')
        return
      }
      if (response.data.errCode === '0') {
        callback(response)
      } else {
        if (typeof (response.data.errCode) != 'undefined') {
          Message.error(`请求失败 错误码 ${response.data.errCode} 错误信息: ${response.data.errMsg}`)
        }
        if (typeof errCallBack != 'undefined') {
          errCallBack(response)
        }
      }
    })
  }
}
