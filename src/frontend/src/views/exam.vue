<template>

    <div class="bg">
      <video
          muted
          autoplay
          loop
      >
        <source :src="rain" type="video/mp4">
      </video>

      <div class="text">
        <p>Lorem Ipsum Dolor</p>
      </div>
    </div>

</template>

<script>
import rain from "@/assets/rain.mp4"
export default {
  name: "exam",
  components:[rain],
  data: () => ({
    rain
  }),
}
</script>

<style scoped>

.bg {
  width: 100%;
  height: 400px;
  overflow: hidden;
  margin: 0 auto;
  position: relative;
}
video {
  width: 100%;
}
.text {
  position: absolute;
  width: 100%;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
}
.text p {
  text-align: center;
  font-size: 48px;
  color: #ffffff;
}
</style>