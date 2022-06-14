<template>
  <v-container fluid class="align-center">

    <v-row>
      <v-col cols="12" md="8" class="pa-0 pb-8">
        <span class="main-title">장바구니</span>
        <div class="white--text"> {{selected}}</div>
      </v-col>
    </v-row>

    <v-row class="pa-0">
      <v-col cols="12" md="8">
        <v-row style="background-color: rgb(40,40,40)" class="pb-4 justify-center align-center">

          <v-col cols="3" class="pa-0">
              <v-checkbox
                  dark class="mb-3"
                  on-icon="mdi-check-circle blue--text"
                  off-icon="mdi-check-circle-outline white--text"
                  label="전체선택"
                  v-model="selectAll"
                  @click="selectAllBtn"
                  hide-details/>
          </v-col>

          <v-col cols="8" class="pa-0 justify-end d-flex">
            <v-btn
                class="mt-1 mr-2"
                rounded small
                @click="selectDelete"
            >
              <span style="font-size: 15px">선택삭제</span>
              <v-icon class="pl-1" size="17">mdi-delete</v-icon>
            </v-btn>
          </v-col>

          <v-col cols="12" class="pa-0 mb-4">
            <v-divider class="ma-2 ml-7 mr-7" style="background-color: rgb(180,180,180); border: rgb(180,180,180) solid 1px"></v-divider>
          </v-col>


          <!-- cart 없을때 -->
          <v-col cols="12" class="pa-0 mt-14 mb-14 text-center" v-show="noCart">
            <span style="color:rgb(80,80,80); font-size: 21px; font-weight: bold;">장바구니에 책이 없습니다. 책을 담아보세요!</span>
          </v-col>


          <!--책 이미지 영역-->
          <v-col cols="12" md="11" class="pa-0"
                 v-for="(data,index) in bookData"
                 :key="index"
          >
            <v-row align="center" class="ma-0 pa-0">
              <v-col cols="1" class="justify-end d-flex">
                <v-checkbox
                    class="pb-4" v-model="selected" :value="data.cartId"
                    on-icon="mdi-check-circle blue--text"
                    off-icon="mdi-check-circle-outline white--text"
                    hide-details/>
              </v-col>

              <v-col cols="4" md="2" class="justify-center d-flex">

                <v-img
                    :src="data.bookThumb"
                    :lazy-src="data.bookThumb"
                    alt="bookThumb"
                    max-height="95%"
                    max-width="100%">
                  <template v-slot:placeholder>
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
              <v-col cols="7" md="6" class="mt-5 mb-5">
                <!--책 제목과 저자-->
                <v-row class="book-text ml-1 ma-0">
                  <span>{{data.bookTitle}}</span>
                </v-row>
                <v-row class="book-subtext ml-1 ma-0">
                  <span>{{data.bookAuthor}} | {{data.bookPublisher}}</span>
                </v-row>
              </v-col>

              <!-- 가격 -->
              <v-col cols="12" md="3" class="mt-2">

                <v-row no-gutters class="ma-0 justify-center">
                  <v-col cols="11" md="8" class="pa-0">
                    <v-text-field
                        dark dense solo
                        v-model="data.fixCount" class="book-count pb-0 mb-0 elevation-0"
                        height="22px"  hide-details
                    >
                      <template v-slot:prepend-inner >
                        <v-icon size="18px" @click="setCount(index,1)">mdi-minus</v-icon>
                      </template>
                      <template v-slot:append >
                        <v-icon size="18px" @click="setCount(index,0)">mdi-plus</v-icon>
                      </template>
                    </v-text-field>
                  </v-col>
                </v-row>

                <v-row no-gutters class="justify-center">
                  <v-col cols="11" md="8" class="text-center" >
                    <v-card class="book-amount-card pt-2 pb-2" tile elevation="0">
                      <span style="font-size: 20px; font-weight: bold; color: rgb(170,170,170)">{{data.bookSalePrice * data.bookCount}} 원</span>
                    </v-card>
                  </v-col>
                </v-row>

                <v-row class="ma-0 justify-center">
                  <v-col cols="11" md="8" class="pa-0">
                    <v-btn width="100%" elevation="0" class="book-count-btn" x-small @click="saveCount(index)">
                      <span style="font-size: 13px; font-weight: bold">SAVE</span>
                    </v-btn>
                  </v-col>
                </v-row>

              </v-col>

              <v-col cols="12" class="pt-4">
              <v-divider dark class="ml-2 mr-2"></v-divider>
              </v-col>

            </v-row>
          </v-col>
        </v-row>
      </v-col>

      <!--right nav-->
      <v-col cols="12" md="4">
        <v-row class="pa-0 justify-center">
          <v-card
              elevation="0"
              color="rgb(40,40,40)"
              width="80%"
              class="mt-0 mb-4"
              tile
          >
            <v-row class="pa-0 ma-4 nav-text justify-center">
              <span class="yellow--text text--darken-2 pr-1">{{totalCount}}</span>
              <span style="font-weight: bold">권을 선택하셨습니다</span>
            </v-row>

            <v-divider style="background-color:rgb(200,200,200)"/>

            <v-row class="pa-0 ma-3 justify-center align-center">
              <span class="nav-text">총 결제금액</span>
              <span style="font-size: 30px" class="yellow--text text--darken-2 pb-1 pr-1 pl-1">
                {{totalAmount}}
              </span>
              <span class="nav-text">원</span>
            </v-row>
          </v-card>
        </v-row>

        <v-row>
          <v-col class="pa-0 d-flex justify-center">
            <v-btn color="yellow darken-2" tile width="80%" height="50px" dark @click="linkOrder" :disabled="selected.length<1">
              <span style="font-weight: bold; font-size: 20px; color: rgb(40,40,40)">결제하기</span>
            </v-btn>
          </v-col>
        </v-row>
      </v-col>
      <!--right nav End-->

    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "InfoCart",
  data: function (){
    return{
      bookData:[],
      bid: 5,

      noCart : false, //댓글없을때

      selectAll: false,
      totalCount : 0,
      totalAmount : 0,

      selected :[],
    }
  },
  watch: {

    //이중비교
    //1. 수량변경
    //수량 업데이트시 변경되는 bookData 값을 확인하고 수량과 금액을 변경
    bookData: {
      deep : true,
      handler(val){
        let total = val.filter(e => this.selected.includes(e.cartId))
        this.totalCount = total.map(e=>e.bookCount).reduce((prev,curr) => prev + curr,0)
        this.totalAmount = total.map(e => (e.bookSalePrice * e.bookCount)).reduce((prev,curr) => prev + curr,0)
      }
    },
    //2. 책 선택
    //책 선택시 변경되는 selected 값을 확인하고 수량과 금액을 변경
    selected(val){
      if(this.bookData.length === 0){
        this.selectAll = false
      }else {
        this.selectAll = this.bookData.length === val.length; //전체선택
      }

      let total = this.bookData.filter(e => val.includes(e.cartId))
      this.totalCount = total.map(e=>e.bookCount).reduce((prev,curr) => prev + curr,0)
      this.totalAmount = total.map(e => (e.bookSalePrice * e.bookCount)).reduce((prev,curr) => prev + curr,0)
    }
  },

  methods: {

    getBookInfo() {
      this.$axios.get('cart/')
          .then(response => {
            this.bookData = response.data;
          //  this.selected = response.data.map(e=>e.cartId)
            for(let i =0; i<response.data.length; i++){
              // this.$set(this.bookData[i],'select', true)
              this.$set(this.bookData[i],'fixCount',response.data[i].bookCount) //수량변경용 fixCount
            }
            if(response.data.length === 0){
              this.noCart = true;
            }
            this.selected=[]

          })
          .catch(error => {
            console.log(error.response);
          })
    },

    selectAllBtn(){
      if(this.selectAll === true) {
        this.selected =  this.bookData.map(e=>e.cartId)
      }else{
        this.selected = []
      }
    },
    // 수량변경 - fixCount 변경 (저장x)
    setCount(index,data){
      if(data === 0){
        this.bookData[index].fixCount = this.bookData[index].fixCount +1
      }
      if(data === 1 &&  this.bookData[index].fixCount >1){
        this.bookData[index].fixCount = this.bookData[index].fixCount -1
      }
    },

    saveCount(index){
      let data = {};
      data.cartId = this.bookData[index].cartId;
      data.bookCount = this.bookData[index].fixCount;

      this.$axios.post("cart/count",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response=>{
        console.log(response.data)
        this.getBookInfo()
      }).catch(error =>{
        console.log(error.response);
      })
    },

    //선택삭제
    selectDelete(){
      let cartIdList = this.selected
      this.$axios.delete("cart/"+cartIdList)
          .then(response=>{
            console.log(response.data)
            alert("선택한 책이 성공적으로 삭제되었습니다.")

            this.getBookInfo();
          }).catch(error =>{
            console.log(error.response);
          })
    },

    //주문으로 이동
    linkOrder(){
      let cartArr = this.bookData.filter(e => this.selected.includes(e.cartId))
      cartArr = cartArr.map( e=>{
        return {
          bid: e.bid,
          bookCount: e.bookCount
        }
      })
      this.$store.dispatch('getOrderByCart',cartArr);
      this.$router.push({name: 'Order'});
    },

  },

  mounted() {
    this.getBookInfo();
  }
}
</script>

<style lang="scss">
.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(240,240,240);
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

.book-count{
  background-color: rgb(40,40,40) !important;
  border-radius: 5px 5px 0 0;
}
.book-count-btn{
  background-color: rgb(70,70,70) !important;
  color: rgb(170,170,170) !important;
  border: rgb(70,70,70) 1.5px solid;
  border-radius: 0 0 5px 5px;
  border-top: 0;
  margin-top: -8px;
}
.book-amount-card{
  background-color: rgb(40,40,40) !important;
  border: rgb(70,70,70) 1.5px solid !important;
  margin-top: -1px;
}


.nav-menu{
  outline: rgb(160,160,160) 1.5px solid;
}
.nav-text{
  color: rgb(200,200,200);
  font-size: 17px;
}

.book-count .v-input__control .v-input__slot {
  background-color: transparent !important;
  border: rgb(70,70,70) 1.5px solid !important;
  border-bottom: 0 !important;
  min-height: 0 !important;
  padding: 4px 12px !important;
  font-size: 18px !important;
  margin-bottom: 0 !important;
  display: flex !important;
  align-items: center !important;
}
.book-count .v-input__control {
  margin-bottom: -15px !important;
}
.book-count input {
  text-align: center !important;
  margin-bottom: 2px;
}


</style>