<template>
  <v-row class="justify-center">
    <v-col cols="12" class="mb-8 pa-0">
      <h2 class="grey--text pl-1">비슷한 키워드의 책</h2>
      <v-divider class="mt-3 ml-1 grey darken-2" dark></v-divider>
    </v-col>

    <v-col cols="12" class="pa-0 justify-center d-flex">
      <h2 v-show="noBookData" class="mt-8 grey--text text--darken-2 pl-1">비슷한 책을 준비중이에요...</h2>
    </v-col>

    <v-col cols="12" class="pa-0 justify-center d-flex">
      <div
          style="display: inline-block;"
          class="pa-8"
          v-for="(book, index) in bookData"
          :key="index"
      >
        <v-card
            style="height: 230px; overflow: hidden;"
            elevation="8"
            min-width="90"
            tile dark
        >
          <img
              :src="book.bookThumb"
              alt="bookThumb"
              class="book-thumb"
              height="100%"
              loading="lazy"/>
        </v-card>
        <div class="grey--text mt-2" style="font-weight: bold; font-size: 17px">{{ book.bookTitle }}</div>
        <div class="grey--text text--darken-2" style="font-size: 14px">{{ book.bookPublisher }}</div>
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
    }
  },

  mounted() {
    this.getRecommend();
  }
}
</script>

<style scoped>

</style>