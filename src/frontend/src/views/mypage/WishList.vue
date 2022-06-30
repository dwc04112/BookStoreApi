<template>
  <v-container fluid class="align-center" >

    <v-row>
      <v-col cols=12 md="3">
        <v-card class="align-center flex-column d-flex" color="transparent" tile elevation="0">
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
          <v-col cols="7" class="pa-0 pt-1 pb-3">
            <v-chip-group
                class="mb-4"
                active-class="primary darken-4"
                v-model="selectWishTitle"
                mandatory
                column
                dark
            >
              <v-chip
                  filter class="mb-2"
                  v-for="(wish,index) in wishlistTitle"
                  :key="index"
                  :value="wish.titleNum"
                  :close="titleDelete"
                  @click="titleDelete = false"
                  @click.stop="getWishList(wish.titleNum)"
                  @click:close="setDelete(wish.titleNum)"
              >
               {{wish.wishlistTitle}}
              </v-chip>
            </v-chip-group>
            <v-chip
                @click.stop="titleDelete=true"
                class="red lighten-1 white--text"
                active-class="red white--text"
                filter
            >
              <span class="pl-1 pr-1">Edit</span>
              <v-icon class="white--text" size="19">mdi-wrench</v-icon>
            </v-chip>
          </v-col>

          <v-divider class="ma-2" style="width: 60%" dark></v-divider>

        </v-card>
      </v-col>



      <v-col cols="12" md="7" class="pb-8 ml-md-4">
        <div class="mb-8">
          <span class="main-title">나의 위시리스트</span>
        </div>

        <v-row class="pb-4 align-center pa-0" style="background-color: rgb(30,30,30)">


          <v-checkbox
              dark class="mb-3 ml-4"
              disabled
              off-icon="mdi-check white--text"
              :label="this.selectWidList.length+'권 선택'"
              v-show="showSelect"
              hide-details/>
          <v-spacer></v-spacer>

          <v-spacer/>
          <div class="pa-2 pt-3 pr-3">
            <v-btn icon>
              <v-icon @click="showSelectSet"  :color=" showSelect ? 'blue' : 'rgb(180,180,180)'"
              >{{ showSelect ? 'mdi-check-circle' : 'mdi-check-circle-outline' }}</v-icon>
            </v-btn>
          </div>


          <v-col cols="12" class="pa-0 mb-3 mt-1">
            <v-divider style="background-color: rgb(180,180,180); border: rgb(180,180,180) solid 1px"></v-divider>
          </v-col>

          <!--List Card-->
          <v-col cols="12"
                 class="book-list-col no-gutters ml-md-4"
          >
            <div
                style="display: inline-block;"
                class="pa-4"
                v-for="(book, index) in wishBooks"
                :key="index"
            >
                <v-card
                    style="height: 200px; overflow: hidden; position:relative"
                    elevation="1"
                    tile
                >
                  <img
                      :src="book.bookThumb"
                      alt="bookThumb"
                      class="book-thumb"
                      height="100%"
                      loading="lazy"
                  >
                  <v-overlay
                      :absolute="absolute"
                      :value="showSelect"
                      opacity= 0.5
                      style="z-index: 4"
                  >
                    <v-btn
                        class="img-btn"
                        @click="selectBook(book.wid, index)"
                        fab outlined icon
                    >
                      <v-icon size="70" :color=" book.select ? 'blue' : 'white' ">
                        {{ book.select ? 'mdi-check-circle' : 'mdi-check-circle-outline' }}
                      </v-icon>
                    </v-btn>
                  </v-overlay>
                </v-card>
            </div>
          </v-col>
        </v-row>
      </v-col>
    </v-row>


    <!--삭제 확인 메시지-->
    <v-dialog
        max-width="400"
        v-model="deleteDialog"
    >
      <v-card rounded color="rgb(55,55,55)" tile dark>
        <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">{{ deleteMsg }}</div>
        <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
          <v-btn
              rounded
              text
              @click="deleteDialog =false"
          >Close</v-btn>
          <v-btn
              class="ml-2"
              rounded
              elevation="0"
              color="red"
              @click="listOrItem"
          >Commit</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-snackbar
        transition="dialog-bottom-transition"
        elevation="0"
        color="white"
        rounded
        v-model="snackbar"
    >
      <div>
        <v-icon color="blue" size="30" class="pr-2">mdi-checkbox-marked-circle</v-icon>
        <span class="black--text">{{ successMsg }}.</span>
      </div>
    </v-snackbar>


    <!-- on off Footer -->
    <v-footer color="rgb(40,40,40)" fixed style="z-index: 5" v-show="showSelect">
      <v-row class="pa-0 ma-0 mt-2 mb-2 no-gutters justify-center d-flex" >
        <v-col cols="12" md="9" class="justify-center align-center d-flex">
          <v-chip
              @click="addToCart"
              color="blue lighten-1 white--text"
              v-show="showSelect"
              :disabled="selectWidList.length<1"
          >
            <span class="pl-1 pr-2">Add to Cart</span>
            <v-icon color="white" size="20">mdi-cart</v-icon>
          </v-chip>
          <v-spacer></v-spacer>
          <v-chip
              @click="setDelete(null)"
              color="red lighten-1 white--text"
              text v-show="showSelect"
              :disabled="selectWidList.length<1"
          >
            <span class="pl-1 pr-2">Delete in List</span>
            <v-icon color="white" size="20">mdi-delete</v-icon>
          </v-chip>
        </v-col>
      </v-row>
    </v-footer>

  </v-container>
