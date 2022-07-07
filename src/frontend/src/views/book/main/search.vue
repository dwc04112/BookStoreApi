<template>
  <v-container fluid class="pa-0 ma-0" style="background-color: rgb(20,20,20)">

    <v-row class="justify-center  align-center" style="background-color: rgb(40,40,40);">
      <v-col cols="10" md="8" class="pt-2 pb-1">
        <v-chip-group
            class="top-chip-group"
            mandatory
            color="white"
            next-icon="mdi-chevron-right white--text"
            prev-icon="mdi-chevron-left white--text"
            show-arrows
        >
          <v-chip
              v-for="(data,index) in detailTag"
              :key="index"
              outlined
              color="rgb(60,60,60)"
              text-color="rgb(240,240,240)"
              class="top-chip"
              active-class="white"
              @click="byCategory(data.num)"
          ><span>{{data.main}}</span>
          </v-chip>
        </v-chip-group>
      </v-col>
    </v-row>


    <v-row style="background-color: rgb(20,20,20)">
      <!--List Card-->
      <v-col class="no-gutters ml-md-4">
        <div style="text-align: start;">
          <div
              style="display: inline-block;"
              class="pa-3"
              v-for="(book, index) in bookDatas"
              :key="index"
          >
            <v-card
                style="height: 225px; overflow: hidden;"
                elevation="4"
                min-width="120px"
                tile
            >
              <img
                  :src="book.bookThumb"
                  alt="bookThumb"
                  class="book-thumb"
                  height="100%"
                  loading="lazy"
                  @click="widthSize(book)"
              />
            </v-card>
          </div>
        </div>
      </v-col>
      <div style="height: 1000px"></div>


      <!--Transition Select-->
      <transition name="sub-slide" mode="in-out">
        <v-col
            class="pa-0 ma-0"
            cols="7" sm="5" md="4"
            color="grey lighten-3"
            v-show="show.data"
        >
          <div
              class="select-book"
          >
            <div class="inner-select-book">
              <v-card-actions class="pt-5 pl-5">
                <v-btn
                    icon
                    color="rgb(220,220,220)"
                    @click.stop="show.data =false"
                >
                  <v-icon size="40px">mdi-close</v-icon>
                </v-btn>
              </v-card-actions>

              <div class="align-center d-flex flex-column pt-8">
                <v-img
                    class="select-book-img"
                    :src="selectBook.bookThumb"
                ></v-img>

                <h4 class="pt-4" style="color: rgb(220,220,220)"> {{selectBook.bookTitle}} </h4>

                <v-card-subtitle style="color: rgb(220,220,220)">{{selectBook.bookAuthor}} | {{selectBook.bookPublisher}}</v-card-subtitle>

                <div>
                  <v-chip
                      class="inner-chip ma-1"
                      v-for="keyword in selectKeywords"
                      :key="keyword"
                      :value="keyword"
                      outlined
                      color="rgb(220,220,220)"
                      small
                      @click="keywordSearch(keyword)"
                  >
                    <span>{{keyword}}</span>
                  </v-chip>
                </div>

                <v-divider></v-divider>
                <v-card-actions class="mt-2">
                  <v-col>
                    <v-tooltip top color="green darken-2">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            class="ma-2"
                            v-bind="attrs"
                            v-on="on"
                            elevation="2"
                            fab color="rgb(50,50,50)"
                            @click.stop="addCart(selectBook.bid)"
                        >
                          <v-icon color="green darken-2" large>
                            mdi-cart
                          </v-icon>
                        </v-btn>
                      </template>
                      <span>장바구니 추가</span>
                    </v-tooltip>

                    <v-tooltip top color="pink">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            class="ma-2"
                            v-bind="attrs"
                            v-on="on"
                            elevation="2"
                            fab color="rgb(50,50,50)"
                            @click.stop="setComponentData(selectBook.bid)"
                        >
                          <v-icon color="pink" large>
                            mdi-heart
                          </v-icon>
                        </v-btn>
                      </template>
                      <span>위시리스트에 추가</span>
                    </v-tooltip>

                    <v-tooltip top color="yellow darken-2 black--text">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            class="ma-2"
                            v-bind="attrs"
                            v-on="on"
                            elevation="2"
                            fab color="rgb(50,50,50)"
                            @click.stop="detailView(selectBook.bid)"
                        >
                          <v-icon color="yellow darken-2" large>
                            mdi-book-open-variant
                          </v-icon>
                        </v-btn>
                      </template>
                      <span>책 보러가기</span>
                    </v-tooltip>
                  </v-col>
                </v-card-actions>
              </div>
            </div>
          </div>
        </v-col>
      </transition>


      <!--      컴포넌트 호출      -->
      <v-container fluid class="pa-0 ma-0">
        <v-dialog
            class="align-center justify-center align-content-center"
            v-model="dialog"
            max-width="600"
        >
          <v-card color="#FDF6EC">
            <v-toolbar
                elevation="0"
                class="white--text"
                color="rgb(33,33,33)"
            >
              <v-card-title>나의 위시리스트</v-card-title>
            </v-toolbar>

            <component
                v-bind:selectBid="setBid"
                :key="componentKey"
                :is="component"
                @childKey="updateComponentKey"
                @pushTab="setWishTab"
            ></component>

            <v-card-text>
              <div>
                <ul>
                  <li>카테고리 추가는 메인페이지 > 책 등록시 가능합니다</li>
                  <li>새로 만들어진 카테고리는 자동으로 공개처리됩니다.</li>
                  <li>카테고리 관리는 마이페이지 > 위시리스트 > 나의 찜목록 으로 이동하시면 가능합니다.</li>
                </ul>
              </div>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  color=rgb(33,33,33)
                  text
                  @click="pushInfoWishList(3)"
              >
                <h4>내 보관함으로 이동</h4>
              </v-btn>
              <v-btn
                  color=rgb(33,33,33)
                  text
                  @click="dialog = false"
              >
                Close
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>

    </v-row>
  </v-container>
