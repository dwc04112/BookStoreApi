<template>
  <v-app>
    <SearchMenu @drawMenu="drawMenu" @moveTabNum="pushLink"></SearchMenu>
    <div style="height: 80px"></div>

    <v-row class="ma-0 pa-0 justify-center align-center" style="height: fit-content; background-color: rgb(40,40,40);">
      <v-col cols="9" md="6" class="ma-0 pa-0" >
        <v-tabs
            grow
            v-show="links[this.moveTabNum].show"
            v-model="moveTabNum"
            background-color="transparent"
            class="top-chip"
            :color="links[this.moveTabNum].color"
            dark
        >
          <v-tab
              v-for="(data,index) in links"
              :key="index"
              @click="pushLink(index)"
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

    <!-- update child 삭제? -->
    <v-container fluid class="ma-0 pa-0" style="height: calc(100vh - 80px); background-color: rgb(24,24,24)">
      <v-row class="ma-0 pa-0">
        <v-col cols="12" class="ma-0 pa-0">
            <component
                :is="component"
                :key="componentKey"
                @updateChildKey="updateComponentKey"
                @pushLink="pushLink"
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
    //  selectedItem : null,
    //  drawer: false,
      links: [
        {icon:'mdi-home', name:'My Page', link:'InfoMain', color:'red lighten-1', show:false},
        {icon:'mdi-pencil', name:'Edit Info', link:'InfoEdit', color:'red lighten-1', show:true},
        {icon:'mdi-cart', name:'Cart', link:'InfoWishList', color:'orange lighten-1', show:true},
        {icon:'mdi-library', name:'Wish List', link:'InfoWishList', color:'green lighten-1', show:true},
        {icon:'mdi-book', name:'Keyword', link:'InfoWishList', color:'blue lighten-1', show:true},
      ],
      //Tab model
      moveTabNum : 0,

      // 리 렌더링을 위해 부모 컴포넌트 Data 변경
      componentKey : 0,
    }
  },
  methods : {

    pushLink(data) {
      if (data == null) {
        console.log("tab is null")
      }else{
        //tab 이동
        this.moveTabNum = data
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
      const moveTab = this.links[this.moveTabNum].link;
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