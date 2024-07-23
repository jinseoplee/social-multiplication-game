import { createRouter, createWebHistory } from "vue-router";

import SignUpView from "@/views/SignUpView.vue";

const routes = [
  {
    path: "/signup",
    name: "SignUpView",
    component: SignUpView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
