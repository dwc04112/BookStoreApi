<template >
    <v-container fluid  class="grid-list-sm pa-0">
          <v-row>
            <v-col>

              <nav class="nav" style="position: fixed; z-index: 6;">
                  <ul class="nav-menu">
                    <v-card
                        class = "menu-card"
                        height=120px
                        width=15px
                        style="margin-left: 50%; background-color: rgba(255,255,255,0.4); margin-top: 40vh"
                        elevation="1"
                    >
                      <li @click="('li1')"><a id="li1" href="#home"><div class="div-menu-1"></div></a></li>
                      <li @click="('li2')"><a id="li2" href="#about"><div class="div-menu-2"></div></a></li>
                    </v-card>
                  </ul>
              </nav>

            </v-col>

          </v-row>


          <div class="section">
            <section id="home" style="background-color: rgb(40,40,40);">
              <MainBook/>
            </section>
          </div>
          <div class="section">
            <section id="about" style="background-color: #F7ECDE">
              <About/>
            </section>
          </div>
    </v-container>
</template>
<script>
import $ from 'jquery'

import About from "@/views/book/About";
import MainBook from "@/views/book/MainBook";


export default {
  name: "Home",
  components: {MainBook, About},
  data () {
    return {

    }
  },
  methods : {

  },

  created() {
    $(document).ready(function () {
      const $menu_a = $("a", $(".nav-menu"));

      $menu_a.click(function () {
        $("html").animate(
            {scrollTop: $($(this).attr("href")).offset().top},
            {duration: 900, complete: $(this).attr("href")}
        );
      });


      $(".section").each(function () {
        // 개별적으로 Wheel 이벤트 적용 mousewheel(IE/chrome/opera) DOMMouseScroll(FF)
        $(this).on("mousewheel DOMMouseScroll", function (e) {
          e.preventDefault();
          var delta = 0;
          /* IE */
          if (!event) var event = window.event;
          //휠에 대한 정보 얻기 파이어폭스 외 IE/Chrome/Opera = wheelDelta
          if (event.wheelDelta) {
            delta = event.wheelDelta / 50;
            //평균 50~120 사이로 요소의 인식높이에 따라 다름(한 화면(height100%)기준일떄는 120
            if (window.opera) delta = -delta;
            //휠에 대한 정보 얻기 Mozilla FF = detail
          } else if (event.detail) delta = -event.detail / 3;
          var moveTop = null;
          // 마우스휠을 위에서 아래로
          if (delta < 0) {
            if ($(this).next() !== undefined) {

              //error
              if(($(this).offset().top>2400)){
                return null;
              }

              moveTop = $(this).next().offset().top;
            }
            // 마우스휠을 아래에서 위로
          } else {
            if ($(this).prev() !== undefined) {
              moveTop = $(this).prev().offset().top;
              if($(this).offset().top <100){
                moveTop = 0;
              }
            }
          }


          // 화면 이동 0.8초(800)
          $("html,body").stop().animate({
            scrollTop: moveTop + 'px'
          },{
            duration: 700, complete: function () {
            }
          });

        });
      });

    })
  },


}

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Source+Sans+Pro&display=swap');
#home ,#about{
  height: 100vh;
  width: 100%;
}
.nav-menu{
  list-style : none;
}
div{
  font-family: 'Source Sans Pro', sans-serif;
}
.section{
  z-index: 3;
}
.div-menu-1,.div-menu-2,.div-menu-3{
  height: 40px;
}
.div-menu-1{
  background-color: rgba(255,178,166,0.3);
}
.div-menu-2{
  background-color: rgba(154,220,255,0.3);
}
.div-menu-2{
  background-color: rgba(255,248,166,0.3);
}

</style>