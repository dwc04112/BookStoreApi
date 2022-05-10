<template>
  <v-app>
    <SearchMenu @moveTabNum="pushLink"></SearchMenu>
    <div style="height: 80px"></div>

    <v-container fluid class="ma-0 pa-0" style="height: 100%; background-color: rgb(24,24,24)">
      <v-row class="ma-0 pa-0 justify-center">
        <v-col cols="12" md="6" class="ma-0 pa-0">
          <BookDetailComponent/>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>

import BookDetailComponent from "@/views/book/bookComponents/BookDetailComponent";
import SearchMenu from "@/views/SearchMenu";
export default {
  name: "DetailView",
  components: {SearchMenu, BookDetailComponent},
  data: function (){
    return{


      //Tab model
      moveTabNum : 0,
    }
  },
  methods: {
    pushLink(data) {
      if (data == null) {
        console.log("tab is null")
      }else{
        //tab 이동
        this.moveTabNum = data
      }
    },
  },

  mounted() {

  }
}

</script>

<style scoped>


</style>