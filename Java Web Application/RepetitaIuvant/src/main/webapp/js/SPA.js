
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
        '                            <img id="logoPag2Size" src="./image/Logo.png" alt="Logo REPETITA IUVANT">\n' +
        '                        </figure>\n' +
        '                        <h1 class="titleStyle">REPETITA IUVANT</h1>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>',
});


let app = new Vue({
    el: '#SPA',
    data: {
        firstPage: true,
        secondPage: false,
        signInPage: false,
        thirdPage: false,
        fourthPage: false,
        wrongPassword: false,
        visiblePassword: false,
        newUserUname: "",
        newUserPassword: "",
        newUserName: "",
        newUserSurname: "",
        servletResponse: "",
        linkSingUpServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/signUp-servlet"
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
            this.wrongPassword = !(this.newUserPassword.length >= 8 && this.newUserPassword.length <= 20);
        },
        toggle: function () {
            this.visiblePassword = !this.visiblePassword;

            //Only hand made binding!
            seePassword();
        },
        AjaxOldMethod: function () {
            let request = new XMLHttpRequest();
            request.onreadystatechange = () => {
                if (request.readyState === 4) {
                    alert("got status: " + request.status + "\nText:" + request.responseText);
                    this.servletResponse = request.responseText;
                }
            };
            let params = "uname=" + this.newUserUname + "&password=" + this.newUserPassword + "&name=" + this.newUserName + "&surname=" + this.newUserSurname;
            request.open("POST", "http://localhost:8080/RepetitaIuvant_war_exploded/signUp-servlet", true);
            request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            request.send(params);
        },
        registerNewUser: function () {
            var self = this;
            $.post(this.linkSingUpServlet, {
                uname: this.newUserUname,
                password: this.newUserPassword,
                name: this.newUserName,
                surname: this.newUserSurname
            }, function (data) {
                alert("got : " + data);
                const response = data.toString().split(" ");
                if (response[0] !== "Error")
                    self.servletResponse = response[0];
            });
        }
    }
});