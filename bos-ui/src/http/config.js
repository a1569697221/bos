// 对axios来进行初始化的参数配置
export default {
  method: 'get',
  // 基础url前缀
  // baseURL: 'http://localhost:8080/',
  baseURL: 'http://localhost:8888/bos',
  // 请求头信息
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  },
  // 参数
  data: {},
  // 设置超时时间
  timeout: 10000,
  // 携带凭证
  withCredentials: true,
  // 返回数据类型
  responseType: 'json'
}
