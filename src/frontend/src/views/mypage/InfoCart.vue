<template>
  <v-container fluid class="align-center">

    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="no-gutters pa-0 ma-0 mt-10" >
        <span class="main-title">장바구니</span>
      </v-col>
    </v-row>

    <v-row class="pa-0 ma-0 justify-end">
      <v-col cols="12" md="6" offset="3" class="pa-0 ma-0 pl-1 pr-1 mt-6">
        <v-row style="background-color: rgb(40,40,40)" class="pb-4 justify-center align-center">

          <v-col cols="3" class="pa-0">
              <v-checkbox
                  dark class="mb-3"
                  on-icon="mdi-check-circle blue--text"
                  off-icon="mdi-check-circle-outline white--text"
                  label="전체선택"
                  v-model="selectAll"
                  hide-details/>
          </v-col>

          <v-col cols="8" class="pa-0 justify-end d-flex">
            <v-btn
                class="mt-1 mr-2"
                rounded small
            >
              <span style="font-size: 15px">선택삭제</span>
              <v-icon class="pl-1" size="17">mdi-delete</v-icon>
            </v-btn>
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
                    class="pb-4" v-model="selected" :value="data"
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
              <v-col cols="7" md="5" class="mt-5 mb-5">
                <!--책 제목과 저자-->
                <v-row class="book-text ml-1 ma-0">
                  <span>{{data.bookTitle}}</span>
                </v-row>
                <v-row class="book-subtext ml-1 ma-0">
                  <span>{{data.bookAuthor}} | {{data.bookPublisher}}</span>
                </v-row>
              </v-col>

              <v-col cols="2" md="2">
                <v-text-field
                    dark dense outlined solo
                    v-model="data.bookCount"
                    height="27px" style="width: 95px"
                >
                  <template v-slot:prepend-inner >
                    <v-icon size="18px" class="pr-2" @click="setCount(index,1)">mdi-minus</v-icon>
                  </template>
                  <template v-slot:append >
                      <v-icon size="18px" @click="setCount(index,0)">mdi-plus</v-icon>
                  </template>
                </v-text-field>
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

      <!--right nav-->
      <v-col cols="12" md="3">
        <v-row class="pa-0 ma-0">
          <v-card
              elevation="0"
              color="transparent"
              class="ma-8 mt-0 mb-4 nav-menu"
              width="300px"
              rounded
          >
            <v-divider></v-divider>
            <v-divider dark></v-divider>
            <v-row class="pa-0 ma-8 nav-text">
              <span>{{selected.length}}</span>
              <span style="font-weight: bold">권을 선택하셨습니다</span>
            </v-row>

            <v-row class="pa-0 ma-4 justify-center align-center">
              <span class="nav-text">총 결제금액</span>
              <span style="font-size: 30px" class="yellow--text text--darken-2 pr-1 pl-1">
                {{totalAmount}}
              </span>
              <span class="nav-text">원</span>
            </v-row>
            <v-divider dark></v-divider>

            <v-divider></v-divider>
          </v-card>
        </v-row>
        <v-row class="ma-0">
          <v-col class="pa-0" cols="12">
            <v-btn class="ma-8 mt-4" color="yellow darken-2" max-width="300px" width="80%" height="50px">
              <span style="font-weight: bold; font-size: 20px; color: rgb(40,40,40)">결제하기</span>
            </v-btn>
          </v-col>
        </v-row>
      </v-col>
      <!--right nav End-->

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

      selected:[],
      totalAmount : 0,
    }
  },
  computed:{
    selectAll: {
      get(){
        return this.bookData ? this.selected.length == this.bookData.length : false;
      },
      set(value) {
        let selected = [];

        if (value) {
          this.bookData.forEach(function (data) {
            selected.push(data);
          });
        }
        this.selected = selected;
      }
    }
  },
  watch: {
    selected(val){
      this.totalAmount = val.map(item => (item.bookSalePrice * item.bookCount)).reduce((prev,curr) => prev + curr,0)
    }
  },
  methods: {

    getBookInfo() {
      this.$axios.get('cart/')
          .then(response => {
            this.bookData = response.data;
            for(let i =0; i<response.data.length; i++){
              this.$set(this.bookData[i],'select',false)
            }
          })
          .catch(error => {
            console.log(error.response);
          })
    },


    setCount(index,data){
      if(data === 0){
        this.bookData[index].bookCount = this.bookData[index].bookCount+1
      }
      if(data === 1 && this.bookData[index].bookCount>1){
        this.bookData[index].bookCount = this.bookData[index].bookCount-1
      }
    }
  },

  mounted() {
    this.getBookInfo();
  }
}
</script>

<style lang="scss">
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


.nav-menu{
  outline: rgb(200,200,200) 1.5px solid;
  border-radius: 30px 30px 30px 30px;
}
.nav-text{
  color: rgb(200,200,200);
  font-size: 17px;
}

.v-text-field .v-input__control .v-input__slot {
  min-height: 0 !important;
  padding: 4px 8px !important;
  margin-bottom: 2px !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
}



</style>