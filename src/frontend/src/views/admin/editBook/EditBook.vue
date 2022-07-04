<template>
  <v-row class="pa-0">
    <v-col>
      <v-card dark class="pa-4">
        <v-card-title>
          <h3>도서 관리</h3>
          <v-spacer></v-spacer>
          <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
          ></v-text-field>
        </v-card-title>
        <v-data-table
            dark
            height="800"
            :search="search"
            :headers="headers"
            :items="bookData"
            item-key="name"
            :items-per-page="15"
            class="elevation-0"
        >
          <template v-slot:item.isDel="{ item }">
            <v-btn
                @click="bookIsDelUpdate(item.bid)"
                :color="getColor(item.isDel)"
                rounded
            >{{item.isDel}}</v-btn>
          </template>
        </v-data-table>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>

export default {
  name: "EditBook",
  data: function (){
    return{
      bookData:[],
      search:'',
      headers: [
        {
          text: '도서번호',
          align: 'start',
          sortable: false,
          value: 'bid',
          width:'80'
        },
        { text: '도서제목', value: 'bookTitle', width:'400' },
        { text: '작가', value: 'bookAuthor', width:'200' },
        { text: '출판사', value: 'bookPublisher', width:'100' , align: 'center', },
        { text: '출판일', value: 'bookPublishedDate', width:'100', align: 'center', },
        { text: 'ISBN', value: 'bookIsbn', width:'150', align: 'center', },
        { text: '상태', value: 'isDel', width:'70', sortable: false,  align: 'center',},
      ],
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

  }},
  methods: {
    getAllBook() {
      this.$axios.get("admin/book")
          .then(response => {
            this.bookData = response.data
          }).catch(error => {
            console.log(error.response);
          })
    },

    bookIsDelUpdate(bid){
      this.$axios.delete("admin/book/"+bid)
          .then(response => {
            console.log(response.data);
            this.getAllBook();
          }).catch(error => {
            console.log(error.response);
          })
    },

    getColor(state){
      if (state === 'Y') return 'yellow darken-4'
      else return 'green darken-4'
    }

  },
  mounted() {
    this.getAllBook()
  }
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