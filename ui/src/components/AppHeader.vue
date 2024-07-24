<template>
  <v-app-bar app>
    <v-app-bar-title
      class="font-weight-bold"
      @click="goToHome"
      style="cursor: pointer"
    >
      {{ appTitle }}
    </v-app-bar-title>

    <template v-slot:append>
      <v-btn
        v-for="item in barItems"
        :key="item.title"
        :icon="item.icon"
        :to="item.to"
      ></v-btn>
      <v-btn v-if="isAuthenticated" icon="mdi-logout" @click="logout"></v-btn>
    </template>
  </v-app-bar>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "AppHeader",
  props: {
    appTitle: {
      type: String,
      required: true,
    },
    barItems: {
      type: Array,
      required: true,
    },
  },
  computed: {
    ...mapGetters(["isAuthenticated"]),
  },
  methods: {
    ...mapActions(["logout"]),
    goToHome() {
      this.$router.push("/");
    },
    logout() {
      this.$store.dispatch("logout").then(() => {
        this.$router.push("/");
      });
    },
  },
};
</script>
