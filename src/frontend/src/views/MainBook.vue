<template>
    <v-container>
      <div style="height: 200px"/>
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
                height="700px"
            >
              <v-sheet
                  width="100%"
                  height="680px"
                  color="white"
              >
                <v-row>
                  <v-col class="img-area" cols="12" sm="4" >
                    <v-img :src="book.bookThumb" width="480px" height="712px"></v-img>
                  </v-col>

                  <v-col class="info-area" cols="12" sm="7">
                    <div>
                      <v-chip-group
                          active-class="primary--text"
                          multiple
                          v-model="selection"
                      >
                        <v-chip
                            v-for="keyword in keywords[index]"
                            :key="keyword"
                            :value="keyword"
                            outlined
                        >{{keyword}}
                        </v-chip>
                      </v-chip-group>
                    </div>

                    <div style="margin-top: 10%">
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
  },
  mounted() {
    this.getBookInfo();
  }
}
</script>

<style scoped>
.book-content{
  text-overflow: ellipsis;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  color: black;
}
::v-deep .v-carousel__controls > div{
  margin-bottom: 17%;
  margin-left: 67%;
}
::v-deep .v-carousel__controls > div > button{
  width: 40px;
  height: 40px;
  color: mediumseagreen !important;
}
::v-deep .v-carousel__controls > div > button > span > i {
  font-size: 33px !important;
  color: #1b5e20 !important;
}


.img-area{
  margin-top: 50px;
  margin-left: 90px;
}
.info-area{
  margin-top: 50px;
}
.book-title{
  font-size: 27px;
}
</style>