<template>
  <v-app>
    <SearchMenu @drawMenu="drawMenu"></SearchMenu>
    <div style="height: 85px"></div>
    <v-row class="justify-center align-center" style="background-color: rgb(40,40,40);">
      <v-col cols="9" md="6" class="d-flex align-center justify-center">
          <v-tabs
              grow
              background-color="transparent"
              class="top-chip"
              color="white darken-1"
              dark
          >
            <v-tab
                v-for="(data,index) in links"
                :key="index"
                @click="pushLink(data.link)"
            >
              <span>{{data.name}}</span>
            </v-tab>

          </v-tabs>
      </v-col>
    </v-row>

    <!--
    <v-navigation-drawer
        class="info-nav"
        v-model="drawer"
        width="270px"
        app
        color="rgb(40,40,40)"
    >
      <v-sheet
          class="pa-4"
          color="rgb(40,40,40)"
      >
        <div class="d-flex justify-center pa-4">
          <v-avatar
              class="mb-4"
              color="grey darken-1"
              size="95"
          ></v-avatar>
        </div>

        <div style="font-size: 20px; text-align: center" class="white--text">{{this.$store.state.memberStore.nickName}} 님</div>
        <div style="font-size: 12px; text-align: center" class="pt-2 white--text">개인회원 / 일반등급</div>
      </v-sheet>

      <v-divider class="ma-6 white" />

      <v-list flat nav width="260px">
        <v-list-item-group
            v-model="selectedItem"
            color="primary"
        >
          <v-list-item
              v-for="[icon, text, url] in links"
              :key="icon"
              link
              class="ml-3"
              @click="pushLink(url)"

          >
            <v-list-item-icon >
              <v-icon color="white">{{ icon }}</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title style="color: white">{{ text }}</v-list-item-title>
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
    -->

    <v-container fluid class="ma-0 pa-0">
      <v-row class="ma-0 pa-0">
        <v-col cols="12" class="ma-0 pa-0">
            <component
                style="background-color: rgb(24,24,24)"
                :is="component"
                :key="componentKey"
                @updateChildKey="updateComponentKey"
            ></component>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>


<script>
import InfoWishList from "@/views/mypage/InfoWishList";
import SearchMenu from "@/views/SearchMenu";
export default {
  name: "InfoNavi",
  props:["AboutTab"],
  components: {SearchMenu, InfoWishList},
  data () {
    return {
      selectedItem : null,
      drawer: false,
      links: [
        {icon:'mdi-inbox-arrow-down', name:'마이페이지', link:'InfoMain'},
        {icon:'mdi-inbox-arrow-down', name:'나의활동', link:'InfoWishList'},
        {icon:'mdi-inbox-arrow-down', name:'나의찜목록', link:'InfoWishList'},
        {icon:'mdi-inbox-arrow-down', name:'관심키워드', link:'InfoWishList'},
      ],
      moveTab : "InfoMain",

      // 리 렌더링을 위해 부모 컴포넌트 Data 변경
      componentKey : 0,
    }
  },
  methods : {

    pushLink(data){
      if(data == null){
        console.log("tab is null")
      }else {
        this.moveTab = data
      }
    },
    logout(){
      console.log("logout!!")
    },

    // 리 렌더링을 위해 부모 컴포넌트 Data 변경
    updateComponentKey() {
      console.log("update reload")
      this.componentKey +=1
    },

    drawMenu(data){
      console.log("component data" + data)
      this.drawer = false
    },
  },
  computed:{
    component() {
      const moveTab = this.moveTab;
      return () => import(`@/views/mypage/${moveTab}`);
    }
  },
  mounted() {
    this.pushLink(this.AboutTab)
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
  margin-top : 30%;
  color: white;
}
.info-nav{
  margin-top: 80px;
  align-items: center;
  justify-content: center;
}

.top-chip span{
  font-size: 13px;
  font-weight: lighter;
}

</style>