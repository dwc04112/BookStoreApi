<template>
  <v-row class="align-center justify-end pa-0" style="background-color: rgb(40,40,40)">
    <v-col cols="12" class="pa-0">

      <v-simple-table dark>
        <tbody>
        <tr style="height: 80px">
          <td class="name-td"><span style="font-size: 16px">이름</span></td>
          <td class="content-td">
            <v-col>
              <v-text-field
                  outlined dense dark text
                  hide-details
                  class="text-fields white--text justify-start"
                  :placeholder="nameHolder"
                  disabled
              >
              </v-text-field>
            </v-col>
          </td>
        </tr>


        <tr style="height: 80px">
          <td class="name-td"><span style="font-size: 16px">이메일</span></td>
          <td class="content-td">
            <v-col>
              <v-text-field
                  outlined dense dark text
                  hide-details
                  class="text-fields white--text justify-start"
                  :placeholder="email"
                  v-model="email"
              >
              </v-text-field>
            </v-col>
          </td>
        </tr>

        <tr style="height: 80px">
          <td class="name-td"><span style="font-size: 16px">핸드폰</span></td>
          <td class="content-td">
            <v-col>
              <v-row no-gutters class="ma-0 pa-0">
                <v-text-field
                    outlined dense dark text
                    hide-details
                    @input="replaceNum(num)"
                    class="text-fields white--text justify-start"
                    :placeholder="num"
                    v-model="num"
                >
                </v-text-field>
              </v-row>
            </v-col>
          </td>
        </tr>
        </tbody>
      </v-simple-table>
    </v-col>

    <v-btn
        color="primary darken-1"
        class="white--text ma-4 mt-6 mb-6"
        rounded width="150px"
        @click="infoCheck"
    ><span style="font-weight: bold; font-size: 15px">저장하기</span></v-btn>


    <v-dialog
        max-width="400"
        v-model="dialog"
    >
      <v-card rounded color="rgb(55,55,55)" tile dark>
        <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">{{dialogMsg}}</div>
        <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
          <v-btn
              rounded
              text
              color="blue"
              @click="linkTo"
          >Home</v-btn>
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
        <v-icon color="red" size="30" class="pr-2">mdi-checkbox-marked-circle</v-icon>
        <span class="black--text">{{snackbarMsg}}</span>
      </div>
    </v-snackbar>


  </v-row>
</template>

<script>
// const { IMP } = window;
export default {
  name: "InfoEditComponent",
  data () {
    return {
    //  impCode : 'imp10391932 ',
      nameHolder : this.$store.state.member.userData.fullName,
      email : this.$store.state.member.loginData.email,
      num : this.$store.state.member.userData.phoneNum,

      dialog: false,
      snackbar : false,
      dialogMsg : '',
      snackbarMsg : '',
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

  methods:{
    editInfo(email, num){
      let data = {}
      data.email = email;
      data.phoneNum = num;
      this.$axios.post("edit/info",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response => {
        console.log(response.data)
        this.dialogMsg = "개인정보를 성공적으로 변경하였습니다. 로그아웃 처리 됩니다."
        this.$store.dispatch('logout').then(()=> this.dialog =true)
      }).catch(error => {
        console.log(error.response);
      })
    },
    linkTo(){
      this.$router.push({path:'/'})
    },


    infoCheck(){
      let numCheck= false;
      let emailCheck= false;

      var regEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
      var regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

      if(this.email!==''){
        emailCheck = regEmail.test(this.email);
      }else{
        emailCheck = false;
      }

      if(this.num!==''){
        numCheck = regPhone.test(this.num);
      }else{
        numCheck = false;
      }


      if(numCheck && emailCheck){
        if( (this.email === this.$store.state.member.loginData.email) &&(this.num === this.$store.state.member.userData.phoneNum) ){
          return null;
        }else{
          this.editInfo(this.email,this.num)
        }
      }else{
        this.snackbarMsg = "입력한 정보를 확인해주세요."
        this.snackbar = true;
      }
    },

    replaceNum(num){
      this.num= num.replace(/[^0-9]/g, '')
          .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
    },


    /*
    //휴대폰 인증 > 다날 유료결제시 사용가능
    certification() {
      //1. 객체 초기화 (가맹점 식별코드 삽입)
      IMP.init(this.impCode);
      // IMP.certification(param, callback) 호출
      IMP.certification(
        {
          popup : true
        }, rsp => { // callback
            console.log(rsp);
            if (rsp.success) {
              let data = {};
              data.imp_uid= rsp.imp_uid;
              console.log(data)
              this.$axios.post("payments/certification/",JSON.stringify(data),{
                headers: {
                  "Content-Type": `application/json`,
                },
              }).then(response=>{
                console.log(response.data)
              }).catch(error =>{
                console.log(error.response);
              })
            } else {
              let msg = '결제에 실패하였습니다.';
              msg += '에러내용 : ' + rsp.error_msg;
              console.log(msg)
            }
          });
    },

 */
  },

}
</script>

<style lang="scss">
.name-td{
  width: 35%;
}
.content-td{
  background-color: rgb(35,35,35);
}
tbody {
tr:hover {
  background-color: transparent !important;
}
}
</style>