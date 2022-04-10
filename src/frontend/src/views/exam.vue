<template>
  <v-container fluid style="padding: 0">
    <v-row>

        <v-col>
          <div class="pl-12">
            <div
                style="display: inline-block;"
                class="pa-2"
                v-for="(book, index) in bookDatas"
                :key="index"
            >
              <v-card
                  width="160px"
                  elevation="2"
                  color="rgba(244, 223, 186, 0.5)"
              >
                <v-img
                    :src="book.bookThumb"
                    width="160px"
                    @click.stop="openInfo(book)"
                ></v-img>

              </v-card>
            </div>
          </div>
        </v-col>



      <transition name="sub-slide" mode="in-out">
        <v-col
            cols="5" sm="4" md="4"
            color="grey lighten-3"
            v-show="show.data"
        >

          <div
              style="background-color: rgb(40,40,40); text-align: center; height: 100vh;">

            <v-card-actions class="pr-5 pt-5">
              <v-spacer></v-spacer>
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
                  :src="selectBook.bookThumb"
                  style=" width: 200px"
              ></v-img>

              <h4 class="pt-4" style="color: rgb(220,220,220)"> {{selectBook.bookTitle}} </h4>

              <v-card-subtitle style="color: rgb(220,220,220)">{{selectBook.bookAuthor}} | {{selectBook.bookPublisher}}</v-card-subtitle>

              <v-chip-group
                  active-class="primary--text"
                  multiple
                  column
                  v-model="selection"
              >
                <v-chip
                    class="inner-chip"
                    v-for="keyword in keywords[1]"
                    :key="keyword"
                    :value="keyword"
                    outlined
                    color="rgb(220,220,220)"
                    small
                ><span>{{keyword}}</span>
                </v-chip>
              </v-chip-group>

              <v-divider></v-divider>
              <v-card-actions>
                <v-tooltip top color="pink">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                        v-bind="attrs"
                        v-on="on"
                        icon
                    >
                      <v-icon color="pink">
                        mdi-heart
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>위시리스트에 추가</span>
                </v-tooltip>

                <v-tooltip top color="green">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                        v-bind="attrs"
                        v-on="on"
                        icon
                    >
                      <v-icon color="green">
                        mdi-book-open-variant
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>책 보러가기</span>
                </v-tooltip>
              </v-card-actions>
            </div>
          </div>
        </v-col>
      </transition>


    </v-row>


  </v-container>
</template>

<script>

export default {
  name: "exam",
  data: () => ({
    bookDatas : [],
    keywords : [],
    selection : [],
    selectTag : '',
    show : {data:false , bid: null},

    selectBook : '',

  }),


  methods: {
    getBookInfo(){
      this.$axios.get('book/info')
          .then(response=>{
            this.bookDatas = response.data
            for(let i =0; i<response.data.length; i++){
              this.keywords.push(response.data[i].bookKeyword.split(','))
            }
          })
          .catch(error =>{
            console.log(error.response);
          })
    },


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
    },


  },
  mounted() {
    this.getBookInfo()
  }
}
</script>

<style scoped>

.bg {
  width: 100%;
  height: 400px;
  overflow: hidden;
  margin: 0 auto;
  position: relative;
}
video {
  width: 100%;
}
.text {
  position: absolute;
  width: 100%;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
}
.text p {
  text-align: center;
  font-size: 48px;
  color: #ffffff;
}
.right-section{
  display: block;
  position: relative;
}


.sub-slide-enter{
  transform: translateX(400px);
  opacity: 1;
}
.sub-slide-enter-active,
.sub-slide-leave-active {
  transition: all 0.3s ease-out;
}

.sub-slide-leave-to {
  transform: translateX(400px);
  opacity: 1;
}


@media screen and (max-width: 768px){
  .right-section{
    display: none;
    position: absolute;
  }
}

</style>