import { post } from "./api";

const AUTH_SERVICE = "/api/v1/auth";

// 사용자 등록(회원가입)
const register = (userData) => post(`${AUTH_SERVICE}/register`, userData);

// 사용자 인증(로그인)
const authenticate = (credentials) =>
  post(`${AUTH_SERVICE}/authenticate`, credentials);

export { register, authenticate };
