<template>
  <v-container fluid class="align-center">


    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="no-gutters pa-0 ma-0 mt-10" >
        <span class="main-title">장바구니</span>
      </v-col>
    </v-row>

    <v-row class="pa-0 ma-0 justify-center align-center">
      <v-col cols="12" md="6" class="pa-0 ma-0 pl-1 pr-1 mt-6">
        <v-row style="background-color: rgb(40,40,40)" class="pb-4 justify-center align-center">

          <v-col cols="3" class="pa-0">
              <v-checkbox
                  dark class="mb-3"
                  on-icon="mdi-check-circle blue--text"
                  off-icon="mdi-check-circle-outline white--text"
                  label="전체선택"
                  hide-details/>
          </v-col>

          <v-col cols="8" class="white--text">
            <div>
              <v-icon class="pl-4" color="white" size="20">mdi-check</v-icon>
              <span class="pl-1"> 권 선택</span>
            </div>
          </v-col>
          <v-col cols="12" class="pa-0 mb-4">
            <v-divider class="ma-1 ml-8 mr-8" style="background-color:rgb(200,200,200)"/>
          </v-col>


          <!--책 이미지 영역-->
          <v-col cols="12" md="11" class="pa-0"
                 v-for="(data,index) in bookData"
                 :key="index"
          >
            <v-row align="center" class="ma-0 pa-0">
              <v-col cols="1" class="justify-end d-flex">
                <v-checkbox
                    class="pb-4"
                    on-icon="mdi-check-circle blue--text"
                    off-icon="mdi-check-circle-outline white--text"
                    hide-details/>
              </v-col>

              <v-col cols="12" md="2" class="justify-center d-flex">
                <v-img
                    :src="data.bookThumb"
                    :lazy-src="data.bookThumb"
                    alt="bookThumb"
                    max-height="130"
                    max-width="90">
                  <template v-slot:placeholder>
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

              <!--책 이미지 영역 끝-->
              <v-col cols="7" md="6" class="mt-5 mb-5">
                <!--책 제목과 저자-->
                <v-row class="book-text ml-1 ma-0">
                  <span>{{data.bookTitle}}</span>
                </v-row>
                <v-row class="book-subtext ml-1 ma-0">
                  <span>{{data.bookAuthor}} | {{data.bookPublisher}}</span>
                </v-row>
              </v-col>

              <v-col cols="2" md="1" class=" mt-5 mb-5 justify-start d-flex">
                <span>수량</span>
              </v-col>
              <v-col cols="3" md="2" class=" mt-5 mb-5 justify-end d-flex">
                <span class="book-text pr-4">{{data.bookSalePrice}} 원</span>
              </v-col>

              <v-col cols="12" class="pa-0 pt-4 pb-4">
              <v-divider dark class="ml-2 mr-2"></v-divider>
              </v-col>

            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>


    <div style="height: 800px"></div>
  </v-container>
</template>

<script>
export default {
  name: "InfoCart",
  data: function (){
    return{
      bookData:[],
      bid: 5,
    }
  },
  methods: {
    getBookInfo() {
      this.$axios.get('cart/')
          .then(response => {
            this.bookData = response.data;
          })
          .catch(error => {
            console.log(error.response);
          })
    },
  },

  mounted() {
    this.getBookInfo();
  }
}
</script>

<style scoped>
.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(240,240,240);
}

.book-text{
  color: #BDBDBD;
  font-size: 18px;
  font-weight: bold;
}
.book-subtext{
  color: #BDBDBD;
  font-size: 13px;
}
</style>