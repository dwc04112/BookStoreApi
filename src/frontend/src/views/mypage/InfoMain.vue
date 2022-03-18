<template>
  <v-container
      fluid
  >
    <v-layout wrap row>
      <v-flex>
        <div>
        info Main Page
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "InfoMain"
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