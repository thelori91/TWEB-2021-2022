Vue.component('homebutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="homeButton"><- Back to Main Menu</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

Vue.component('newreservationbutton', {
    template: '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg greyHoverSelection ">New Reservation</button>',
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

Vue.component('signinbutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="signInButton">Sign in</button>',
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

Vue.component('donestatebutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="doneButton">Done</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

Vue.component('cancelstatebutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="cancelButton">Cancel</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

function seePassword() {
    let x = document.getElementsByClassName("form-control formStyle switchable");
    if (app.visiblePassword) {
        x[0].type = "text";
    } else {
        x[0].type = "password";
    }
}

function moreReservation() {
    app.newReservations.push({
        subject: "",
        teacher: "",
        day: "",
        time: "",
        courseOptions: [],
        teacherOptions: [],
        dayOptions: [],
        timeOptions: []
    });
    app.initCourseOptions();
}

function lessReservation() {
    app.newReservations.pop();
}

let app = new Vue({
    el: '#SPA',
    data: {
        homePage: true,
        handleReservationPage: false,
        newReservationPage: false,
        thirdPage: false,
        fourthPage: false,
        signInPage: false,
        signUpPage: false,
        wrongPassword: false,
        visiblePassword: false,
        showMore: false,
        redirectFunction: 'Home',
        showMoreText: "Show Cancelled/Done lessons",
        newUserUname: "",
        newUserPassword: "",
        newUserName: "",
        newUserSurname: "",
        username: "",
        role: "",
        upcomingEventsCollection: [],
        //New Lesson Variables
        newReservations: [{
            subject: "",
            teacher: "",
            day: "",
            time: "",

            //Options shown in the page
            courseOptions: [],
            teacherOptions: [],
            dayOptions: [],
            timeOptions: []
        }],
        //Internal, not used in html
        allCoursesWithTeachers: [],
        allLessons: [],
        pendingOperation: false,
        //////////////////////
        linkSingUpServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/signUp-servlet",
        linkSingInServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/signIn-servlet",
        linkLogOutServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/logOut-servlet",
        linkOnLoadServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/onLoad-servlet",
        linkReservationServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/reservation-servlet",
        linkGetAllTeacherCoursesServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/getAllTeacherCourses-servlet",
        linkGetAllLessonsServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/getAllLessons-servlet",
        linkUpdateLessonServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/updateLesson-servlet",
    },
    methods: {
        TOPHome: function () {
            this.homePage = true;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.thirdPage = false;
            this.fourthPage = false;
            this.signInPage = false;
            this.signUpPage = false;
        },
        TOPHandleReservation: function () {
            if (this.username.localeCompare('') !== 0) {
                this.homePage = false;
                this.handleReservationPage = true;
                this.newReservationPage = false;
                this.thirdPage = false;
                this.fourthPage = false;
                this.signInPage = false;
                this.signUpPage = false;
            } else {
                this.TOPSignUp();
            }
        },
        TOPnewReservation: function () {
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = true;
            this.thirdPage = false;
            this.fourthPage = false;
            this.signInPage = false;
            this.signUpPage = false;
            this.loadAllForNewReservation();
        },
        TOP3: function () {
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.thirdPage = true;
            this.fourthPage = false;
            this.signInPage = false;
            this.signUpPage = false;
        },
        TOP4: function () {
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.thirdPage = false;
            this.fourthPage = true;
            this.signInPage = false;
            this.signUpPage = false;
        },
        TOPSignIn: function () {
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.thirdPage = false;
            this.fourthPage = false;
            this.signInPage = true;
            this.signUpPage = false;
        },
        TOPSignUp: function () {
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.thirdPage = false;
            this.fourthPage = false;
            this.signInPage = false;
            this.signUpPage = true;
        },
        updateWrongPassword: function () {
            this.wrongPassword = !(this.newUserPassword.length >= 8 && this.newUserPassword.length <= 20);
        },
        toggle: function () {
            this.visiblePassword = !this.visiblePassword;

            //Only hand made binding!
            seePassword();
        },
        changeState: function (lesson, eventState) {
            var self = this;
            $.post(this.linkUpdateLessonServlet, {
                teacherId: lesson.teacherId,
                course: lesson.course,
                username: this.username,
                day: lesson.day,
                time: lesson.time,
                state: eventState
            }, function (data) {
                alert(data);
                if (data.split('\n')[0].localeCompare('Error:') !== 0) {
                    self.onPageLoad();
                }
            });
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
                        if (self.redirectFunction.localeCompare('Home') === 0) self.TOPHome();
                        if (self.redirectFunction.localeCompare('NewReservation') === 0) self.TOPnewReservation();
                        self.redirectFunction = 'Home';
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
                        if (self.redirectFunction.localeCompare('Home') === 0) self.TOPHome();
                        if (self.redirectFunction.localeCompare('NewReservation') === 0) self.TOPnewReservation();
                        self.redirectFunction = 'Home';
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
                    self.upcomingEventsCollection = [];
                });
            }
        },
        onPageLoad: function () {
            var self = this;
            $.get(this.linkOnLoadServlet, function (data) {
                try {
                    let obj = JSON.parse(data);
                    self.upcomingEventsCollection = obj;
                    self.username = obj[0].username;
                    self.role = obj[0].role;
                } catch (e) {
                    console.log('Error: http session does not contain username and password, log in/sign up to fix.');
                }
            });
        },
        showMoreLess: function () {
            this.showMore = !this.showMore;
            if (this.showMore) {
                this.showMoreText = "Hide Done/Cancelled lessons"
            } else {
                this.showMoreText = "Show Done/Cancelled lessons"
            }
        },
        bookLessons: function () {
            var self = this;

            //Before sending everything to the servlet, we check some things
            if (this.username.localeCompare('') === 0) {
                alert('Please, Sign In before you book any lesson.\nIf you are new, please, Sign Up!');
                this.pendingOperation = true;
                this.TOPSignUp();
                this.redirectFunction = 'NewReservation';
                return;
            }

            let allRight = true;
            for (let i = 0; i < this.newReservations.length && allRight; i++) {
                let reservation = this.newReservations[i];
                if (reservation.subject.localeCompare('') === 0) allRight = false;
                if (reservation.teacher.localeCompare('') === 0) allRight = false;
                if (reservation.day.localeCompare('') === 0) allRight = false;
                if (reservation.time.localeCompare('') === 0) allRight = false;
            }

            let array = [];
            this.newReservations.forEach(reservation => {
                let obj = {
                    course: reservation.subject,
                    teacherId: reservation.teacher.split(" ")[reservation.teacher.split(" ").length - 1],
                    day: reservation.day,
                    time: reservation.time
                };
                array.push(obj);
            });
            $.post(this.linkReservationServlet, {reservations: JSON.stringify(array)}, function (data) {
                alert(data);
                if (data.split("\n")[0].localeCompare("Error:") !== 0) {
                    self.pendingOperation = false;
                    self.TOPHome();
                }
            });
        },
        loadAllForNewReservation: function () {
            if (!this.pendingOperation) {
                this.newReservations = [{
                    subject: "",
                    teacher: "",
                    day: "",
                    time: "",
                    courseOptions: [],
                    teacherOptions: [],
                    dayOptions: [],
                    timeOptions: []
                }];
                var self = this;
                $.get(this.linkGetAllTeacherCoursesServlet, function (data) {
                    let arrayOfCourses = JSON.parse(data);
                    self.allCoursesWithTeachers = arrayOfCourses;
                    self.initCourseOptions();
                });
                $.get(this.linkGetAllLessonsServlet, function (data) {
                    let teacherWithLessonsArray = JSON.parse(data)
                    self.allLessons = teacherWithLessonsArray;
                });
            }
        },
        initCourseOptions: function () {
            for (let i = 0; i < this.newReservations.length; i++) {
                this.newReservations[i].courseOptions = [];
                for (let j = 0; j < this.allCoursesWithTeachers.length; j++) {
                    this.newReservations[i].courseOptions.push(this.allCoursesWithTeachers[j].courseName);
                }
            }
        },
        updateTeacherOptions: function (reservation) {
            let selectedSubject = reservation.subject;
            reservation.teacherOptions = [];
            reservation.teacher = "";
            reservation.dayOptions = [];
            reservation.day = "";
            reservation.timeOptions = [];
            reservation.time = "";
            let indexOfSubject = -1;
            for (let i = 0; i < this.allCoursesWithTeachers.length; i++) {
                if (this.allCoursesWithTeachers[i].courseName.localeCompare(selectedSubject) === 0) indexOfSubject = i;
            }
            let arrayOfTeachers = this.allCoursesWithTeachers[indexOfSubject].teachers;

            for (let i = 0; i < arrayOfTeachers.length; i++) {
                let name = arrayOfTeachers[i].teacherName;
                let surname = arrayOfTeachers[i].teacherSurname;
                let id = arrayOfTeachers[i].teacherId;
                let option = name + " " + surname + " " + id;
                reservation.teacherOptions.push(option);
            }
        },
        updateDayOptions: function (reservation) {
            if (typeof reservation.teacher !== 'undefined') {
                const allDayOptions = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
                reservation.dayOptions = allDayOptions;
                reservation.day = "";
                reservation.timeOptions = [];
                reservation.time = "";
                let teacher = reservation.teacher;
                let teacherId = teacher.split(" ")[teacher.split(" ").length - 1];
                let indexOfTeacher = -1;

                //Find indexOfteacher in allLessons array
                for (let i = 0; i < this.allLessons.length; i++) {
                    console.log(this.allLessons[i].teacherId);

                    if (this.allLessons[i].teacherId.toString().localeCompare(teacherId) === 0) {
                        indexOfTeacher = i;
                    }
                }

                if (indexOfTeacher !== -1) {

                    //For the given teacher
                    for (let k = 0; k < this.allLessons.length; k++) {
                        let teacher = this.allLessons[k];
                        console.log('teacher = ');
                        console.log(teacher);
                        //Find Id
                        let teacherId = reservation.teacher.split(" ")[reservation.teacher.split(" ").length - 1];
                        console.log('teacherId = ' + teacherId);
                        if (teacher.teacherId.toString().localeCompare(teacherId) === 0) {
                            //for a specific day
                            for (let i = 0; i < allDayOptions.length; i++) {
                                let day = allDayOptions[i];
                                let daysAvailableTimes = ["08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00"];
                                //find out if teacher is available
                                for (let j = 0; j < teacher.lessons.length; j++) {
                                    let lesson = teacher.lessons[j];

                                    console.log(lesson.lessonDay.localeCompare(day) === 0);
                                    console.log(lesson.lessonState.localeCompare("Active") === 0);

                                    if (lesson.lessonDay.localeCompare(day) === 0 && lesson.lessonState.localeCompare("Cancelled") !== 0) {
                                        //If already booked, remove time slot
                                        daysAvailableTimes = daysAvailableTimes.filter(function (value, index, arr) {
                                            return value !== lesson.lessonTime;
                                        });
                                    }
                                }
                                //If no time slot is left, we hide the option for that day
                                if (daysAvailableTimes.length === 0) {
                                    reservation.dayOptions = reservation.dayOptions.filter(function (value, index, arr) {
                                        return value.localeCompare(day) !== 0;
                                    });
                                }
                            }
                        }
                    }
                }
            }
        },
        updateTimeOptions: function (reservation) {
            if (typeof reservation.teacher !== 'undefined') {
                const allTimeOptions = ["08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00"];
                reservation.timeOptions = allTimeOptions;
                reservation.time = "";
                let teacher = reservation.teacher;
                let teacherId = teacher.split(" ")[teacher.split(" ").length - 1];
                let indexOfTeacher = -1;
                for (let i = 0; i < this.allLessons.length; i++) {
                    if (this.allLessons[i].teacherId.toString().localeCompare(teacherId) === 0) {
                        indexOfTeacher = i;
                    }
                }

                if (indexOfTeacher !== -1) {

                    let arrayOfLessons = this.allLessons[indexOfTeacher].lessons;

                    for (let i = 0; i < arrayOfLessons.length; i++) {
                        let lesson = arrayOfLessons[i];
                        if (lesson.lessonState.localeCompare('Cancelled') !== 0 && lesson.lessonDay.localeCompare(reservation.day) === 0) {
                            reservation.timeOptions = reservation.timeOptions.filter(function (value, index, array) {
                                return value.localeCompare(lesson.lessonTime) !== 0;
                            });
                        }

                    }
                }
            }
        }
    },
    beforeMount() {
        this.onPageLoad();
    }
});