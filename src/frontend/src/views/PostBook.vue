<template>
  <v-app>
    <v-container>
      <v-layout>
        <v-flex>

      <v-row class="inner_row">
        <h1 style="color: #404040">도서 등록</h1>
      </v-row>

      <v-row style="justify-content: right">
        <h4 style="color: #404040;">* 표시는 필수 입력사항 입니다</h4>
      </v-row>

      <v-row>
        <v-card class="name_card" elevation="0" style="border-radius: 0; border: 1.5px gray solid ; border-collapse: collapse" color="#EEEEEE">
          <v-card-title>
            도서 이름
          </v-card-title>
        </v-card>
        <v-card class="input_card" elevation="0" style="border-radius: 0; border-top : 1.5px gray solid; border-right: 1.5px gray solid; border-bottom: 1.5px gray solid;">
          <v-row class="inner_row">
            <v-col cols="12" sm="11" >
                <v-text-field
                    label="*제목"
                    v-model="title"
                    :rules="[rules.required]"
                    append-outer-icon="mdi-magnify"
                    @click:append-outer="searchTitle(title)"
                ></v-text-field>
            </v-col>

            <v-col cols="12" sm="11" >
              <v-text-field
                  style="margin-bottom: 2%"
                  label="*부제목"
                  v-model="sub_title"
                  :rules="[rules.required]"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-card>
      </v-row>



      <v-row>
        <v-card class="name_card" elevation="0"  style="border-radius: 0; border-left: 1.5px gray solid; border-right: 1.5px gray solid;"  color="#EEEEEE">
          <v-card-title>
            출판 정보
          </v-card-title>
        </v-card>
        <v-card class="input_card" elevation="0" style="border-radius: 0; border-right: 1.5px gray solid;" >
              <v-row class="inner_row" >
                <v-col cols="12" sm="5" >
                  <v-text-field
                      v-model="publisher"
                      label="*출판사"
                      :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" sm="5"  offset-sm="1">

                  <v-menu
                      ref="menu"
                      v-model="menu"
                      :close-on-content-click="false"
                      :return-value.sync="date"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                          v-model="date"
                          label="출간일"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker
                        v-model="date"
                        no-title
                        scrollable
                    >
                      <v-spacer></v-spacer>
                      <v-btn
                          text
                          color="primary"
                          @click="menu = false"
                      >
                        Cancel
                      </v-btn>
                      <v-btn
                          text
                          color="primary"
                          @click="$refs.menu.save(date)"
                      >
                        OK
                      </v-btn>
                    </v-date-picker>
                  </v-menu>
                </v-col>


                <v-col cols="12" sm="5" >
                  <v-text-field
                      label="*저자 명"
                      v-model="author"
                      :rules="[rules.required]"
                      style="margin-bottom: 2%"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="5"   offset-sm="1">
                  <v-text-field
                      v-model="translator"
                      label="번역가"
                      :rules="[rules.required]"
                      style="margin-bottom: 2%"
                  ></v-text-field>
                </v-col>
              </v-row>
        </v-card>
      </v-row>

      <v-row>
        <v-card class="name_card" elevation="0"  style=" border-radius: 0; border: 1.5px gray solid;" color="#EEEEEE">
            <v-card-title>
              도서 정보
            </v-card-title>
        </v-card>

        <v-card class="input_card" elevation="0"  style="border-radius: 0; border-right: 1.5px gray solid; border-bottom: 1.5px gray solid; border-top: 1.5px gray solid;" >
          <v-row class="inner_row">
              <v-col cols="12" sm="5" >
                <v-text-field
                    v-model="isbn"
                    placeholder="13자리 또는 10자리 ISBN을 입력해주세요"
                    label="*ISBN"
                    :rules="[rules.required, rules.min, rules.intStrType]"
                    append-outer-icon="mdi-magnify"
                    @click:append-outer="searchIsbn(isbn)"
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="5"   offset-sm="1">
                <v-text-field
                    v-model="page"
                    label="*페이지 수"
                    :rules="[rules.required, rules.intType]"
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="5" >
                <v-text-field
                    v-model="price"
                    label="*가격"
                    :rules="[rules.required, rules.intType]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="5"  offset-sm="1" >
                <v-text-field
                    v-model="sale_price"
                    label="*판매 가격"
                    :rules="[rules.required, rules.intType]"
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="5" >
                <v-text-field
                    v-model="size"
                    label="책 사이즈 (가로*세로*너비/무게g)"
                    :rules="[rules.required]"
                > </v-text-field>
              </v-col>

              <v-col cols="12" sm="5" offset-sm="1">
                <v-text-field
                    style="margin-top: 5%"
                    v-model="thumb"
                    label="*책 표지 URL등록"
                    outlined
                    dense
                ></v-text-field>
              </v-col>
        </v-row>
      </v-card>
    </v-row>


      <v-row>
        <v-card class="name_card" elevation="0" style="border-radius: 0; border-left: 1.5px gray solid; border-right: 1.5px gray solid;"  color="#EEEEEE" >
          <v-card-title>
            도서 소개
          </v-card-title>
        </v-card>

        <v-card class="input_card" elevation="0" style="border-radius: 0; border-right: 1.5px gray solid;" >
          <v-row class="inner_row">
                <v-col cols="12" sm="11"  >
                  <v-textarea
                      style="margin-top: 30px ;"
                      v-model="content"
                      label="책 소개"
                      auto-grow
                      outlined
                      row-height="40"
                  ></v-textarea>
                </v-col>
          </v-row>
          <v-row class="inner_row">
            <v-col cols="12" sm="11"  >
              <v-textarea
                  style="margin-top: -6% ;"
                  v-model="index"
                  label="책 목차"
                  auto-grow
                  outlined
                  row-height="40"
              ></v-textarea>
            </v-col>
          </v-row>
          <v-row class="inner_row">
            <v-col cols="12" sm="11"  >
              <v-textarea
                  style="margin-top: -6% ;"
                  v-model="preview"
                  label="출판사 서평"
                  auto-grow
                  outlined
                  row-height="40"
              ></v-textarea>
            </v-col>
          </v-row>
        </v-card>
      </v-row>




      <v-row>
        <v-card class="name_card" elevation="0" style="border-radius: 0; border: 1.5px gray solid;" color="#EEEEEE">
          <v-card-title>
            도서 태그
          </v-card-title>
        </v-card>

        <v-card class="input_card" elevation="0" style="border-radius: 0; border-right: 1.5px  gray solid; border-bottom: 1.5px gray solid; border-top: 1.5px gray solid;">
          <v-row class="inner_row" >
                <v-col cols="12" sm="5"  >
                  <v-select
                      v-model="selectedTag"
                      :items=detailTag
                      item-text="main"
                      item-value="sub"
                      label="*Tag"
                      return-object
                  >
                  </v-select>
                </v-col>
                <v-col cols="12" sm="5"  offset-sm="1">
                  <v-select
                      v-model="selectedSubTag"
                      :items=selectedTag.sub
                      label="*subTag"
                  >
                  </v-select>
                </v-col>

                <v-col cols="12" sm="11">
                  <v-combobox
                      style="margin-bottom: 2%"
                      v-model="keyword"
                      :items="items"
                      :search-input.sync="search"
                      hide-selected
                      hint="최대 5가지 키워드를 입력해주세요"
                      label="키워드를 입력해주세요"
                      multiple
                      persistent-hint
                      small-chips
                  >
                    <template v-slot:no-data>
                      <v-list-item>
                        <v-list-item-content>
                          <v-list-item-title>
                            <kbd>Enter</kbd> 를 눌러 <strong>{{ search }}</strong> 키워드를 등록
                          </v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>
                    </template>
                  </v-combobox>
                </v-col>
          </v-row>
        </v-card>
      </v-row>



          <v-row justify="center" style="margin-bottom: 5%; margin-top: 5%">
            <v-col cols="12" sm="1">
              <v-btn
                  @click="cancel"
                  elevation="0"
                  color="#EEEEEE"
                  style="border: gray 1.5px solid; background-color: #EEEEEE"
                  width="150%"
              >
                <h3>취소</h3>
              </v-btn>
            </v-col>

            <v-col cols="12" sm="1" offset-sm="1" >
              <v-btn
                  @click="commit"
                  outlined
                  elevation="0"
                  style="border: gray 1.5px solid; background-color: #EEEEEE"
                  width="150%"
              ><h3>등록</h3></v-btn>
            </v-col>
          </v-row>


      <div></div>




        </v-flex>
      </v-layout>
    </v-container>
  </v-app>
