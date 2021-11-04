<template>
  <v-app style="display: flex; justify-content: center">
    <section class="section-container">
      <v-row class="signin">
        <v-col cols="12" sm="8" class="left">
          <img src="@/assets/bigLogo.png" alt="" />
        </v-col>
        <v-col cols="12" sm="4" class="right">
          <h2>LOGIN</h2>
          <validation-observer ref="observer">
            <v-form @submit.prevent="submit">
              <validation-provider
                v-slot="{ errors }"
                name="Email"
                rules="required|email"
              >
                <v-text-field
                  v-model="email"
                  :error-messages="errors"
                  label="Email"
                  required
                  outlined
                  dark
                  filled
                  dense
                ></v-text-field>
              </validation-provider>
              <validation-provider
                v-slot="{ errors }"
                name="Password"
                rules="required"
              >
                <v-text-field
                  v-model="password"
                  :error-messages="errors"
                  label="Password"
                  :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="showPass = !showPass"
                  required
                  outlined
                  dense
                  dark
                  filled
                  :type="showPass ? 'text' : 'password'"
                ></v-text-field>
              </validation-provider>
              <div class="text-center">
                <v-btn
                  class="signin-btn"
                  type="submit"
                  rounded
                  color="white"
                  light
                >
                  <img
                    src="@/assets/Mattermost_Logo.png"
                    style="width: 1.5rem; height: 1.5rem"
                    alt=""
                  />
                  Mattermost
                </v-btn>
              </div>
            </v-form>
          </validation-observer>
        </v-col>
      </v-row>
    </section>
  </v-app>
</template>

<script>
import { required, email } from 'vee-validate/dist/rules'
import UserApi from '@/api/UserApi'
import {
  extend,
  ValidationProvider,
  setInteractionMode,
  ValidationObserver,
} from 'vee-validate'

setInteractionMode('eager')

extend('required', {
  ...required,
  message: '{_field_} can not be empty',
})

extend('email', {
  ...email,
  message: 'Email must be valid',
})

export default {
  components: {
    ValidationProvider,
    ValidationObserver,
  },
  data: () => ({
    email: '',
    password: null,
    showPass: false,
  }),
  computed: {
    params() {
      return {
        email: this.email,
        password: this.password,
      }
    },
  },
  methods: {
    async submit() {
      const valid = await this.$refs.observer.validate()
      if (valid) {
        UserApi.Login(
          {
            login_id: this.email,
            password: this.password,
          },
          res => {
            console.log(res.data)

            console.log(res.data.isSignUp)

            if (res.data.isSignUp) {
              this.$router.push({ name: 'MainPage' })
            } else {
              this.$router.push({ name: 'SelectLoginPage' })
            }
          },
          err => {
            if (err.response.status == 401) {
              alert('유효하지 않은 계정입니다.')
            }
          },
        )
      }
    },
    clear() {
      // you can use this method to clear login form
      this.email = ''
      this.password = null
      this.$refs.observer.reset()
    },
  },
}
</script>

<style scoped></style>
