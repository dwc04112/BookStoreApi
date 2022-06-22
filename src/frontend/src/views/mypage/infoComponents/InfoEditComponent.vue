<template>
    <v-row style="background-color: rgb(40,40,40); min-height: 500px">

      <v-col cols="12" md="4" class="red lighten-2 pt-8">
        <v-row class="justify-center">
          <v-col cols="11">
            <span class="white--text" style="font-size: 20px; font-weight: bold">회원정보 수정</span>
          </v-col>
          <v-col cols="12" class="d-flex justify-center pt-md-8 pb-12">
            <v-avatar color="white" size="195" >
              <img
                  :src="imageUrl"
                  style="object-fit: cover"
              >
            </v-avatar>
            <div class="file-div">
              <input @change="upload" type="file" id="file" accept="image/png, image/jpeg">
              <label class="input-label" for="file">Edit</label>
            </div>
          </v-col>
        </v-row>
      </v-col>

      <v-col cols="12" md="8" class="pt-8">
        <v-row class="justify-center">



          <v-col cols="10" class="pa-0">
            <span class="item-text">이름</span>
            <v-text-field
                outlined dense dark
                class="pt-1 text-fields"
                v-model="nameField"
            >
            </v-text-field>
          </v-col>

          <v-col cols="10" class="pa-0">
            <span class="item-text">닉네임</span>
            <v-text-field
                outlined dense dark
                class="pt-1 text-fields"
                v-model="nickField"
            >
            </v-text-field>
          </v-col>


          <v-col cols="10" class="pa-0">
            <span class="item-text">이메일</span>
            <v-text-field
                outlined dense dark
                class="pt-1 text-fields white--text"
                v-model="emailField"
            >
            </v-text-field>
          </v-col>

          <v-col cols="10" class="pa-0">
            <span class="white--text item-text">주소</span>
            <v-text-field
                outlined dense dark
                class="pt-1 text-fields"
                v-model="nickField"
            >
            </v-text-field>
          </v-col>

          <v-col cols="11" class="justify-end d-flex pb-6">
            <v-btn
                rounded
                width="200px"
                class="red lighten-2 update-btn"
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
  name: "InfoEditComponent",
  data () {
    return {
      imageUrl:'',

      nameField : this.$store.state.memberStore.fullName,
      nickField : this.$store.state.memberStore.nickName,
      emailField : this.$store.state.memberStore.email,
    }
  },
  methods:{
    upload(e) {
      let imageFile = e.target.files; // 업로드한 파일의 데이터가 여기있음.
      let url = URL.createObjectURL(imageFile[0]); // 파일의 필요한 데이터만을 url 변수에 넣음
      console.log("url : "+url); // 확인
      this.imageUrl = url; // 미리 작성해둔 imageUrl : ' ' 변수에 가지고있는 경로데이터를 넣는다
    },

  },

}
</script>

<style scoped>
.profile-btn{
  position: absolute;
  margin-top: 155px;
  margin-left: 110px;
  background-color: white;
  border-width: 2px;
  border-color: rgb(60,60,60);
}
.item-text{
  font-size: 16px;
  color: rgb(240,240,240);
}
.file-div{
  position: absolute;
  margin-top: 155px;
  margin-left: 110px;
}
.file-div .input-img {
  display: inline-block;
  height: 40px;
  padding: 0 10px;
  vertical-align: middle;
  border: 1px solid rgb(140,140,140);
  color: #999999;
  width: 100%;
}
.input-label {
  display: inline-block;
  padding: 10px 20px;
  color: #fff;
  vertical-align: middle;
  background-color: rgb(50,50,50);
  cursor: pointer;
  height: 43px;
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