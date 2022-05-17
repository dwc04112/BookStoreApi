<template>
  <v-container fluid class="pa-0 ma-0 align-center" style="background-color: rgb(24,24,24);">
    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="no-gutters pa-0 ma-0 mt-10">
        <span class="main-title">개인정보 수정</span>
      </v-col>
    </v-row>

    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="pa-0 ma-0 mt-6">
        <v-chip-group
            class="ma-0 pa-0 pb-1"
            mandatory
            active-class="red lighten-1 white--text"
            dark
        >
          <v-chip
              v-for="(data,index) in topChip"
              :key="index"
              filter
              @click="setInfoComponents(data.link)"
          >
            {{data.name}}
          </v-chip>
        </v-chip-group>
      </v-col>
    </v-row>

    <!-- 중간부분 책 리스트 -->
    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="12" md="6" class="ma-0 pa-0 pl-1 pr-1 mt-6" >
        <component
            :key="componentKey"
            :is="component"
        >
        </component>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import InfoEditComponent from "@/views/mypage/infoComponents/InfoEditComponent";
export default {
  name: "InfoEdit",
  components: {InfoEditComponent},
  data () {
    return {
      topChip : [
        {name:'회원정보 수정',link:'InfoEditComponent'},
        {name:'비밀번호 변경',link:'PasswordEditComponent'},
      ],

      //컴포넌트
      setComponent: 'InfoEditComponent', //불러올 컴포넌트
      componentKey: 0 // reload component
    }
  },

  methods:{
    setInfoComponents(data){
      this.setComponent = data
    },
  },

  computed:{
    // 컴포넌트에서 페이지 변경
    component() {
      let setComponent = this.setComponent
      return () => import(`@/views/mypage/infoComponents/${setComponent}`);
    }
  },
}
</script>

<style scoped>
.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(240,240,240);
}
</style>