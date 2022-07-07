<template>
  <v-container class="ma-0 pa-0">
    <div style="height: 140px"></div>

    <!-- 우측 Nav -->
    <v-row>
      <v-col cols="2" md="2" class="ml-8 pt-8 mt-8 detail-menu">
          <v-row class="pa-0 ma-1 ml-3">
            <v-col @click="$vuetify.goTo(0)" cols="12" class="nav-menu-main-text">1. {{bookData.bookTitle}}</v-col>
          </v-row>
          <v-row class="ma-0 ma-0 ml-3">
            <v-col cols="1" class="ma-0 pa-0 ml-5" >
              <v-divider style="background-color: rgb(100,100,100);border-width: 1.5px;" vertical></v-divider>
            </v-col>
            <v-col cols="8" class="pa-0 ma-0 nav-menu-sub-text">
              <v-col class="pa-1" @click="moveScroll('content_id')">
                책 소개
                <v-icon class="pb-1 pl-3" size="18" color="yellow darken-2">mdi-book</v-icon>
              </v-col>
              <v-col class="pa-1" @click="moveScroll('index_id')">
                책 목차
              </v-col>
              <v-col class="pa-1" @click="moveScroll('preview_id')">
                출간자 서평
              </v-col>
            </v-col>
          </v-row>

          <v-row class="pa-0 ma-1 ml-3 ">
            <v-col cols="12" class="nav-menu-main-text" @click="moveScroll('comment_id')">2. 댓글 및 평점</v-col>
          </v-row>
          <v-row class="ma-0 pa-0 ml-3">
            <v-col cols="1" class="ma-0 pa-0 ml-5" >
              <v-divider style="background-color: rgb(100,100,100); border-width: 1.5px;" vertical></v-divider>
            </v-col>
            <v-col cols="8" class="pa-0 ma-0 nav-menu-sub-text">
              <v-col class="pa-1"  @click="moveScroll('comment_id')">
                별점
                <v-icon class="pb-1 pl-3" size="18" color="yellow darken-2">mdi-star</v-icon>
              </v-col>
              <v-col class="pa-1"  @click="moveScroll('comment_id')">
                댓글
              </v-col>
            </v-col>
          </v-row>
          <v-row class="pa-0 ma-1 ml-3 pb-8">
            <v-col cols="12" class="nav-menu-main-text" @click="moveScroll('recommend_id')">3. 비슷한 책 추천</v-col>
          </v-row>
          <v-row class="pa-0 ma-1 ml-4 mb-5">
            <v-btn icon color="rgb(40,40,40)" height="45px" width="200px"
                   class="pay-card align-center d-flex flex-column ma-3"
                   @click="pushLink()"
            >
              <span class="white--text pl-2" style="font-size: 16px">바로구매</span>
              <v-icon right size="26px" color="yellow darken-2">mdi-checkbox-multiple-marked-circle</v-icon>
            </v-btn>

            <v-btn icon color="rgb(40,40,40)" height="45px" width="200px"
                   class="pay-card align-center d-flex flex-column ma-3"
                   @click="addCart()"
            >
              <span class="white--text pl-2" style="font-size: 16px">장바구니</span>
              <v-icon right size="24px" color="green darken-2">mdi-cart</v-icon>
            </v-btn>

            <v-btn icon color="rgb(40,40,40)" height="45px" width="200px"
                   class="pay-card align-center d-flex flex-column ma-3"
                   @click="dialog=true"
            >
              <span class="white--text pl-2" style="font-size: 16px">위시리스트</span>
              <v-icon right size="22px" color="red darken-2">mdi-heart</v-icon>
            </v-btn>
          </v-row>
      </v-col>
    </v-row>
    <!-- 우측 Nav 끝-->

    <v-row class="ma-0">
      <v-col cols="12" style="background-color: rgb(40,40,40)">
        <!-- 이미지와 메인정보 -->
        <v-row>
          <!--책 이미지 -->
          <v-col cols="12" lg="4" class="justify-center align-center d-flex" >
            <div style="display: inline-block;" class="ma-4">
              <v-card
                  style="overflow: hidden; position:relative"
                  elevation="2"
                  class="fill-height"
                  width=""
                  tile>
                <v-img
                    :src="bookData.bookThumb"
                    :lazy-src="bookData.bookThumb"
                    alt="bookThumb"
                    height="100%"
                    max-width="230px"
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
              </v-card>
            </div>
          </v-col>


          <!--메인 정보-->
          <v-col cols="12" lg="8" class="mt-8">

            <v-row>
              <v-col cols="12" class="ml-md-0 ml-4">
                <span class="white--text" style="font-size: 25px">{{bookData.bookTitle}}</span>
                <span class="grey--text" style="font-size: 20px"> {{bookData.bookSubTitle}}</span>
              </v-col>
            </v-row>

            <v-row class="ma-0 pa-0">
              <v-col cols="12" class="ma-0 pa-0 ml-md-0 ml-4">
                <div>
                  <span class="grey--text" style="font-size: 14px">{{bookData.bookAuthor}}</span>
                  <span class="grey--text" style="font-size: 14px">&nbsp;&nbsp;|&nbsp;&nbsp;{{bookData.bookPublisher}}</span>
                  <span class="grey--text" style="font-size: 14px">&nbsp;&nbsp;|&nbsp;&nbsp;{{bookData.bookPublishedDate}} 출간</span>
                </div>
              </v-col>
            </v-row>

            <v-divider class="mt-7 mb-7 mr-md-10 grey darken-3" style="border: 0.5px solid"></v-divider>

            <v-row class="pl-4">
              <v-col>
                <v-row>
                  <v-btn color="rgb(40,40,40)" height="60px" disabled class="price-card d-flex flex-column">
                    <span class="white--text" style="font-size: 14px">판매가: </span>
                    <span v-html="bookData.bookSalePrice" class="white--text pl-1" style="font-size: 20px"></span>
                    <span class="white--text" style="font-size: 14px" >원</span>
                  </v-btn>
                </v-row>

              </v-col>
            </v-row>

            <v-divider class="mt-7 mb-7 mr-md-10 grey darken-3" style="border: 0.5px solid"></v-divider>

            <v-row>
              <v-col cols="12" class="ml-4 ml-md-0 mb-6">
                <v-chip
                    class="ma-1 yellow darken-2">
                  <v-icon left color="rgb(45,45,45)" size="18">mdi-key-variant</v-icon>
                  Keyword
                </v-chip>

                <v-chip
                    class="inner-chip ma-1"
                    v-for="(item,index) in keyword"
                    :key="index"
                    :value="item"
                    outlined
                    color="rgb(220,220,220)"
                >
                  <span>{{item}}</span>
                </v-chip>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <!-- 이미지와 메인정보 끝-->

        <!-- 세부정보 -->
        <v-row class="yellow darken-2" id="content_id">
          <v-col
              cols="3" md="2"
              class="yellow darken-2 align-center justify-center d-flex flex-column"
              v-for="(item,index) in detailItem"
              :key="index"
          >
            <span style="font-size: 13px">{{item.text}}</span>
            <v-icon class="pb-2 pt-1" size="36">{{item.icon}}</v-icon>
            <span style="font-size: 14px; font-weight: bold">{{item.data}}</span>
          </v-col>
        </v-row>
        <!-- 세부정보 끝 -->

        <v-timeline dense class="mt-3 mb-3">
          <div style="height: 80px"></div>
          <v-timeline-item color="yellow darken-2" fill-dot>
            <v-card class="elevation-2" elevation="0" width="95%" color="rgb(40,40,40)">
              <v-card-title class="mb-4">
               <v-icon class="pr-3  yellow--text text--darken-2" size="36">mdi-book</v-icon>
                <span class="grey--text text--lighten-1" style="font-size: 30px; font-weight: bold" > 책 소개 </span>
              </v-card-title>
              <v-card-text>
                <span class="grey--text text--lighten-1" v-html="bookData.bookContent"></span>
              </v-card-text>
            </v-card>
          </v-timeline-item>

          <div style="height: 120px" id="index_id"></div>

          <v-timeline-item color="yellow darken-2" fill-dot>
            <v-card class="elevation-2" elevation="0" width="95%" color="rgb(40,40,40)">
              <v-card-title class="mb-4">
                <v-icon class="pr-3  yellow--text text--darken-2" size="36">mdi-book-multiple</v-icon>
                <span class="grey--text text--lighten-1" style="font-size: 30px; font-weight: bold"> 책 목차 </span>
              </v-card-title>
              <v-card-text>
                <span class="grey--text text--lighten-1" v-html="bookData.bookIndex"></span>
              </v-card-text>
            </v-card>
          </v-timeline-item>

          <div style="height: 120px" id="preview_id"></div>

          <v-timeline-item color="yellow darken-2" fill-dot style="z-index:5;" >
            <v-card class="elevation-2" elevation="0" width="95%" color="rgb(40,40,40)">
              <v-card-title class="mb-4">
                <v-icon class="pb-1 pr-3 yellow--text text--darken-2" size="36">mdi-book-open-variant</v-icon>
                <span class="grey--text text--lighten-1"  style="font-size: 30px; font-weight: bold">
                  출간자 서평
                </span>
              </v-card-title>
              <v-card-text>
                <span class="grey--text text--lighten-1" v-html="bookData.bookPreview"></span>
              </v-card-text>
            </v-card>
          </v-timeline-item>

          <div style="height: 80px" id="comment_id"></div>
        </v-timeline>



        <!-- 댓글 및 별점 -->
        <v-lazy
            :options="{threshold: .5}"
            transition="fade-transition"
        >
          <v-row>
            <v-col>
                <CommentComponent
                  v-bind:selectBid="bid"
                  :key="componentKey"
                  @childKey="componentKey++"
                >
                </CommentComponent>
            </v-col>
          </v-row>
        </v-lazy>
        <!-- 댓글 끝 -->


      </v-col>
    </v-row>

    <v-row class="ma-0" style="border-right: rgb(40,40,40) solid 1.5px">
      <v-col cols="12">
        <div style="height: 130px"></div>
      </v-col>
      <v-col cols="12">
        <Recommend
            id="recommend_id"
            v-bind:selectBid="bid"
        >
        </Recommend>
      </v-col>
    </v-row>



    <v-row class="nav-mini-btn" style="position:absolute; right: 8%">
      <v-col cols="12" class="ma-8">
        <v-btn fab elevation="0" width="50px" height="50px" color="yellow darken-2" style="position: fixed; bottom: 8%" @click="$vuetify.goTo(0)">
          <v-icon color="rgb(60,60,60)" size="40">mdi-chevron-up</v-icon>
        </v-btn>

        <v-btn fab elevation="0" width="50px" height="50px" color="yellow darken-2" style="position: fixed; bottom: 15%"
               @click="pushLink()"
        >
          <v-icon color="rgb(60,60,60)" size="24">mdi-checkbox-multiple-marked-circle</v-icon>
        </v-btn>

        <v-btn fab elevation="0" width="50px" height="50px" color="yellow darken-2" style="position: fixed; bottom: 22%"
          @click="dialog=true"
        >
          <v-icon color="rgb(60,60,60)" size="24">mdi-heart</v-icon>
        </v-btn>

        <v-btn fab elevation="0" width="50px" height="50px" color="yellow darken-2" style="position: fixed; bottom: 29%"
               @click="addCart()"
        >
          <v-icon color="rgb(60,60,60)" size="22">mdi-cart</v-icon>
        </v-btn>
      </v-col>
    </v-row>


    <!--장바구니 msg-->
    <v-dialog
        max-width="400"
        v-model="cartDialog"
        content-class="my-custom-dialog"
    >
      <v-card rounded color="rgb(55,55,55)" tile dark>
        <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">{{cartDialogMsg}}</div>
        <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
          <v-btn
              rounded class="white--text"
              @click="cartDialog = false"
          >확인</v-btn>
          <v-spacer/>
          <v-btn
              rounded class="white--text"
              color="teal accent-6"
              @click="$router.push({path:'/my/cart'})"
          >장바구니로 이동</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!--      컴포넌트 호출      -->
    <v-container fluid class="pa-0 ma-0">
      <v-dialog
          class="align-center justify-center align-content-center"
          v-model="dialog"
          max-width="600"
      >
        <v-card color="rgb(50,50,50)" dark>
          <v-toolbar
              elevation="0"
              class="white--text"
              color="rgb(40,40,40)"
          >
            <v-card-title>나의 위시리스트</v-card-title>
          </v-toolbar>
          <component
              v-bind:selectBid="bid"
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
          <v-card-actions style="background-color: rgb(40,40,40)">
            <v-btn
                class="white--text"
                rounded
                @click="dialog = false"
            >
              Close
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn
                color="teal accent-5" class="white--text"
                rounded
                @click="$router.push({path:'/my/wish'})"
            >
              <h4>내 보관함으로 이동</h4>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>

  </v-container>
