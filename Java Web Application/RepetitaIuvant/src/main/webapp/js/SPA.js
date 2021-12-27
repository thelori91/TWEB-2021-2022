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
    },
    data: {}
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
        username: "",
        role: "",
        linkSingUpServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/signUp-servlet",
        linkSingInServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/signIn-servlet",
        linkLogOutServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/logOut-servlet"
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
        P1TOPSignIn: function () {
            this.signInPage = true;
            this.firstPage = false;
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
        checkFields: function (isLogIn) {
            let nothingIsNull = this.newUserPassword != null && this.newUserName != null && this.newUserSurname != null && this.newUserUname != null;
            if (!nothingIsNull) return false;

            if (!isLogIn) {
                this.newUserName = this.newUserName.trim();
                this.newUserSurname = this.newUserSurname.trim();
            }
            this.newUserPassword = this.newUserPassword.trim();
            this.newUserUname = this.newUserUname.trim();

            let nothingEmptySignUp = this.newUserSurname.localeCompare('') !== 0 && this.newUserName.localeCompare('') !== 0;
            let nothingEmpty = this.newUserUname.localeCompare('') !== 0 && this.newUserPassword.localeCompare('') !== 0;
            if (!isLogIn) {
                nothingEmpty = nothingEmpty && nothingEmptySignUp;
            }
            let passwordLength = this.newUserPassword.length <= 20 && this.newUserPassword.length >= 8;

            let allRight = nothingEmpty && passwordLength;

            if (!passwordLength) alert("Check password length " + this.newUserPassword.length);
            if (!nothingEmpty) alert("Please fill everything");
            return allRight;

        }, registerNewUser: function () {
            var self = this;
            let nothingIsEmpty = this.checkFields(false);
            if (nothingIsEmpty) {
                $.post(this.linkSingUpServlet, {
                    uname: this.newUserUname,
                    password: this.newUserPassword,
                    name: this.newUserName,
                    surname: this.newUserSurname
                }, function (data) {
                    const response = data.toString().split("\n");
                    if (response[0].localeCompare("Success:") === 0) {
                        self.username = response[1];
                        self.role = response[2];
                        self.P2TOP1();
                    } else
                        alert(data);
                });
            }
        },
        signInUser: function () {
            var self = this;
            let nothingIsEmpty = this.checkFields(true);
            if (nothingIsEmpty) {
                $.post(this.linkSingInServlet, {
                    uname: this.newUserUname,
                    password: this.newUserPassword,
                }, function (data) {
                    const response = data.toString().split("\n");
                    if (response[0].localeCompare("Success:") === 0) {
                        self.username = response[1];
                        self.role = response[2];
                        self.PSignInTOP1();
                    } else
                        alert(data);
                });
            }
        },
        logOut: function () {
            var self = this;
            if (this.username.localeCompare("") !== 0 && this.role.localeCompare("") !== 0) {
                $.get(this.linkLogOutServlet, function (data) {
                    self.username = "";
                    self.role = "";
                    self.newUserPassword = "";
                });
            }
        }
    }
});