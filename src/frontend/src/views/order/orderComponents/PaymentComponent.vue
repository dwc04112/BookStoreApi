<template>
  <v-container fluid class="ma-0 pa-0" >
    <v-row>
      <v-col cols="12" style="background-color: rgb(40,40,40)">


        <v-row>
          <div>{{bookData}}</div>
        </v-row>

        <v-row>
          <v-col>
            <h2 class="white--text">iamPort-checkout</h2>
            <input class="grey--text" v-model="impCode" placeholder="가맹점 식별코드">
            <input class="grey--text" v-model="order.name" placeholder="상품명">
            <input class="grey--text" v-model="order.amount" placeholder="상품가격">
            <input class="grey--text" v-model="order.buyer_tel" placeholder="주문자 전화번호">
          </v-col>
        </v-row>

        <v-row>
          <v-col>
            <v-btn @click="requestPay('kakaopay')">카카오</v-btn>
            <v-btn @click="requestPay('tml5_inicis')">전체</v-btn>
          </v-col>
        </v-row>


      </v-col>
    </v-row>
  </v-container>
</template>


<script>
const { IMP } = window;
export default {
  name: "PaymentComponent",
  //arrow not this
  data: function (){
    return{
      bid : this.$route.query.bid,
      bookData:[],

      impCode : 'imp85667087',
      order: {
        name : '닭',
        amount : 100,
        buyer_email : 'dwc04112@gmail.com',
        buyer_tel : '010-1234-5678',
        buyer_name : 'test',
        buyer_addr : '서울특별시 영등포구 당산동',
        buyer_postcode : '07222',
      }
    }
  },

  methods: {
    getBookInfo(){
      this.$axios.get('book/info/'+this.bid)
          .then(response=>{
            this.bookData = response.data
          })
          .catch(error =>{
            console.log(error.response);
          })
    },
    requestPay(pgData) {
      //1. 객체 초기화 (가맹점 식별코드 삽입)
      IMP.init(this.impCode);
      //3. 결제창 호출
      IMP.request_pay({
        pg : pgData,
        pay_method : 'card',
        merchant_uid : 'merchant_' + new Date().getTime(),
        name : this.order.name,
        amount : this.order.amount,
        buyer_email:  this.order.buyer_email,
        buyer_name:  this.order.buyer_name,
        buyer_tel:  this.order.buyer_tel,
        buyer_addr:  this.order.buyer_addr,
        buyer_postcode:  this.order.buyer_postcode
      }, rsp => { // callback
        console.log(rsp);
        if (rsp.success) {
          let msg = '결제가 완료되었습니다.';
          msg += '고유ID : ' + rsp.imp_uid;           //결제번호
          msg += '상점 거래ID : ' + rsp.merchant_uid;  //주문번호
          msg += '결제 금액 : ' + rsp.paid_amount;
          msg += '카드 승인번호 : ' + rsp.apply_num;
          console.log(msg)
        } else {
          let msg = '결제에 실패하였습니다.';
          msg += '에러내용 : ' + rsp.error_msg;
          console.log(msg)
        }
      });
    }
  },
  mounted() {
    this.getBookInfo();
    window.scrollTo(0, 0);
  }
}
</script>

<style scoped>

</style>