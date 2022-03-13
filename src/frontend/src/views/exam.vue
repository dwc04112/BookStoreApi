<template>
  <v-container fluid>
    <v-layout wrap row justify-center>
      <v-flex
          class="pb-4 pl-4"
          xs12 sm4 md2
          style="flex-basis: auto"
          v-for="(data,k) in examD"
          :key="k"
      >
        <v-card
            width="250px"
            height="520px"
            color="grey"

        >
          {{data.main}}
          {{data.sub}}
        </v-card>
      </v-flex>
    </v-layout>
    <v-layout wrap row justify-center>
      <v-flex
          class="pb-4 pl-4"
          xs12 sm4 md2
          style="flex-basis: auto"
          v-for="(data,k) in examF"
          :key="k"
      >
        <v-card
            width="250px"
            height="520px"
            color="grey"
        >
          {{data.main}}
          {{data.sub}}
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "exam",
  data: () => ({
    justify: [
      'start',
      'center',
      'end',
    ],
    examD:[
      {main:1, sub:'hi1'},
      {main:2, sub:'hi2'},
      {main:3, sub:'hi3'},
      {main:4, sub:'hi4'},
      {main:5, sub:'hi5'},
      {main:6, sub:'hi6'},
    ],
    examF:[
      {main:1, sub:'fehi1'},
      {main:2, sub:'fehi2'},
      {main:3, sub:'fehi3'},
      {main:4, sub:'fehi4'},
      {main:5, sub:'fehi5'},
      {main:6, sub:'fehi6'},
      {main:7, sub:'fehi7'},
    ],
  }),
}
</script>

<style scoped>
v-container{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-content: center;
}
</style>