<template>
  <div class="sub-title">
    <button @click="exportExcel()">
      <p>Excel로 반환</p>
      <i class="fas fa-reply"></i>
    </button>
  </div>
</template>

<script>
import Xlsx from 'xlsx'

export default {
  props: {
    survey: {
      type: Object,
      required: true,
    },
  },
  data: () => ({
    answers: [],
    question: [],
    execel_data: [],
  }),
  methods: {
    exportExcel() {
      const workBook = Xlsx.utils.book_new()
      const workSheet = Xlsx.utils.json_to_sheet(this.execel_data)
      Xlsx.utils.book_append_sheet(
        workBook,
        workSheet,
        this.survey.title + '설문',
      )
      const date = new Date(+new Date() + 3240 * 10000)
        .toISOString()
        .replace(/\..*/, '')
        .replace('_', ':')
      Xlsx.writeFile(workBook, this.survey.title + '설문결과_' + date + '.xlsx')
    },
  },
  mounted() {
    this.answers = this.survey.answers
    this.question = this.survey.question

    for (let index = 0; index < this.answers[0].answers.length; index++) {
      let person = '{"name":"' + this.answers[0].answers[index].name + '",'

      this.answers.forEach(element => {
        person +=
          '"' +
          this.question[element.q_number * 1 - 1].q_number +
          '. ' +
          this.question[element.q_number * 1 - 1].q_explanation +
          '":'
        person += '"' + element.answers[index].answer + '",'
      })

      person = person.slice(0, -1)
      person += '}'

      this.execel_data.push(JSON.parse(person))
    }
  },
}
</script>

<style scoped></style>
