<template>
  <v-row class="pa-0">
    <v-col>
      <v-card dark class="pa-4">
        <v-card-title>
          <h3>주문 관리</h3>
        <v-spacer></v-spacer>
        <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
        ></v-text-field>
          </v-card-title>
        <v-data-table
            dark
            height="650px"
            :search="search"
            :headers="headers"
            :items="orderData"
            item-key="name"
            class="elevation-0"
        >
          <template v-slot:item.orderState="{ item }">
            <v-chip
                :color="getColor(item.orderState)"
                dark
            >
              {{ item.orderState }}
            </v-chip>
          </template>

          <template v-slot:item.cancel="{ item }">
            <v-btn :disabled="item.orderState==='결제중단'"
                   rounded small elevation="0"
                   :to="`./order/${item.orderId}`"
            >
              상세보기
            </v-btn>
          </template>

        </v-data-table>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "AdminOrder",
  data: () => ({
    orderData: [],
    search:'',

    dialog:false,
    cancelId:null,

    headers: [
      {
        text: '주문번호',
        align: 'start',
        sortable: false,
        value: 'orderId',
        width:'200'
      },
      { text: '주문자고유번호', value: 'mid', width:'150' },
      { text: '상품명', value: 'orderItemName', width:'400' },
      { text: '주문날짜', value: 'orderDate', width:'150' , align: 'center', },
      { text: '주문시간', value: 'orderTime', width:'150', align: 'center', },
      { text: '주문상태', value: 'orderState', width:'170', align: 'center', },
      { text: '결제취소', value: 'cancel', width:'90', sortable: false,  align: 'center',},
    ],
  }),
  methods: {
    getOrdersByAdmin() {
      this.$axios.get("admin/order")
          .then(response => {
            this.orderData = response.data
            for(let i=0; i<response.data.length; i++){
              if(response.data[i].orderItems.length>1){
                this.orderData[i].orderItemName = response.data[i].orderItems[0].bookTitle + "외 " +response.data[i].orderItems.length+"종"
              }
              this.orderData[i].orderItemName = response.data[i].orderItems[0].bookTitle
            }
          }).catch(error => {
        console.log(error.response);
      })
    },


    getColor (stateText) {
      if (stateText === '결제중단') return 'yellow darken-4'
      else if (stateText === '결제실패') return 'red darken-4'
      else if (stateText === '결제취소') return 'red darken-4'
      else return 'green darken-4'
    },
  },
  mounted() {
    this.getOrdersByAdmin();
  }
}
</script>

<style scoped>

</style>