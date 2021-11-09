<template>
  <div class="container-template">
    <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn color="primary" dark v-bind="attrs" v-on="on">
          템플릿 목록
        </v-btn>
      </template>
      <v-list>
        <v-list-item v-for="(item, index) in templates" :key="index">
          <v-list-item-title @click="click(item)">{{
            item.t_title
          }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <!-- TODO: template 내용 꾸미기 -->
    {{ selectedTemplate.t_explain }}

    <v-btn depressed color="primary" @click="selectTemplate()">
      질문 가져오기
    </v-btn>
  </div>
</template>

<script>
import TemplateApi from "@/api/TemplateApi";

export default {
  data: () => ({
    templates: [],
    selectedTemplate: {},
  }),
  methods: {
    click(template) {
      this.selectedTemplate = template;
    },
    selectTemplate() {
      this.$store.commit("setSelectedTemplate", this.selectedTemplate);
    },
  },
  created() {
    TemplateApi.getTemplates(
      this.$store.state.uid,
      (res) => {
        this.templates = res.data.data;
      },
      () => {}
    );
  },
};
</script>

<style scoped></style>
