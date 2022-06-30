<template>
  <v-row class="align-center justify-end pa-0" style="background-color: rgb(40,40,40);">

    <v-col class="pa-5 align-center"  v-show="noDataMsg">
      <v-row class="justify-center mt-6">
        <span style="color:rgb(80,80,80); font-size: 21px; font-weight: bold;">배송지가 없습니다. 새로운 배송지를 등록해보세요</span>
      </v-row>
    </v-col>

    <v-col cols="12" class="pa-0">
      <v-simple-table dark v-if="addrList.length>0">
        <thead>
        <tr style="text-align: center">
          <td style="font-weight: bold; color: rgb(180,180,180)">배송지명</td>
          <td>
            <v-row>
              <v-col cols="6" style="font-weight: bold; color: rgb(180,180,180)">
                주소
              </v-col>
              <v-col cols="3" style="font-weight: bold; color: rgb(180,180,180)">
                연락처
              </v-col>
              <v-col cols="3" style="font-weight: bold; color: rgb(180,180,180)">
                수정
              </v-col>
            </v-row>
          </td>
        </tr>
        </thead>
        <tbody>
        <tr style="height: 120px;"
            v-for="(data,index) in addrList"
            :key="index">

          <td class="addr-name-td" style="text-align: center"><span class="addr-text" style="font-weight: bold; color: rgb(180,180,180)">{{data.addrName}}</span></td>
          <td class="addr-content-td">
            <v-row>
              <v-col md="6">
                <div class="addr-text grey--text text--darken-1 pl-4"> {{data.postcode}} </div>
                <div class="addr-text pl-4"> {{data.addr}} </div>
                <div class="addr-text pl-4"> {{data.detailAddr}} </div>
              </v-col>
              <v-col cols="3" class="align-center justify-center d-flex addr-text">
                {{data.phoneNum}}
              </v-col>
              <v-col cols="3" class="align-center justify-center d-flex">
                <v-btn elevation="0" class="ma-1" color="rgb(170,170,170)" small outlined @click="setEditAddr(data)">수정</v-btn>
                <v-btn elevation="0" class="ma-1" color="rgb(170,170,170)"  small outlined @click="setDelete(data.addrId)">삭제</v-btn>
              </v-col>
            </v-row>
          </td>
        </tr>
        </tbody>
      </v-simple-table>
    </v-col>

    <!--삭제 dialog-->
    <v-dialog
        max-width="400"
        v-model="deleteDialog"
    >
      <v-card rounded color="rgb(55,55,55)" tile dark>
        <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">정말 해당 주소록을 삭제하시겠습니까?</div>
        <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
          <v-btn
              class="ml-2"
              rounded
              elevation="0"
              color="grey"
              @click="deleteDialog = false"
          >
            <span style="font-weight: bold; font-size: 15px">뒤로가기</span>
          </v-btn>
          <v-btn
              class="ml-2"
              rounded
              elevation="0"
              color="red"
              @click="deleteAddr()"
          ><span style="font-weight: bold; font-size: 15px">삭제하기</span></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


    <!--주소록 수정 및 추가-->
    <v-dialog
        v-model="dialog"
        scrollable
        width="600px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            color="primary darken-1"
            class="white--text ma-4 mt-6 mb-6"
            rounded
            v-bind="attrs"
            v-on="on" width="150px"
            @click="resetData()"
        ><span style="font-weight: bold; font-size: 15px">추가하기</span></v-btn>
      </template>
      <v-card rounded color="rgb(55,55,55)" elevation="0">
        <v-card-title  style="color: rgb(220,220,220); background-color: rgb(40,40,40); font-weight: bold">배송지 추가/수정</v-card-title>
        <v-form ref="form" v-model="form">
        <v-simple-table style="background-color: rgb(55,55,55)" dark>
          <tbody>
          <tr style="height: 80px">
            <td class="addr-dialog-name-td white--text"><span style="font-size: 16px" class="pl-4">배송지 명</span></td>
            <td class="addr-dialog-content-td">
              <v-col>
                <v-text-field
                    outlined dense dark text hide-details
                    class="text-fields white--text justify-start"
                    placeholder="두 글자 이상 배송지 명"
                    :rules="[rules.min]"
                    v-model="addrName"
                >
                </v-text-field>
              </v-col>
            </td>
          </tr>

          <tr style="height: 80px">
            <td class="addr-dialog-name-td white--text"><span style="font-size: 16px" class="pl-4">우편번호</span></td>
            <td class="addr-dialog-content-td">
              <v-row class="ma-0">
                <v-col cols="8" md="6">
                  <v-text-field
                      outlined dense dark text hide-details
                      class="text-fields white--text justify-start"
                      :rules="[rules.postCheck]"
                      v-model="postcode"
                      placeholder="5자리 우편번호"
                      @click="getPostcode()"
                  >
                  </v-text-field>
                </v-col>
                <v-col cols="2" class="align-center d-flex">
                  <v-btn color="red lighten-2" fab x-small class="ml-2" @click="getPostcode()">
                    <v-icon>mdi-magnify</v-icon>
                  </v-btn>
                </v-col>
              </v-row>
            </td>
          </tr>

          <tr style="height: 80px">
            <td class="addr-dialog-name-td white--text"><span style="font-size: 16px" class="pl-4">주소</span></td>
            <td class="addr-dialog-content-td">
              <v-col>
                <v-text-field
                    outlined dense dark text disabled hide-details
                    class="text-fields white--text justify-start"
                    v-model="addr"
                >
                </v-text-field>
              </v-col>
            </td>
          </tr>
          <tr style="height: 80px">
            <td class="addr-dialog-name-td white--text"><span style="font-size: 16px" class="pl-4">세부주소</span></td>
            <td class="addr-dialog-content-td">
              <v-col>
                <v-text-field
                    outlined dense dark text hide-details
                    class="text-fields white--text justify-start"
                    v-model="detail_addr"
                >
                </v-text-field>
              </v-col>
            </td>
          </tr>
          <tr style="height: 70px">
            <td class="addr-dialog-name-td white--text"><span style="font-size: 16px" class="pl-4">연락처</span></td>
            <td class="addr-dialog-content-td">
              <v-col>
                <v-text-field
                    outlined dense dark text hide-details
                    class="text-fields white--text justify-start"
                    :rules="[rules.phoneCheck]"
                    @input="replaceNum(phoneNum)"
                    placeholder="전화번호를 확인해주세요"
                    v-model="phoneNum"
                >
                </v-text-field>
              </v-col>
            </td>
          </tr>
          </tbody>
        </v-simple-table>
        </v-form>
        <v-divider></v-divider>
        <v-card-actions style="background-color: rgb(40,40,40)">
          <v-spacer/>
          <v-btn
              color="grey darken-3"
              class="ml-2 white--text"
              rounded
              elevation="0"
              @click="dialog = false"
          >
            <span style="font-weight: bold; font-size: 15px">뒤로가기</span>
          </v-btn>
          <v-btn
              color="blue darken-3"
              class="ml-2 white--text"
              rounded
              elevation="0"
              :disabled="!form"
              @click="addAddrBook()"
          >
            <span style="font-weight: bold; font-size: 15px">저장하기</span>
          </v-btn>
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
      <div class="align-center d-flex">
        <v-icon color="blue" size="30" class="pr-2">mdi-checkbox-marked-circle</v-icon>
        <span class="black--text">{{snackbarMsg}}</span>
      </div>
    </v-snackbar>

  </v-row>
