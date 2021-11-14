<template>
  <v-app>
    <v-container>
      <v-row class="">
        <v-col cols="12" sm="12">
          <v-select
            :items="items"
            v-model="selectedItem"
            label="비교할 설문을 선택해주세요."
            dense
            solo
          >
          </v-select>
          {{ selectedItem }}
        </v-col>
        <v-col class="d-flex justify-center" cols="12" sm="6">
          <v-card class="">
            <v-date-picker v-model="dates" width="75%" range></v-date-picker>
            <v-text-field
              v-model="dateRangeText"
              label="Date range"
              prepend-icon="mdi-calendar"
              readonly
            ></v-text-field>
          </v-card>
        </v-col>
        <v-col cols="12" sm="6">
          <v-card>
            <v-list dense class="overflow-y-auto" style="height: 472px;">
              <v-list-item-group v-model="selectedSurvey">
                <template>
                  <v-subheader
                    >설정기간 내 설문목록{{ selectedSurvey }}</v-subheader
                  >
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle>시작</v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <template>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle
                        >설문에 대한 간략한 설명 및 기간</v-list-item-subtitle
                      >
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <template>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle
                        >설문에 대한 간략한 설명 및 기간</v-list-item-subtitle
                      >
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <template>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle
                        >설문에 대한 간략한 설명 및 기간</v-list-item-subtitle
                      >
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <template>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle
                        >설문에 대한 간략한 설명 및 기간</v-list-item-subtitle
                      >
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <template>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle
                        >설문에 대한 간략한 설명 및 기간</v-list-item-subtitle
                      >
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <template>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle
                        >설문에 대한 간략한 설명 및 기간</v-list-item-subtitle
                      >
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <template>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle
                        >설문에 대한 간략한 설명 및 기간</v-list-item-subtitle
                      >
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <template>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>설문제목</v-list-item-title>
                      <v-list-item-subtitle>끝</v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </template>
              </v-list-item-group>
            </v-list>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <v-btn color="warning" @click="compare">비교하기</v-btn>
  </v-app>
</template>
<script>
export default {
  data() {
    return {
      items: ['1번설문', '2번설문', '3번설문'],
      selectedItem: '',
      dates: [],
      selectedSurvey: [],
    }
  },
  methods: {
    compare() {
      console.log('비교하기')
    },
  },
  computed: {
    dateRangeText() {
      return this.dates.join(' ~ ')
    },
  },
}
</script>
