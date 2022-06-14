<template>
  <v-container fluid class="align-center" >

    <v-row>
      <v-col cols="12" md="8" class="pa-0 pb-8">
        <span class="main-title">나의댓글</span>
      </v-col>
    </v-row>

    <v-row class="pa-0">
      <v-col cols="12" md="8">
        <v-row style="background-color: rgb(40,40,40)" class="pb-4">

          <v-col class="pa-0 ma-0 mt-6"  v-show="noComments">
            <span style="color:rgb(80,80,80); font-size: 21px; font-weight: bold;">댓글이 없습니다. 첫번째 댓글을 작성해보세요</span>
          </v-col>

          <v-col cols="12" class="pb-2"></v-col>
          <!-- 댓글 출력 -->
          <v-col
              cols="12" class="pa-0"
              v-for="(data , index) in commentData"
              :key="index"
          >
            <v-row align="center" class="ma-0 pa-0">
              <!--책 이미지 영역-->
              <v-col cols="12" md="3" class="pl-8 align-center d-flex flex-column">
                <v-img
                    :src="data.bookThumb"
                    :lazy-src="data.bookThumb"
                    alt="bookThumb"
                    max-height="150"
                    max-width="100px"
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

              <!--책 정보 & 댓글 영역-->
              <v-col cols="12" md="9" class="pa-5 mt-5 mb-5">
                <!--삭제 버튼 영역-->
                <v-row class="mr-3">
                  <v-spacer></v-spacer>
                  <v-btn class="mr-3" small outlined color="#BDBDBD" disabled rounded dark>
                    <v-icon size="20">mdi-heart</v-icon>
                    <span class="pl-1">{{data.popularity}}</span>
                  </v-btn>
                  <v-btn class="mr-5" small outlined color="rgb(105,105,105)" icon @click="setDialog(data.cid)">
                    <v-icon size="20">mdi-close</v-icon>
                  </v-btn>
                </v-row>


                <!--책 제목과 저자-->
                <v-row class="book-text ml-1 pa-0 ma-0">
                  <span>{{data.bookTitle}}</span>
                </v-row>
                <v-row class="book-subtext ml-1 pa-0 ma-0">
                  <span>{{data.bookAuthor}} | {{data.bookPublisher}}</span>
                </v-row>

                <!--댓글-->
                <v-row class="align-center ma-0 pa-0">
                  <v-rating
                      :value="data.ratings"
                      color="yellow darken-2"
                      background-color="grey darken-2"
                      dense
                      readonly
                      length="4"
                      size="18"
                      class="pr-1"
                  ></v-rating>
                  <span class="date-text">{{data.commentDate}} 일에 작성됨</span>
                </v-row>

                <v-row class="content-text ml-1 pa-0 ma-0 pt-3">
                  <span>{{data.content}}</span>
                </v-row>
                <!--댓글 끝-->
              </v-col>
            </v-row>
            <v-divider class="ma-5 ml-7 mr-7" dark></v-divider>
          </v-col>

          <v-col cols="12" class="mt-4">
            <div class="text-center">
              <v-pagination
                  dark
                  v-model="page"
                  :length="totalPages"
                  :total-visible="5"
                  circle
                  class="my-pagination"
                  color="yellow darken-2 black--text"
                  @input="getBookComment"
              ></v-pagination>
            </div>
          </v-col>

          <!--삭제 확인 메시지-->
          <v-dialog
              max-width="400"
              v-model="dialog"
          >
              <v-card rounded color="rgb(55,55,55)" tile dark>
                <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">정말 이 댓글을 삭제할까요?</div>
                <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
                  <v-btn
                      rounded
                      text
                      @click="setDialog(0)"
                  >Close</v-btn>
                  <v-btn
                      class="ml-2"
                      rounded
                      elevation="0"
                      color="red"
                      @click="deleteComment()"
                  >Commit</v-btn>
                </v-card-actions>
              </v-card>
          </v-dialog>

          <v-snackbar
              transition="dialog-bottom-transition"
              elevation="0"
              color="white"
              rounded
              v-model="snackbar"
          >
            <div>
              <v-icon color="blue" size="30" class="pr-2">mdi-checkbox-marked-circle</v-icon>
              <span class="black--text">삭제되었습니다.</span>
            </div>
          </v-snackbar>
        </v-row>
      </v-col>
    </v-row>
  </v-container>

</template>

<script>
export default {
  name: "MyCommentComponent",
  data: () => ({
        selectKeyword: 1,   //선택된 키워드
        commentData : [],
        noComments : false,

        //page
        page : 1,
        size : 5,
        totalPages : 0,

        setCid : 0,         //삭제할 댓글 id
        dialog : false,     //삭제 확인 메시지
        snackbar : false,   //삭제 성공? 실패?
      }
  ),

  watch: {
    snackbar(val) {
      val && setTimeout(() => {
        this.snackbar = false
      }, 4000)
    },
  },

  methods: {
    getBookComment() {
      let data = {}
      data.page = this.page -1
      data.size = this.size
      this.$axios.post("comment/myComment", JSON.stringify(data), {
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response => {
        this.commentData = response.data.content;
        this.totalPages = response.data.totalPages;
        if(response.data.content.length === 0){
          this.noComments = true;
        }
      }).catch(error => {
        console.log(error.response);
      })
    },
    //댓글 삭제
    deleteComment(){
      let cid = this.setCid
      this.$axios.delete("comment/" + cid)
          .then(response=>{
            console.log(response.data.message)
            this.getBookComment();
            this.setDialog(0);
            this.snackbarDelay();
            // this.$emit('childKey')
          }).catch(error =>{
        console.log(error.response);
      })
    },

    setDialog(cid){
      this.setCid = cid
      this.dialog = !this.dialog;
    },

    snackbarDelay(){
      clearTimeout(this._timerId)
      // delay new call 500ms
      this._timerId = setTimeout(() => {
        // maybe : this.fetch_data()
        this.snackbar = true;
      }, 600)
    }

  },

  mounted() {
    this.getBookComment();
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
  color: rgb(120,120,120);
  font-size: 15px;
}
.book-subtext{
  color: rgb(120,120,120);
  font-size: 13px;
}


.date-text{
  color: rgb(120,120,120);
  font-size: 13px;
}
.content-text{
  color: rgb(160,160,160);
  font-size: 18px;
  font-weight: bold;
}
</style>