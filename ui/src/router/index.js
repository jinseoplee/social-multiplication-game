import { createRouter, createWebHistory } from "vue-router";

import SignUpView from "@/views/SignUpView.vue";
import SignInView from "@/views/SignInView.vue";

const routes = [
  {
    path: "/signup",
    name: "SignUpView",
    component: SignUpView,
  },
  {
    path: "/signin",
    name: "SignInView",
    component: SignInView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
