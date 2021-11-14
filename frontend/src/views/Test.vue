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
  </v-app>
</template>
<script>
import MySurveyAnswer from '@/components/SurveyResult/MySurveyAnswer.vue'
export default {
  components: {
    MySurveyAnswer,
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
  }),
  methods: {},
}
</script>
