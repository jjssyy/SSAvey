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
        <template v-for="(item, index) in items">
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
                style="text-overflow:ellipsis; overflow:hidden; white-space: nowrap;"
                >동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리 나라
                만세 무궁화 삼천리 화려강산 대한사람 대한으로 길이
                보전하세</v-list-item-subtitle
              >
              <v-list-item-subtitle>
                기간: 2021.11.01 09:00 ~ 2021.11.05 18:00
              </v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-action>
              설문 종류
              <v-btn icon>
                <v-icon>mdi-arrow-right</v-icon>
              </v-btn>
            </v-list-item-action>
          </v-list-item>
        </template>
      </v-list>
    </v-card>
  </v-app>
</template>
<script>
export default {
  data: () => ({
    items: [
      {
        title: 'SSAFY 5기 만족도 설문',
      },
      { divider: true, inset: true },
    ],
  }),
}
</script>
