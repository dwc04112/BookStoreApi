<template>
  <v-container fluid class="pa-0 ma-0 align-center" style="background-color: rgb(24,24,24)">

    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="no-gutters pa-0 ma-0 mt-10" >
        <span class="main-title">나의 활동</span>
      </v-col>
    </v-row>

    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="pa-0 ma-0 mt-6">
        <v-chip-group
            class="ma-0 pa-0 pb-1"
            v-model="selectKeyword"
            active-class="deep-purple accent-4 white--text"
            column
        >
          <v-chip>5:30PM</v-chip>

          <v-chip>7:30PM</v-chip>

          <v-chip>8:00PM</v-chip>

          <v-chip>9:00PM</v-chip>
        </v-chip-group>

        <v-btn
            color="deep-purple lighten-2"
            @click="infoEdit"
        >
          회원정보 수정
        </v-btn>
      </v-col>
    </v-row>




    <!-- 2222222 -->

    <v-row class=" pa-0 ma-0 justify-center align-center pt-10">
      <v-col cols="11" md="6" class="no-gutters pa-0 ma-0 mt-10" >
        <span class="main-title">나의 리뷰</span>
      </v-col>
    </v-row>

    <v-row class="pa-0 ma-0 justify-center align-center">
      <v-col cols="12" md="6" class="pa-0 ma-0 pl-1 pr-1 mt-6">
        <MyCommentComponent
          :key="componentKey"
          @childKey="componentKey++"
        >
        </MyCommentComponent>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import MyCommentComponent from "@/views/mypage/infoComponents/MyCommentComponent";
export default {
  name: "InfoActivity",
  components: {MyCommentComponent},
  data: () => ({
        selectKeyword: 1, //선택된 키워드
        componentKey: 0 // reload component
    }
  ),
  methods: {
    infoEdit(){
      console.log("hi")
    },

  },

  mounted() {
  }
}
</script>

<style scoped>
.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(240,240,240);
}

</style>