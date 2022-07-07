<template>
  <v-container fluid class="pa-0">
    <v-row class="ma-0 align-center" style="background-color: rgb(20,20,20); height: 100vh">
      <v-col class="pa-0 justify-center d-flex">
        <v-card rounded flat width="650px" color="rgb(40,40,40)">
          <div style="height: 24px; background-color: rgb(30,30,30)" class="align-center justify-end d-flex">
            <v-btn width="14px" elevation="0" height="14px" color="yellow darken-2 mr-2" fab></v-btn>
            <v-btn width="14px" elevation="0" height="14px" color="red darken-2 mr-2" fab></v-btn>
          </div>
          <v-divider dark></v-divider>

          <v-stepper v-model="e1" tile style="background-color: rgb(50,50,50)" dark>
            <v-stepper-header>
              <v-stepper-step
                  color="teal accent-5"
                  :complete="e1 > 1"
                  step="1"
              >
                회원가입
              </v-stepper-step>

              <v-divider></v-divider>

              <v-stepper-step
                  color="teal accent-5"
                  :complete="e1 > 2"
                  step="2"
              >
               회원가입 완료
              </v-stepper-step>

              <v-divider></v-divider>

              <v-stepper-step color="teal accent-5" step="3">
                프로필 등록
              </v-stepper-step>
            </v-stepper-header>

            <v-stepper-items>
              <v-stepper-content step="1" class="pa-0">
                <v-card class="pa-6" elevation="0" color="rgb(50,50,50)">
                  <v-form
                      ref="form"
                      v-model="form"
                      class="pt-4"
                  >
                    <v-text-field
                        v-model="email"
                        :rules="[rules.email]"
                        filled outlined
                        label="이메일 주소" class="pa-2"
                        type="email"
                    ></v-text-field>

                    <v-row class="ma-0">
                      <v-col cols="6" class="pa-0">
                        <v-text-field
                            v-model="fullName"
                            :rules="[rules.name, rules.length]"
                            filled outlined class="pa-2"
                            label="이름"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="6"  class="pa-0">
                        <v-text-field
                            filled outlined
                            :rules="[rules.length]"
                            label="닉네임" class="pa-2"
                            v-model="nickName"
                        >
                          <template v-slot:prepend-inner>
                            <v-icon size="25" class="pr-2" :color=" nickNameCheck ? 'blue lighten-2' : 'red lighten-2'">mdi-check</v-icon>
                          </template>
                          <template v-slot:append>
                            <v-btn color="teal accent-6" style="font-weight: bold; bottom: 6px" class="ml-1" @click="nickCheck(nickName)">
                              중복확인
                            </v-btn>
                          </template>
                        </v-text-field>
                      </v-col>

                    </v-row>

                    <v-text-field
                        v-model="phoneNum"
                        :rules="[rules.phone]"
                        filled outlined class="pa-2"
                        @input="replaceNum(phoneNum)"
                        label="연락처"
                    >
                    </v-text-field>

                    <v-text-field
                        outlined filled
                        class="text-fields pa-2"
                        type="password"
                        hint=" "
                        persistent-hint
                        :rules="[rules.passLength,rules.space,rules.small,rules.number]"
                        label="비밀번호"
                        v-model="newPassword"
                        @keyup="passwordRules(newPassword) "
                        @focus="passwordRules(newPassword) "
                    >
                      <template v-slot:append>
                        <v-icon v-if="(smallPass&&minPass&&numPass&&spacePass)&&newPassword.length>0" class="blue--text text--lighten-2">mdi-check</v-icon>
                        <v-icon v-if="!(smallPass&&minPass&&numPass&&spacePass)&&newPassword.length>0" class="red--text text--lighten-2">mdi-alert</v-icon>
                      </template>
                      <template v-slot:message>
                        <span :class="numPass ? 'blue--text text--lighten-2' : 'red--text text--lighten-1'"  class="security-text"> 숫자와 </span>
                        <span :class="smallPass ? 'blue--text text--lighten-2' : 'red--text text--lighten-1'"  class="security-text"> &nbsp;소문자를 포함하여 </span>
                        <span :class="minPass ? 'blue--text text--lighten-2' : 'red--text text--lighten-1'" class="security-text"> &nbsp;10 ~ 16자리 이여야 합니다. </span>

                        <span v-if="!spacePass" class="red--text text--lighten-1"> &nbsp;&nbsp;( 공백이 포함되면 안됩니다 ! )</span>
                      </template>
                    </v-text-field>

                    <v-text-field
                        outlined filled
                        class="text-fields pa-2 pb-0"
                        type="password"
                        :rules="[rules.match]"
                        hint=" "
                        label="비밀번호 확인"
                        v-model="confirmNewPassword"
                    >
                      <template v-slot:append>
                        <v-icon v-if="(matchPass)&&confirmNewPassword.length>0" class="blue--text text--lighten-2">mdi-check</v-icon>
                        <v-icon v-if="!(matchPass)&&confirmNewPassword.length>0" class="red--text text--lighten-2">mdi-alert</v-icon>
                      </template>
                      <template v-slot:message>
                        <span v-if="!matchPass" class="red--text text--lighten-1" >비밀번호가 일치하지 않습니다.</span>
                      </template>
                    </v-text-field>
                    <v-checkbox
                        class="pl-3"
                        v-model="agreement"
                        :rules="[rules.required]"
                    >
                      <template v-slot:label>
                        <a
                            href="#"
                            @click.stop.prevent="agreementDialog = true"
                        >개인정보 수집 및 이용안내</a> &nbsp;&nbsp;(필수)
                      </template>
                    </v-checkbox>
                  </v-form>
                </v-card>

                <v-card-actions class="pa-4" style="background-color: rgb(40,40,40)">
                  <v-btn
                      text
                      @click="$router.push({path:'/login'})"
                  >
                    cancel
                  </v-btn>
                  <v-spacer></v-spacer>
                  <v-btn
                      :disabled="!form"
                      :loading="isLoading"
                      class="white--text" rounded
                      color="teal accent-5"
                      depressed @click="signCommit"
                  >
                    sign-up
                  </v-btn>
                </v-card-actions>
              </v-stepper-content>




              <v-stepper-content step="2" class="pa-0">
                <v-card
                    class="pa-12 pb-16 justify-center"
                    elevation="0" color="rgb(50,50,50)"
                >
                  <span style="font-size: 45px" class="teal--text text--accent-5 pa-0">축하합니다!</span>
                  <h3 class="pa-1"> 회원가입이 완료되었습니다!<font-awesome-icon icon="fa-solid fa-kiwi-bird" class="ma-0 ml-2 teal--text text--accent-5"/></h3>
                  <span class="pl-1">다음 페이지에서 <strong class="teal--text text--accent-5" >프로필 설정</strong> 을 진행해주세요</span>
                </v-card>
                <v-card-actions class="pa-4" style="background-color: rgb(40,40,40)">
                  <v-btn
                      color="teal accent-5" style="font-weight: bold"
                      @click="dialog = true" rounded
                  >
                    건너뛰기
                  </v-btn>
                  <v-spacer></v-spacer>
                  <v-btn
                      color="teal accent-5"
                      @click="e1 = 3" rounded
                  >
                    Continue
                  </v-btn>
                </v-card-actions>
              </v-stepper-content>



              <v-stepper-content step="3" class="pa-0">
                <v-card class="pa-6" elevation="0" color="rgb(50,50,50)">
                  <v-card class="pa-4 mb-10" color="rgb(60,60,60)" style="border: rgb(120,120,120) solid 1px">
                    <div class="grey--text text--lighten-1 pb-4">프로필 사진 등록</div>
                    <div class="justify-center d-flex pb-4">
                      <v-avatar size="150" style="border: rgb(140,140,140) solid 6px">
                        <img
                            v-show="imageUrl !== void 0"
                            :src="imageUrl"
                            style="object-fit: cover"
                            alt=""
                        >
                      </v-avatar>
                      <div class="file-div">
                        <input @change="upload" type="file" id="file" accept="image/png, image/jpeg">
                        <label class="input-label" for="file"><v-icon color="white">mdi-pencil</v-icon></label>
                      </div>
                    </div>
                  </v-card>
                </v-card>

                <v-card-actions class="pa-4" style="background-color: rgb(40,40,40)">
                  <v-btn
                      color="teal accent-5" style="font-weight: bold"
                      @click="dialog = true" rounded
                  >
                    건너뛰기
                  </v-btn>
                  <v-spacer></v-spacer>
                  <v-btn
                      color="teal accent-5" :disabled="imageUrl === undefined"
                      @click="editInfo" rounded
                  >
                    Continue
                  </v-btn>
                </v-card-actions>
              </v-stepper-content>
            </v-stepper-items>
          </v-stepper>
        </v-card>
      </v-col>
    </v-row>



    <!--메시지 창s-->
    <v-snackbar
        transition="dialog-bottom-transition"
        elevation="0"
        color="white"
        rounded
        v-model="snackbar"
    >
      <div class="align-center d-flex">
        <v-icon size="30" class="pr-2" :color=" nickNameCheck ? 'blue' : 'red'"
        >{{ nickNameCheck ? 'mdi-checkbox-marked-circle' : 'mdi-alert-circle' }}</v-icon>
        <span class="black--text">{{snackbarMsg}}</span>
      </div>
    </v-snackbar>

    <v-dialog
        v-model="agreementDialog"
        absolute
        max-width="400"
        persistent
    >
      <v-card color="rgb(55,55,55)" dark>
        <v-card-title class="text-h6">
          개인정보 수집 및 이용안내
        </v-card-title>
        <v-card-text>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions style="background-color: rgb(50,50,50)">
          <v-btn
              text rounded
              @click="agreement = false , agreementDialog = false"
          >
            No
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn
              class="white--text" rounded
              color="teal accent-6"
              @click="agreement = true , agreementDialog = false"
          >
            Yes
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog
        max-width="400"
        v-model="dialog" persistent
        content-class="my-custom-dialog"
    >
      <v-card rounded color="rgb(55,55,55)" tile dark>
        <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">로그인 화면으로 이동하시겠습니까?</div>
        <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
          <v-btn
              rounded class="white--text"
              color="teal accent-6"
              @click="dialog=false"
          >취소</v-btn>
          <v-spacer></v-spacer>
          <v-btn
              rounded class="white--text"
              @click="linkToLogin"
          >확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog
        max-width="400"
        v-model="loginDialog" persistent
        content-class="my-custom-dialog"
    >
      <v-card rounded color="rgb(55,55,55)" tile dark>
        <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">{{loginDialogMsg}}</div>
        <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
          <v-btn
              rounded class="white--text"
              color="teal accent-6"
              @click="$router.push({path:'/login'}).then(()=>$router.go(0))"
          >Login</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "SignUp.vue",
  data () {
    return {
      //dialogs
      dialog:false,

      loginDialog : false,
      loginDialogMsg:'',

      agreementDialog : false,

      e1:1,

      // step 1
      newPassword: '',
      confirmNewPassword:'',
      //비밀번호 매치
      matchPass: false,
      //정규식 검사 비밀번호
      minPass: false,
      numPass: false,
      smallPass: false,
      spacePass: false,

      //중복확인
      nickNameCheck : false,
      snackbar : false,
      snackbarMsg : '',

      fullName : undefined,
      nickName : undefined,
      agreement: false,
      email: undefined,
      form: false,
      isLoading: false,
      phoneNum: undefined,

      // step 2
      imageUrl: undefined,
      imgFile:[],

      rules: {
        email: v => /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/.test(v) || '정확한 이메일 형식을 입력해주세요',
        name : v => /^[가-힣a-zA-Z]+$/.test(v) || '이름을 확인해주세요',
        length : v=> (v||'').length >1 || '길이는 최소 2글자 이상입니다',
        phone : v => /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/.test(v) || '올바른 전화번호 형식을 입력해주세요',
        required: v => !!v || '개인정보 수집 및 이용안내 동의해 주세요',

        passLength : v => (v||'').length >9 &&  (v||'').length < 17 || '비밀번호는 10 ~ 16자리 이여야 합니다',
        number: v =>  /[0-9]/.test(v)  || '숫자가 포함되지 않았습니다',
        small: v =>  /[a-z]/.test(v)  || '한글자 이상 소문자를 입력해주세요',
        space: v =>  !/\s/g.test(v)  || '비밀번호에 공백이 있습니다',
        match : v => this.newPassword === v || '비밀번호가 일치하지 않습니다'
      },
    }
  },
  watch: {
    newPassword(val) {
      this.matchPass = val === this.confirmNewPassword;
    },
    confirmNewPassword(val) {
      this.matchPass = val === this.newPassword;
    },
    // 확인 msg
    snackbar(val) {
      val && setTimeout(() => {
        this.snackbar = false
      }, 4000)
    },
    nickName(){
      this.nickNameCheck = false;
    }
  },
  methods: {
    // step 1
    // 중복체크
    nickCheck(nick){
      this.$axios.get(' /signup/doublecheck/'+nick)
          .then(response=>{
            if(response.data > 0){
              this.nickNameCheck = false
              this.snackbarDelay("중복된 닉네임이 존재합니다")
            }else{
              this.nickNameCheck = true
              this.snackbarDelay("사용 가능한 닉네임입니다")
            }
          })
          .catch(error =>{
            console.log(error.response);
          })
    },
    snackbarDelay(str){
      this.snackbarMsg = str;
      clearTimeout(this._timerId)
      // delay new call 500ms
      this._timerId = setTimeout(() => {
        // maybe : this.fetch_data()
        this.snackbar = true;
      }, 600)
    },

    passwordRules(data){
      //길이 10자이상
      this.minPass = data.length > 9 && data.length < 17

      if(this.data!=='') {
        this.numPass = /[0-9]/.test(data);   //숫자 포함
        this.smallPass = /[a-z]/.test(data); //소문자 포함
        this.spacePass = !/\s/g.test(data);    //공백 체크
      }
    },

    replaceNum(num){
      this.phoneNum= num.replace(/[^0-9]/g, '')
          .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
    },

    signCommit(){
      if(!this.nickNameCheck){
        this.snackbarDelay("중복확인을 먼저 진행해주세요");
        return null;
      }
      let saveData = {};
      saveData.email = this.email;
      saveData.password = this.newPassword;
      saveData.nickName = this.nickName;
      saveData.fullName = this.fullName
      saveData.phoneNum = this.phoneNum

      this.$axios.post("/api/member", JSON.stringify(saveData), {
        headers: {
          "Content-Type": `application/json`,
        },
      }).then((response) => {
            if (response.status === 200) {
              console.log(response.data);
              this.e1 = 2
            }
      })
      .catch(error =>{
        console.log(error.response);
        if(error.response.status===401){
          this.loginDialogMsg = "회원가입 오류. 다시한번 회원가입을 진행해주세요"
          this.loginDialog = true;
          this.newPassword = '';
          this.confirmNewPassword = '';
          this.email = undefined;
          this.fullName = undefined;
          this.phoneNum = undefined;
          this.agreement = false;
        }
      });
    },

    // step 3
    editInfo(){
      let data = new FormData();
      data.append("email", JSON.stringify(this.email));
      data.append("file", this.imgFile);
      axios({
        method: 'post',
        url: '/api/profile',
        data: data,
        headers: {
          "Content-Type": "multipart/form-data", // Content-Type 주의
        },
      }).then(response => {
        console.log(response.data)
        this.loginDialogMsg="성공적으로 저장했습니다. 로그인 화면으로 돌아갑니다"
        this.loginDialog=true
      }).catch(error => {
        console.log(error.response);
        this.loginDialogMsg="정보 저장 중 오류가 있었습니다."
        this.loginDialog=true
      })
    },

    upload(e) {
      let imageFile = e.target.files; // 업로드한 파일의 데이터가 여기있음.
      this.imgFile = imageFile[0]
      this.imageUrl = URL.createObjectURL(imageFile[0]); // 파일의 필요한 데이터만을 url 변수에 넣음
      // 미리 작성해둔 imageUrl : ' ' 변수에 가지고있는 경로데이터를 넣는다
    },


    linkToLogin(){
      this.$router.push({path:'/login'})
    },
  },
}
</script>

<style scoped>
>>> .my-custom-dialog {
  align-self: flex-start;
}

.file-div{
  position: absolute;
  margin-top: 110px;
  margin-left: 95px;
}
.file-div .input-img {
  display: inline-block;
  height: 40px;
  padding: 0 10px;
  vertical-align: middle;
  border: 1px solid rgb(140,140,140);
  width: 80%;
}
.input-label {
  display: inline-block;
  padding: 10px 10px;
  color: #fff;
  vertical-align: middle;
  background-color: rgb(90,90,90);
  cursor: pointer;
  height: 43px;
  width: 43px;
  border-radius: 70px;
  margin-left: 10px;
}
.file-div input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}
</style>