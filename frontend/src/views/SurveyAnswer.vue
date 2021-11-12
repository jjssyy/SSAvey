<template>
  <v-app>
    <v-card width="1000" class="mx-auto">
      <v-toolbar color="#4E7AF5" dark>
        <v-toolbar-title
          >{{ this.items[0].title }} {{ resultCompute }}</v-toolbar-title
        >
        <v-spacer></v-spacer>
      </v-toolbar>
      <v-list>
        <template v-for="(item, index) in items">
          <v-subheader v-if="item.title" :key="index"
            >{{ item.explain }} <br />시작 {{ item.start_date }} ~ 종료
            {{ item.start_date }}</v-subheader
          >

          <v-list-item v-for="(ques, index) in item.question" :key="index + 1">
            <v-list-item-content>
              <v-list-item-title>
                {{ ques.q_number }}. {{ ques.q_explanation }}
              </v-list-item-title>
              <v-radio-group v-if="ques.q_type == 'SINGLE'" class="mx-5">
                <v-radio
                  v-for="(answer, r_index) in ques.q_option"
                  :key="r_index + 2"
                  :label="`${answer.o_explanation}`"
                  :value="`${answer.o_number}`"
                  @click="checkSingle(index + 1, `${answer.o_number}`)"
                >
                </v-radio>
              </v-radio-group>
              <v-container class="mx-2" v-if="ques.q_type == 'MULTIPLE'">
                <v-checkbox
                  v-for="(answer, c_index) in ques.q_option"
                  :key="c_index + 3"
                  class="mt-0 pt-0"
                  :label="`${answer.o_explanation}`"
                  :value="`${answer.o_number}`"
                  @click="checkMultiple(index + 1, `${answer.o_number}`)"
                >
                </v-checkbox>
              </v-container>
              <v-container v-if="ques.q_type == 'SHORT'">
                <v-textarea
                  auto-grow
                  counter
                  :rules="shortrules"
                  :value="shortvalue"
                  v-model="short[index + 1 + '번']"
                  background-color="grey lighten-4"
                  color="cyan"
                  label="답변을 입력해주세요."
                  @focusout="checkShort(index + 1)"
                ></v-textarea>
              </v-container>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
      <v-card-actions style="float: right;">
        <v-btn @click="showresult" text color="primary accent-4">
          SUBMIT
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    shortrules: [
      v =>
        v ? (v.length <= 20 ? true : '최대 20자까지만 입력해주세요.') : false,
    ],
    // shortrules: null,
    short: {},
    shortvalue: '',
    result: {},
    items: [
      {
        title: '설문지 제목입니다.',
        explain: '설문지 설명입니다. 테스트용 설문이며 응답은 안하셔도 됩니다.',
        is_anony: false,
        start_date: '2021.11.19.09:00',
        end_date: '2021.11.19.18:00',
        question: [
          {
            q_number: '1',
            q_explanation: '1번 문항 객관식 단일 선택 제목입니다.',
            q_type: 'SINGLE',
            is_required: true,
            q_option: [
              { o_number: '1', o_explanation: '1번선택지', is_short: false },
              { o_number: '2', o_explanation: '2번선택지', is_short: false },
              { o_number: '3', o_explanation: '3번선택지', is_short: false },
              { o_number: '4', o_explanation: '기타', is_short: true },
            ],
          },
          {
            q_number: '2',
            q_explanation: '2번 문항 객관식 단일 선택 제목입니다.',
            q_type: 'SINGLE',
            is_required: true,
            q_option: [
              { o_number: '1', o_explanation: '1번선택지', is_short: false },
              { o_number: '2', o_explanation: '2번선택지', is_short: false },
              { o_number: '3', o_explanation: '3번선택지', is_short: false },
              { o_number: '4', o_explanation: '기타', is_short: true },
            ],
          },
          {
            q_number: '3',
            q_explanation: '3번 문항 객관식 복수 선택 제목입니다.',
            q_type: 'MULTIPLE',
            is_required: false,
            q_option: [
              { o_number: '1', o_explanation: '1번 선택지', is_short: false },
              { o_number: '2', o_explanation: '2번 선택지', is_short: false },
              { o_number: '3', o_explanation: '3번 선택지', is_short: false },
              { o_number: '4', o_explanation: '기타', is_short: true },
            ],
          },
          {
            q_number: '4',
            q_explanation: '4번 문항 객관식 복수 선택 제목입니다.',
            q_type: 'MULTIPLE',
            is_required: false,
            q_option: [
              { o_number: '1', o_explanation: '1번 선택지', is_short: false },
              { o_number: '2', o_explanation: '2번 선택지', is_short: false },
              { o_number: '3', o_explanation: '3번 선택지', is_short: false },
              { o_number: '4', o_explanation: '기타', is_short: true },
            ],
          },
          {
            q_number: '5',
            q_explanation: '5번 문항 주관식 제목입니다.',
            q_type: 'SHORT',
            is_required: true,
            q_option: [],
          },
          {
            q_number: '6',
            q_explanation: '6번 문항 주관식 제목입니다.',
            q_type: 'SHORT',
            is_required: true,
            q_option: [],
          },
        ],
        state: 'EXPECTED',
        share: [],
        target: [],
        complete: [],
        incomplete: [],
        use_template: null,
        template: null,
        writer: 'eg41qb97a7bfpfrrc5qga7e46c',
      },
    ],
  }),
  // mounted() {
  //   this.shortrules = [v => v.length <= 20 || '최대 20자까지만 입력해주세요.']
  // },
  computed: {
    resultCompute() {
      return this.result
    },
  },
  methods: {
    showresult() {
      console.log(this.result)
    },
    checkMultiple(index, key) {
      if (this.result.hasOwnProperty(`${index}번`)) {
        if (this.result[`${index}번`].includes(key)) {
          this.result[`${index}번`].splice(
            this.result[`${index}번`].indexOf(key),
            1,
          )
        } else {
          this.result[`${index}번`].push(key)
        }
      } else {
        this.result[`${index}번`] = []
        this.result[`${index}번`].push(key)
      }
      console.log(this.result)
    },
    checkSingle(index, key) {
      if (this.result.hasOwnProperty(`${index}번`)) {
        if (this.result[`${index}번`].length != 0) {
          this.result[`${index}번`].pop()
          this.result[`${index}번`].push(key)
        } else {
          this.result[`${index}번`].push(key)
        }
      } else {
        this.result[`${index}번`] = []
        this.result[`${index}번`].push(key)
      }
      console.log(this.result)
    },
    checkShort(index) {
      if (this.result.hasOwnProperty(`${index}번`)) {
        this.result[`${index}번`].pop()
        this.result[`${index}번`].push(this.short[`${index}번`])
      } else {
        this.result[`${index}번`] = []
        this.result[`${index}번`].push(this.short[`${index}번`])
      }
      console.log(this.result)
    },
  },
}
</script>
