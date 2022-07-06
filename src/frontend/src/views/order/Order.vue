<template>
    <v-container fluid style="height: 100%; background-color: rgb(24,24,24)">
      <div style="height: 40px"></div>
      <v-row class="ma-0 pa-0 justify-center ">
        <v-col cols="12" style="height: 40px"/>
        <v-col cols="12" md="3"></v-col>

        <!--main-->
        <v-col cols="12" md="6">
          <v-row class="ma-1">
            <span class="top-text">주문목록</span>
          </v-row>


          <v-row style="background-color: rgb(40,40,40)" class="justify-center align-center">

            <v-col cols="12" md="11" class="pa-6"><v-divider dark></v-divider></v-col>

            <v-col cols="12" md="11" class="pa-0 "
                   v-for="(data,index) in bookData"
                   :key="index">
              <v-row align="center" class="ma-0 pl-4 pr-4 no-gutters ">
                <!--책 이미지 영역-->
                <v-col cols="12" md="2" class="pl-4">
                  <v-img
                      :src="data.bookThumb"
                      :lazy-src="data.bookThumb"
                      alt="bookThumb"
                      max-height="130"
                      max-width="80"
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
                <v-col cols="8" class="mt-5 mb-5">
                  <!--책 제목과 저자-->
                  <v-row class="book-text ml-1 ma-0">
                    <span>{{data.bookTitle}}</span>
                  </v-row>
                  <v-row class="book-subtext ml-1 ma-0">
                    <span>{{data.bookAuthor}} | {{data.bookPublisher}}</span>
                  </v-row>
                </v-col>

                <v-col cols="4" md="2" class="mt-5 mb-5 pr-4 align-end d-flex flex-column">
                  <span class="book-text">{{data.bookSalePrice}} 원</span>
                  <span class="book-subtext" style="font-size: 14px">수량 : {{data.bookCount}} 권</span>
                </v-col>

                <v-col cols="12" class="pt-6 pb-6">
                  <v-divider dark></v-divider>
                </v-col>
              </v-row>

            </v-col>
          </v-row>







          <v-row class="ma-1 mt-10">
            <span class="top-text">배송정보</span>
          </v-row>

          <v-row class="mb-12 justify-center" style="background-color: rgb(40,40,40)">
            <v-col cols="2" class="align-center d-flex"><span class="user-text" style="font-weight: bold">나의 주소록</span></v-col>
            <v-col cols="10" class="pa-0">
              <v-chip-group mandatory
                            class="pa-2"
                            active-class="primary darken-4"
                            dark>
                <v-chip @click="clearAddrData">
                  직접입력
                </v-chip>

                <v-chip
                    v-for="(data, index) in myAddrBook"
                    :key="index" @click="setAddrData(data)"
                >
                  {{data.addrName}}
                </v-chip>
              </v-chip-group>
            </v-col>


            <v-col cols="12" class="pa-0">
              <v-simple-table dark>
                <tbody>
                  <tr style="height: 80px">
                    <td class="name-td"><span style="font-size: 16px">이름</span></td>
                    <td class="content-td">
                      <v-col>
                        <v-row>
                          <v-text-field
                              outlined dense dark
                              hide-details
                              class="pt-1 user-text"
                              v-model="order.buyer_name"
                          ></v-text-field>
                        </v-row>
                      </v-col>
                    </td>
                  </tr>

                  <tr style="height: 80px">
                    <td class="name-td"><span style="font-size: 16px">핸드폰</span></td>
                    <td class="content-td">
                      <v-row>
                        <v-col>
                          <v-text-field
                              outlined dense dark text
                              hide-details
                              @input="replaceNum(num)"
                              class="text-fields white--text justify-start"
                              :placeholder="num"
                              v-model="num"
                          >
                          </v-text-field>
                        </v-col>
                      </v-row>
                    </td>
                  </tr>

                  <tr style="height: 80px">
                    <td class="name-td"><span style="font-size: 16px">주소</span></td>
                    <td class="content-td">
                      <v-row no-gutters class="pa-0 mt-6">

                        <v-col cols="6" md="3">
                          <v-text-field
                              outlined dense dark
                              hide-details
                              class=" user-text"
                              v-model="order.buyer_postcode"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="2" md="2">
                          <v-btn color="yellow darken-2" height="40px" class="ml-2" @click="getPostcode()">
                            <span style="color: rgb(40,40,40); font-weight: bold">우편번호</span>
                          </v-btn>
                        </v-col>

                        <v-col cols="12">
                          <v-text-field
                              outlined dense dark
                              class=" user-text mt-4"
                              hide-details
                              v-model="order.buyer_addr"
                          ></v-text-field>
                          <v-text-field
                              outlined dense dark
                              class="user-text mt-4"
                              v-model="order.buyer_detail_addr"
                              :hint="order.buyer_extra_addr"
                          ></v-text-field>
                        </v-col>

                      </v-row>
                    </td>
                  </tr>
                </tbody>
              </v-simple-table>
            </v-col>
          </v-row>
        </v-col>






        <!--right nav-->
        <v-col cols="12" md="3" class="mt-16">
          <v-row class="pa-0 ma-0">
            <v-card
                elevation="0"
                color="transparent"
                class="ma-8 mt-0 mb-4 nav-menu"
                width="300px"
                rounded
            >
              <v-divider></v-divider>
              <v-row v-for="(item,index) in orderItems" :key="index" class="pa-0 ma-8 nav-text">
                <span>{{item.text}}</span>
                <v-spacer/>
                <span style="font-weight: bold">{{item.data}} 원</span>
              </v-row>

              <v-divider dark></v-divider>
              <v-row class="pa-0 ma-4 justify-center align-center">
                <span class="nav-text">총 결제금액</span>
                <span style="font-size: 30px" class="yellow--text text--darken-2 pr-1 pl-1">{{order.amount}}</span>
                <span class="nav-text">원</span>
              </v-row>
              <v-divider dark></v-divider>

              <v-row class="pa-0 ma-5 mb-0 nav-text">
                <span>결제수단</span>
              </v-row>

              <v-row class="ma-0">
                <v-col class="d-flex justify-center">
                  <v-radio-group dark v-model="payRadios">
                    <v-radio :value="item.value" v-for="(item,index) in payItems" :key="index" class="pb-2">
                      <template v-slot:label>
                        <div class="white--text"><strong :class="item.color">{{item.text}}</strong> 로 결제하기</div>
                      </template>
                    </v-radio>
                  </v-radio-group>
                </v-col>
              </v-row>

              <v-divider></v-divider>
            </v-card>
          </v-row>

          <v-row class="pa-0 ma-0">
            <v-col class="ml-8 mr-8">
                <v-checkbox
                    v-model="orderCheck"
                    dark
                    class="mt-0"
                    color="yellow darken-2"
                    value="red"
                    label="주문내역확인 동의 (필수)"
                    hide-details
                ></v-checkbox>
            </v-col>
          </v-row>
          <v-row class="ma-0">
            <v-col class="pa-0" cols="12">
              <v-btn class="ma-8 mt-4" color="yellow darken-2" max-width="300px" width="80%" height="50px" @click="orderDataCheck">
                <span style="font-weight: bold; font-size: 20px; color: rgb(40,40,40)">결제하기</span>
              </v-btn>
            </v-col>
          </v-row>
        </v-col>
        <!--right nav End-->



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
</template>

