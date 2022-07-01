<template>
  <v-row class="pa-0">
    <v-col>
      <v-card dark class="pa-4">
        <v-card-title>
          <h3>회원 관리</h3>
        <v-spacer></v-spacer>
        <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
        ></v-text-field>
          </v-card-title>
        <v-data-table
            dark
            height="650px"
            :search="search"
            :headers="headers"
            :items="memberData"
            item-key="name"
            class="elevation-0"
        >

          <template v-slot:item.userRule="{ item }">
            <v-chip
                :color="getColor(item.userRule)"
                dark
            >
              {{ item.userRule }}
            </v-chip>
          </template>
        </v-data-table>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "AdminMember",
  data: () => ({
    memberData: [],
    search:'',
    headers: [
      {
        text: '회원번호',
        align: 'start',
        sortable: false,
        value: 'mid',
        width:'100'
      },
      { text: '권한', value: 'userRule', width:'120',},
      { text: '회원이름', value: 'fullName', width:'120' },
      { text: '닉네임', value: 'nickName', width:'120' },
      { text: '이메일', value: 'email', width:'200' },
      { text: '연락처', value: 'phoneNum', width:'150' },
      { text: '생성날짜', value: 'createDate', width:'150' , align: 'center',},
    ],
  }),
  methods: {
    getOrdersByAdmin() {
      this.$axios.get("admin/member")
          .then(response => {
            this.memberData = response.data
          }).catch(error => {
        console.log(error.response);
      })
    },
    getColor (userRule) {
      if (userRule === 'admin') return 'orange darken-4'
      else if (userRule === 'user') return 'green darken-4'
      else return 'blue darken-4'
    },
  },
  mounted() {
    this.getOrdersByAdmin();
  }
}
</script>

<style scoped>

</style>