<template>
  <v-row style="background-color: rgb(40,40,40); min-height: 500px">

    <v-col cols="12" md="4" class="red lighten-2 pt-8">
      <v-row class="justify-center">
        <v-col cols="11">
          <span class="white--text" style="font-size: 20px; font-weight: bold">비밀번호 변경</span>
        </v-col>
        <v-col cols="12" class="align-center pt-md-12 pb-8 d-flex justify-center">
          <v-icon size="185" color="white">
            mdi-lock
          </v-icon>
        </v-col>
      </v-row>
    </v-col>

    <v-col cols="12" md="8" class="pt-8">
      <v-row class="justify-center align-center">
        <v-col cols="11">
          <span class="item-text">현재 비밀번호</span>
          <v-text-field
              outlined dense dark
              class="pt-1 text-fields"
              type="password"
              v-model="oldPassword"
          >
          </v-text-field>
        </v-col>


        <v-col cols="11">
          <span class="item-text">새로운 비밀번호</span>
          <v-text-field
              outlined dense dark
              class="pt-1 text-fields"
              type="password"
              hint=" "
              v-model="newPassword"
              @keyup="passwordRules(newPassword) "
              @focus="passwordRules(newPassword) "
          >
            <template v-slot:append>
              <v-icon v-if="(smallPass&&minPass&&numPass&&spacePass)&&newPassword.length>0" class="blue--text text--lighten-2">mdi-check</v-icon>
              <v-icon v-if="!(smallPass&&minPass&&numPass&&spacePass)&&newPassword.length>0" class="red--text text--lighten-2">mdi-alert</v-icon>
            </template>
            <template v-slot:message>
              <span v-if="!spacePass" class="red--text text--lighten-2">공백이 포함되면 안됩니다.</span>
            </template>

          </v-text-field>
        </v-col>



        <v-col cols="11">
          <span class="item-text">비밀번호 확인</span>
          <v-text-field
              outlined dense dark
              class="pt-1 text-fields white--text"
              type="password"
              hint=" "
              v-model="confirmNewPassword"
          >
            <template v-slot:append>
              <v-icon v-if="(matchPass)&&confirmNewPassword.length>0" class="blue--text text--lighten-2">mdi-check</v-icon>
              <v-icon v-if="!(matchPass)&&confirmNewPassword.length>0" class="red--text text--lighten-2">mdi-alert</v-icon>
            </template>
            <template v-slot:message>
              <span v-if="!matchPass" class="red--text text--lighten-2" >비밀번호가 일치하지 않습니다.</span>
            </template>
          </v-text-field>
        </v-col>


        <v-col cols="11" class="justify-center">
          <span :class="numPass ? 'blue--text text--lighten-2' : 'red--text text--lighten-2'"  class="security-text"> 숫자와 </span>
          <span :class="smallPass ? 'blue--text text--lighten-2' : 'red--text text--lighten-2'"  class="security-text"> &nbsp;소문자를 포함하여 </span>
          <span :class="minPass ? 'blue--text text--lighten-2' : 'red--text text--lighten-2'" class="security-text"> &nbsp;10 ~ 16자리 이여야 합니다. </span>
          <span class="security-text" style="color: rgb(240,240,240);"> &nbsp; 자세히 알아 보기</span>
        </v-col>


        <v-col cols="11" class="justify-end d-flex pb-6">
          <v-btn
              rounded
              width="200px"
              class="red lighten-2 update-btn"
              @click="checkRules"
          >
            <span style="color: rgb(40,40,40)">Update</span>
          </v-btn>
        </v-col>

      </v-row>
    </v-col>

  </v-row>
</template>

<script>
export default {
  name: "passwordEditComponent",
  data () {
    return {
      //비밀번호 검사
      oldPassword:'',
      newPassword:'',
      confirmNewPassword:'',

      //정규식 검사
      minPass: false,
      numPass: false,
      smallPass: false,
      spacePass: false,

      //비밀번호 매치
      matchPass: false,
    }
  },
  watch: {
    newPassword(val) {
      this.matchPass = val === this.confirmNewPassword;
    },
    confirmNewPassword(val) {
      this.matchPass = val === this.newPassword;
    },
  },
  methods:{
    passwordRules(data){
      //길이 10자이상
      this.minPass = data.length > 9 && data.length < 17

      if(this.data!=='') {
        this.numPass = /[0-9]/.test(data);   //숫자 포함
        this.smallPass = /[a-z]/.test(data); //소문자 포함
        this.spacePass = !/\s/g.test(data);    //공백 체크
      }
    },


    //버튼 누르면 비밀번호 체크
    checkRules(){
      if(!(this.numPass&&this.smallPass&&this.spacePass&&this.minPass)){
        alert("사용 불가능한 비밀번호입니다.")
        return null;
      }
      if(!this.matchPass){
        alert("비밀번호가 일치하지 않습니다")
        return null;
      }
      this.updatePassword();
    },

    //업데이트
    updatePassword(){
      let data = {};
      data.oldPassword = this.oldPassword;
      data.newPassword = this.newPassword;

      console.log(data.newPassword + data.oldPassword)
      this.$axios.post("edit/password",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response=>{
        console.log(response.data)
      }).catch(error =>{
        console.log(error.response);
      })

    }

  },
}
</script>

<style scoped>
.item-text{
  font-size: 16px;
  color: rgb(240,240,240);
}
.security-text{
  font-size: 14px;
}



</style>