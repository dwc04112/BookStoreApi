<template>
  <v-container fluid>

    <v-row>
      <v-col cols=12 md="3">
        <v-card height="100%" class="align-center flex-column d-flex" color="transparent" tile elevation="0">
          <v-sheet class="align-center justify-center flex-column d-flex" color="transparent">
            <v-avatar
                color="grey"
                size="110">
              <img :src="this.$store.state.member.userData.profilePicture"
                   style="object-fit: cover"
                   alt=""/>
            </v-avatar>
            <strong class="pt-5" style="color: rgb(190,190,190); font-size: 20px">{{ $store.state.member.userData.nickName }}</strong>
          </v-sheet>
          <v-divider class="ma-2" style="width: 60%" dark></v-divider>
        </v-card>
      </v-col>


      <v-col cols="12" md="7" class="pb-8">
        <div class="mb-8">
          <span class="main-title">결제내역</span>
        </div>


        <v-row class="align-center pa-0" style="background-color: rgb(30,30,30)">
          <v-col cols="12" class="pa-0">
            <v-simple-table dark v-if="orderData.payments">
              <tbody>
                <tr>
                  <td class="name-td">주문번호</td>
                  <td class="content-td">{{ orderData.payments.orderId }}</td>
                </tr>
                <tr>
                  <td class="name-td">결제일자</td>
                  <td class="content-td">{{ new Date(orderData.payments.paidAt*1000).toISOString().split('T')[0] }}</td>
                </tr>
                <tr>
                  <td class="name-td">결제목록</td>
                  <td class="pt-2 pb-2 content-td">
                    <div class="pt-1 pb-1"
                         v-for="(item,i) in  orderData.orders.orderItems"
                         :key="i">
                      <strong>{{ item.bookTitle }}</strong>
                      <span style="color: rgb(110,110,110)"> ({{item.bookCount}}권, {{item.bookSalePrice*item.bookCount}}원)</span>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="name-td">주문상태</td>
                  <td class="content-td">
                    <v-chip
                        :color="getColor(orderData.payments.payStatus)"
                        dark
                    >
                      {{ orderData.payments.payStatus  }}
                    </v-chip>
                  </td>
                </tr>
                <tr>
                  <td class="name-td">총 결제금액</td>
                  <td class="content-td">{{ orderData.payments.payAmount  }} 원</td>
                </tr>

                <tr v-show="orderData.payments.payStatus==='cancel'">
                  <td class="name-td">취소금액</td>
                  <td class="content-td">{{ orderData.payments.cancelAmount  }} 원</td>
                </tr>


                <tr v-show="orderData.payments.bankName!=null">
                  <td class="name-td">은행명</td>
                  <td class="content-td">{{ orderData.payments.bankName  }}</td>
                </tr>
                <tr v-show="orderData.payments.bankName!=null">
                  <td class="name-td">예금주</td>
                  <td class="content-td">{{ orderData.payments.bankHorder  }}</td>
                </tr>
                <tr>
                  <td class="name-td">결제수단</td>
                  <td class="content-td">{{ orderData.payments.payMethod  }}</td>
                </tr>
                <tr>
                  <td class="name-td">결제승인사</td>
                  <td class="content-td">{{ orderData.payments.pgProvide  }}</td>
                </tr>
              </tbody>
            </v-simple-table>
          </v-col>
        </v-row>

        <div class="mb-8 mt-8">
          <span class="main-title">배송지 정보</span>
        </div>


        <v-row class="align-center pa-0" style="background-color: rgb(30,30,30)">
          <v-col cols="12" class="pa-0">
            <v-simple-table dark v-if="orderData.payments">
              <tbody>
              <tr>
                <td class="name-td">구매자</td>
                <td class="content-td">{{ orderData.orders.buyerName  }}</td>
              </tr>
              <tr>
                <td class="name-td">연락처</td>
                <td class="content-td">{{ orderData.orders.phoneNum  }}</td>
              </tr>
              <tr>
                <td class="name-td">우편번호</td>
                <td class="content-td">{{ orderData.orders.postcode  }}</td>
              </tr>
              <tr>
                <td class="name-td" rowspan="2">배송지</td>
                <td class="pt-2 pb-2 content-td">
                  <div class="pt-1 pb-1">
                    <div class="content-td">{{ orderData.orders.addr  }}</div>
                    <div  class="content-td">{{ orderData.orders.detailAddr  }}</div>
                  </div>
                </td>
              </tr>
              </tbody>
            </v-simple-table>
          </v-col>

          <v-btn
              color="blue"
              class="white--text ma-4 mt-8"
              rounded
              @click="$router.go(-1)"
          ><span style="font-weight: bold; font-size: 15px">뒤로가기</span></v-btn>
          <v-spacer></v-spacer>
          <v-btn
              color="red"
              class="white--text ma-4 mt-8"
              rounded
              @click="cancelDialog=true"
          ><span style="font-weight: bold; font-size: 15px">결제취소</span></v-btn>

          <!--삭제 확인 메시지-->
          <v-dialog
              max-width="400"
              v-model="cancelDialog"
          >
            <v-card rounded color="rgb(55,55,55)" tile dark>
              <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">정말 결제를 취소하시겠습니까?</div>
              <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
                <v-btn
                    rounded
                    text
                    @click="cancelDialog =false"
                >Close</v-btn>
                <v-btn
                    class="ml-2"
                    rounded
                    elevation="0"
                    color="red"
                    @click="cancelPay"
                >Commit</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>

      </v-col>

    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "orderDetail",
  data: () => ({
    orderData : [],

    cancelDialog : false,
    //가상계좌 환불입력목록
    refund_holder : null,
    refund_bank : null,
    refund_account : null,
  }),

  methods: {

    getOrderDetail(){
      this.$axios.get("payments/"+this.$route.params.orderId)
          .then(response => {
            console.log(response.data)
            this.orderData = response.data.data
          }).catch(error => {
            console.log(error.response);
      })
    },


    cancelPay() {
      let data = {};
      data.merchant_uid = this.$route.params.orderId;

      data.refund_holder = this.refund_holder;
      data.refund_bank = this.refund_bank;
      data.refund_account = this.refund_account;
      this.$axios.post("payments/cancel",JSON.stringify(data),{
              headers: {
                "Content-Type": `application/json`,
              },
            }).then(response => {
              console.log(response.data);
              this.getOrderDetail();
              this.cancelDialog=false
            }).catch(error => {
              console.log(error.response);
            })
    },
    getColor (stateText) {
      if (stateText === 'paid') return 'green darken-4'
      else return 'orange darken-4'
    },
  },

  mounted() {
    this.getOrderDetail();
    window.scrollTo(0, 0);
  }
}
</script>

<style lang="scss">
.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(240,240,240);
}
tbody {
  tr:hover {
    background-color: transparent !important;
  }
}
.name-td{
  width: 30%;
}
.content-td{
  background-color: rgb(35,35,35);
}
</style>