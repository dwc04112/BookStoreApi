<template>
  <v-row style="background-color: rgb(40,40,40)" class="pb-4 justify-center align-center">
    <v-col cols="12" class="pa-0 mt-12 mb-3 text-center" v-show="noOrders">
      <span style="color:rgb(80,80,80); font-size: 21px; font-weight: bold;">댓글이 없습니다. 첫번째 댓글을 작성해보세요</span>
    </v-col>
    <v-col cols="12" class="pb-6"></v-col>
    <!-- 댓글 출력 -->

    <v-col
        cols="12" md="11" class="pa-0"
        v-for="(data , index) in ordersData"
        :key="index"
    >
      <v-row align="center" class="ma-0 pa-0">

        <!--책 정보 & 댓글 영역-->
        <v-col cols="12" class="pa-5">

          <v-row class="align-center justify-center pa-0 mt-1 mb-1">
            <v-divider class="ma-7" style="background-color: rgb(110,110,110); border: rgb(110,110,110) solid 1px"></v-divider>
          </v-row>

        </v-col>
        <v-col cols="12" class="pa-0"
          v-for="(orderItem , index) in data.orderItems"
          :key="index">
          <v-row align="center" class="ma-0 pa-0">

            <v-col cols="12" md="3" class=" justify-center align-center d-flex flex-column">
              <!--책 이미지 영역-->
              <v-img
                  :src="orderItem.bookThumb"
                  :lazy-src="orderItem.bookThumb"
                  alt="bookThumb"
                  max-height="150px"
                  max-width="100px"
              ><template v-slot:placeholder>
                <v-row
                    class="fill-height ma-0"
                    align="center"
                    justify="center"
                >
                  <v-progress-circular
                      indeterminate
                      color="black lighten-5"
                  ></v-progress-circular>
                </v-row>
              </template>
              </v-img>
            </v-col>
            <!--책 정보 & 댓글 영역-->
            <v-col cols="9" md="6" class="pa-5 mt-5 mb-5">
              <!--책 제목과 저자-->
              <v-row class="book-text ml-1 pa-0 ma-0" @click="pushOrderDetail(data.orderId)">
                <a style="color: #BDBDBD">{{orderItem.bookTitle}}</a>
              </v-row>
              <v-row class="content-text ml-1 pa-0 ma-0 mt-1">
                <span>{{orderItem.bookCount * orderItem.bookSalePrice}}원 / {{orderItem.bookCount}}권</span>
              </v-row>
              <v-row class="content-text ml-1 pa-0 ma-0 mt-1 mb-6">
                <span class="grey--text text--darken-1">{{data.orderDate}}</span>
              </v-row>
            </v-col>

            <v-col cols="3" md="2" class="pa-5 mt-5 mb-5">
              <span class="book-text">{{data.orderState}}</span>
            </v-col>
          </v-row>
          <v-divider v-show="vShow(index, data.orderItems.length-1)" class="ma-3 ml-10 mr-10" dark></v-divider>
        </v-col>
      </v-row>
    </v-col>

    <v-col cols="12" md="11">
      <v-row class="align-center justify-center pa-0 pt-1 pb-1">
        <v-divider class="ma-7" style="background-color: rgb(110,110,110); border: rgb(110,110,110) solid 1px"></v-divider>
      </v-row>
    </v-col>

  </v-row>
</template>

<script>
export default {
  name: "MyOrderComponent",
  data: () => ({
    ordersData: [],
    noOrders : false,


  }),

  methods: {
    getBookOrders() {
      this.$axios.get("order/")
      .then(response => {
        this.ordersData = response.data
        if(response.data.content.length === 0){
          this.noOrders = true;
        }

      }).catch(error => {
        console.log(error.response);
      })
    },
    vShow(index , num){
      return index !== num;
    },
    pushOrderDetail(orderId){
      this.$router.push({name: 'OrderDetail' ,query: {orderId}});
    }

  },

  mounted() {
    this.getBookOrders()
  }
}
</script>

<style scoped>
.book-text{
  color: #BDBDBD;
  font-size: 19px;
  font-weight: bold;
}
.book-subtext{
  color: #BDBDBD;
  font-size: 13px;
}

.content-text{
  color: #BDBDBD;
  font-size: 16px;
}
</style>