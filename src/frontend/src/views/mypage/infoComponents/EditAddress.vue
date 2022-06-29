<template>
  <v-row class="align-center justify-end pa-0" style="background-color: rgb(40,40,40)">
    <v-col cols="12" class="pa-0">
      <div>{{selectAddr}}</div>
      <v-chip-group
          mandatory
          active-class="primary darken-4"
          v-model="selectAddr"
          column
          dark
      >
        <v-chip
            v-for="(data,index) in addrList"
            :key="index"
            :value="index"
            :close="titleDelete"
            @click="titleDelete = false"
            @click:close="deleteAddr(data.addrId)"
            filter
        >
          {{data.addrName}}
        </v-chip>
      </v-chip-group>

      <v-chip
          active-class="primary darken-4 white--text"
          filter
      >
        추가
      </v-chip>

      <v-chip @click.stop="titleDelete=!titleDelete">
        삭제
      </v-chip>

      <v-simple-table dark v-if="addrList.length>0">
        <tbody>
        <tr style="height: 80px">
          <td class="name-td"><span style="font-size: 16px">배송지 명</span></td>
          <td class="content-td">
            <v-col>
              <v-text-field
                  outlined dense dark text
                  hide-details
                  class="text-fields white--text justify-start"
                  :placeholder="addrList[selectAddr].addrName"
                  v-model="addrName"
              >
              </v-text-field>
            </v-col>
          </td>
        </tr>

        <tr style="height: 80px">
          <td class="name-td"><span style="font-size: 16px">우편번호</span></td>
          <td class="content-td">
            <v-row class="ma-0">
              <v-col cols="5">
                <v-text-field
                    outlined dense dark text
                    hide-details
                    class="text-fields white--text justify-start"
                    :placeholder="addrList[selectAddr].postcode"
                    v-model="postcode"
                    @click="getPostcode()"
                >
                </v-text-field>
              </v-col>
              <v-col cols="2" class="align-center d-flex">
                <v-btn color="red lighten-2" fab x-small class="ml-2" @click="getPostcode()">
                  <v-icon>mdi-magnify</v-icon>
                </v-btn>
              </v-col>
            </v-row>
          </td>
        </tr>


        <tr style="height: 80px">
          <td class="name-td"><span style="font-size: 16px">주소</span></td>
          <td class="content-td">
            <v-col>
              <v-text-field
                  outlined dense dark text disabled
                  hide-details
                  class="text-fields white--text justify-start"
                  :placeholder="addrList[selectAddr].addr"
                  v-model="addr"
              >
              </v-text-field>
            </v-col>
          </td>
        </tr>
        <tr style="height: 80px">
          <td class="name-td"><span style="font-size: 16px">세부주소</span></td>
          <td class="content-td">
            <v-col>
              <v-text-field
                  outlined dense dark text hide-details
                  class="text-fields white--text justify-start"
                  :placeholder="addrList[selectAddr].detailAddr"
                  v-model="detail_addr"
              >
              </v-text-field>
            </v-col>
          </td>
        </tr>
        <tr style="height: 80px">
          <td class="name-td"><span style="font-size: 16px">연락처</span></td>
          <td class="content-td">
            <v-col>
              <v-text-field
                  outlined dense dark text
                  hide-details
                  class="text-fields white--text justify-start"
                  :placeholder="addrList[selectAddr].phoneNum"
                  v-model="phoneNum"
              >
              </v-text-field>
            </v-col>
          </td>
        </tr>
        </tbody>
      </v-simple-table>
    </v-col>

    <v-btn
        color="blue"
        class="white--text ma-4 mt-8"
        rounded
        @click="editInfo"
    ><span style="font-weight: bold; font-size: 15px">저장하기</span></v-btn>
  </v-row>
</template>

<script>
export default {
  name: "EditAddress",
  data () {
    return {
      addrList:[],
      selectAddr : 0,
      titleDelete : false,


      addrName:'',
      postcode: '',
      addr: '',
      detail_addr: '',
      extra_addr: '',
      phoneNum: '',
    }
  },

  methods:{
    getMyAddrBook(){
      this.$axios.get('addr/')
          .then(response => {
            console.log(response.data);
            this.addrList = response.data
          })
          .catch(error => {
            console.log(error.response);
          })
    },
    addAddrBook(){

    },
    deleteAddr(addrId){
      console.log(addrId)
      this.$axios.delete('addr/'+addrId)
          .then(response => {
            console.log(response.data);
            this.getMyAddrBook();
          })
          .catch(error => {
            console.log(error.response);
          })
    },

    editInfo(){


    },

    //카카오 우편번호 api
    getPostcode(){
      new window.daum.Postcode({
        oncomplete: (data) => {
          if (this.extra_addr !== "") {
            this.extra_addr = "";
          }
          if (data.userSelectedType === "R") {
            // 사용자가 도로명 주소를 선택했을 경우
            this.addr = data.roadAddress;
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            this.addr = data.jibunAddress;
          }

          // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
          if (data.userSelectedType === "R") {
            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              this.extra_addr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== "" && data.apartment === "Y") {
              this.extra_addr +=
                  this.extra_addr !== ""
                      ? `, ${data.buildingName}`
                      : data.buildingName;
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (this.extra_addr !== "") {
              this.extra_addr = `(${this.extra_addr})`;
            }
          } else {
            this.extra_addr = "";
          }
          // 우편번호를 입력한다.
          this.postcode = data.zonecode;
        },
      }).open();
    },
  },

  mounted() {
    this.getMyAddrBook();
  }
}
</script>

<style lang="scss">
.item-text{
  font-size: 16px;
  color: rgb(240,240,240);
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