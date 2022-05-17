<template>
    <v-row style="background-color: rgb(40,40,40); min-height: 500px">

      <v-col cols="12" md="5" class="red lighten-2 pt-8">

        <v-row class="justify-center">
          <v-col cols="11">
            <span class="white--text" style="font-size: 22px; font-weight: bold">Profile picture</span>
          </v-col>

          <v-col cols="12" class="d-flex justify-center pt-md-8 pb-12">
            <v-avatar color="white" size="195" >
            </v-avatar>
            <v-btn
                rounded
                elevation="0"
                outlined
                width="75px"
                height="40px"
                class="profile-btn"
            > Edit</v-btn>
          </v-col>
        </v-row>
      </v-col>

      <v-col cols="12" md="7" class="pt-8">
        <v-row class="justify-center">
          <v-col cols="11">
            <span class="item-text">User name</span>
            <v-text-field
                outlined dense dark
                class="pt-1 text-fields"
                v-model="nameField"
            >
            </v-text-field>
          </v-col>

          <v-col cols="11">
            <span class="item-text">Nick name</span>
            <v-text-field
                outlined dense dark
                class="pt-1 text-fields"
                v-model="nickField"
            >
            </v-text-field>
          </v-col>


          <v-col cols="11">
            <span class="item-text">Public email</span>
            <v-text-field
                outlined dense dark
                class="pt-1 text-fields white--text"
                v-model="emailField"
            >
            </v-text-field>
          </v-col>

          <v-col cols="11">
            <span class="white--text item-text">address</span>
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
      nameField : this.$store.state.memberStore.fullName,
      nickField : this.$store.state.memberStore.nickName,
      emailField : this.$store.state.memberStore.email,
    }
  },
  methods:{

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


</style>