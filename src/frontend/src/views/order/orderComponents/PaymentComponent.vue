<template>
  <v-container fluid class="ma-0 pa-0">

    <v-row class="ma-1">
      <span class="top-text">구매상품</span>
    </v-row>

    <v-row align="center" class="mb-12 pa-3" style="background-color: rgb(40,40,40)">
      <!--책 이미지 영역-->
      <v-col cols="3" md="3" class="justify-center align-center d-flex flex-column">
        <v-img
            :src="bookData.bookThumb"
            :lazy-src="bookData.bookThumb"
            alt="bookThumb"
            max-height="150"
            max-width="100"
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
      <!--책 이미지 영역 끝-->

      <v-col cols="8" md="8" class="mt-5 mb-5">
        <!--책 제목과 저자-->
        <v-row class="book-text ml-1 pa-0 ma-0">
          <span>{{bookData.bookTitle}}</span>
        </v-row>
        <v-row class="book-subtext ml-1 pa-0 ma-0 mb-6">
          <span>{{bookData.bookAuthor}} | {{bookData.bookPublisher}}</span>
        </v-row>
        <v-row class="content-text ml-1 pa-0 ma-0 pt-1">
          <span>정보영역</span>
        </v-row>
      </v-col>
    </v-row>

    <v-row class="ma-1">
      <span class="top-text">배송정보</span>
    </v-row>

    <v-row class="mb-12" style="background-color: rgb(40,40,40)">
      <v-col class="pa-6">

        <v-row  no-gutters>
          <v-col cols="3" md="2" class="ma-2">
            <span class="user-text">이름</span>
          </v-col>
          <v-col cols="8" md="3">
            <v-text-field
                outlined dense dark
                class="pt-1 user-text"
                v-model="order.buyer_name"
            ></v-text-field>
          </v-col>
        </v-row>

        <v-row no-gutters>
          <v-col cols="3" md="2" class="ma-2">
            <span class="user-text">핸드폰</span>
          </v-col>
          <v-col cols="8" md="3">
              <v-select
                  v-model="firstNum"
                  :items="phoneItems"
                  dense
                  dark
                  outlined
              ></v-select>
              <v-text-field
                  outlined dense dark
                  class="pt-1 user-text"
                  v-model="order.buyer_tel2"
              ></v-text-field>
              <v-text-field
                  outlined dense dark
                  class="pt-1 user-text"
                  v-model="order.buyer_tel3"
              ></v-text-field>
          </v-col>
        </v-row>

        <v-row no-gutters>
          <v-col cols="3" md="2" class="ma-2">
            <span class="user-text">주소</span>
          </v-col>
          <v-col cols="8" md="8">
            <v-text-field
                outlined dense dark
                class="pt-1 user-text"
                v-model="order.buyer_postcode"
            ></v-text-field>

            <v-text-field
                outlined dense dark
                class="pt-1 user-text"
                v-model="order.buyer_addr"
            ></v-text-field>
            <v-text-field
                outlined dense dark
                class="pt-1 user-text"
                v-model="order.buyer_detail_addr"
            ></v-text-field>
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

      firstNum : '010',
      phoneItems : ['010','011','016','017','018','019'],

      impCode : 'imp85667087',
      order: {
        name : '',
        amount : 0,
        buyer_email : 'dwc04112@gmail.com',
        buyer_tel : '010-1234-5678',
        buyer_name : 'test',
        buyer_addr : '서울특별시 영등포구 당산동',
        buyer_detail_addr : '아파트 500동 500호',
        buyer_postcode : '07222',
      }
    }
  },

  methods: {
    getBookInfo(){
      this.$axios.get('book/info/'+this.bid)
          .then(response=>{
            this.bookData = response.data;
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
.top-text{
  color: #BDBDBD;
  font-size: 23px;
  font-weight: bold;
}
.user-text{
  color: #BDBDBD;
  font-size: 18px;
}

.book-text{
  color: #BDBDBD;
  font-size: 18px;
}
.book-subtext{
  color: #BDBDBD;
  font-size: 13px;
}


.date-text{
  color: #BDBDBD;
  font-size: 13px;
}
.content-text{
  color: #BDBDBD;
  font-size: 15px;
}
</style>