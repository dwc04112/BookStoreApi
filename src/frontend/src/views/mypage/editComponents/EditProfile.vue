<template>
  <v-row class="align-center justify-end pa-0" style="background-color: rgb(40,40,40)">
    <v-col cols="12" class="pa-0">

      <v-simple-table dark>
        <tbody>
        <tr style="height: 80px">
          <td class="name-td"><span style="font-size: 16px">닉네임</span></td>
          <td class="content-td">
            <v-col>
              <v-text-field
                  outlined dense dark text
                  hide-details
                  class="text-fields white--text justify-start"
                  :placeholder="nickHolder"
                  v-model="nickName"
              >
              </v-text-field>
            </v-col>
          </td>
        </tr>


        <tr style="height: 160px">
          <td class="name-td"><span style="font-size: 16px">프로필 사진</span></td>
          <td class="content-td">
            <v-col>
              <v-avatar size="100">
                <img
                    :src="imageUrl"
                    style="object-fit: cover"
                    alt=""
                >
              </v-avatar>
              <div class="file-div">
                <input @change="upload" type="file" id="file" accept="image/png, image/jpeg">
                <label class="input-label" for="file"><v-icon color="white">mdi-pencil</v-icon></label>
              </div>
            </v-col>
          </td>
        </tr>
        </tbody>
      </v-simple-table>
    </v-col>

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
              @click="$router.go(0)"
          >Commit</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-btn
        color="primary darken-1"
        class="white--text  ma-4 mt-6 mb-6"
        rounded width="150px"
        @click="editInfo"
    ><span style="font-weight: bold; font-size: 15px">저장하기</span></v-btn>
  </v-row>
</template>

<script>
import axios from "axios";

export default {
  name: "EditProfile",
  data () {
    return {
      imageUrl: this.$store.state.member.userData.profilePicture,
      imgFile:[],
      nickHolder : this.$store.state.member.userData.nickName,
      nickName : "",
      dialog:false,
      dialogMsg:'',
    }
  },

  methods:{
    upload(e) {
      let imageFile = e.target.files; // 업로드한 파일의 데이터가 여기있음.
      this.imgFile = imageFile[0]
      this.imageUrl = URL.createObjectURL(imageFile[0]); // 파일의 필요한 데이터만을 url 변수에 넣음
      // 미리 작성해둔 imageUrl : ' ' 변수에 가지고있는 경로데이터를 넣는다
    },

    editInfo(){
      let data = new FormData();

      data.append("file", this.imgFile);
      data.append("nick", JSON.stringify(this.nickName));
      axios({
        method: 'post',
        url: '/user/profile',
        data: data,
        headers: {
          "Content-Type": "multipart/form-data", // Content-Type 주의
        },
      }).then(response => {
        console.log(response.data)
        this.$store.dispatch('updateProfile',response.data)
            .then(() => {
              this.dialogMsg="성공적으로 저장했습니다."
              this.dialog=true
            });
      }).catch(error => {
        console.log(error.response);
        this.dialogMsg="정보 저장 중 오류가 있었습니다."
        this.dialog=true
      })
    },
  },
}
</script>

<style lang="scss">
.item-text{
  font-size: 16px;
  color: rgb(240,240,240);
}
.file-div{
  position: absolute;
  margin-top: -35px;
  margin-left: 55px;
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