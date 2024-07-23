import axios from "axios";

const apiClient = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
  },
});

const get = (url, params = {}) => apiClient.get(url, { params });
const post = (url, data) => apiClient.post(url, data);
const put = (url, data) => apiClient.put(url, data);
const del = (url) => apiClient.delete(url);

export { get, post, put, del };
