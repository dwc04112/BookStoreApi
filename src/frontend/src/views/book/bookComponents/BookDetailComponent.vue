<template>
  <v-container fluid class="ma-0 pa-0">

    <v-row class="pl-1 pb-1 pt-8 mt-8">
     <span class="grey--text text--darken-1" style="font-size: 14px">소설 > 한국소설</span>
    </v-row>
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

        <v-timeline dense class="mt-3">
          <div style="height: 80px"></div>
          <v-timeline-item color="yellow darken-2" fill-dot>
            <v-card class="elevation-2" elevation="0" color="rgb(40,40,40)">
              <v-card-title class="mb-4">
               <v-icon class="pr-3  yellow--text text--darken-2" size="36">mdi-book</v-icon>
                <span class="grey--text text--lighten-1" style="font-size: 30px; font-weight: bold"> 책 소개 </span>
              </v-card-title>
              <v-card-text>
                <span class="grey--text text--lighten-1" v-html="bookData.bookContent"></span>
              </v-card-text>
            </v-card>
          </v-timeline-item>

          <div style="height: 120px"></div>

          <v-timeline-item color="yellow darken-2" fill-dot>
            <v-card class="elevation-2" elevation="0" color="rgb(40,40,40)">
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

          <v-timeline-item color="yellow darken-2" fill-dot>
            <v-card class="elevation-2" elevation="0" color="rgb(40,40,40)">
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
        </v-timeline>



        <!--
        <v-row class="justify-center align-center pt-16 mt-10 pb-12">
          <v-col cols="11">
            <v-icon class="pb-4 yellow--text text--darken-2" size="40">mdi-book</v-icon>
            <span class="grey--text text--lighten-1" style="font-size: 30px; font-weight: bold"> 책 소개 </span>
          </v-col>
          <v-col cols="11">
            <span class="grey--text text--lighten-1" v-html="bookData.bookContent"></span>
          </v-col>
        </v-row>

        <v-divider class="grey darken-2 ma-16" style="border: 1px solid;"></v-divider>

        <v-row class="justify-center align-center pt-12 pb-12">
          <v-col cols="11">
            <v-icon class="pb-4 yellow--text text--darken-2" size="40">mdi-book-multiple</v-icon>
            <span class="grey--text text--lighten-1" style="font-size: 30px; font-weight: bold"> 책 목차 </span>
          </v-col>
          <v-col cols="11">
            <span class="grey--text text--lighten-1" v-html="bookData.bookIndex"></span>
          </v-col>
        </v-row>

        <v-divider class="grey darken-2  ma-16" style="border: 1px solid"></v-divider>

        <v-row class="justify-center align-center pt-12 pb-12">
          <v-col cols="11">
            <v-icon class="pb-4 yellow--text text--darken-2" size="40">mdi-book-open-variant</v-icon>
            <span class="grey--text text--lighten-1" style="font-size: 30px; font-weight: bold">
              출간자 서평
            </span>
          </v-col>
          <v-col cols="11">
            <span class="grey--text text--lighten-1" v-html="bookData.bookPreview"></span>
          </v-col>
        </v-row>
        -->


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
    }
  },
  mounted() {
    this.getBookDetail()
    window.scrollTo(0, 0);

  }
}

</script>

<style scoped>
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



@media screen and (max-width: 768px){
.title-br{
  display: block;
}

}

</style>