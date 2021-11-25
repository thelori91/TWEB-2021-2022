Vue.component('testa', {
    template: '<header>\n' +
        '    <div class="container-fluid">\n' +
        '        <div class="row justify-content-md-center">\n' +
        '            <div class="col-md-auto">\n' +
        '                <p>Username is a Role since dd/mm/yyyy</p>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</header>'
});

Vue.component('centro', {
    props: ['title'],
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

Vue.component('piede', {
    template: '<footer>\n' +
        '    <div class="d-flex justify-content-between">\n' +
        '        <div class="p-2">legal info of the company/universtiy</div>\n' +
        '        <div class="p-2">Design by Andrea Cacioli, Lorenzo Cassinelli</div>\n' +
        '    </div>\n' +
        '</footer>'
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
        transit: function () {
            this.secondaPagina = true;
            this.primaPagina = false;
        }
    }
});