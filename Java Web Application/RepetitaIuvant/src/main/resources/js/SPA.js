Vue.component('homebutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="homeButton"><- Back to Main Menu</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

Vue.component('reservationbutton', {
    template: '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg greyHoverSelection ">Handle Reservation</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});
Vue.component('teacherbutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg greyHoverSelection">Teachers</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});
Vue.component('signinbutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="signInButton">Sign in</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});
Vue.component('coursebutton', {
    template: '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg greyHoverSelection ">Courses</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});
Vue.component('logosection', {
    template: '<div class="container-fluid center">\n' +
        '                <div class="row justify-content-md-center">\n' +
        '                    <div class="col-md-auto">\n' +
        '                        <figure>\n' +
        '                            <img id="logoPag2Size" src="../resources/image/Logo.png" alt="Logo REPETITA IUVANT">\n' +
        '                        </figure>\n' +
        '                        <h1 class="titleStyle">REPETITA IUVANT</h1>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>',
});
Vue.component('credentialsection', {
    template: '<div>'+
        '<div class="row g-3 align-items-center">\n' +
        '          <div class="mb-3">\n' +
        '              <input type="email" class="form-control formStyle" placeholder="Email" required>\n' +
        '          </div>\n' +
        '      </div>\n' +
        '      <div class="row g-3 align-items-center">\n' +
        '           <div class="input-group mb-3">\n' +
        '               <input v-model="password" @keyup="handle" type="password" id="inputPassword6"\n' +
        '                                               class="form-control formStyle" placeholder="Password" required>\n' +
        '               <button class="btn btn-outline-secondary" type="button" v-if="visiblePassword" v-on:click="toggle"><i\n' +
        '                                                class="bi bi-eye-slash-fill"></i>\n' +
        '               </button>\n' +
        '               <button class="btn btn-outline-secondary" type="button" v-else v-on:click="toggle"><i class="bi bi-eye-fill"></i>\n' +
        '               </button>\n' +
        '           </div>\n' +
        '     </div>\n' +
        '     <div v-if="wrongPassword" class="row g-3 align-items-center">\n' +
        '           <div class="mb-3">\n' +
        '               <span id="passwordHelpInline" class="form-text" style="color: red;">\n' +
        '                                            Password must be 8-20 characters long\n' +
        '               </span>\n' +
        '           </div>\n' +
        '     </div>\n' +
        '     <div class="row g-3 align-items-center">\n' +
        '       <div class="col-auto">\n' +
        '            <button type="submit" class="btn btn-primary">Submit</button>\n' +
        '       </div>\n' +
        '     </div>'+
        '</div>'
});


let app = new Vue({
    el: '#SPA',
    data: {
        firstPage: true,
        secondPage: false,
        signInPage: false,
        thirdPage: false,
        fourthPage: false,
        password: "",
        wrongPassword: false,
        visiblePassword: false
    },
    methods: {
        P1TOP2: function () {
            this.secondPage = true;
            this.firstPage = false;
        },
        P2TOP1: function () {
            this.firstPage = true;
            this.secondPage = false;
        },
        P2TOSignIn: function () {
            this.signInPage = true;
            this.secondPage = false;
        },
        PSignInTOP1: function () {
            this.firstPage = true;
            this.signInPage = false;
        },
        P1TOP3: function () {
            this.thirdPage = true;
            this.firstPage = false;
        },
        P3TOP1: function () {
            this.firstPage = true;
            this.thirdPage = false;
        },
        P1TOP4: function () {
            this.fourthPage = true;
            this.firstPage = false;
        },
        P4TOP1: function () {
            this.firstPage = true;
            this.fourthPage = false;
        },
        handle: function () {
            this.wrongPassword = !(this.password.length >= 8 && this.password.length <= 20);
        },
        toggle: function () {
            this.visiblePassword = !this.visiblePassword;
            seePassword();
        }
    }
});