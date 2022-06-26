<template>
  <v-row class="align-center justify-end pa-0" style="background-color: rgb(30,30,30)">
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
                  :placeholder="emailHolder"
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
                <v-select
                    v-model="phoneNum1"
                    :items="phoneItems"
                    dense
                    dark
                    style="max-width: 85px"
                    outlined
                    hide-details
                ></v-select>
                <v-text-field
                    outlined dense dark
                    hide-details
                    class="user-text ml-2"
                    style="max-width: 85px"
                    v-model="phoneNum2"
                ></v-text-field>
                <v-text-field
                    outlined dense dark
                    hide-details
                    class="user-text ml-2"
                    style="max-width: 85px"
                    v-model="phoneNum3"
                ></v-text-field>
              </v-row>
            </v-col>
          </td>
        </tr>
        </tbody>
      </v-simple-table>
    </v-col>

    <v-btn
        color="blue"
        class="white--text ma-4 mt-8"
        rounded
        :disabled="(phoneNum2.length<1 || phoneNum3.length<1) &&email.length<1"
        @click="orderDataCheck"
    ><span style="font-weight: bold; font-size: 15px">저장하기</span></v-btn>
  </v-row>
</template>

<script>
// const { IMP } = window;
export default {
  name: "InfoEditComponent",
  data () {
    return {
    //  impCode : 'imp10391932 ',
      nameHolder : this.$store.state.memberStore.userData.fullName,
      emailHolder : this.$store.state.memberStore.email,
      phoneNumHolder : this.$store.state.memberStore.userData.phoneNum,

      phoneItems : ['010','011','016','017','018','019'],
      phoneNum1 : '010',
      phoneNum2 : '',
      phoneNum3 : '',

      num : '',
      email : '',
    }
  },


  methods:{
    editInfo(){
      let data = {}
      data.phoneNum = this.num;
      data.email = this.email;
      this.$axios.post("user/info",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response => {
        console.log(response.data)
      }).catch(error => {
        console.log(error.response);
      })
    },
    orderDataCheck(){
      if(this.phoneNum2!==''|| this.phoneNum3!=='') {
        if (this.phoneNum2.length < 3 || this.phoneNum3.length < 4) {
          alert("연락처를 확인해주세요")
          return false;
        }else{
          this.num = this.phoneNum1+"-"+this.phoneNum2+"-"+this.phoneNum3
        }
      }else{
        this.num = '';
      }

      let emailRule=/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; //이메일 정규식
      if(this.email!==''){
        if (!emailRule.test(this.email)) {
          alert("이메일을 확인해주세요")
          return false;
        }
      }else{
         this.email='';
      }

      this.editInfo();
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
        },  rsp => { // callback
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