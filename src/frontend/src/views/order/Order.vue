<template>
  <v-app>
    <SearchMenu @moveTabNum="pushLink"></SearchMenu>
    <div style="height: 80px"></div>

    <v-container fluid style="height: 100%; background-color: rgb(24,24,24)">
      <v-row class="ma-0 pa-0 justify-center ">

        <v-col cols=12 style="height: 80px"/>
        <v-col cols="12" md="6" class="ma-0 pa-0">
          <PaymentComponent/>
        </v-col>

        <v-row class="nav-btn" style="position:absolute; right: 8%">
          <v-col cols="12" class="ma-8">
            <v-btn fab elevation="0" width="50px" height="50px" color="yellow darken-2" style="position: fixed; bottom: 5%" @click="$vuetify.goTo(0)">
              <v-icon color="rgb(60,60,60)" size="40">mdi-chevron-up</v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-row>

      <v-row style="height: 80px"></v-row>

    </v-container>
  </v-app>
</template>

<script>
import SearchMenu from "@/views/SearchMenu";
import PaymentComponent from "@/views/order/orderComponents/PaymentComponent";

export default {
  components: {PaymentComponent, SearchMenu,},
  name: "Order",
  data: function (){
    return{
      bookData : [],
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
    window.scrollTo(0, 0);
  }
}

</script>

<style scoped>
.nav-btn{
  display: none;
}
@media screen and (max-width: 960px){
  .nav-btn{
    display: block;
  }
}
</style>