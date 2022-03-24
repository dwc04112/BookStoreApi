<template>
    <v-container>
      <div style="height: 150px"/>
      <v-carousel
          cycle
          height="800px"
          hide-delimiter-background
          :show-arrows="false"
      >
        <v-carousel-item
            v-for="(book, index) in bookDatas"
            :key="index"
            reverse-transition="fade-transition"
        >
          <v-layout align-center justify-center>
            <v-sheet
                color="rgba(0,0,0,0)"
                width="85%"
                height="490px"
            >
              <v-sheet
                  width="100%"
                  height="476px"
                  color="#FFEDDB"
              >
                <v-row>
                  <v-col class="img-area" cols="12" sm="4" xs="8" style="display: flex" >

                    <div
                        class="img-box"
                        style="
                        height : 477px;
                        width: 322px;
                        background-color: rgba(83, 67, 64,0.4);
                        position: absolute;
                        margin-top: 8px;
                        margin-left: 8px"/>

                    <v-img :src="book.bookThumb"
                           class="main-img"
                           width="322px"
                           height="477px"
                           style="position: absolute"
                    ></v-img>

                  </v-col>

                  <v-col class="info-area" cols="12" sm="6">
                    <div class="main-chip-div">
                      <v-chip
                          class="ml-2"
                          v-for="keyword in keywords[index]"
                          :key="keyword"
                          :value="keyword"
                          outlined
                          small
                          @click="pushKeyword(keyword)"
                      >{{keyword}}
                      </v-chip>
                    </div>

                    <div style="margin-top: 7%">
                      <a class="book-title"> {{book.bookTitle}} </a>
                      <a>{{book.bookAuthor}} | {{book.bookPublisher}}</a>
                      <a class="book-content">{{book.bookPreview}}</a>
                      <br>
                      <a class="book-content">{{book.bookContent}}</a>
                    </div>
                  </v-col>
                </v-row>
              </v-sheet>
            </v-sheet>
          </v-layout>
        </v-carousel-item>
      </v-carousel>


    </v-container>
</template>

<script>
export default {
  name: "MainBook",
  data: function (){
    return{
      bookDatas : [],
      keywords : [],
      selection : [],
      colors: [
        'primary',
        'secondary',
        'yellow darken-2',
        'red',
        'orange',
      ],
    }},

  methods: {
    getBookInfo(){
      this.$axios.get('book/main')
          .then(response=>{
            console.log(response.data);
            this.bookDatas = response.data
            for(let i =0; i<response.data.length; i++){
              this.keywords.push(response.data[i].bookKeyword.split(','))
            }
          })
          .catch(error =>{
            console.log(error.response);
          })
    },
    pushKeyword(keyword){
      this.$eventBus.$emit('mainKeyword',keyword)
    },
  },
  mounted() {
    this.getBookInfo();
  }
}
</script>

<style scoped>
.main-chip-div{
  margin-right: 7%;
}
.book-title{
  font-size: 18px;
}
.book-content{
  text-overflow: ellipsis;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  color: black;
  font-size: 11px;
}
::v-deep .v-carousel__controls > div{
  margin-bottom: 59%;
  margin-left: 62%;
}
::v-deep .v-carousel__controls > div > button{
  width: 26px;
  height: 26px;
  color: #534340 !important;
}
::v-deep .v-carousel__controls > div > button > span > i {
  font-size: 20px !important;
  color: #534340 !important;
}

@media screen and (max-width: 768px){
  .img-area .main-img,.img-box{
    max-width : 160px;
    max-height :250px;
    width: 160px;
  }
}

.img-area{
  margin-top: 30px;
  margin-left: 50px;
}
.info-area{
  margin-top: 50px;
  margin-left: 30px;
}
</style>