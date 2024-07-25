<template>
  <v-card height="435px">
    <div class="bg-blue-grey-lighten-5 pa-3 text-center">
      <h2>통계</h2>
    </div>

    <div class="pa-5">
      <v-table>
        <thead>
          <tr>
            <th
              v-for="(header, index) in headers"
              :key="index"
              class="text-center font-weight-bold"
            >
              {{ header }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(row, index) in leaderboard"
            :key="index"
            class="text-center"
          >
            <td>{{ index + 1 }}</td>
            <td>{{ row.userId }}</td>
            <td>{{ row.totalScore }}</td>
          </tr>
        </tbody>
      </v-table>
    </div>
  </v-card>
</template>

<script>
import { getLeaderboard } from "@/api/gamification";

export default {
  data() {
    return {
      leaderboard: {},
      headers: ["순위", "아이디", "총 점수"],
    };
  },
  mounted() {
    this.getLeaderboard();
  },
  methods: {
    async getLeaderboard() {
      try {
        const response = await getLeaderboard();
        this.leaderboard = response.data;
      } catch {
        console.error("리더보드를 가져오는 데 문제가 발생했습니다.");
      }
    },
  },
};
</script>
