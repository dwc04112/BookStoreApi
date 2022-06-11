<template>
  <v-row style="background-color: rgb(40,40,40)" class="pb-4 mt-1 justify-center align-center">
    <div class="white--text pa-4">{{this.$route.query.orderId}}</div>
    <v-btn @click="cancelPay()">환불하기</v-btn>
  </v-row>
</template>

<script>
export default {
  name: "OrderDetail",
  data: () => ({

  }),

  methods: {
    cancelPay() {
      alert("hello")
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