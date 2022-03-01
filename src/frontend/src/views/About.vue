<template>
  <v-container
      fluid
      style="background-color: #FFBBBB;"

  >
    <v-layout wrap row>
      <v-flex
          xs12 sm6 md2 class="pb-2"
          v-for="(book, index) in bookDatas"
          :key="index"
      >
        <v-card class="mx-auto">
          <v-img :src="book.bookThumb" width="50%" height="50%"></v-img>
          <v-card-title>
            {{book.bookTitle}}
          </v-card-title>
          <v-card-subtitle>{{book.bookKeyword}}</v-card-subtitle>
          <v-card-subtitle>{{book.bookAuthor}}</v-card-subtitle>
        </v-card>

      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>


export default {
  name: "About",

  data: function (){
    return{
      bookDatas : [],
    }},

  methods: {
    getBookInfo(){

      this.$axios.get('book/info')
      .then(response=>{
        console.log(response.data);
        this.bookDatas = response.data
      })
      .catch(error =>{
        console.log(error.response);
      })
    }
  },
  mounted() {
    this.getBookInfo();
  }
}
</script>


<style scoped>

</style>