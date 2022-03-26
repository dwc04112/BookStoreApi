<template>
  <v-layout wrap row class="pa-4">
    <v-flex md12 sm12>


      <v-card-title>나의 찜목록</v-card-title>
      <v-card-text>

        <v-chip-group
            active-class="deep-purple accent-4 white--text"
            mandatory
            column
        >
          <v-chip
              v-for="(wish,index) in wishlistTitle"
              :key="index"
              @click.stop="getWishList(wish.titleNum)"
          >{{wish.wishlistTitle}}</v-chip>
        </v-chip-group>
      </v-card-text>

      <v-divider class="mx-4"></v-divider>

      <v-card-text>
        <v-layout wrap row justify-center>
          <v-flex
              class="pb-4 pt-4 pl-2 pr-2"
              xs12 sm12 md12
              style="flex-basis: auto;"
          >
            <v-data-table
                dense
                :headers="headers"
                :items="wishBooks"
                :items-per-page="5"
                item-key="name"
                class="elevation-0"
            >
             <template v-slot:item.bookThumb="{item}" >
               <div class="pa-2">
                <v-img :src="item.bookThumb" width="60" height="87" ></v-img>
               </div>
             </template>
              <template v-slot:item.wid="{ item }">
                <v-btn
                    small
                    outlined
                    text
                    @click="editItem(item.wid)"
                    width="80"
                    height="22"
                    elevation="0"
                >
                  <h4>장바구니</h4>
                </v-btn>
                <v-btn
                    small
                    outlined
                    text
                    class="mt-2"
                    @click="editItem(item.wid)"
                    width="80"
                    height="22"
                    elevation="0"
                >
                  <h4>바로구매</h4>
                </v-btn>
                <v-btn
                    small
                    outlined
                    @click="deleteItem(item.wid)"
                    text
                    class="mt-2"
                    width="80"
                    height="22"
                    elevation="0"
                >
                  <h4>삭제</h4>
                </v-btn>
              </template>
            </v-data-table>
          </v-flex>
        </v-layout>

      </v-card-text>


      <v-divider class="mx-4"></v-divider>

      <v-card-title>나의 관심 키워드</v-card-title>

      <v-card-text>
        <v-chip-group
            v-model="selection"
            active-class="deep-purple accent-4 white--text"
            column
        >
          <v-chip>5:30PM</v-chip>

          <v-chip>7:30PM</v-chip>

          <v-chip>8:00PM</v-chip>

          <v-chip>9:00PM</v-chip>
        </v-chip-group>
      </v-card-text>

        <v-card-actions>
          <v-btn
              color="deep-purple lighten-2"
              text
              @click="infoEdit"
          >
            회원정보 수정
          </v-btn>
        </v-card-actions>

    </v-flex>
  </v-layout>
</template>

<script>
export default {
  name: "InfoWishList",
  data: () => ({
    selection: 1,
    wishlistTitle :[],

    wishBooks: [

    ],
    headers: [
      {
        align: 'center',
        sortable: false,
        value: 'bookThumb',
        width: 150,
      },
      { text: '책 이름', value: 'bookTitle', width : 550, align: 'center'},
      { text: '작가', value: 'bookAuthor',width : 200 , align: 'start'},
      { text: '판매 가격', value: 'bookSalePrice', width : 150 , align: 'start' },
      { test: '책 선택' ,value: 'wid' ,width : 100 , align: 'center'},
    ],


  }),
  methods: {
    getWishTitle(){
      this.$axios.get("wish/title/")
          .then(response=>{
            this.wishlistTitle = response.data
          }).catch(error =>{
        console.log(error.response);
      })
    },

    getWishList(titleNum){
      if(titleNum == null){
        titleNum=0;
      }
      console.log(titleNum)

      this.$axios.get("wish/"+titleNum)
          .then(response=>{
            this.wishBooks = response.data
          }).catch(error =>{
        console.log(error.response);
      })
    },


    infoEdit(){
      console.log("hi")
    },
    editItem(bid){
      alert("edit : "+bid)
    },
    deleteItem(bid){
      alert("delete : "+bid)
    }
  },

  mounted() {
    this.getWishTitle()
    this.getWishList()
  }
}
</script>

<style scoped>
h4{
  color: #606060;
}
</style>