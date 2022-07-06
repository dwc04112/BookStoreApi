<template>
    <v-container fluid style="height: 100%; background-color: rgb(20,20,20)">
      <v-row class="justify-center">
        <v-col cols="12" md="6">
          <BookDetailComponent/>
        </v-col>
      </v-row>
      <v-row style="height: 180px"></v-row>
    </v-container>
</template>

<script>

import BookDetailComponent from "@/views/book/bookComponents/BookDetail";
export default {
  name: "DetailView",
  components: {BookDetailComponent},
  data: function (){
    return{
      //Tab model
      moveTabNum : 0,
    }
  },
  methods: {
  },

  mounted() {

  }
}

</script>

<style scoped>

</style>