<script>
const { IMP } = window;
export default {
  name: "Order",
  data: function (){
    return{
  //    bid : this.$route.query.bid,
  //    cartArr : this.$route.query.cartArr,
   //   orderData : this.$store.state.toOrderStore.bookList,
      bookData : [],
      myAddrBook : [],
      orderItems:[
        {text: '상품금액', data: 0, },
        {text: '배송금액', data: 0, },
        {text: '할인금액', data: 0, },
      ],
      payRadios : 'kakaopay',
      payItems: [
        {text:'카카오페이',icon:'mdi-checkbox-multiple-marked-circle',color:'yellow--text text--darken-2',size:'26px', value:'kakaopay'},
        {text:'그 외 결제수단',icon:'mdi-cart',color:'blue--text text--darken-2',size:'24px', value:'html5_inicis'},
      ],
      num : '',
      //주문관련
      impCode : 'imp85667087',
      orderCheck : false,
      order: {
        name : '',
        amount : 0,
        buyer_email : this.$store.state.member.loginData.email,
        buyer_name : this.$store.state.member.userData.fullName,
        buyer_postcode: '',
        buyer_addr: '',
        buyer_detail_addr: '',
        buyer_extra_addr: '',
      },

    }
  },



  methods: {
    pushLink(data) {
      if (data == null) {
        console.log("tab is null")
      } else {
        //tab 이동
        this.moveTabNum = data
      }
    },

    //책정보 받아오기
    getBookInfo() {
      let data =  this.$store.state.toOrderStore.bookList;
      this.$axios.post("book/order",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response => {
            this.bookData = response.data;
            //set
            //orderItems 리스트에 가격 set
            this.orderItems[0].data = response.data.map(e => (e.bookSalePrice*e.bookCount)).reduce((prev,curr) => prev + curr,0)
            //주문-amount = 최종금액. (책 가격 + 배송비 - 할인금액)
            this.order.amount = this.orderItems[0].data + this.orderItems[1].data - this.orderItems[2].data;
            //주문 item name
            if(response.data.length>1) {
              this.order.name = response.data[0].bookTitle + " 외 " + (response.data.length - 1) + "종"
            }else{
              this.order.name = response.data[0].bookTitle
            }
          })
          .catch(error => {
            console.log(error.response);
          })
    },
    getAddrBook(){
      this.$axios.get('addr/')
          .then(response=>{
            console.log(response.data)
            this.myAddrBook = response.data
          })
          .catch(error =>{
            console.log(error.response);
          })
    },

    replaceNum(num){
      this.num= num.replace(/[^0-9]/g, '')
          .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
    },

    setAddrData(data){
        this.num = data.phoneNum;
        this.order.buyer_postcode = data.postcode;
        this.order.buyer_addr = data.addr;
        this.order.buyer_detail_addr = data.detailAddr;
    },
    clearAddrData(){
      this.num = '';
      this.order.buyer_postcode = '';
      this.order.buyer_addr ='';
      this.order.buyer_detail_addr ='';
    },

    //
    //카카오 우편번호 api
    getPostcode(){
      new window.daum.Postcode({
        oncomplete: (data) => {
          if (this.order.buyer_extra_addr !== "") {
            this.order.buyer_extra_addr = "";
          }
          if (data.userSelectedType === "R") {
            // 사용자가 도로명 주소를 선택했을 경우
            this.order.buyer_addr = data.roadAddress;
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            this.order.buyer_addr = data.jibunAddress;
          }

          // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
          if (data.userSelectedType === "R") {
            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              this.order.buyer_extra_addr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== "" && data.apartment === "Y") {
              this.order.buyer_extra_addr +=
                  this.order.buyer_extra_addr !== ""
                      ? `, ${data.buildingName}`
                      : data.buildingName;
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (this.order.buyer_extra_addr !== "") {
              this.order.buyer_extra_addr = `(${this.order.buyer_extra_addr})`;
            }
          } else {
            this.order.buyer_extra_addr = "";
          }
          // 우편번호를 입력한다.
          this.order.buyer_postcode = data.zonecode;
        },
      }).open();
    },


    //유효성 검사
    orderDataCheck(){

      let nameTest = /^[가-힣a-zA-Z]+$/.test(this.order.buyer_name)
      let nameCheck = false
      let addrCheck = false
      let phoneCheck =  /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/.test(this.num)

      if(!nameTest){
        alert("이름을 확인해주세요")
        nameCheck = false;
        return false;
      }else{
        nameCheck = true;
      }

      if(!phoneCheck){
        alert("이름을 확인해주세요")
        phoneCheck = false;
      }

      if(this.order.buyer_postcode.length!==5){
        alert("우편번호는 5자리 입니다")
        addrCheck = false;
        return false;
      }else if(this.order.buyer_addr===''){
        alert("주소를 확인해주세요")
        addrCheck = false;
        return false;
      }else if(this.order.buyer_detail_addr==='') {
        alert("상세주소를 확인해주세요")
        addrCheck = false;
        return false;
      }else{
        addrCheck = true;
      }
      if(!this.orderCheck){
        alert("구매 동의를 체크해주세요")
        return false;
      }
      if(nameCheck && addrCheck && phoneCheck && this.orderCheck){
        this.createOrder();
      }
    },


    //주문번호 생성
    createOrder(){
      let data = {};
      data.bookOrder = this.$store.state.toOrderStore.bookList; //책정보
      data.buyerName = this.order.buyer_name;  //받는사람으로 씀
      data.postcode = this.order.buyer_postcode;                 //우편번호
      data.addr = this.order.buyer_addr;                          //주소
      data.detailAddr = this.order.buyer_detail_addr;          //상세주소
      data.phoneNum = this.num        //구매자 번호

      this.$axios.post("order/",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response=>{
        this.requestPay(this.payRadios, response.data.data);
      }).catch(error =>{
        console.log(error.response);
      })
    },

    //import 주문관련
    requestPay(pgData, orderId) {
      //1. 객체 초기화 (가맹점 식별코드 삽입)
      IMP.init(this.impCode);
      //3. 결제창 호출
      IMP.request_pay({
        pg: pgData,
        pay_method: 'card',
        merchant_uid: orderId,
        name: this.order.name,
        amount: this.order.amount,
        buyer_email: this.order.buyer_email,
        buyer_name: this.order.buyer_name,
        buyer_tel: this.num,
        buyer_addr: this.order.buyer_addr,
        custom_data : this.order.buyer_detail_addr,
        buyer_postcode: this.order.buyer_postcode
      }, rsp => { // callback
        console.log(rsp);
        if (rsp.success) {
          let data = {};
          data.imp_uid= rsp.imp_uid;
          data.merchant_uid= rsp.merchant_uid;
          console.log(data)
          this.$axios.post("payments/complete/",JSON.stringify(data),{
            headers: {
              "Content-Type": `application/json`,
            },
          }).then(response=>{
            console.log(response.data)
            //order state 초기화
            this.$store.dispatch('clearOrderState');
            alert("주문에 성공했습니다. 마이페이지로 이동합니다")
            this.$router.push({name: 'MyOrderComponent'})
          }).catch(error =>{
            console.log(error.response);
          })

        } else {
          let msg = '결제에 실패하였습니다.';
          msg += '에러내용 : ' + rsp.error_msg;
          console.log(msg)

          this.$axios.get("order/stop/"+rsp.merchant_uid)
              .then(response=>{
                console.log(response.data)
                //order state 초기화
                this.$store.dispatch('clearOrderState');
              }).catch(error =>{
                console.log(error.response);
              })
        }
      });
    },


  },
  mounted() {
    this.getBookInfo();
    this.getAddrBook();
    window.scrollTo(0, 0);
  }
}

</script>

<style lang="scss">
.nav-btn{
  display: none;
}

.nav-menu{
  outline: rgb(200,200,200) 1.5px solid;
  border-radius: 30px 30px 30px 30px;
}
.nav-text{
  color: rgb(200,200,200);
  font-size: 17px;
}


/*main*/
.top-text{
  color: #BDBDBD;
  font-size: 23px;
  font-weight: bold;
}
.user-text{
  color: #BDBDBD;
  font-size: 16px;
}

.book-text{
  color: #BDBDBD;
  font-size: 18px;
  font-weight: bold;
}
.book-subtext{
  color: #BDBDBD;
  font-size: 13px;
}

.name-td{
  width: 35%;
}
.content-td{
  background-color: rgb(35,35,35);
}
tbody {
  tr:hover {
    background-color: transparent !important;
  }
}

@media screen and (max-width: 960px){
  .nav-btn{
    display: block;
  }
}
</style>