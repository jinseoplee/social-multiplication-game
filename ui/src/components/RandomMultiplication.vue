<template>
  <v-card height="435px">
    <div class="bg-blue-grey-lighten-5 pa-3 text-center">
      <h2>랜덤 곱셈 문제</h2>
    </div>

    <div class="pa-5">
      <h2 class="text-center">{{ factorA }} X {{ factorB }}</h2>
      <v-form @submit.prevent="checkAttempt">
        <v-text-field
          v-model="answer"
          prepend-icon="mdi-equal"
          variant="underlined"
          class="mb-5"
        >
        </v-text-field>

        <v-alert
          v-if="showAlert"
          :type="resultType"
          variant="tonal"
          class="mb-5"
          >{{ resultMessage }}
        </v-alert>

        <v-btn
          v-bind:disabled="
            isNaN(answer) || answer.trim() === '' || answer < 1 || answer > 81
          "
          type="submit"
          >제출</v-btn
        >
      </v-form>
    </div>
  </v-card>
</template>

<script>
import { getRandomMultiplication, checkAttempt } from "@/api/multiplication";

export default {
  data() {
    return {
      factorA: 0,
      factorB: 0,
      answer: "",
      showAlert: false,
      resultType: "",
      resultMessage: "",
    };
  },
  mounted() {
    this.getRandomMultiplication();
  },
  methods: {
    async getRandomMultiplication() {
      try {
        const response = await getRandomMultiplication();
        this.factorA = response.data.factorA;
        this.factorB = response.data.factorB;
      } catch {
        this.resultType = "error";
        this.resultMessage = "문제를 가져오는 데 문제가 발생했습니다.";
        this.showAlert = true;
      }
    },
    async checkAttempt() {
      try {
        const response = await checkAttempt({
          userId: this.$store.state.userId,
          factorA: this.factorA,
          factorB: this.factorB,
          answer: this.answer,
        });

        const isCorrect = response.data.isCorrect;
        if (isCorrect) {
          this.getRandomMultiplication();
          this.answer = "";
          this.resultType = "success";
          this.resultMessage = "정답입니다!";
        } else {
          this.resultType = "error";
          this.resultMessage = "오답입니다!";
        }

        this.showAlert = true;

        setTimeout(() => {
          this.$emit("refresh-attempts");
          this.$emit("refresh-statistics");
        }, 500);
      } catch {
        this.resultType = "error";
        this.resultMessage = "정답을 검증하는 데 문제가 발생했습니다.";
        this.showAlert = true;
      }
    },
  },
};
</script>
