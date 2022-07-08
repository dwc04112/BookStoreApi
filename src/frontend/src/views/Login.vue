<template>
  <v-container fluid class="pa-0">
    <v-row class="ma-0 align-center" style="background-color: rgb(20,20,20); height: 100vh">
      <v-col class="pa-0 justify-center d-flex">
        <v-card rounded flat width="400px" color="rgb(40,40,40)">
          <div style="height: 24px; background-color: rgb(30,30,30)" class="align-center justify-end d-flex">
            <v-btn width="14px" elevation="0" height="14px" color="yellow darken-2 mr-2" fab></v-btn>
            <v-btn width="14px" elevation="0" height="14px" color="red darken-2 mr-2" fab></v-btn>
          </div>
          <v-divider dark></v-divider>
          <div class="pa-3">
            <div class="pa-3 pb-5">
              <v-text-field
                  outlined
                  placeholder="email"
                  v-model="email"
                  required dark>
              </v-text-field>
              <v-text-field
                  outlined hide-details
                  placeholder="password"
                  type="password"
                  v-model="password"
                  required dark>
              </v-text-field>
            </div>
            <v-card-actions>
              <v-btn color="#2c4f91" dark large rounded block @click="loginSubmit" >
                <span style="font-size: 17px; font-weight: bold">Login</span>
              </v-btn >
            </v-card-actions>
            <v-card-actions>
              <v-btn
                  color="teal accent-6 white--text"
                  @click="signLink" rounded
              >
                <span style="color: rgb(45,45,45); font-weight: bold">Sign-up</span>
              </v-btn>
              <v-btn
                  color="teal accent-6" rounded
                  @click="$router.push({path:'/'})"
              >
                <span style="color: rgb(45,45,45); font-weight: bold">Home</span>
              </v-btn>
            </v-card-actions>
          </div>
        </v-card>
      </v-col>
    </v-row>

    <v-dialog
        max-width="400"
        v-model="dialog" persistent
        content-class="my-custom-dialog"
    >
      <v-card rounded color="rgb(55,55,55)" tile dark>
        <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">{{dialogMsg}}</div>
        <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
          <v-btn
              rounded class="white--text"
              color="teal accent-6"
              @click="$router.push({path:'/'})"
          >Home</v-btn>
        </v-card-actions>

      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
export default {
  name: "Login.vue",
  data () {
    return {
      email: 'dwc04112@gmail.com',
      password: '',

      dialog:false,
      dialogMsg:'',
    }
  },
  methods: {
    loginSubmit() {
      if(this.email&&this.password){
        let saveData = {};
        saveData.email = this.email;
        saveData.password = this.password;
        try {
          this.$axios.post("/authenticate", JSON.stringify(saveData), {
            headers: {
              "Content-Type": `application/json`,
            },
          })
              .then((response) => {
                console.log(response.data)
                if (response.status === 200) {
                  this.$store.dispatch('login',response.data)
                      .then(()=>{
                        this.dialogMsg="로그인 성공."
                        this.dialog=true
                      })
                }
              })
              .catch(error =>{
                console.log(error.response);
                if(error.response.status === 401){
                  alert("인증오류. 아이디와 비밀번호를 확인해주세요");
                  this.password = null; this.email = null;
                }
              })
        } catch (error) {
          console.error(error);
        }
      }else{
        alert("아이디 혹은 비밀번호가 입력되지 않았습니다")
      }
    },
    signLink(){
      this.$router.push({path:'/signup'})
    },
  },
}
</script>

<style scoped>
>>> .my-custom-dialog {
  align-self: flex-start;
}
</style>