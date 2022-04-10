<template>
  <v-container fluid class="container">
    <v-row justify="center" align="center">
      <!-- 여백 -->
      <v-col cols="12">
        <div style="height: 100px"/>
      </v-col>


      <v-col cols="12" sm="12" md="9" lg="7" style="background-color: rgb(220,220,220); height: 3000px">
        <v-row>
            <!-- 좌측 책 이미지 & 메뉴-->
              <v-col cols="12" sm="3" md="2" style="background-color: #b0bec5; height: 3000px;" class="left-section">
                <div class="align-center flex-column d-flex">
                  <v-img width="120px" class="left-section" :src="bookData.bookThumb"></v-img>
                </div>

                <!-- 키워드 -->
                <div class="mt-4">
                  <v-chip
                      class="ma-1"
                      v-for="(data, index) in keyword"
                      :key="index"
                      :value="data"
                      outlined
                      small
                      color="rgb(20,20,20)"
                  >{{data}}
                  </v-chip>
                </div>


                <!-- 좌측 메뉴 리스트 -->
                <v-list
                    nav
                    dense
                    color="#b0bec5"
                    class="mt-4"

                >
                  <v-list-item-group
                      color="primary"
                  >
                    <v-list-item
                        v-for="(item, i) in items"
                        :key="i"

                    >
                      <v-list-item-content>
                        <v-list-item-title><b class="list-text">{{item.text}}</b></v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-list-item-group>
                </v-list>

              </v-col>

              <v-col cols="12" sm="9" md="9">

                <div class=" align-center flex-column d-flex">
                  <v-img width="120px" class="main-img" :src="bookData.bookThumb"></v-img>
                </div>

                {{bookData.bookTitle}}
                <br>
                {{bookData.bookAuthor}} | {{bookData.bookPublisher}} | {{bookData.bookPublishedDate}} 출간
                <br>
                판매가격 : {{bookData.bookSalePrice}} 원
                <br>

                <div class="mb-4">
                  <v-btn>
                    구매하기
                  </v-btn>
                  <v-btn>
                    장바구니
                  </v-btn>
                  <v-btn>
                    위시리스트
                  </v-btn>
                </div>
                <v-divider/>

                <div class="mt-4 mb-4">
                  <a>책 소개</a>
                  <br>
                  {{bookData.bookContent}}
                </div>

                <br>

                <div class="mt-4 mb-4">
                  <a>목차</a>
                  <br>
                  {{bookData.bookIndex}}
                </div>

                <br>

                <div class="mt-4 mb-4">
                  <a>출판사 서평</a>
                  <br>
                  {{bookData.bookPreview}}
                </div>

              </v-col>
            </v-row>


      </v-col>
    </v-row>
  </v-container>
</template>

<script>

export default {
  name: "DetailView",
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
    }
  },

  methods: {
    getBookDetail(){
      this.$axios.get('book/'+this.bid)
          .then(response=>{
            this.bookData = response.data
            this.keyword = (response.data.bookKeyword.split(','))
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
.container{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-content: center;
  margin: 0;
  padding: 0;
}
.list-text{
  font-weight: bold;
  font-size: 13px;
}

.main-img{
  display: none;
  position: absolute;
}

@media screen and (max-width: 768px){
  .left-section{
    display: none;
    position: absolute;
  }
  .main-img{
    display: block;
    position: relative;
  }

}

</style>