</template>

<script>
import SearchMenu from "@/views/SearchMenu";
export default {
  name: "search",
  components: {SearchMenu},
  data: function (){
    return{
      //메인 데이터
      bookDatas : [],         //메인 데이터 (타이틀로 검색)



      //선택된 책 보기
      show : {data:false , bid: null},
      selectBook : '',
      selectKeywords : '',


      //상단 카테고리
      tab : null,
      selectTag : [],
      detailTag : [
        { main : '전체' },
        { main: '소설', num: '00', },
        { main : '시/에세이', num: '01', },
        { main : '자기계발', num: '02', },
        { main : '인문', num: '03', },
        { main : '역사/문화', num: '04', },
        { main : '종교', num: '05', },
        { main : '정치/사회', num: '06', },
        { main : '예술/대중문화', num: '07', },
        { main : '과학', num: '08', },
        { main : '기술/공학', num: '09', },
        { main : '컴퓨터/IT', num: '10', },
      ],
      completeData : [],
      inputMsg : '',



      //컴포넌트 관련 데이터 (Dialog)
      dialog: false,              //wishlist Dialog
      componentKey: 0,             // reload component
      wishTab :'WishList',         // 보여줄 컴포넌트 값
      setBid : '',                 // push to component

    }
  },

  watch: {
    $route(to,from){
      if (to.query !== from.query) {
        this.searchByMenu(to.query.search)
      }
    },


  },
  methods: {
    searchByMenu(val){
      this.$axios.get("book/search/" + val)
          .then(response => {
            this.bookDatas = response.data
          }).catch(error => {
        console.log(error.response);
      })
    },

    //화면 크기에 따라 다른 method
    widthSize(book){
      let x = window.innerWidth

      if(x>=600){
        this.openInfo(book)
      }else{
        this.detailView(book.bid)
      }
    },
    //책 보러가기
    detailView(bid){
      this.$router.push({name: 'DetailView' ,query: {bid}});
    },
    //Select Book Info
    openInfo(book){
      if(this.show.data === true){  //책 정보가 열려있을때
        if(this.show.bid === book.bid){ //같은 책이라면 정보 닫기
          this.show.data= !this.show.data
        }
      }else{  //닫혀있으면 열기
        this.show.data= !this.show.data
      }
      this.show.bid = book.bid
      this.selectBook=book
      this.selectKeywords = book.bookKeyword.split(',')
    },

    //카테고리로 검색
    byCategory(num){
      this.$axios.get("book/category/"+num)
          .then(response=>{
            this.bookDatas = response.data
          }).catch(error =>{
        console.log(error.response);
      })
    },
    //키워드로 검색
    keywordSearch(data){
      this.$axios.get("book/keyword/"+data)
          .then(response=>{
            this.bookDatas = response.data
          }).catch(error =>{
        console.log(error.response);
      })
    },

    /*
    * 컴포넌트 관련 메소드
    *
    * */
    setComponentData(bid){
      this.dialog =true;
      this.setBid = bid             //선택한 책 id를 child 컴포넌트에 보내기위해
      this.setWishTab("WishList")   // 디폴트 페이지는 항상 WishList
      this.updateComponentKey()     // 컴포넌트를 리로드 하기위해
    },

    updateComponentKey(){
      this.componentKey +=1        //컴포넌트 리로드
    },
    setWishTab(data){
      this.wishTab = data
    },

    //마이페이지 위시리스트 수정으로 넘어가기
    pushInfoWishList(pushNum){
      // num 0 ~ 3
      console.log("push num : "+ pushNum)
      this.$router.push({name: 'InfoNavi', params: {AboutTab: pushNum}})
    },

    //cart에 담기
    addCart(bid){
      this.$axios.get("cart/add/"+bid
      ).then(response=>{
        console.log(response.data.message);
        alert("성공적으로 장바구니에 추가했습니다")
      }).catch(error =>{
        console.log(error.response);
      })
    },

    // == 컴포넌트관련 끝 ==


  },
  computed:{
    // 컴포넌트에서 페이지 변경
    component() {
      const wishTab = this.wishTab;
      return () => import(`@/views/wishlist/${wishTab}`);
    },
  },

  mounted() {
    this.searchByMenu(this.$route.query.search)
  }
}
</script>

<style scoped>



/* 1. 상단 카테고리 영역 */

.top-chip span{
  font-size: 13px;
  font-weight: lighter;
}



/* 상단 chip */


.high-chip span{
  color: black;
}

/* 2.책 리스트 영역 */


/* 선택한 책 영역 */
.select-book{
  background-color: rgb(40,40,40);
  text-align: center;
  height: 100vh;
  position: sticky;
  top: 40px;
}
.inner-select-book{
  position: sticky;
  top: 55px;
}
.select-book-img{
  width: 200px;
}

.sub-slide-enter{
  transform: translateX(400px);
  opacity: 1;
}
.sub-slide-enter-active,
.sub-slide-leave-active {
  transition: all 0.4s ease-out;
}
.sub-slide-leave-to {
  transform: translateX(400px);
  opacity: 1;
}

@media screen and (max-width: 600px){


  .select-book-img{
    width: 130px;
    margin-right: 13px;
  }

}

/* 컴포넌트  */
li{
  font-size: 12px;
}


</style>