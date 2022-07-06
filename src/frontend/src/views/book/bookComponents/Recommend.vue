<template>
  <v-row class="justify-center">
    <v-col cols="12" class="mb-8 pa-0">
      <v-row class="ml-2">
        <h2 class="grey--text mb-1">비슷한 키워드의 책</h2>
        <v-btn fab width="33"
               height="33"
               color="rgb(80,80,80)"
               outlined
               class="pa-0 ml-2"
               @click="getRecommend"
               style="border: rgb(80,80,80) solid 1.5px"
        >
          <v-icon color="rgb(100,100,100)">mdi-reload</v-icon>
        </v-btn>
      </v-row>

      <v-divider class="mt-3 ml-1 grey darken-2" dark></v-divider>
    </v-col>

    <v-col cols="12" class="pa-0 justify-center d-flex flex-column" >
      <h2 v-show="noBookData" class="mt-8 grey--text text--darken-2 pl-1">비슷한 책을 준비중이에요...</h2>
    </v-col>

    <v-col cols="12" class="pa-0 justify-center align-start d-flex flex-column">
      <div>
        <div
            style="display: inline-block;"
            v-for="(book, index) in bookData"
            :key="index"
        >
          <v-card height="220px" class="ma-6" tile @click="detailView(book.bid)">
            <img :src="book.bookThumb" alt="" class="img-class">
          </v-card>
          <v-card color="transparent" elevation="0" class="ma-6">
            <div class="re-bookTitle">{{ book.bookTitle }}</div>
            <div class="grey--text text--darken-2" style="font-size: 14px">{{ book.bookPublisher }}</div>
          </v-card>
        </div>
      </div>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "Recommend.vue",
  props : ["selectBid"],
  data: function (){
    return{
      bookData : [],
      noBookData : false
    }
  },
  methods: {
    getRecommend(){
      this.$axios.get('book/recommend/'+this.selectBid)
          .then(response=>{
            this.bookData = response.data;
            this.noBookData = response.data.length === 0;
          })
          .catch(error =>{
            console.log(error.response);
          })
    },

    //책 보러가기
    detailView(bid){
      this.$router.push({name: 'DetailView' ,query: {bid}})
    },
  },

  mounted() {
    this.getRecommend();
  }
}
</script>

<style scoped>
.re-bookTitle{
    color: rgb(200,200,200);
    font-size: 16px;
    font-weight: bold;
    max-width: 140px;
    white-space:nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.img-class{
  max-width: 100%;
  max-height: 100%;
}
</style>