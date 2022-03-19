<template>
  <v-app id="inspire">

      <v-navigation-drawer
          class="info-nav"
          v-model="drawer"
          width="300px"
          app
      >
        <v-sheet
            class="pa-4"
        >
          <div class="d-flex justify-center pa-4">
            <v-avatar
                class="mb-4"
                color="grey darken-1"
                size="95"
            ></v-avatar>
          </div>

          <div style="font-size: 20px; text-align: center" class="md12">john@vuetifyjs.com 님</div>
          <div style="font-size: 12px; text-align: center" class="pt-2">개인회원 / 일반등급</div>
        </v-sheet>

        <v-divider class="mt-6 mb-6"/>

        <v-list flat nav width="260px">
          <v-list-item-group
              v-model="selectedItem"
              color="primary"
          >
            <v-list-item
                v-for="[icon, text, url] in links"
                :key="icon"
                link
                @click="pushLink(url)"
            >
              <v-list-item-icon>
                <v-icon>{{ icon }}</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title>{{ text }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list>

        <div class="d-flex justify-center">
          <v-btn
              outlined
              rounded
              width="200px"
              class="logout-btn"
              @click="logout()"
          >
            로그아웃
          </v-btn>
        </div>
      </v-navigation-drawer>


    <v-container
        class="py-8 px-6"
        fluid
    >
      <v-row>
        <v-col
            cols="12"
        >
          <v-card height="100vh" elevation="0">
            <keep-alive>
              <component :is="component"></component>
            </keep-alive>
          </v-card>
        </v-col>
      </v-row>
    </v-container>


  </v-app>
</template>


<script>
export default {
  name: "InfoNavi",

  data () {
    return {
      selectedItem : null,
      drawer: true,
      links: [
        ['mdi-inbox-arrow-down', '마이페이지', 'InfoMain'],
        ['mdi-send', 'Info', 'Login'],
        ['mdi-delete', 'About', 'About'],
        ['mdi-alert-octagon', '메인 북', 'MainBook'],
      ],
      tab : "About"
    }
  },
  computed:{
    component() {
      const tab = this.tab;
      return () => import(`@/views/mypage/${tab}`);
    }
  },
  methods : {
    pushLink(tab){
      console.log('push : ' + tab)
      this.tab= tab
    },
    logout(){
      console.log("logout!!")
    }
  }
}
</script>

<style scoped>
v-container{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-content: center;
}
.logout-btn{
  margin-top: 50%;
}
.info-nav{
  align-items: center;
  justify-content: center;
}
</style>