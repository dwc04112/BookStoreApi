<template>
  <v-container fluid class="align-center">

    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="no-gutters pa-0 ma-0 mt-10" >
        <span class="main-title">구매 내역</span>
      </v-col>
    </v-row>

    <v-row class="pa-0 ma-0 justify-center align-center">
      <v-col cols="12" md="6" class="pa-0 ma-0 pl-1 pr-1 mt-6">
        <MyOrderComponent
            :key="componentKey"
            @childKey="componentKey++"
        >
        </MyOrderComponent>
      </v-col>
    </v-row>


    <!--my REVIEW-->
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

    <v-row style="height: 80px"></v-row>
  </v-container>
</template>

<script>
import MyCommentComponent from "@/views/mypage/infoComponents/MyCommentComponent";
import MyOrderComponent from "@/views/mypage/infoComponents/MyOrderComponent";
export default {
  name: "InfoActivity",
  components: {MyOrderComponent, MyCommentComponent},
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