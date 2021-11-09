<template>
  <div>
    <p>
      {{ survey }}
    </p>
  </div>
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
