<template>
  <v-row class="pa-0">
    <v-col>
      <v-card dark class="pa-4">
        <v-card-title>
          <h3>댓글 관리</h3>
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
            v-model="selected"
            height="650px"
            :search="search"
            :headers="headers"
            :items="commentData"
            item-key="cid"
            show-select
            @input="enterSelect()"
            class="elevation-0"
        >
          <template v-slot:footer.prepend>
            <v-btn :disabled="cidArr.length<1" color="red darken-2" @click="dialog=true">선택 삭제</v-btn>
          </template>
        </v-data-table>
      </v-card>

      <!--삭제 확인 메시지-->
      <v-dialog
          max-width="400"
          v-model="dialog"
      >
        <v-card rounded color="rgb(55,55,55)" tile dark>
          <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">정말 선택한 댓글을 삭제할까요?</div>
          <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
            <v-btn
                rounded
                text
                @click="dialog=false"
            >Close</v-btn>
            <v-btn
                class="ml-2"
                rounded
                elevation="0"
                color="red"
                @click="deleteList()"
            >Commit</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "AdminComment",
  data: () => ({
    commentData: [],
    selected: [],
    cidArr: [],
    search:'',

    dialog:false,
    headers: [
      {
        text: '댓글번호',
        align: 'start',
        value: 'cid',
        width:'100'
      },
      { text: '작성자고유번호', value: 'mid', width:'150' },
      { text: '책번호', value: 'bid', width:'120' },
      { text: '댓글 내용', value: 'content', width:'400' },
      { text: '추천수', value: 'popularity', width:'100' },
      { text: '작성날짜', value: 'commentDate', width:'150' },
      { text: '작성시간', value: 'commentTime', width:'150' },
    ],
  }),
  watch: {
    snackbar(val) {
      val && setTimeout(() => {
        this.snackbar = false
      }, 4000)
    },
  },
  methods: {
    getOrdersByAdmin() {
      this.$axios.get("admin/comment")
          .then(response => {
            this.commentData = response.data
          }).catch(error => {
        console.log(error.response);
      })
    },
    deleteList(){
      this.$axios.delete("admin/comment/"+this.cidArr)
          .then(() => {
             this.dialog=false;
             this.$router.go(0)
          }).catch(error => {
        console.log(error.response);
      })
    },
    enterSelect() {
       this.cidArr = this.selected.map(function(value){
        return value.cid
      })
    }
  },
  mounted() {
    this.getOrdersByAdmin();
  }
}
</script>

<style scoped>

</style>