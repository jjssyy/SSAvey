<template>
  <v-app>
    <v-card width="600" class="mx-auto">
      <v-toolbar color="black" dark>
        <v-toolbar-title>예정된 내 설문리스트</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-toolbar>

      <v-list three-line v-if="surveys.length >= 2">
        <template v-for="(item, index) in surveys">
          <v-subheader
            v-if="item.header"
            :key="index"
            v-text="item.header"
          ></v-subheader>

          <v-divider
            v-else-if="item.divider"
            :key="index + 1"
            :inset="item.inset"
          ></v-divider>

          <v-list-item v-else :key="index + 2">
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
                    <v-list-item-title v-if="uid == item.writer"
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
      <v-list v-else three-line>
        <template>
          <v-subheader v-text="'예정된 내 설문이 없습니다.'"></v-subheader>
        </template>
      </v-list>
      <v-pagination v-model="page" :length="rows"></v-pagination>
    </v-card>
  </v-app>
</template>

<script>
import SurveyApi from '@/api/SurveyApi'

export default {
  data: () => ({
    surveys: [],
    rows: 2,
    page: 1,
    uid: '',
  }),
  methods: {},
  watch: {
    page() {
      SurveyApi.getMysurvey(
        'EXPECTED',
        this.$store.state.uid,
        this.page - 1,
        res => {
          console.log(res.data.data)
          this.rows = res.data.Pagecount
          this.surveys = res.data.data
          this.surveys.push({ divider: true, inset: true })
        },
        () => {},
      )
    },
  },
  created() {
    SurveyApi.getMysurvey(
      'EXPECTED',
      this.$store.state.uid,
      this.page - 1,
      res => {
        console.log(res.data.data)
        this.rows = res.data.Pagecount
        this.surveys = res.data.data
        this.uid = this.$store.state.uid
        this.surveys.push({ divider: true, inset: true })
      },
      () => {},
    )
  },
}
</script>

<style scoped></style>
