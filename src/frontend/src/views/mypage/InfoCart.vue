<template>
  <v-container fluid >


    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="no-gutters pa-0 ma-0 mt-10" >
        <span class="main-title">장바구니</span>
      </v-col>
    </v-row>

    <v-row class="justify-center">
      <v-col cols="6" style="background-color: rgb(40,40,40)">
        <v-row align="center" class="pa-md-10" v-for="(data,index) in bookData"
               :key="index">
          <!--책 이미지 영역-->
          <v-col cols="12" md="3" class=" justify-center align-center d-flex flex-column">
            <v-img
                :src="data.bookThumb"
                :lazy-src="data.bookThumb"
                alt="bookThumb"
                max-height="150"
                max-width="100"
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
          </v-col>
          <!--책 이미지 영역 끝-->
          <v-col cols="9" md="6" class="mt-5 mb-5">
            <!--책 제목과 저자-->
            <v-row class="book-text ml-1 ma-0">
              <span>{{data.bookTitle}}</span>
            </v-row>
            <v-row class="book-subtext ml-1 ma-0">
              <span>{{data.bookAuthor}} | {{data.bookPublisher}}</span>
            </v-row>
          </v-col>
          <v-col cols="3" md="2" class="mt-5 mb-5">
            <span class="book-text">{{data.bookSalePrice}} 원</span>
          </v-col>

          <v-col cols="12">
          <v-divider dark></v-divider>
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