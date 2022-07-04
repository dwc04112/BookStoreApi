<template>
  <v-container>
    <v-col cols="12" md="10" class="pa-16">

        <v-row class="pa-0">
          <v-card-title>
            <h3 style="color: rgb(200,200,200)">도서 등록</h3>
          </v-card-title>
        </v-row>

        <v-row style="justify-content: right">
          <h4 style="color: rgb(200,200,200)">* 표시는 필수 입력사항 입니다</h4>
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
              <v-col cols="12" sm="5">
                <v-select
                    v-model="selectedTag"
                    :items="detailTag"
                    item-text="main"
                    item-value="sub"
                    label="*Tag"
                >
                </v-select>
              </v-col>
              <v-col cols="12" sm="5"  offset-sm="1">
                <v-select
                    v-model="selectedSubTag"
                    :items="selectedTag"
                    item-text="subMain"
                    item-value="num"
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

        <v-row class="justify-end pt-6">
          <v-col cols="2" class="justify-end">
            <v-btn
                @click="commit"
                rounded
                min-width="130"
                elevation="0"
                class="blue white--text"
            ><h3>등록하기</h3>
            </v-btn>
          </v-col>
      </v-row>

    </v-col>
  </v-container>
</template>

<script>

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

        {
          main: '소설',
          num: '00',
          sub: [
            {num: '0000', subMain: '한국소설'},
            {num: '0001', subMain: '영미소설'},
            {num: '0002', subMain: '일본소설'},
            {num: '0003', subMain: '중국소설'},
            {num: '0004', subMain: '러시아소설'},
            {num: '0005', subMain: '프랑스소설'},
            {num: '0006', subMain: '독일소설'},
            {num: '0007', subMain: '북유럽소설'},
            {num: '0008', subMain: '그외유럽소설'},
            {num: '0009', subMain: '기타나라소설'},
            {num: '0010', subMain: '고전소설'},
            {num: '0011', subMain: '장르소설'},
          ]
        },
        {
          main : '시/에세이',
          num: '01',
          sub : [
            {num: '0100', subMain: '한국시'},
            {num: '0101', subMain: '해외시'},
            {num: '0102', subMain: '나라별 에세이'},
            {num: '0103', subMain: '인물/자전적에세이'},
            {num: '0104', subMain: '청소년 시/에세이'}
          ]
        },
        {
          main : '자기계발',
          num: '02',
          sub : [
            {num: '0200', subMain: '자기능력계발'},
            {num: '0201', subMain: '비즈니스'},
            {num: '0202', subMain: '능력개발'},
            {num: '0203', subMain: '화술/협상'},
          ]
        },
        {
          main : '인문',
          num: '03',
          sub : [
            {num: '0300', subMain: '인문일반학'},
            {num: '0301', subMain: '심리학'},
            {num: '0302', subMain: '교육학'},
            {num: '0303', subMain: '유아교육'},
            {num: '0304', subMain: '특수교육'},
            {num: '0305', subMain: '임용고시'},
            {num: '0306', subMain: '철학'},
            {num: '0307', subMain: '문학이론'},
            {num: '0308', subMain: '언어학'},
            {num: '0309', subMain: '독서/글쓰기'},
            {num: '0310', subMain: '문헌정보학'},
          ]
        },
        {
          main : '역사/문화',
          num: '04',
          sub : [
            {num: '0400', subMain: '역사일반'},
            {num: '0401', subMain: '세계사'},
            {num: '0402', subMain: '서양사'},
            {num: '0403', subMain: '동양사'},
            {num: '0404', subMain: '한국사'},
            {num: '0405', subMain: '신화'},
            {num: '0406', subMain: '민속학'},
            {num: '0407', subMain: '문화사'},
            {num: '0408', subMain: '역사인물'},
            {num: '0409', subMain: '역사기행'},
            {num: '0410', subMain: '지리학'},
          ]
        },
        {
          main : '종교',
          num: '05',
          sub : [
            {num: '0500', subMain: '종교일반'},
            {num: '0501', subMain: '기독교'},
            {num: '0502', subMain: '가톨릭'},
            {num: '0503', subMain: '불교'},
            {num: '0504', subMain: '그외종교'},
          ]
        },
        {
          main : '정치/사회',
          num: '06',
          sub : [
            {num: '0600', subMain: '정치/외교'},
            {num: '0601', subMain: '행정/정책'},
            {num: '0602', subMain: '국방/군사'},
            {num: '0603', subMain: '법학'},
            {num: '0604', subMain: '사회학'},
            {num: '0605', subMain: '사회문제/복지'},
            {num: '0606', subMain: '언론/신문/방송'},
            {num: '0607', subMain: '정치/사회문고'},
          ]
        },
        {
          main : '예술/대중문화',
          num: '07',
          sub : [
            {num: '0700', subMain: '예술일반'},
            {num: '0701', subMain: '미술'},
            {num: '0702', subMain: '만화/애니메이션'},
            {num: '0703', subMain: '디자인'},
            {num: '0704', subMain: '패션/의류'},
            {num: '0705', subMain: '음악'},
            {num: '0706', subMain: '연극'},
            {num: '0707', subMain: '영화'},
            {num: '0708', subMain: '예술기행'},
            {num: '0709', subMain: '예술문화'},
          ]
        },
        {
          main : '과학',
          num: '08',
          sub : [
            {num: '0800', subMain: '과학이론'},
            {num: '0801', subMain: '수학'},
            {num: '0802', subMain: '물리학'},
            {num: '0803', subMain: '화학'},
            {num: '0804', subMain: '지구과학'},
            {num: '0805', subMain: '생물과학'},
            {num: '0806', subMain: '천문학'},
            {num: '0807', subMain: '청소년 교향과학'},
          ]
        },
        {
          main : '기술/공학',
          num: '09',
          sub : [
            {num: '0900', subMain: '건축'},
            {num: '0901', subMain: '토목/건설'},
            {num: '0902', subMain: '환경/소방/도시/조경'},
            {num: '0903', subMain: '자동차/운전'},
            {num: '0904', subMain: '공학일반'},
            {num: '0905', subMain: '금속/재료'},
            {num: '0906', subMain: '기계/역학/항공'},
            {num: '0907', subMain: '전기/전자'},
            {num: '0908', subMain: '농수산/축사'},
            {num: '0909', subMain: '생활과학'},
            {num: '0910', subMain: '의학'},
          ]
        },
        {
          main : '컴퓨터/IT',
          num: '10',
          sub : [
            {num: '1000', subMain: '컴퓨터공학'},
            {num: '1001', subMain: 'IT일반'},
            {num: '1002', subMain: '데이터베이스'},
            {num: '1003', subMain: '네트워크'},
            {num: '1004', subMain: '보안/해킹'},
            {num: '1005', subMain: '프로그래밍/언어'},
            {num: '1006', subMain: '웹프로그래밍'},
            {num: '1007', subMain: '게임'},
            {num: '1008', subMain: '모바일 프로그래밍'},
            {num: '1009', subMain: '그래픽'},
            {num: '1010', subMain: '멀티미디어'},
            {num: '1011', subMain: 'CAD'},
            {num: '1012', subMain: '자격증/수험서'},
          ]
        },
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
          this.$axios.get("https://dapi.kakao.com/v3/search/book",{
            params : {
              query : title ,
              target : 'title'
            },
            headers: {'Authorization': 'KakaoAK 4df208f0ba1d7575a4e67ef3822dcf1c'},

        })
          .then(response=>{
            console.log(response.data);
          })
          .catch(error =>{
            console.log(error.response);
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
              this.translator = info.translators[0]
              this.content = info.contents
              this.price = info.price
              this.sale_price = info.sale_price
              this.publisher = info.publisher
              this.date = info.datetime.substring(0,10)

              if(info.isbn.length === 24){
                this.isbn = info.isbn.substring(11,24)+('(')+info.isbn.substring(0,10)+(')')
                this.thumb = ('http://image.kyobobook.co.kr/images/book/xlarge/')+info.isbn.substring(21,24)+('/x')+info.isbn.substring(11,24)+('.jpg')
              }else{
                this.isbn = info.isbn
                this.thumb = ('http://image.kyobobook.co.kr/images/book/xlarge/')+info.isbn.substring(21,24)+('/x')+info.isbn+('.jpg')
              }
            })
            .catch(error =>{
              console.log(error.response);
            })
      }
    },

    commit(){

      let bookData = {}

      bookData.bookTitle = this.title
      bookData.bookSubTitle = this.sub_title
      bookData.bookAuthor = this.author.toString()
      bookData.bookPublisher = this.publisher
      bookData.bookPublishedDate = this.date

      bookData.bookTranslator = this.translator

      bookData.bookIsbn = this.isbn
      bookData.bookPage = Number(this.page)
      bookData.bookPrice = Number(this.price)
      bookData.bookSalePrice = Number(this.sale_price)
      bookData.bookSize = this.size
      bookData.bookThumb = this.thumb
      bookData.bookContent = this.content
      bookData.bookIndex = this.index
      bookData.bookPreview = this.preview
      bookData.bookTag = this.selectedSubTag
      if(this.bookKeyword!==''){
        bookData.bookKeyword = this.keyword.toString()
      }


      this.$axios.post('book/',JSON.stringify(bookData),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response=>{
        console.log(response.data)
        alert("책 등록신청을 완료하였습니다.");
        this.$router.go(0)
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

<style lang="scss">
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