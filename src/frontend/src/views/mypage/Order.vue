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

          <div v-for="data in orderState"
               :key="data.state"
               class="pa-1 d-flex align-center"
          >
            <span class="nav-text pt-3 pa-1">{{data.state}}</span>
            <strong
                class="pa-1"
                :class="data.count > 0 ? 'yellow--text text--darken-2' : 'grey--text text--darken-1'"
                style="font-size: 30px"
            >{{data.count}}</strong>

            <span class="nav-text pt-3 pa-1">건</span>
          </div>
          <v-divider class="ma-2" style="width: 60%" dark></v-divider>
        </v-card>
      </v-col>


      <v-col cols="12" md="7" class="pb-8 ml-md-4">
        <div class="mb-8">
          <span class="main-title">주문목록 / 상태</span>
        </div>

        <v-row class="mb-6 pa-0 align-center" style="background-color: rgb(40,40,40);">
          <v-col cols="12" md="5" class="justify-center d-flex ml-md-6">
            <v-btn-toggle
                dense
                dark
                rounded
                active-class="grey grey--text text--darken-4"
                v-model="selectMonth"
            >
              <v-btn
                  v-for="(month,i) in autoMonth"
                  :key="i"
                  :value="month"
                  @click="setDate(month)"
              >
                <span>{{ month }}월</span>
              </v-btn>
            </v-btn-toggle>
          </v-col>

          <v-col cols="12" md="6">
            <v-row class="justify-center align-center d-flex">
              <v-col cols="5">
                <v-menu
                    v-model="menu1"
                    :close-on-content-click="false"
                    :nudge-right="35"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                        v-model="fromDate"
                        prepend-icon="mdi-calendar"
                        readonly
                        hide-details
                        v-bind="attrs"
                        v-on="on"
                        dark
                        outlined
                        dense
                    ></v-text-field>
                  </template>
                  <v-date-picker
                      v-model="fromDate"
                      @input="(menu1 = false) || (selectMonth = null)"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-icon size="15px" class="white--text">mdi-tilde</v-icon>
              <v-col cols="4">
                <v-menu
                    v-model="menu2"
                    :close-on-content-click="false"
                    :nudge-right="35"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                    locale="zh-cn"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                        v-model="toDate"
                        readonly
                        hide-details
                        v-bind="attrs"
                        v-on="on"
                        dark
                        outlined
                        dense
                    ></v-text-field>
                  </template>
                  <v-date-picker
                      v-model="toDate"
                      @input="(menu2 = false) || (selectMonth = null)"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1" class="align-center d-flex">
                <v-btn elevation="0" fab x-small @click="searchByDate">
                  <v-icon>mdi-magnify</v-icon>
                </v-btn>
              </v-col>

            </v-row>
          </v-col>
        </v-row>

        <v-row class="pb-4 align-center pa-0" style="background-color: rgb(30,30,30); min-height: 200px;">
          <v-col class="pa-5 align-center" v-show="noOrders">
            <v-row class="justify-center mt-6">
              <span style="color:rgb(80,80,80); font-size: 21px; font-weight: bold;">주문내역이 없습니다.</span>
            </v-row>
          </v-col>

          <v-col cols="12" class="pb-1"></v-col>

          <v-col
              cols="12" class="pa-0"
              v-for="(data , index) in ordersData"
              :key="index"
          >
            <v-row align="center" class="ma-0 pa-0">

              <!--책 정보 & 댓글 영역-->


              <v-col cols="12" class="pa-0"
                v-for="(orderItem , index) in data.orderItems"
                :key="index">
                <v-row align="center" class="ma-0 pa-0">

                  <v-col cols="12" md="3" class="pl-8 justify-center align-center d-flex flex-column">
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
                    <v-row class="book-text ml-1 pa-0 ma-0" >
                      <router-link :to="`./order/${data.orderId}`" >
                        <span style="color: #BDBDBD">{{orderItem.bookTitle}}</span>
                      </router-link>
                    </v-row>

                    <v-row class="content-text ml-1 pa-0 ma-0 mt-1">
                      <span>{{orderItem.bookCount * orderItem.bookSalePrice}}원 / {{orderItem.bookCount}}권</span>
                    </v-row>
                    <v-row class="content-text ml-1 pa-0 ma-0 mt-1 mb-6">
                      <span class="grey--text text--darken-1">{{data.orderDate}}</span>
                    </v-row>
                  </v-col>

                  <v-col cols="3" md="2" class="pa-5 mt-5 mb-5 d-flex align-center justify-center">
                    <v-avatar :color="getColor(data.orderState)" size="13" class="mr-2"/>
                    <span class="book-state">{{data.orderState}}</span>
                  </v-col>
                </v-row>
                <v-divider v-show="vShow(index, data.orderItems.length-1)" class="ma-2 ml-10 mr-10" dark></v-divider>
              </v-col>
            </v-row>

            <v-col cols="12">
              <v-row class="align-center justify-center pa-0">
                <v-divider class="mt-3 mb-3" style="background-color: rgb(70,70,70); border: rgb(70,70,70) solid 1px"></v-divider>
              </v-row>
            </v-col>
          </v-col>
        </v-row>
      </v-col>



    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "Order",
  data: () => ({
    ordersData: [],
    noOrders : false,
    selectMonth: null,
    month : new Date().getMonth()+1,  //0~11로 출력
    fromDate : (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
    toDate: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
    menu1: false,
    menu2: false,

    orderState: [
      {state:'결제완료', count: 0},
      {state:'결제대기', count: 0},
      {state:'결제취소', count: 0},
    ],

    links: [
      {icon:'mdi-pencil', name:'개인정보 수정', link:'infoEdit', color:'red lighten-1', show:true},
    ],
  }),
  computed:{
    autoMonth(){
      let arr = [];
      for (let i = this.month-5; i <= this.month; i++) {
        arr.push(i);
      }
      return arr;
    }
  },
  watch:{
    ordersData:{
      deep : true,
      handler(val){
        this.orderState[0].count = val.filter(e=> e.orderState==="결제완료").length
        this.orderState[1].count = val.filter(e=> e.orderState==="결제대기중").length
        this.orderState[2].count = val.filter(e=> e.orderState==="결제실패").length
      }
    },
  },


  methods: {
    getBookOrders() {
      this.$axios.get("order/")
      .then(response => {
        this.ordersData = response.data
        this.noOrders = response.data.length < 1;
      }).catch(error => {
        console.log(error.response);
      })
    },

    searchByDate(){
      this.$axios.get("order/range/",{
        params: {
          fromDate:this.fromDate,
          toDate:this.toDate
        }
      })
      .then(response => {
        this.ordersData = response.data
        this.noOrders = response.data.length < 1;

      }).catch(error => {
        console.log(error.response);
      })
    },

    vShow(index , num){
      return index !== num;
    },

    setDate(month){
      let nowDate = new Date();
      let lastDay = new Date(nowDate.getFullYear(),month,0).getDate()
      month = month >= 10 ? month : '0' + month;
      this.fromDate = nowDate.getFullYear()+"-"+month+"-01"
      this.toDate = nowDate.getFullYear()+"-"+month+"-"+lastDay
    },

    getColor (stateText) {
      if (stateText === '결제중단') return 'yellow darken-4'
      else if (stateText === '결제실패') return 'red darken-4'
      else if (stateText === '결제취소') return 'red darken-4'
      else return 'green darken-4'
    },
  },

  mounted() {
    this.getBookOrders();
    window.scrollTo(0, 0);
  }
}
</script>

<style lang="scss">
.nav-text{
  color: rgb(170,170,170);
  font-size: 15px;
  font-weight: bold;
}
.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(200,200,200);
}
.book-text{
  color: rgb(120,120,120);
  font-size: 19px;
  font-weight: bold;
}
.book-state{
  color: rgb(170,170,170);
  font-size: 15px;
  font-weight: bold;
}

.content-text{
  color: rgb(120,120,120);
  font-size: 16px;
}


a {
  text-decoration: none;
}
</style>