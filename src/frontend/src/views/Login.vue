<template>
  <v-container fluid class="pa-0">
    <SearchMenu></SearchMenu>
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
                <span style="color: rgb(45,45,45);">Sign-up</span>
              </v-btn>
              <v-btn
                  color="teal accent-6" rounded
                  @click="$router.push({path:'/'})"
              >
                <span style="color: rgb(45,45,45);">Home</span>
              </v-btn>
            </v-card-actions>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import SearchMenu from "@/views/SearchMenu";
export default {
  name: "Login.vue",
  components: {SearchMenu},
  data () {
    return {
      email: 'test@gmail.com',
      password: '',
      userInfoData:[],
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
                  alert("로그인 성공")
                  this.$store.dispatch('login',response.data);
                  this.$router.push({path:'/'})
                }
              })
              .catch(error =>{
                console.log(error.response);
                if(error.response.status===401){
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

</style>