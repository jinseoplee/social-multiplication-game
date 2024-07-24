import { createStore } from "vuex";
import persistedstate from "vuex-persistedstate";

const store = createStore({
  state() {
    return {
      userId: "",
      accessToken: "",
    };
  },
  mutations: {
    setUserId(state, userId) {
      state.userId = userId;
    },
    setAccessToken(state, accessToken) {
      state.accessToken = accessToken;
    },
  },
  getters: {
    isAuthenticated(state) {
      return !!state.accessToken;
    },
  },
  plugins: [
    persistedstate({
      paths: ["userId", "accessToken"],
    }),
  ],
});

export default store;
