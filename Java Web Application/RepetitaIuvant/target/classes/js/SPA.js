Vue.component('homebutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="headButton"><- Back to Main Menu</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

Vue.component('reservationbutton', {
    template: '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg ">Handle Reservation</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});
Vue.component('teacherbutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg ">Teachers</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});
Vue.component('signinbutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="fontStyle headButton">Sign in</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});
Vue.component('coursebutton', {
    template: '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg ">Courses</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
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
        handle: function(){
                this.wrongPassword = !(this.password.length >= 8 && this.password.length <= 20);
        },
        toggle: function(){
            this.visiblePassword = !this.visiblePassword;
            seePassword();
        }
    }
});