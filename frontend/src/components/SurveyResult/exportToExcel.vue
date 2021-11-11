<template>
  <div class="container-template">
    <v-btn elevation="2" @click="exportExcel()"></v-btn>
  </div>
</template>

<script>
import Xlsx from "xlsx";

export default {
  data: () => ({
    aas: "ss",
    answers: [
      {
        q_number: "1",
        answers: [
          {
            uid: "o1ojzjw173d17c3itj67o5k5do",
            name: "aaa",
            position: "교육생",
            generation: 6,
            area: "대전",
            group: null,
            team: "",
            alias: "6기/대전/",
            answer: "1-1",
          },
          {
            uid: "o1ojzjw173d17c3itj67o5k5do",
            name: "bbb",
            position: "교육생",
            generation: 6,
            area: "대전",
            group: null,
            team: "",
            alias: "6기/대전/",
            answer: "1-2",
          },
        ],
      },
      {
        q_number: "2",
        answers: [
          {
            uid: "o1ojzjw173d17c3itj67o5k5do",
            name: "aaa",
            position: "교육생",
            generation: 6,
            area: "대전",
            group: null,
            team: "",
            alias: "6기/대전/",
            answer: "2-1",
          },
          {
            uid: "o1ojzjw173d17c3itj67o5k5do",
            name: "bbb",
            position: "교육생",
            generation: 6,
            area: "대전",
            group: null,
            team: "",
            alias: "6기/대전/",
            answer: "2-2",
          },
        ],
      },
    ],
    question: [
      {
        q_number: "1",
        q_explanation: "1번 문항",
        q_type: "SINGLE",
        q_option: [
          {
            o_number: 1,
            o_explanation: "1",
            _short: false,
          },
          {
            o_number: 2,
            o_explanation: "2",
            _short: false,
          },
        ],
        _required: false,
      },
      {
        q_number: "2",
        q_explanation: "2번 문항",
        q_type: "SINGLE",
        q_option: [
          {
            o_number: 1,
            o_explanation: "1",
            _short: false,
          },
          {
            o_number: 2,
            o_explanation: "2",
            _short: false,
          },
        ],
        _required: false,
      },
    ],
    execel_data: [],
  }),
  methods: {
    exportExcel() {
      const workBook = Xlsx.utils.book_new();
      const workSheet = Xlsx.utils.json_to_sheet(this.execel_data);
      Xlsx.utils.book_append_sheet(workBook, workSheet, "example");
      Xlsx.writeFile(workBook, "example.xlsx");
    },
  },
  created() {
    for (let index = 0; index < this.answers[0].answers.length; index++) {
      let person = '{"name":"' + this.answers[0].answers[index].name + '",';

      this.answers.forEach((element) => {
        person +=
          '"' +
          this.question[element.q_number * 1 - 1].q_number +
          ". " +
          this.question[element.q_number * 1 - 1].q_explanation +
          '":';
        person += '"' + element.answers[index].answer + '",';
      });

      person = person.slice(0, -1);
      person += "}";

      this.execel_data.push(JSON.parse(person));
    }
  },
};
</script>

<style scoped></style>