</template>

<script>
export default {
  name: "WishList",
  data: () => ({

    // 불러온 값
    wishlistTitle :[],
    wishBooks: [],

    selectWishTitle: null,
    titleDelete : false,


    //책 선택
    showSelect : false, //책 선택 보여줄건지?
    absolute : true,
    selectWidList : [],

    //delete
    deleteMsg: '',
    deleteDialog : false,
    deleteTitle : null,
    snackbar : false,
    successMsg: '',

    page : 1,
    pageCount: 0,
    headers: [
      {
        align: 'center',
        sortable: false,
        value: 'bookThumb',
        class:  "grey lighten-3"
      },
      { text: '책 이름', value: 'bookTitle', align: 'start',class: "grey lighten-3"},
      { text: '작가', value: 'bookAuthor', align: 'start',class:  "grey lighten-3"},
      { text: '판매 가격', value: 'bookSalePrice', align: 'start',class:  "grey lighten-3" },
      { sortable: false, value: 'wid' ,align: 'start',class:  "grey lighten-3" },
    ],

  }),
  watch: {
    snackbar(val) {
      val && setTimeout(() => {
        this.snackbar = false
      }, 3000)
    },
  },
  methods: {
    getWishTitle(){
      this.$axios.get("wish/title/")
          .then(response=>{
            this.wishlistTitle = response.data
            this.selectWishTitle = response.data[0].titleNum
            this.getWishList();
          }).catch(error =>{
        console.log(error.response);
      })
    },

    //선택된 위시리스트 목록 받아오기
    getWishList(titleNum){
      this.showSelect = false;  //책 선택영역 닫기
      this.selectWidList = []   // 책 선택 리스트 초기화
      if(titleNum == null){
        titleNum= this.wishlistTitle[0].titleNum;
      }
      this.$axios.get("wish/title/"+titleNum)
          .then(response=>{
            this.wishBooks = response.data
            for(let i =0; i<response.data.length; i++){
              this.$set(this.wishBooks[i],'select',false)
            }
          }).catch(error =>{
        console.log(error.response);
      })
    },

    // 리스트에서 책 수정? true false
    showSelectSet(){
      this.selectWidList = []   // 책 선택 리스트 초기화
      this.wishBooks.map(e => {
        e.select = false;
      })
      this.showSelect = !this.showSelect
    },
    // 책 선택하기
    selectBook(wid, index){
      this.wishBooks[index].select = !this.wishBooks[index].select
      //해당 책 wish id 찾아서 추가 or 제거
      if(this.selectWidList.indexOf(wid) === -1) {
        this.selectWidList.push(wid)
      }else {
        this.selectWidList.splice(this.selectWidList.indexOf(wid), 1)
      }
    },

    //삭제 부분
    setDelete(num){
      if(!num){
        this.deleteMsg = "선택한 책 목록을 삭제하겠습니까?";
      }else {
        this.deleteMsg = "선택한 위시리스트를 삭제하겠습니까?";
        this.deleteTitle = num;
      }
      this.deleteDialog = true
    },
    listOrItem(){
      console.log(this.deleteTitle)
      if(this.deleteTitle !=null){
        this.deleteWishList()
      }else {
        this.deleteInList()
      }
    },
    deleteWishList(){
      this.$axios.delete("wish/title/"+this.deleteTitle)
          .then(response=>{
            console.log(response.data)
            this.getWishTitle()  //선택된 타이틀 번호로 타이틀 다시받기 (리로드)
            this.deleteDialog =false
            this.snackbarDelay("위시리스트를 삭제했습니다");
          }).catch(error =>{
            console.log(error.response);
          })
    },
    // 선택한 책 삭제하기
    deleteInList(){
      let widArr = this.selectWidList
      this.$axios.delete("wish/"+widArr)
          .then(response=>{
            console.log(response.data)
            this.getWishList(this.selectWishTitle)  //선택된 키워드 번호로 책목록 다시받기 (리로드)
            this.deleteDialog =false
            this.snackbarDelay("선택한 책을 삭제했습니다");
          }).catch(error =>{
            console.log(error.response);
          })
    },

    snackbarDelay(str){
      this.successMsg = str;
      clearTimeout(this._timerId)
      // delay new call 500ms
      this._timerId = setTimeout(() => {
        // maybe : this.fetch_data()
        this.snackbar = true;
      }, 600)
    },



    addToCart(){
      let widArr = this.selectWidList
      this.$axios.get("cart/fromWish/"+widArr)
          .then(response=>{
            console.log(response.data)
            this.getWishList(this.selectWishTitle)  //선택된 키워드 번호로 책목록 다시받기 (리로드)
            this.snackbarDelay("성공적으로 장바구니에 추가했습니다");
          }).catch(error =>{
        console.log(error.response);
      })
    },

    // 리 렌더링을 위해 부모 컴포넌트 Data 변경
    updateComponentKey(){
      this.$emit('updateChildKey')
    }
  },

  mounted() {
    this.getWishTitle();
    window.scrollTo(0, 0);
  }
}
</script>

<style scoped>
h4{
  color: white;
}

li{
  font-size: 12px;
}

.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(180,180,180);
}
@media screen and (max-width: 768px) {
  .book-list-col{
    text-align: center;
  }

}



</style>