</template>

<script>
import $ from 'jquery'

export default {
  name: "PostBook",
  data: function (){
    return{
      title : '',
      sub_title : '',

      author : [],
      publisher: '',
      translator : [],

      price : '',
      sale_price : '',
      page : '',
      size : '',
      // length : '',
      // width : '',
      // height : '',
      // weight : '',

      index : '',
      preview: '',
      content : '',

      isbn : '',
      thumb : '',

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
      selectedTag : [],
      selectedSubTag : '',
      rules:{
        required : value => !!value || '필수 입력란입니다.',
        min : v => (v && v.length === 10 || v.length ===13 || v.length === 25) || 'ISBN은 10자 또는 13자 입니다',
        intType :  v => /^[0-9]*$/.test(v) || '숫자만 입력해주세요.',
        intStrType : v => /^[a-zA-Z0-9 ()]*$/.test(v) || '영문+숫자만 입력 가능합니다.', /*space & () 제외*/
        img : value => !value || 0< value.size < 2000000 || 'Avatar size should be less than 2 MB!',
      },

      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      menu: false,


      items: [],
      keyword: [],
      search: null,
      tmp : '',
  }},


  methods: {
    searchTitle(title) {
      console.log(title)
      if (title !== undefined && title !== "") {
        $.ajax({
          url: "https://dapi.kakao.com/v3/search/book",
          headers: {'Authorization': 'KakaoAK 4df208f0ba1d7575a4e67ef3822dcf1c'},
          type : "get",
          data:{
            query: title,
            target:'title'
          },
          success:function(data){
            console.log(data);
          },
          error : function(e){
            console.log(e);
          }
        })
      }
    },
    searchIsbn(isbn){
      console.log(isbn)
      if (isbn !== undefined && isbn !== "") {
        this.$axios.get("https://dapi.kakao.com/v3/search/book",{
          params : {
            query : isbn ,
            target : 'isbn'
          },
          headers: {'Authorization': 'KakaoAK 4df208f0ba1d7575a4e67ef3822dcf1c'},
        })
            .then(response=>{
              console.log(response.data);
              let info = response.data.documents[0]
              this.title = info.title
              this.author = info.authors
              this.translator = info.translators
              this.content = info.contents
              this.price = info.price
              this.sale_price = info.sale_price
              this.thumb = info.thumbnail
              this.publisher = info.publisher
              this.date = info.datetime.substring(0,10)

              if(info.isbn.length === 24){
                this.isbn = info.isbn.substring(11,24)+('(')+info.isbn.substring(0,10)+(')')
              }else{
                this.isbn = info.isbn
              }
            })
            .catch(error =>{
              console.log(error.response);
            })
      }
    },

    commit(){
      let bookData = {}

      bookData.bTitle = this.title
      bookData.bSubTitle = this.sub_title
      bookData.bAuthor = this.author.toString()
      bookData.bPublisher = this.publisher
      bookData.bPublishedDate = this.date
      bookData.bTranslator = this.translator.toString()
      bookData.bIsbn = this.isbn
      bookData.bPage = Number(this.page)
      bookData.bPrice = Number(this.price)
      bookData.bSalePrice = Number(this.sale_price)
      bookData.bSize = this.size
      bookData.bThumb = this.thumb
      bookData.bContent = this.content
      bookData.bIndex = this.index
      bookData.bPreview = this.preview
      bookData.bTag = this.selectedTag.main
      bookData.bDetailTag = this.selectedSubTag
      bookData.bKeyword = this.keyword.toString()


      this.$axios.post('book/',JSON.stringify(bookData),{
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
    },

    cancel(){
      this.$axios.get('book/')
        .then(response=>{
            console.log(response.data);
          })
        .catch(error =>{
          console.log(error.response);
        })
      },
  },
}
</script>

<style scoped>
.size_field{
  display: flex;
  width: 20%;
  float: left;
}

.name_card{
  display: flex;
  align-items: center;
  justify-content: center;
  width: 15%;
}

.input_card{
  width: 85%;
}
.inner_row{
  justify-content: center;
  margin-bottom: 3%;
  margin-top: 3%;
}

</style>