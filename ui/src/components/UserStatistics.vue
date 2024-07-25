<template>
  <v-card height="435px">
    <div class="bg-blue-grey-lighten-5 pa-3 text-center">
      <h2>통계</h2>
    </div>

    <div class="pa-5">
      <v-table>
        <tbody>
          <tr>
            <th>아이디</th>
            <td>{{ statistics.userId }}</td>
          </tr>
          <tr>
            <th>총 점수</th>
            <td>{{ statistics.totalScore }}</td>
          </tr>
          <tr>
            <th>배지</th>
            <td>{{ formattedBadges }}</td>
          </tr>
        </tbody>
      </v-table>
    </div>
  </v-card>
</template>

<script>
import { getUserStatistics } from "@/api/gamification";

export default {
  data() {
    return {
      statistics: {},
    };
  },
  mounted() {
    this.getUserStatistics(this.$store.state.userId);
  },
  computed: {
    formattedBadges() {
      return this.statistics.badges ? this.statistics.badges.join(" ") : "";
    },
  },
  methods: {
    async getUserStatistics(userId) {
      try {
        const response = await getUserStatistics(userId);
        this.statistics = response.data;
      } catch {
        console.error("사용자 통계를 가져오는 데 문제가 발생했습니다.");
      }
    },
  },
};
</script>
