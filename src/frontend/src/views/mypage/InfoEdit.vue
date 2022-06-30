<template>
  <v-container fluid class="align-center">


    <v-row class=" pa-0">
      <v-col cols=12 md="3">
        <v-card class="align-center flex-column d-flex" color="transparent" tile elevation="0">
          <v-sheet class="align-center justify-center flex-column d-flex" color="transparent">
            <v-avatar
                color="grey"
                size="110">
              <img :src="this.$store.state.member.userData.profilePicture"
                   style="object-fit: cover"
                   alt=""/>
            </v-avatar>
            <strong class="pt-5" style="color: rgb(190,190,190); font-size: 20px">{{ $store.state.member.userData.nickName }}</strong>
          </v-sheet>
          <v-divider class="ma-2" style="width: 60%" dark></v-divider>

          <v-col cols="7" class="pa-0 pt-1 pb-2">
            <v-chip-group
                mandatory
                column
                v-model="selectChip"
                active-class="red darken-2 white--text"
                dark
            >
              <v-chip
                  v-for="(data,index) in topChip"
                  :key="index"
                  :value="data.name"
                  filter class="mb-2"
                  @click="setInfoComponents(data.link)"
              >
                {{data.name}}
              </v-chip>
            </v-chip-group>
          </v-col>

          <v-divider class="ma-2 mt-0" style="width: 60%" dark></v-divider>
        </v-card>
      </v-col>

      <v-col cols="12" md="7" class="pb-8 ml-md-4">
        <div class="mb-8">
          <span class="main-title">{{selectChip}}</span>
        </div>

        <component
            :key="componentKey"
            :is="component"
            @childLink ="setInfoComponents"
        >
        </component>
      </v-col>
    </v-row>
    <v-row style="height: 80px"></v-row>
  </v-container>
</template>

<script>
import InfoEditComponent from "@/views/mypage/editComponents/InfoEditComponent";
export default {
  name: "InfoEdit",
  components: {InfoEditComponent},
  data () {
    return {
      selectChip:'',
      topChip : [
        {name:'프로필 수정',link:'EditProfile'},
        {name:'연락처 수정',link:'InfoEditComponent'},
        {name:'배송지 수정',link:'AddressList'},
        {name:'비밀번호 변경',link:'PasswordEditComponent'},
      ],



      //컴포넌트
      setComponent: 'EditProfile', //불러올 컴포넌트
      componentKey: 0 // reload component
    }
  },
  methods:{
    setInfoComponents(data){
      this.setComponent = data
    },

  },


  computed:{
    // 컴포넌트에서 페이지 변경
    component() {
      let setComponent = this.setComponent
      return () => import(`@/views/mypage/editComponents/${setComponent}`);
    }
  },
}
</script>

<style scoped>
.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(180,180,180);
}
</style>