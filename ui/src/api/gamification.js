import { get } from "./api";

const GAMIFICATION_SERVICE = "/api/v1/gamification";

// 사용자 통계 가져오기
const getUserStatistics = (userId) =>
  get(`${GAMIFICATION_SERVICE}/statistics?userId=${userId}`);

// 리더보드 가져오기
const getLeaderboard = () => get(`${GAMIFICATION_SERVICE}/leaderboard`);

export { getUserStatistics, getLeaderboard };
