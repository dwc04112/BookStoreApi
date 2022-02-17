<template>
  <v-app>
    <v-container>
          <v-row justify="center" style="margin-top: 3%">
            <v-col cols="12" sm="11" >
              <v-text-field
                  label="제목"
                  v-model="title"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="11" >
              <v-text-field
                  label="부제목"
                  v-model="sub_title"
              ></v-text-field>
            </v-col>


            <v-col cols="12" sm="5" >
              <v-text-field
                  v-model="publisher"
                  label="출판사"
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="5"  offset-sm="1">
              <v-text-field
                  v-model="published_date"
                  label="출간일"
              ></v-text-field>
            </v-col>


            <v-col cols="12" sm="5" >
              <v-text-field
                  label="저자 명"
                  v-model="author"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="5"   offset-sm="1">
              <v-text-field
                  v-model="translator"
                  label="번역가"
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="5" >
              <v-text-field
                  v-model="isbn"
                  label="isbn"
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="5"   offset-sm="1">
              <v-text-field
                  v-model="page"
                  label="페이지 수"
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="5" >
              <v-text-field
                  v-model="price"
                  label="가격"
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="5"   offset-sm="1">
              <v-text-field
                  v-model="thumb"
                  label="책 표지"
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="11"  >
              <v-textarea
                  v-model="content"
                  label="책 소개"
                  auto-grow
              ></v-textarea>
            </v-col>

            <v-col cols="12" sm="5" >
              <v-select

                  v-model="selectedTag"
                  :items=detailTag
                  item-text="main"
                  item-value="sub"
                  label="Tag"
                  return-object
              >
              </v-select>
            </v-col>

            <v-col cols="12" sm="5"  offset-sm="1">
              <v-select
                  v-model="selectedSubTag"
                  :items="selectedTag.sub"
                  label="subTag"
              >
              </v-select>
            </v-col>

            <v-col cols="12" sm="11">
              <v-text-field
                  v-model="keyword"
                  label="keyword"
              ></v-text-field>
            </v-col>

          </v-row>

          <v-row justify="center" style="margin-bottom: 3%; margin-top: 3%">
            <v-col cols="12" sm="1">
              <v-btn text>
                Cancel
              </v-btn>
            </v-col>

            <v-col cols="12" sm="1" >
              <v-btn
                  color="primary"
                  text
                  @click="commit"
              >submit</v-btn>
            </v-col>
          </v-row>

      <div>{{selectedTag.main}}</div>
      <div>{{selectedSubTag}}</div>
    </v-container>
  </v-app>
</template>

<script>
export default {
  name: "PostBook",
  data: () => ({
    title : '',
    sub_title : '',
    author : '',
    content : '',
    publisher: '',
    published_date: '',
    price : '',
    page : '',
    keyword : '',
    isbn : '',
    thumb : '',
    translator : '',
    detailTag : [
      {main : '소설' , sub : ['한국소설', '영미소설' , '일본소설' , '중국소설', '기타나라소설','고전소설','장르소설']},
      {main : '시/에세이',  sub : ['한국시','해외시','나라별 에세이','인물/자전적에세이']},
      {main : '자기계발', sub : ['자기능력계발','비즈니스','능력개발','화술/협상']},
      {main : '인문', sub : ['인문일반학', '심리학','교육학','유아교육','특수교육','임용고시','철학','문학이론','언어학','독서/글쓰기','문헌정보학']},
      {main : '역사/문화', sub : ['역사일반','세계사','서양사','동양사','한국사','신화','민속학']},
      {main : '종교', sub : ['종교일반','기독교','가톨릭','불교','그외종교']},
      {main : '정치/사회', sub : ['정치/외교','행정/정책','국방/군사','법학','사회학','사회문제/복지','언론/신문/방송']},
      {main : '예술/대중문화', sub : ['예술일반','미술','만화/애니메이션','디자인','패션/의류','음악','연극']},
      {main : '과학', sub : ['과학이론','수학','물리학','화학','지구과학','생물과학','천문학']},
      {main : '기술/공학', sub : ['건축','토목/건설','환경/소방/도시/조경','자동차/운전','공학일반','금속/재료']},
      {main : '컴퓨터/IT', sub : ['컴퓨터공학','IT일반','데이터베이스','네트워크','프로그래밍/언어','웹프로그래밍']},
    ],
    selectedTag : '',
    selectedSubTag : '',
  }),

  methods: {
    commit(){
      let bookData = {}
      bookData.mid = 1
      bookData.title = this.title
      bookData.sub_title = this.sub_title
      bookData.author = this.author
      bookData.translator = this.translator
      bookData.content = this.content
      bookData.page = this.page
      bookData.isbn = this.isbn
      bookData.price = this.price
      bookData.size = this.size
      bookData.thumb = this.thumb
      bookData.publisher = this.publisher
      bookData.published_date = this.published_date
      bookData.tag = this.selectedTag.main
      bookData.detail_tag = this.selectedSubTag
      bookData.keyword = this.keyword
      this.$axios.post("book/",JSON.stringify(bookData),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response=>{
        console.log(response.data)
        alert("책 등록신청을 완료하였습니다.");
        this.$router.push({path: './home'});
      }).catch(error =>{
        console.log(error.response);
      })
    }
  },
}
</script>

<style scoped>

</style>