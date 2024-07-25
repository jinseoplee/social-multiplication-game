import axios from "axios";
import store from "@/store";

const apiClient = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
  },
});

// 요청 인터셉터 추가
apiClient.interceptors.request.use(
  (config) => {
    // Vuex 스토어에서 토큰을 가져온다.
    const accessToken = store.state.accessToken;

    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

const get = (url, params = {}) => apiClient.get(url, { params });
const post = (url, data) => apiClient.post(url, data);
const put = (url, data) => apiClient.put(url, data);
const del = (url) => apiClient.delete(url);

export { get, post, put, del };
