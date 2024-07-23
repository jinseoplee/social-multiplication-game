import { post } from "./api";

const AUTH_SERVICE = "/api/v1/auth";

// 사용자 등록(회원가입)
const register = (userData) => post(`${AUTH_SERVICE}/register`, userData);

export { register };
