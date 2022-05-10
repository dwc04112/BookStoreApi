<template>
  <v-container fluid class="ma-0 pa-0">

    <v-row style="height: 60px" class="pl-1 pb-1 pt-8 mt-8">
      <v-col cols="12"  class="pa-0">
        <span class="grey--text text--darken-1" style="font-size: 14px">소설 > 한국소설</span>
      </v-col>
    </v-row>

    <!-- 우측 Nav -->
    <v-row>
      <v-col cols="2" md="2" class="pt-8 mt-8" style="position: absolute; right: 8%; top:90px;">
        <v-card elevation="0" color="transparent" class="ma-8 nav-menu" height="350px" width="300px" style="position: fixed;" tile>
          <v-row class="pa-0 ma-1">
            <v-col @click="$vuetify.goTo(0)" cols="12" class="nav-menu-main-text">1. {{bookData.bookTitle}}</v-col>
          </v-row>
          <v-row class="ma-0 ma-0">
            <v-col cols="1" class="ma-0 pa-0 ml-5" >
              <v-divider class="grey darken-3" style="border-width: 1.5px;" vertical></v-divider>
            </v-col>
            <v-col cols="8" class="pa-0 ma-0 nav-menu-sub-text">
              <v-col class="pa-1">
                책 소개
                <v-icon class="pb-1 pl-3" size="18" color="yellow darken-2">mdi-book</v-icon>
              </v-col>
              <v-col class="pa-1">
                책 목차
              </v-col>
              <v-col class="pa-1">
                출간자 서평
              </v-col>
            </v-col>
          </v-row>

          <v-row class="pa-0 ma-1 mt-1">
            <v-col cols="12" class="nav-menu-main-text">2. 댓글 및 평점</v-col>
          </v-row>
          <v-row class="ma-0 pa-0 ">
            <v-col cols="1" class="ma-0 pa-0 ml-5" >
              <v-divider class="grey darken-3" style="border-width: 1.5px;" vertical></v-divider>
            </v-col>
            <v-col cols="8" class="pa-0 ma-0 nav-menu-sub-text">
              <v-col class="pa-1">
                별점
                <v-icon class="pb-1 pl-3" size="18" color="yellow darken-2">mdi-star</v-icon>
              </v-col>
              <v-col class="pa-1">
                댓글
              </v-col>
            </v-col>
          </v-row>

          <v-row class="pa-0 ma-1 mt-1">
            <v-col cols="12" class="nav-menu-main-text">3. 비슷한 책 추천</v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
    <!-- 우측 Nav -->



    <v-row>
      <v-col cols="12" style="background-color: rgb(40,40,40)">
        <!-- 이미지와 메인정보 -->
        <v-row>
          <!--책 이미지 -->
          <v-col cols="12" md="4" class="justify-center align-center d-flex" >
            <div style="display: inline-block;" class="ma-4">
              <v-card
                  style="height: 360px;overflow: hidden; position:relative"
                  elevation="2"
                  class="fill-height"
                  width=""
                  tile>
                <img
                    :src="bookData.bookThumb"
                    alt="bookThumb"
                    height="100%">
              </v-card>
            </div>
          </v-col>


          <!--메인 정보-->
          <v-col cols="12" md="8" class="mt-8">

            <v-row>
              <v-col cols="12">
                <span class="white--text" style="font-size: 27px">{{bookData.bookTitle}}</span>
                <br class="title-br">
                <span class="grey--text" style="font-size: 22px"> {{bookData.bookSubTitle}}</span>
              </v-col>
            </v-row>

            <v-row class="ma-0 pa-0">
              <v-col cols="12" class="ma-0 pa-0">
                <div>
                  <span class="grey--text" style="font-size: 14px">{{bookData.bookAuthor}}</span>
                  <span class="grey--text" style="font-size: 14px">&nbsp;&nbsp;|&nbsp;&nbsp;{{bookData.bookPublisher}}</span>
                  <span class="grey--text" style="font-size: 14px">&nbsp;&nbsp;|&nbsp;&nbsp;{{bookData.bookPublishedDate}} 출간</span>
                </div>
              </v-col>
            </v-row>

            <v-divider class="mt-10 mb-7 mr-9 grey darken-3" style="border: 0.5px solid"></v-divider>

            <v-row>
              <v-col cols="4" md="3">
                <span class="white--text" style="font-size: 13px">판매 가격</span>
                <v-btn color="rgb(40,40,40)" height="60px" class="price-card d-flex flex-column">
                  <span v-html="bookData.bookSalePrice" class="white--text" style="font-size: 20px"></span>
                  <span class="white--text" >&nbsp;원</span>
                </v-btn>
              </v-col>
            </v-row>

            <v-divider class="mt-7 mb-7 mr-10 grey darken-3" style="border: 0.5px solid"></v-divider>

            <v-row class="pb-6">
              <v-col cols="12">
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
        <v-row>
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

          <div style="height: 120px"></div>

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

          <div style="height: 120px"></div>

          <v-timeline-item color="yellow darken-2" fill-dot style="z-index:5">
            <v-card class="elevation-2" elevation="0" width="95%" color="rgb(40,40,40)">
              <v-card-title class="mb-4">
                <v-icon class="pb-1 pr-3 yellow--text text--darken-2" size="36">mdi-book-open-variant</v-icon>
                <span class="grey--text text--lighten-1" style="font-size: 30px; font-weight: bold">
                  출간자 서평
                </span>
              </v-card-title>
              <v-card-text>
                <span class="grey--text text--lighten-1" v-html="bookData.bookPreview"></span>
              </v-card-text>
            </v-card>
          </v-timeline-item>

          <div style="height: 80px"></div>
        </v-timeline>



        <!-- 댓글 및 별점 -->
        <v-row class="yellow darken-2 justify-center">
          <v-col cols="4" class="justify-end">
            <br>
            <v-icon size="140" color="white">mdi-star</v-icon>
          </v-col>
          <v-col cols="6">
            <div
                v-for="(data,index) in ratings"
                :key="index"
            >
              <v-progress-linear
                  class="ma-4"
                  v-model="data.value"
                  color="white"
                  height="25"
              ><strong>{{data.value}} %</strong></v-progress-linear>
            </div>
          </v-col>
        </v-row>
        <!-- 댓글 끝 -->
        <v-row style="height: 500px">

        </v-row>

      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "BookDetailComponent",
  data: function (){
    return{
      //책 정보
      bid: this.$route.query.bid,
      bookData : [],
      keyword : '',

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


      interval: {},
      ratings:[
        {value: 0, star: 30},
        {value: 0, star: 50},
        {value: 0, star: 20},
        {value: 0, star: 70},
      ],

    }
  },
  beforeDestroy () {
    clearInterval(this.interval)
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
    startBuffer () {
      //clearInterval(this.interval)
      for(let i = 0 ; i<4; i++) {
        this.interval = setInterval(() => {
          if (this.ratings[i].value === this.ratings[i].star) {
            return (this.ratings[i].star)
          }
          this.ratings[i].value += 1
        }, 100)
      }
    },
  },
  mounted() {
    this.getBookDetail()
    window.scrollTo(0, 0);

    this.startBuffer()
  }
}

</script>

<style scoped>
.v-progress-circular {
  margin: 1rem;
}

.title-br{
  display: none;
}

.price-card{
  outline: rgb(220,220,220) 1px solid;
  border-radius: 5px 30px 30px 30px;
}
.theme--light.v-timeline:before {
  background: rgb(60,60,60);
}

.nav-menu-main-text{
  color: rgb(200,200,200);

  font-size: 14px;
}
.nav-menu-sub-text{
  color: rgb(200,200,200);
  font-size: 13px;
}
.nav-menu{
  display: block;
}


@media screen and (max-width: 768px){
  .title-br{
    display: block;
  }
  .nav-menu{
    display: none;
  }
}

</style>