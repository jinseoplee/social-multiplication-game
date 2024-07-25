<template>
  <v-card height="435px">
    <div class="bg-blue-grey-lighten-5 pa-3 text-center">
      <h2>최근 시도 결과</h2>
    </div>

    <div class="pa-5">
      <v-table>
        <thead>
          <tr>
            <th
              v-for="header in headers"
              :key="header"
              class="text-center font-weight-bold"
            >
              {{ header }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(multiplication, index) in multiplications"
            :key="index"
            class="text-center"
          >
            <td>{{ index + 1 }}</td>
            <td>{{ multiplication.factorA }} X {{ multiplication.factorB }}</td>
            <td>{{ multiplication.answer }}</td>
            <td>{{ multiplication.isCorrect ? "O" : "X" }}</td>
          </tr>
        </tbody>
      </v-table>
    </div>
  </v-card>
</template>

<script>
import { getRecentAttempts } from "@/api/multiplication";

export default {
  data() {
    return {
      headers: ["ID", "문제", "값", "정답"],
      multiplications: [],
    };
  },
  mounted() {
    this.getRecentAttempts(this.$store.state.userId);
  },
  methods: {
    async getRecentAttempts(userId) {
      try {
        const response = await getRecentAttempts(userId);
        this.multiplications = response.data.multiplications;
      } catch {
        console.error("최근 시도 목록을 가져오는 데 문제가 발생했습니다.");
      }
    },
  },
};
</script>
