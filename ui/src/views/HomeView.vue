<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="10" md="8" lg="6" xl="4">
        <RandomMultiplication
          @refresh-attempts="refreshRecentAttempts"
          @refresh-statistics="refreshUserStatistics"
          @refresh-leaderboard="refreshLeaderboard"
        />
      </v-col>
      <v-col cols="12" sm="10" md="8" lg="6" xl="4">
        <RecentAttempts ref="recentAttempts" />
      </v-col>
    </v-row>

    <v-row justify="center">
      <v-col cols="12" sm="10" md="8" lg="6" xl="4">
        <UserStatistics ref="userStatistics" />
      </v-col>
      <v-col cols="12" sm="10" md="8" lg="6" xl="4">
        <CurrentLeaderboard ref="leaderboard" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import RandomMultiplication from "@/components/RandomMultiplication.vue";
import RecentAttempts from "@/components/RecentAttempts.vue";
import UserStatistics from "@/components/UserStatistics.vue";
import CurrentLeaderboard from "@/components/CurrentLeaderboard.vue";

export default {
  components: {
    RandomMultiplication,
    RecentAttempts,
    UserStatistics,
    CurrentLeaderboard,
  },
  data() {
    return {
      recentAttempts: [],
      userStatistics: {},
      leaderboard: [],
    };
  },
  methods: {
    refreshRecentAttempts() {
      this.$refs.recentAttempts.getRecentAttempts(this.$store.state.userId);
    },
    refreshUserStatistics() {
      this.$refs.userStatistics.getUserStatistics(this.$store.state.userId);
    },
    refreshLeaderboard() {
      this.$refs.leaderboard.getLeaderboard();
    },
  },
};
</script>

<style scoped>
.v-container {
  margin-top: 50px;
}
</style>
