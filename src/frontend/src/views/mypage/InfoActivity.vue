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


    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="12" md="6" class="pa-0 ma-0 pl-1 pr-1 mt-6">
        <v-row style="background-color: rgb(40,40,40)" class="pb-4">

          <v-col cols="12" class="pa-0 mt-12 mb-3 text-center" v-show="noComments">
            <span style="color:rgb(80,80,80); font-size: 21px; font-weight: bold;">댓글이 없습니다. 첫번째 댓글을 작성해보세요</span>
          </v-col>

          <!-- 댓글 출력 -->
          <v-col
              cols="12" class="pa-0 mt-3 mb-3"
              v-for="(data , index) in commentData"
              :key="index"
          >
            <v-card color="rgb(40,40,40)" elevation="0">
              <v-row align="center" class="ma-0 pa-0">
                <v-col cols="3">
                  <v-card>
                    dfa
                  </v-card>
                </v-col>
                <v-col cols="8" class="pa-5 mt-5 mb-5 ">
                  <v-row class="pl-4 pr-4 pt-3 align-center">
                    <v-rating
                        :value="data.ratings"
                        color="yellow darken-2"
                        background-color="grey darken-2"
                        dense
                        readonly
                        length="4"
                        size="24"
                        class="pr-1"
                    ></v-rating>
                    <span class="date-text">{{data.commentDate}} 일에 작성됨</span>

                    <v-btn class="ml-4" outlined dark disabled x-small height="17">
                      <v-icon x-small color="#BDBDBD">mdi-thumb-up</v-icon>
                      <span class="pl-2 name-text">{{data.popularity}}</span>
                    </v-btn>
                  </v-row>

                  <v-row class="content-text pl-5 pr-5 pt-2">
                    <span>{{data.content}}</span>
                  </v-row>

                </v-col>
              </v-row>
            </v-card>

            <v-divider class="mt-12 mr-16 ml-16" dark></v-divider>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "InfoActivity",
  data: () => ({
        selectKeyword: 1, //선택된 키워드
        commentData : [],
        noComments : false,
    }
  ),
  methods: {
    getBookComment() {
      this.$axios.get("comment/myComment/")
          .then(response=>{
              this.commentData = response.data;
              if(response.data.length === 0){
                this.noComments = true;
              }
          }).catch(error =>{
        console.log(error.response);
      })
    },


    infoEdit(){
      console.log("hi")
    },
  },

  mounted() {
    this.getBookComment();
  }
}
</script>

<style scoped>
.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(240,240,240);
}

.pop-text{
  color: #BDBDBD;
  font-weight: bold;
  font-size: 20px;
}
.name-text{
  color: #BDBDBD;
}
.date-text{
  color: #BDBDBD;
  font-size: 13px;
}
.content-text{
  color: #BDBDBD;
  font-size: 15px;
}
</style>