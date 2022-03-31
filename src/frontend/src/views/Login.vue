<template>

  <v-main>
    <v-container
        style="position: relative; top: 10%; margin-left: 10%"
        class="text-xs-center"
    >
      <v-layout row wrap class="text-xs-center">
        <v-flex>
          <v-card flat class="mx-auto" max-width="800">
            <v-row style="margin-top: 60px">
              <v-col>
                <v-form style="width: 400px; height: 300px">
                  <div class="mx-3">
                    userId
                    <div class="mx-1">
                      <v-text-field
                          placeholder="email"
                          v-model="email"
                          required >
                      </v-text-field>
                    </div>
                  </div>
                  <div class="mx-3">
                    userPassword
                    <div class="mx-1">
                      <v-text-field
                          placeholder="password"
                          type="password"
                          v-model="password"
                          required >
                      </v-text-field>
                    </div>
                  </div>
                  <v-card-actions>
                    <v-btn color="#2c4f91" dark large block @click="loginSubmit" >
                      Login
                    </v-btn >
                  </v-card-actions>
                  <v-card-actions>
                    <v-btn
                        text
                        color="teal accent-6"
                        @click="signLink"
                    >
                      Sign-Up
                    </v-btn>
                    <v-btn
                        text
                        color="teal accent-6"
                        @click="reveal = true"
                    >
                      Edit
                    </v-btn>
                  </v-card-actions>
                </v-form>
              </v-col>
            </v-row>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "Login.vue",
  data () {
    return {
      email: 'test@gmail.com',
      password: '1234',
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
                if (response.status === 200) {
                  alert("로그인 성공")
                  this.$store.dispatch('login',response.data);
                  this.$router.push('/');
                }
              })
              .catch(error =>{
                console.log(error.response);
              })
              /*
              .catch(error =>{
                console.log(error.response);
                if(error.response.status===401){
                  alert("인증오류. 아이디와 비밀번호를 확인해주세요");
                  this.password = null; this.email = null;
                }
              });

               */
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