<template>
  <v-app>
    <v-dialog v-model="dialog" width="500">
      <template v-slot:activator="{ on, attrs }">
        <v-btn color="red lighten-2" dark v-bind="attrs" v-on="on">
          Click Me
        </v-btn>
      </template>
      <MySurveyAnswer></MySurveyAnswer>
    </v-dialog>
    <vue-word-cloud
      style="
          height: 550px;
          width: 450px;
          margin-left: 30px;
          cursor: pointer;
        "
      :words="words"
      :color="color"
      font-family="Roboto"
      font-weight="Bold"
      :font-size-ratio="Ratio"
      :rotation="rotation"
    >
    </vue-word-cloud>
    <button @click="asd">버튼</button>
  </v-app>
</template>
<script>
import MySurveyAnswer from '@/components/SurveyResult/MySurveyAnswer.vue'
import VueWordCloud from 'vuewordcloud'
let Chance = require('chance')
let chance = new Chance()

export default {
  components: {
    MySurveyAnswer,
    VueWordCloud,
  },
  props: {},
  data: () => ({
    shortvalue: '',
    my_answer: {
      '1번': ['1'],
      '2번': ['2'],
      '3번': ['2', '3'],
      '4번': ['1', '4'],
      '5번': ['5번 주관식답변'],
      '6번': ['6번 주관식답변'],
    },
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
    words: [
      ['한글', 3],
      ['이미', 3],
      ['test', 1],
      ['test', 1],
      ['test', 1],
      ['test', 1],
      ['test', 1],
      ['test', 1],
    ],
    Ratio: 5,
    colorItems: [
      [
        '#FEDE00',
        '#B4F8C8',
        '#6AB8EE',
        '#ff4e69',
        '#BCECE0',
        '#4C5270',
        '#F652A0',
        '#36EEE0',
        '#461e47',
        '#DB1F48',
      ],
    ],
    rotationItemIndex: undefined,
    rotationItems: [
      {
        value: 0,
      },
      {
        value: function() {
          let chance = new Chance()
          return chance.pickone([0, 3 / 4])
        },
      },
      {
        value: function() {
          let chance = new Chance()
          return chance.pickone([0, 1 / 8, 3 / 4, 7 / 8])
        },
      },
    ],
    temp: ['aa', 'bb', 'aa', 'a', 'c'],
    temp2: [],
  }),
  computed: {
    // colorItem에 있는 칼라를 랜덤으로 추출해서 바인딩
    color() {
      const colors = this.colorItems[0]
      return function() {
        return chance.pickone(colors)
      }
    },
    // rotationItem에 있는 각도를 랜덤으로 추출해서 바인딩
    rotation() {
      const item = this.rotationItems[this.rotationItemIndex]
      return item.value
    },
  },
  created() {
    // 단어 구성 형태 랜덤 선택(십자, 원형)
    this.rotationItemIndex = chance.integer({
      min: 0,
      max: this.rotationItems.length - 1,
    })
  },
  methods: {
    asd() {
      console.log(this.temp)
      for (var i = 0; i < this.temp.length; i++) {
        var temp = undefined
        for (var j = 0; j < this.temp2.length; j++) {
          if (this.temp[i] == this.temp2[j][0]) {
            console.log(123)
            temp = j
          }
        }
        if (temp == undefined) {
          this.temp2.push([this.temp[i], 1])
        } else {
          this.temp2[temp][1]++
        }
      }
      this.words = this.temp2
      console.log(this.temp2)
    },
  },
}
</script>
