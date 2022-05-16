<template>
  <v-container fluid class="pa-0 ma-0 align-center" style="background-color: rgb(24,24,24)">
    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="12" class="no-gutters pa-0 ma-0 mt-10">

      </v-col>
      <v-col cols="11" md="6" offset-md="3" class="pa-0 ma-0">
        <v-sheet color="rgb(24,24,24)" class="pa-6">
          <div class="d-flex justify-center pa-4">
            <v-avatar
                class="mb-4"
                color="grey darken-1"
                size="185"
            ></v-avatar>
          </div>
          <div style="font-size: 30px; text-align: center" class="white--text">{{this.$store.state.memberStore.nickName}} 님</div>
          <div style="font-size: 14px; text-align: center" class="pt-2 white--text">개인회원</div>
        </v-sheet>
      </v-col>
    </v-row>

    <v-row class=" pa-0 ma-0 justify-center align-center" >
      <v-col cols="11" md="6" class="mt-2">
        <v-row class="align-center">
          <v-col cols="6" class="text-center">
            <v-btn class="col-12 main-card-1 red lighten-1" height="80px" @click="pushLink(1)">
              <span class="main-card-inner">Edit Info</span>
              <v-icon class="pl-2 main-card-inner" color="rgb(240,240,240)">mdi-pencil</v-icon>
            </v-btn>
          </v-col>

          <v-col cols="6" class="text-center">
            <v-btn class="col-12 main-card-2 orange lighten-1" height="80px" @click="pushLink(2)">
              <span class="main-card-inner">Cart</span>
              <v-icon class="pl-2 main-card-inner" color="rgb(240,240,240)">mdi-cart</v-icon>
            </v-btn>
          </v-col>

          <v-col cols="6" class="text-center">
            <v-btn class="col-12 main-card-3 green lighten-1" height="80px" @click="pushLink(3)">
              <span class="main-card-inner">Wish List</span>
              <v-icon class="pl-2 main-card-inner" color="rgb(240,240,240)">mdi-library</v-icon>
            </v-btn>
          </v-col>

          <v-col cols="6" class="text-center">
            <v-btn class="col-12 main-card-4 blue lighten-1" height="80px" @click="pushLink(4)">
              <span class="main-card-inner">Activity</span>
              <v-icon class="pl-2 main-card-inner" color="rgb(240,240,240)">mdi-book</v-icon>
            </v-btn>
          </v-col>

        </v-row>
      </v-col>

      <v-col cols="12">
        <div style="height: 100vh"></div>
      </v-col>
    </v-row>


  </v-container>
</template>

<script>
export default {
  name: "InfoMain",
  data: () => ({
    loading: false,
  }),
  methods: {
    pushLink(tabNum){
      this.$emit('pushLink',tabNum)
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

/* main card */
.main-card-1{
  border-radius: 5px 5px 30px 5px;
  align-items: center;
  justify-content: center;
  display: flex;
}
.main-card-2{
  border-radius: 5px 5px 5px 30px;
  align-items: center;
  justify-content: center;
  display: flex;
}
.main-card-3{
  border-radius: 5px 30px 5px 5px;
  align-items: center;
  justify-content: center;
  display: flex;
}.main-card-4{
   border-radius: 30px 5px 5px 5px;
   align-items: center;
   justify-content: center;
   display: flex;
 }
.main-card-inner{
  font-size: 38px;
  font-weight: bold;
  color: rgb(240,240,240);
}
@media screen and (max-width: 768px) {
  .main-card-inner{
    font-size: 25px;
    font-weight: bold;
    color: rgb(240,240,240);
  }
}
</style>