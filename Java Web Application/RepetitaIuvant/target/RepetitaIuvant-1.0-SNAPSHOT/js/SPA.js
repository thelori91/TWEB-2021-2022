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
        '                            <img id="logoSize" src="./image/Logo.png" alt="Logo REPETITA IUVANT">\n' +
        '                        </figure>\n' +
        '                        <h1 class="titleStyle">REPETITA IUVANT</h1>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>',
});

function seePassword() {
    let x = document.getElementsByClassName("form-control formStyle switchable");
    if (app.visiblePassword) {
        x[0].type = "text";
    } else {
        x[0].type = "password";
    }
}

let app = new Vue({
    el: '#SPA',
    data: {
        firstPage: true,
        secondPage: false,
        thirdPage: false,
        fourthPage: false,
        signInPage: false,
        signUpPage: false,
        wrongPassword: false,
        visiblePassword: false,
        newUserUname: "",
        newUserPassword: "",
        newUserName: "",
        newUserSurname: "",
        username: "",
        role: "",
        upcomingEventsCollection: [],
        linkSingUpServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/signUp-servlet",
        linkSingInServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/signIn-servlet",
        linkLogOutServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/logOut-servlet",
        linkOnLoadServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/onLoad-servlet"
    },
    methods: {
        TOP1: function () {
            this.firstPage = true;
            this.secondPage = false;
            this.thirdPage = false;
            this.fourthPage = false;
            this.signInPage = false;
            this.signUpPage = false;
        },
        TOP2: function () {
            this.firstPage = false;
            this.secondPage = true;
            this.thirdPage = false;
            this.fourthPage = false;
            this.signInPage = false;
            this.signUpPage = false;
        },
        TOP3: function () {
            this.firstPage = false;
            this.secondPage = false;
            this.thirdPage = true;
            this.fourthPage = false;
            this.signInPage = false;
            this.signUpPage = false;
        },
        TOP4: function () {
            this.firstPage = false;
            this.secondPage = false;
            this.thirdPage = false;
            this.fourthPage = true;
            this.signInPage = false;
            this.signUpPage = false;
        },
        TOPSignIn: function () {
            this.firstPage = false;
            this.secondPage = false;
            this.thirdPage = false;
            this.fourthPage = false;
            this.signInPage = true;
            this.signUpPage = false;
        },
        TOPSignUp: function () {
            this.firstPage = false;
            this.secondPage = false;
            this.thirdPage = false;
            this.fourthPage = false;
            this.signInPage = false;
            this.signUpPage = true;
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
            let nothingIsNull = this.newUserName != null && this.newUserSurname != null && this.newUserUname != null && this.newUserPassword != null;

            if (!nothingIsNull) return false;

            if (!isLogIn) {
                this.newUserName = this.newUserName.trim();
                this.newUserSurname = this.newUserSurname.trim();
            }
            this.newUserUname = this.newUserUname.trim();
            this.newUserPassword = this.newUserPassword.trim();


            let nothingEmptySignUp = this.newUserName.localeCompare('') !== 0 && this.newUserSurname.localeCompare('') !== 0;
            let nothingEmpty = this.newUserUname.localeCompare('') !== 0 && this.newUserPassword.localeCompare('') !== 0;
            if (!isLogIn) {
                nothingEmpty = nothingEmpty && nothingEmptySignUp;
            }
            let passwordLength = this.newUserPassword.length >= 8 && this.newUserPassword.length <= 20;

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
                        self.onPageLoad();
                        self.TOP1();
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
                        self.onPageLoad();
                        self.TOP1();
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
        },
        onPageLoad: function () {
            var self = this;
            $.get(this.linkOnLoadServlet, function (data) {
                let obj = JSON.parse(data);
                self.upcomingEventsCollection = obj;
                self.username = obj.username.toString();
                self.role = obj.role;
                console.log('Username = ' + self.username);
            });
        }
    },
    beforeMount() {
        console.log('Request done ');
        this.onPageLoad();
    }
});