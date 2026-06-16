import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8121/api',
  timeout: 30000,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

apiClient.interceptors.request.use(
  (config) => {
    const userInfo = localStorage.getItem('user_info') || sessionStorage.getItem('user_info');
    if (userInfo) {
      try {
        const parsed = JSON.parse(userInfo);
        if (parsed.token) {
          config.headers['Authorization'] = `Bearer ${parsed.token}`;
        }
      } catch (e) {
        // ignore parse error
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

apiClient.interceptors.response.use(
  (response) => {
    const res = response.data;
    if (res.code !== 0 && res.code !== 200) {
      console.error('请求失败:', res.message);
      return Promise.reject(new Error(res.message || '请求失败'));
    }
    return res;
  },
  (error) => {
    if (error.response) {
      if (error.response.status === 401) {
        localStorage.removeItem('user_info');
        sessionStorage.removeItem('user_info');
        console.error('登录已过期，请重新登录');
        window.location.href = '/login';
      } else {
        console.error('请求失败:', error.response.data?.message || '请求失败');
      }
    } else {
      console.error('网络错误，请检查网络连接');
    }
    return Promise.reject(error);
  }
);

export default apiClient;