</template>

<script>
export default {
  name: "AddressList",
  data () {
    return {
      addrList:[],
      noDataMsg : false,
      dialog: false,  //추가 및 수정 다이얼로그

      deleteDialog :false,  //삭제 다이얼로그
      deleteId : 0,

      addrId : 0,
      addrName:'',
      postcode: '',
      addr: '',
      detail_addr: '',
      extra_addr: '',
      phoneNum: '',

      form : false,
      rules: {
        phoneCheck: v => /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/.test(v) || '전화번호를 확인해주세요.',
        min: v => v.length >= 2 || '2자 이상 입력해주세요',
        postCheck: v => v.length === 5 || '우편번호는 5자 입니다',
      },

      snackbar : false,
      snackbarMsg: '',
    }
  },
  watch: {
    // 확인 msg
    snackbar(val) {
      val && setTimeout(() => {
        this.snackbar = false
      }, 4000)
    },
  },

  methods: {
    //불러오기
    getMyAddrBook() {
      this.$axios.get('addr/')
          .then(response => {
            this.addrList = response.data
            this.noDataMsg = response.data.length < 1;
          })
          .catch(error => {
            console.log(error.response);
          })
    },

    //추가하기
    addAddrBook() {
      let data = {}
      data.addrId = this.addrId;
      data.addrName = this.addrName;
      data.postcode = this.postcode;
      data.addr = this.addr;
      data.detailAddr = this.detail_addr;
      data.phoneNum = this.phoneNum;
      this.$axios.post("addr/",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response => {
        console.log(response.data)
        this.snackbarDelay("주소록 저장에 성공했습니다.")
        this.getMyAddrBook();
        this.dialog = false;
        this.resetData();
      }).catch(error => {
        console.log(error.response);
      })
    },

    // dialog에 edit할 값 세팅
    setEditAddr(addrData){
      this.addrId = addrData.addrId
      this.addrName = addrData.addrName;
      this.postcode = addrData.postcode;
      this.addr = addrData.addr;
      this.detail_addr = addrData.detailAddr
      this.phoneNum = addrData.phoneNum
      this.dialog = true
    },

    // dialog에 삭제할 id 세팅
    setDelete(id){
      this.deleteId = id;
      this.deleteDialog = true;
    },

    //삭제
    deleteAddr() {
      this.$axios.delete('addr/' + this.deleteId)
          .then(response => {
            console.log(response.data);
            this.getMyAddrBook();
            this.snackbarDelay("해당 주소록을 삭제했습니다.")
            this.deleteDialog = false;
          })
          .catch(error => {
            console.log(error.response);
          })
    },


    //등록 / 수정 / 삭제 성공시
    snackbarDelay(str){
      this.snackbarMsg = str;
      clearTimeout(this._timerId)
      // delay new call 500ms
      this._timerId = setTimeout(() => {
        // maybe : this.fetch_data()
        this.snackbar = true;
      }, 600)
    },

    // input 초기화
    resetData(){
      this.addrId = 0
      this.addrName = ''
      this.postcode =''
      this.addr = ''
      this.detail_addr = ''
      this.extra_addr = ''
      this.phoneNum = ''
    },

    //전화번호 하이픈 추가
    replaceNum(num){
      this.phoneNum= num.replace(/[^0-9]/g, '')
          .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
    },


    //카카오 우편번호 api
    getPostcode(){
      new window.daum.Postcode({
        oncomplete: (data) => {
          if (this.extra_addr !== "") {
            this.extra_addr = "";
          }
          if (data.userSelectedType === "R") {
            // 사용자가 도로명 주소를 선택했을 경우
            this.addr = data.roadAddress;
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            this.addr = data.jibunAddress;
          }

          // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
          if (data.userSelectedType === "R") {
            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              this.extra_addr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== "" && data.apartment === "Y") {
              this.extra_addr +=
                  this.extra_addr !== ""
                      ? `, ${data.buildingName}`
                      : data.buildingName;
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (this.extra_addr !== "") {
              this.extra_addr = `(${this.extra_addr})`;
            }
          } else {
            this.extra_addr = "";
          }
          // 우편번호를 입력한다.
          this.postcode = data.zonecode;
        },
      }).open();
    },
  },

  mounted() {
    this.getMyAddrBook();
  }
}
</script>

<style lang="scss">
.item-text{
  font-size: 16px;
  color: rgb(240,240,240);
}

.addr-name-td{
  width: 15%;
}
.addr-content-td{
  background-color: rgb(35,35,35);
}
.addr-text{
  font-size: 14px;
  color: rgb(240,240,240)
}
.addr-dialog-name-td{
  width: 35%;
  background-color: rgb(50,50,50);
}
.addr-dialog-content-td{
  background-color: rgb(55,55,55);
}
tbody {
  tr:hover {
    background-color: transparent !important;
  }
}
</style>