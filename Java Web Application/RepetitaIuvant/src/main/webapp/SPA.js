Vue.component('testa', {
    template:
        '<div><button v-on:click="transitInner" type="button" class=" fontStyle headButton"><- Back to Main Menu</button></div>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

Vue.component('centro', {
    template: '<div class="container-fluid">' +
        '<div class="d-grid gap-2 col-4 mx-auto">' +
        '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg ">Handle Reservation</button>' +
        '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg ">Teachers</button>\n' +
        '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg ">Courses</button></div></div>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

let app = new Vue({
    el: '#SPA',
    data: {
        primaPagina: true,
        secondaPagina: false,
        terzaPagina: false,
        quartaPagina: false
    },
    methods: {
        P1TOP2: function () {
            this.secondaPagina = true;
            this.primaPagina = false;
        },
        P2TOP1: function () {
            this.primaPagina = true;
            this.secondaPagina = false;
        },
        P1TOP3: function () {
            this.terzaPagina = true;
            this.primaPagina = false;
        },
        P3TOP1: function () {
            this.primaPagina = true;
            this.terzaPagina = false;
        },
        P1TOP4: function () {
            this.quartaPagina = true;
            this.primaPagina = false;
        },
        P4TOP1: function () {
            this.primaPagina = true;
            this.quartaPagina = false;
        }
    }
});