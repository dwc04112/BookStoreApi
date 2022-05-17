<template>
  <v-row style="background-color: rgb(40,40,40)" class="pb-4 justify-center align-center">
    <v-col cols="12" class="pa-0 mt-12 mb-3 text-center" v-show="noComments">
      <span style="color:rgb(80,80,80); font-size: 21px; font-weight: bold;">댓글이 없습니다. 첫번째 댓글을 작성해보세요</span>
    </v-col>
    <v-col cols="12" class="pb-6"></v-col>
    <!-- 댓글 출력 -->
    <v-col
        cols="12" md="11" class="pa-0"
        v-for="(data , index) in commentData"
        :key="index"
    >
      <v-row align="center" class="ma-0 pa-0">
        <!--책 이미지 영역-->
        <v-col cols="12" md="3" class=" justify-center align-center d-flex flex-column">
          <v-img
              :src="data.bookThumb"
              :lazy-src="data.bookThumb"
              alt="bookThumb"
              max-height="180px"
              max-width="120px"
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
          <!--삭제 버튼-->
          <v-row>
            <v-spacer></v-spacer>
            <v-btn class="mr-3" small outlined color="#BDBDBD" disabled rounded dark>
              <v-icon size="19px">mdi-heart</v-icon>
              <span class="pl-1">{{data.popularity}}</span>
            </v-btn>
            <v-btn class="mr-5" small outlined color="rgb(105,105,105)" icon @click="deleteComment(data.cid)">
              <v-icon size="19px">mdi-close</v-icon>
            </v-btn>
          </v-row>

          <!--책 제목과 저자-->
          <v-row class="book-text ml-1 pa-0 ma-0">
            <span>{{data.bookTitle}}</span>
          </v-row>
          <v-row class="book-subtext ml-1 pa-0 ma-0 mb-6">
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

          <v-row class="content-text ml-1 pa-0 ma-0 pt-1">
            <span>{{data.content}}</span>
          </v-row>
          <!--댓글 끝-->
        </v-col>
      </v-row>

      <v-divider class="ma-7" dark></v-divider>

    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "MyCommentComponent",
  data: () => ({
        selectKeyword: 1, //선택된 키워드
        commentData : [],
        noComments : false,
      }
  ),
  methods: {
    getBookComment() {
      this.$axios.get("comment/myComment/")
          .then(response=>{
            this.commentData = response.data;
            if(response.data.length === 0){
              this.noComments = true;
            }
          }).catch(error =>{
        console.log(error.response);
      })
    },
    //댓글 삭제
    deleteComment(cid){
      this.$axios.delete("comment/" + cid)
          .then(response=>{
            console.log(response.data)
            this.$emit('childKey')
          }).catch(error =>{
        console.log(error.response);
      })
    },

  },

  mounted() {
    this.getBookComment();
  }
}
</script>

<style scoped>
.book-text{
  color: #BDBDBD;
  font-size: 18px;
}
.book-subtext{
  color: #BDBDBD;
  font-size: 13px;
}

.name-text{
  color: #BDBDBD;
}
.date-text{
  color: #BDBDBD;
  font-size: 13px;
}
.content-text{
  color: #BDBDBD;
  font-size: 15px;
}
</style>