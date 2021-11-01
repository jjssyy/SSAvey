<template>
  <div class="component-total">
    <!-- 설문 문항 -->
    <div class="component-11" ref="left" id="display drop-target"></div>
    <!-- 오른쪽 사이드바 -->
    <div class="component-22" ref="right" id="drag-elements">
      <div>테스트1</div>
      <div>테스트2</div>
      <div>테스트3</div>
    </div>
  </div>
</template>

<script>
import dragula from 'dragula'

export default {
  mounted() {
    const { left, right } = this.$refs

    dragula([right, left], {
      revertOnSpill: true,
    })
  },
}
</script>

<style scoped src="./../css/survey/surveyset.css"></style>