</template>

<script>
import CommentComponent from "@/views/book/bookComponents/CommentComponent";
import Recommend from "@/views/book/bookComponents/Recommend"
export default {
  name: "BookDetailComponent",
  components: {Recommend, CommentComponent},
  data: function (){
    return{
      //책 정보
      bid: this.$route.query.bid,
      bookData : [],
      keyword : '',

      //컴포넌트 관련 데이터 (Dialog)
      dialog: false,              //wishlist Dialog
      componentKey: 0,             // reload component
      wishTab :'WishList',         // 보여줄 컴포넌트 값
      cartDialog : false,
      cartDialogMsg : '',


      //좌측 리스트
      items: [
        { text: '맨 위로'},
        { text: '책 정보'},
        { text: '책 소개'},
        { text: '목차'},
        { text: '출판사 서평' },
        { text: '리뷰'},
      ],

      detailItem: [
        {icon: "mdi-book-open-page-variant", text: 'Page'},
        {icon: "mdi-move-resize", text: 'Dimensions'},
        {icon: "mdi-weight", text: 'Weight'},
        {icon: "mdi-barcode", text: 'ISBN-10'},
        {icon: "mdi-barcode", text: 'ISBN-13'},
        {icon: "mdi-calendar", text: 'Publication date'},
      ],

    }
  },
  watch:{
    $route(){
      this.bid = this.$route.query.bid;
      this.getBookDetail()
      this.$vuetify.goTo(0)
    }
  },
  computed:{
    // 컴포넌트에서 페이지 변경
    component() {
      const wishTab = this.wishTab;
      return () => import(`@/views/wishlist/${wishTab}`);
    }
  },

  methods: {
    getBookDetail(){
      this.$axios.get('book/'+this.bid)
          .then(response=>{
            this.bookData = response.data
            this.keyword = (response.data.bookKeyword.split(','))
            //받아온 내용 줄바꿈 적용
            this.bookData.bookContent = response.data.bookContent.replace(/(?:\r\n|\r|\n)/g, '<br />')
            this.bookData.bookIndex = response.data.bookIndex.replace(/(?:\r\n|\r|\n)/g, '<br />')
            this.bookData.bookPreview = response.data.bookPreview.replace(/(?:\r\n|\r|\n)/g, '<br />')
            //detail item data set
            //page
            this.$set(this.detailItem[0],'data',response.data.bookPage +' page' )
            //치수 & 무게
            this.$set(this.detailItem[1],'data',response.data.bookSize.split('/')[0] )
            this.$set(this.detailItem[2],'data',response.data.bookSize.split('/')[1] )
            //Isbn - 10 & 13
            this.$set(this.detailItem[3],'data',response.data.bookIsbn.split(/[()]/)[0] )
            this.$set(this.detailItem[4],'data',response.data.bookIsbn.split(/[()]/)[1] )
            //data
            this.$set(this.detailItem[5],'data',response.data.bookPublishedDate)
          })
          .catch(error =>{
            console.log(error.response);
          })
    },

    //cart에 담기
    addCart(){
      this.$axios.get("cart/add/"+this.bid
      ).then(response=>{
        console.log(response.data.message);
        this.cartDialogMsg = "성공적으로 장바구니에 추가했습니다"
        this.cartDialog = true
      }).catch(error =>{
        console.log(error.response);
        this.cartDialogMsg = "장바구니 추가에 실패했습니다"
        this.cartDialog = true
      })
    },

    /*
  * 컴포넌트 관련 메소드
  *
  * */
    setComponentData(){
      this.dialog =true;
      this.setWishTab("WishList")   // 디폴트 페이지는 항상 WishList
      this.updateComponentKey()     // 컴포넌트를 리로드 하기위해
    },

    updateComponentKey(){
      this.componentKey +=1        //컴포넌트 리로드
    },
    setWishTab(data){
      this.wishTab = data
    },
    //긑

    pushLink(){
      let cartArr = []
      cartArr.push( {bid:this.bookData.bid, bookCount:1})
      this.$store.dispatch('getOrderByDetail', cartArr)
          .then(()=>
              this.$router.push({path: '/order'}).catch(()=>console.log('잘못된 접근입니다'))
          )
    },

    moveScroll(data){
      let div = document.getElementById(data)
      let abTop = window.pageYOffset + div.getBoundingClientRect().top;
      this.$vuetify.goTo(abTop)
    },
  },
  mounted() {
    this.getBookDetail()
    window.scrollTo(0, 0);
  }
}

</script>

<style scoped>


.price-card{
  outline: rgb(170,170,170) 1.5px solid;
  border-radius: 5px 30px 30px 30px;
}
.pay-card{
  outline: rgb(170,170,170) 1.5px solid;
  border-radius: 30px 30px 30px 30px;
}
.theme--light.v-timeline:before {
  background: rgb(60,60,60);
}

.nav-menu-main-text{
  color: rgb(200,200,200);
  font-size: 16px;
  font-weight: bold;
  white-space:nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.nav-menu-sub-text{
  color: rgb(200,200,200);
  font-size: 13px;
}
.detail-menu{
  position: fixed;
  right: 6%;
  top:70px;
}
.nav-mini-btn{
  display: none;
}


@media screen and (max-width: 960px){
  .title-br{
    display: block;
  }
  .nav-mini-btn{
    display: block;
  }
  .detail-menu{
    display: none;
  }
}

</style>