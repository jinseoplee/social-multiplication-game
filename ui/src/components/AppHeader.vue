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
    </template>
  </v-app-bar>
</template>

<script>
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
  methods: {
    goToHome() {
      this.$router.push("/");
    },
  },
};
</script>
