<template>


    <v-container
        id="scroll-target"
    >
      <div style="position: fixed">{{ offsetTop }}</div>
      <div style="height: 11000px"  v-scroll:#scroll-target="onScroll"></div>
    </v-container>
</template>

<script>
export default {
  data: () => ({
    name: "exam2",
    offsetTop: 0,
  }),
  methods: {
    onScroll (e) {
      this.offsetTop = e.target.scrollTop
    },
  },
}
</script>