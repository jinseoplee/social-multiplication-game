<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="10" md="8" lg="6" xl="4">
        <v-card>
          <div class="bg-teal-accent-2 text-center pa-3 mb-3">
            <h2>{{ title }}</h2>
          </div>

          <div class="pa-5">
            <v-form ref="form" @submit.prevent="registerUser">
              <v-text-field
                v-for="field in fields"
                :key="field.property"
                v-model="user[field.property]"
                :label="field.label"
                :type="field.type"
                :prepend-icon="field.icon"
                :rules="field.rules"
                :error-messages="errorMessages[field.property]"
                variant="underlined"
                class="mb-5"
              >
              </v-text-field>
              <v-btn type="submit" color="teal-accent-2" :loading="loading">{{
                signUpBtn
              }}</v-btn>
            </v-form>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { register } from "@/api/auth.js";

export default {
  data() {
    return {
      title: "회원가입",
      signUpBtn: "회원가입",
      user: {
        userId: "",
        password: "",
        confirmPassword: "",
        email: "",
      },
      fields: [
        {
          property: "userId",
          label: "아이디",
          type: "text",
          icon: "mdi-account",
          rules: [
            (v) => !!v || "아이디를 입력해 주세요.",
            (v) =>
              (v && v.length >= 3) || "아이디는 최소 3자 이상이어야 합니다.",
          ],
        },
        {
          property: "password",
          label: "비밀번호",
          type: "password",
          icon: "mdi-lock",
          rules: [
            (v) => !!v || "비밀번호를 입력해 주세요.",
            (v) =>
              (v && v.length >= 8) || "비밀번호는 최소 8자 이상이어야 합니다.",
          ],
        },
        {
          property: "confirmPassword",
          label: "비밀번호 확인",
          type: "password",
          icon: "mdi-lock-check",
          rules: [
            (v) => !!v || "비밀번호를 입력해 주세요.",
            (v) => v === this.user.password || "비밀번호가 일치하지 않습니다.",
          ],
        },
        {
          property: "email",
          label: "이메일(선택사항)",
          type: "email",
          icon: "mdi-email",
          rules: [(v) => !v || /.+@.+\..+/.test(v)],
        },
      ],
      errorMessages: {},
      loading: false,
    };
  },
  methods: {
    async registerUser() {
      this.loading = true;
      const { valid } = await this.$refs.form.validate();

      if (valid) {
        try {
          await register(this.user);
          alert("회원 가입 성공");
          this.$router.push("/signin");
        } catch (error) {
          this.handleServerError(error);
        }
      }

      this.loading = false;
    },

    handleServerError(error) {
      if (!error.response) {
        alert("네트워크 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
        return;
      }

      const { status, data } = error.response;
      if (status !== 400) {
        alert("서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
        return;
      }

      if (data.message === "유효성 검사 실패") {
        this.errorMessages = data.errors;
      } else if (data.message === "이미 가입된 회원입니다.") {
        this.errorMessages = {
          userId: data.message,
        };
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
