<template>
  <v-app>
    <v-card width="1000" class="mx-auto">
      <v-toolbar color="#4E7AF5" dark>
        <v-toolbar-title
          >{{ this.survey.title }} {{ result
          }}{{ checkresult }}</v-toolbar-title
        >
        <v-spacer></v-spacer>
      </v-toolbar>
      <v-list>
        <template>
          <v-subheader
            >{{ this.survey.explain }} <br />시작 {{ this.survey.start_date }} ~
            종료 {{ this.survey.start_date }}</v-subheader
          >

          <v-list-item
            v-for="(ques, index) in this.survey.question"
            :key="index + 1"
          >
            <v-list-item-content>
              <v-list-item-title>
                {{ ques.q_number }}. {{ ques.q_explanation }}
              </v-list-item-title>
              <v-radio-group
                v-if="ques.q_type == 'SINGLE'"
                class="mx-5"
                disabled
              >
                <v-radio
                  v-for="(answer, index) in ques.q_option"
                  :key="index + 2"
                  :label="`${answer.o_explanation}`"
                  :value="`${answer.o_number}`"
                >
                </v-radio>
              </v-radio-group>
              <v-container class="mx-2" v-if="ques.q_type == 'MULTIPLE'">
                <v-checkbox
                  class="mt-0 pt-0"
                  v-for="(answer, index) in ques.q_option"
                  :key="index + 2"
                  :label="`${answer.o_explanation}`"
                  :value="`${answer.o_number}`"
                  disabled
                >
                </v-checkbox>
              </v-container>
              <v-container v-if="ques.q_type == 'SHORT'">
                <v-textarea
                  auto-grow
                  counter
                  :rules="shortrules"
                  :value="shortvalue"
                  background-color="grey lighten-4"
                  color="cyan"
                  label="답변을 입력해주세요."
                  disabled
                ></v-textarea>
              </v-container>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-card>
  </v-app>
</template>

<script>
export default {
  props: {
    survey: {
      type: Object,
      required: true,
    },
  },
}
</script>

<style scoped src="./../../css/survey/surveyset/survey.css"></style>
