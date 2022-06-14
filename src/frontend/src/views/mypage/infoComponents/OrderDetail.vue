<template>
  <v-row style="background-color: rgb(40,40,40)" class="pb-4 mt-1 justify-center align-center">
    <div class="white--text pa-4">{{this.$route.params.orderId}}</div>
    <v-btn @click="cancelPay()">환불하기</v-btn>
  </v-row>
</template>

<script>
export default {
  name: "orderDetail",
  data: () => ({



    //가상계좌 환불입력목록
    refund_holder : null,
    refund_bank : null,
    refund_account : null,
  }),

  methods: {
    cancelPay() {
      let data = {};
      data.merchant_uid = this.$route.query.orderId;

      data.refund_holder = this.refund_holder;
      data.refund_bank = this.refund_bank;
      data.refund_account = this.refund_account;
      this.$axios.post("payments/cancel",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response => {
        console.log(response.data);
      })
          .catch(error => {
        console.log(error.response);
      })
    },

  },

  mounted() {
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