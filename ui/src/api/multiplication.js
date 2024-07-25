import { get, post } from "./api";

const MULTIPLICATION_SERVICE = "/api/v1/multiplication";

// 랜덤 곱셉 문제 가져오기
const getRandomMultiplication = () => get(`${MULTIPLICATION_SERVICE}/random`);

// 곱셈 문제 제출하기
const checkAttempt = (data) => post(`${MULTIPLICATION_SERVICE}/attempts`, data);

// 최근 시도 결과 가져오기
const getRecentAttempts = (userId) =>
  get(`${MULTIPLICATION_SERVICE}/attempts/results/${userId}`);

export { getRandomMultiplication, checkAttempt, getRecentAttempts };
