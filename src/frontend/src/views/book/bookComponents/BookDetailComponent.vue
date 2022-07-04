<template>
  <v-container fluid class="ma-0 pa-0">
    <div style="height: 140px"></div>

    <!-- 우측 Nav -->
    <v-row>
      <v-col cols="2" md="2" class="pt-8 mt-8" style="position: absolute; right: 8%; top:70px;">
        <v-card elevation="0"
                color="transparent"
                class="ma-8  nav-menu"
                width="300px"
                style="position: fixed;"
                tile>
          <v-row class="pa-0 ma-1 ml-3">
            <v-col @click="$vuetify.goTo(0)" cols="12" class="nav-menu-main-text">1. {{bookData.bookTitle}}</v-col>
          </v-row>
          <v-row class="ma-0 ma-0 ml-3">
            <v-col cols="1" class="ma-0 pa-0 ml-5" >
              <v-divider style="background-color: rgb(170,70,70);border-width: 1.5px;" vertical></v-divider>
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
              <v-divider style="background-color: rgb(170,70,70); border-width: 1.5px;" vertical></v-divider>
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
            <v-col cols="12" class="nav-menu-main-text">3. 비슷한 책 추천</v-col>
          </v-row>
          <v-row
              class="pa-0 ma-1 mb-5"
              v-for="(item,index) in payItems"
              :key="index"
          >
            <v-btn icon color="rgb(40,40,40)" height="45px" width="200px"
                   class="pay-card align-center d-flex flex-column"
                   @click="pushLink(item.link)"
            >
              <span class="white--text pl-2" style="font-size: 16px">{{ item.text }}</span>
              <v-icon right :size="item.size" :color="item.color">{{item.icon}}</v-icon>
            </v-btn>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
    <!-- 우측 Nav 끝-->

    <v-row>
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
  </v-container>
</template>

<script>
import CommentComponent from "@/views/book/bookComponents/CommentComponent";
export default {
  name: "BookDetailComponent",
  components: {CommentComponent},
  data: function (){
    return{
      //책 정보
      bid: this.$route.query.bid,
      bookData : [],
      keyword : '',

      componentKey: 0, // reload component

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

      payItems: [
        {text:'바로구매',icon:'mdi-checkbox-multiple-marked-circle',color:'yellow darken-2',size:'26px'},
        {text:'장바구니',icon:'mdi-cart',color:'green darken-2',size:'24px'},
        {text:'위시리스트',icon:'mdi-heart',color:'red darken-2',size:'22px'}
      ]
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
    }
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
.nav-menu{
  display: block;
}


@media screen and (max-width: 960px){
  .title-br{
    display: block;
  }
  .nav-menu{
    display: none;
  }
}

</style>