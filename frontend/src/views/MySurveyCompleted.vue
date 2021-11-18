<template>
  <v-app class="notosanskr">
    <v-card width="600" class="mx-auto" elevation="1">
      <v-toolbar color="#4E7AF5" dark elevation="1">
        <v-toolbar-title>완료된 내 설문리스트</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>

      <v-list three-line v-if="surveys.length >= 1">
        <template v-for="(item, index) in surveys">
          <v-list-item :key="index + 2">
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
                기간 :
                {{
                  item.start_date.substring(0, 10) +
                    ' ' +
                    item.start_date.substring(11, 16)
                }}
                ~
                {{
                  item.end_date.substring(0, 10) +
                    ' ' +
                    item.end_date.substring(11, 16)
                }}
              </v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-action>
              <v-menu bottom :offset-x="offset">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    plain
                    color="primary"
                    elevation="0"
                    v-bind="attrs"
                    v-on="on"
                  >
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>
                <v-list style="padding:0px">
                  <v-list-item>
                    <v-list-item-title @click="moveResult(item.sid)"
                      ><v-icon>mdi-text-box-search</v-icon> 결과보기
                    </v-list-item-title>
                  </v-list-item>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-title
                      ><v-dialog v-if="item.writer == uid" width="500">
                        <template v-slot:activator="{ on, attrs }">
                          <div v-bind="attrs" v-on="on">
                            <v-icon>mdi-delete</v-icon> 삭제
                          </div>
                        </template>
                        <v-card width="1000" class="mx-auto">
                          <v-toolbar color="error" dark>
                            <v-toolbar-title
                              >정말 삭제하시겠습니까?</v-toolbar-title
                            >
                            <v-spacer></v-spacer>
                          </v-toolbar>
                          <br />
                          <v-card-text>
                            정말로 삭제하시겠습니까?
                          </v-card-text>
                          <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="error" @click="delmysurvey(item.sid)"
                              >삭제</v-btn
                            >
                          </v-card-actions>
                        </v-card>
                      </v-dialog>
                      <div v-else>
                        <v-icon>mdi-delete</v-icon> 삭제
                      </div></v-list-item-title
                    >
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-list-item-action>
          </v-list-item>
          <v-divider :key="index + 1"></v-divider>
        </template>
      </v-list>
      <v-list v-else three-line>
        <template>
          <v-subheader v-text="'완료된 내 설문이 없습니다.'"></v-subheader>
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
    offset: true,
    surveys: [],
    uid: '',
    rows: 2,
    page: 1,
  }),
  methods: {
    moveResult(sid) {
      console.log(sid)
      this.$router.push(`/survey-result/${sid}`)
    },
  },
  watch: {
    page() {
      SurveyApi.getMysurvey(
        'COMPLETED',
        this.$store.state.uid,
        this.page - 1,
        res => {
          console.log(res.data.data)
          this.rows = res.data.Pagecount
          this.surveys = res.data.data
        },
        () => {},
      )
    },
  },
  created() {
    SurveyApi.getMysurvey(
      'COMPLETED',
      this.$store.state.uid,
      this.page - 1,
      res => {
        console.log(res)
        this.uid = this.$store.state.uid
        this.rows = res.data.Pagecount
        this.surveys = res.data.data
      },
      () => {},
    )
  },
}
</script>

<style scoped>
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);

.notosanskr * {
  font-family: 'Noto Sans KR', sans-serif;
}
</style>
