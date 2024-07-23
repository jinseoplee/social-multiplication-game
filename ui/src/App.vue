<template>
  <v-app>
    <AppHeader :appTitle="appTitle" :barItems="barItems" />

    <v-main>
      <router-view></router-view>
    </v-main>

    <AppFooter :footerItems="footerItems" />
  </v-app>
</template>

<script>
import AppHeader from "./components/AppHeader.vue";
import AppFooter from "./components/AppFooter.vue";

export default {
  name: "App",
  components: {
    AppHeader,
    AppFooter,
  },
  data() {
    return {
      appTitle: "Social Multiplication Game",
      barItems: [
        { title: "Home", icon: "mdi-home", to: "/" },
        { title: "Sign Up", icon: "mdi-account-plus", to: "/signup" },
        { title: "Sign In", icon: "mdi-login", to: "/signin" },
      ],
      footerItems: [
        {
          title: "GitHub",
          icon: "mdi-github",
          href: "https://github.com/jinseoplee/social-multiplication-game",
        },
      ],
    };
  },
};
</script>
