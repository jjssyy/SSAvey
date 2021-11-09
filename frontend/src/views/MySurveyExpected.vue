<template>
  <v-app>
    <v-card max-width="600" class="mx-auto">
      <v-toolbar color="black" dark>
        <v-toolbar-title>진행중인 설문리스트</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-toolbar>

      <v-list three-line>
        <template v-for="(item, index) in surveys">
          <v-subheader
            v-if="item.header"
            :key="item.header"
            v-text="item.header"
          ></v-subheader>

          <v-divider
            v-else-if="item.divider"
            :key="index"
            :inset="item.inset"
          ></v-divider>

          <v-list-item v-else :key="item.title">
            <v-list-item-content>
              <v-list-item-title v-html="item.title"></v-list-item-title>
              <v-list-item-subtitle
                style="
                  text-overflow: ellipsis;
                  overflow: hidden;
                  white-space: nowrap;
                "
                v-html="item.explain"
              ></v-list-item-subtitle>
              <v-list-item-subtitle>
                기간: {{ item.start_date }} ~ {{ item.end_date }}
              </v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-action>
              설문 종류
              <v-menu bottom :offset-x="offset">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn color="primary" dark v-bind="attrs" v-on="on">
                    <i class="fas fa-ellipsis-v"></i>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item>
                    <v-list-item-title v-if="this.user.id == this.survey.writer"
                      >수정하기
                      <i class="fas fa-pencil fa-sm" style="float: right;"></i
                    ></v-list-item-title>
                  </v-list-item>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-title
                      >공유
                      <i
                        class="fas fa-share-alt fa-sm"
                        style="float: right;"
                      ></i
                    ></v-list-item-title>
                  </v-list-item>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-title
                      >삭제<i
                        class="fas fa-trash-alt fa-sm"
                        style="float: right;"
                      ></i
                    ></v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-list-item-action>
          </v-list-item>
        </template>
      </v-list>
    </v-card>
  </v-app>
</template>

<script>
import SurveyApi from '@/api/SurveyApi'

export default {
  data: () => ({
    surveys: [],
  }),
  methods: {},
  created() {
    SurveyApi.getCertainStateSurveys(
      'EXPECTED',
      this.$store.state.uid,
      res => {
        console.log(res.data.data)
        this.surveys = res.data.data

        this.surveys.push({ divider: true, inset: true })
      },
      () => {},
    )
  },
}
</script>

<style scoped></style>
