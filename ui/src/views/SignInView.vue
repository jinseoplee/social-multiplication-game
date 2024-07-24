<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="10" md="8" lg="6" xl="4">
        <v-card>
          <div class="bg-teal-accent-2 text-center pa-3 mb-3">
            <h2>{{ title }}</h2>
          </div>

          <div class="pa-5">
            <v-form ref="form" @submit.prevent="authenticate">
              <v-text-field
                v-for="field in fields"
                :key="field.property"
                v-model="credentials[field.property]"
                :label="field.label"
                :type="field.type"
                :prepend-icon="field.icon"
                :rules="field.rules"
                :error-messages="errorMessages[field.property]"
                variant="underlined"
                class="mb-5"
              >
              </v-text-field>

              <v-alert
                v-if="isAuthenticationFailed"
                type="error"
                class="mb-5"
                variant="tonal"
              >
                {{ authenticationFail }}
              </v-alert>

              <v-btn type="submit" color="teal-accent-2">{{ signInBtn }}</v-btn>
            </v-form>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { authenticate } from "@/api/auth";

export default {
  data() {
    return {
      title: "로그인",
      signInBtn: "로그인",
      credentials: {
        userId: "",
        password: "",
      },
      fields: [
        {
          property: "userId",
          label: "아이디",
          type: "text",
          icon: "mdi-account",
          rules: [(v) => !!v || "아이디를 입력해 주세요."],
        },
        {
          property: "password",
          label: "비밀번호",
          type: "password",
          icon: "mdi-lock",
          rules: [(v) => !!v || "비밀번호를 입력해 주세요."],
        },
      ],
      errorMessages: {},
      isAuthenticationFailed: false,
      authenticationFail: "아이디 또는 비밀번호를 확인해 주세요.",
    };
  },
  methods: {
    async authenticate() {
      const { valid } = await this.$refs.form.validate();
      if (valid) {
        try {
          // 인증 요청을 보내고 응답을 받는다.
          const response = await authenticate(this.credentials);
          const { accessToken } = response.data;

          // Vuex 스토어에 userId와 accessToken을 저장한다.
          this.$store.commit("setUserId", this.credentials.userId);
          this.$store.commit("setAccessToken", accessToken);

          // 로그인 후 리다이렉트한다.
          this.$router.push("/");
        } catch (error) {
          this.handleServerError(error);
        }
      }
    },

    handleServerError(error) {
      if (!error.response) {
        alert("네트워크 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
        return;
      }
      const { status, data } = error.response;

      if (status === 401) {
        this.isAuthenticationFailed = true;
      } else if (status === 400) {
        this.errorMessages = data.errors;
      } else {
        alert("서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
      }
    },
  },
};
</script>

<style scoped>
.v-container {
  margin-top: 50px;
}
</style>
