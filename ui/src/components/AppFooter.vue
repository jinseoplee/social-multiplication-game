<template>
  <v-footer class="bg-grey-darken-4" app>
    <v-row justify="center">
      <v-col class="text-center" cols="12">
        <v-btn
          v-for="item in footerItems"
          :key="item.title"
          :icon="item.icon"
          :href="item.href"
          variant="text"
          target="_blank"
        ></v-btn>
      </v-col>
    </v-row>
  </v-footer>
</template>

<script>
export default {
  name: "AppFooter",
  props: {
    footerItems: {
      type: Array,
      required: true,
    },
  },
};
</script>
