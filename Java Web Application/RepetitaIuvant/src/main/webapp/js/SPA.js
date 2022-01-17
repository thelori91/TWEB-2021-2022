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
    template: '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg commonStyleButton">New Reservation</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

Vue.component('reservationbutton', {
    template: '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg commonStyleButton">Handle Reservation</button>',
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
        '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg commonStyleButton">Teachers</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

Vue.component('coursebutton', {
    template: '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg commonStyleButton ">Courses</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

Vue.component('handleteacherandcoursedminbutton', {
    template: '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg commonStyleButton ">Handle Teacher Course Admin</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

Vue.component('handlereservationadminbutton', {
    template: '<button v-on:click="transitInner" type="button" class="btn btn-primary btn-dark btn-lg commonStyleButton ">Handle Reservation Admin</button>',
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

Vue.component('activestatebutton', {
    template:
        '<button v-on:click="transitInner" type="button" class="activeButton">Active</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
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

Vue.component('searchbutton', {
    template:
        '<button type="button" v-on:click="transitInner" class="btn btn-primary">Search</button>',
    methods: {
        transitInner: function () {
            this.$emit('transit-inner');
        }
    }
});

Vue.component('resetfiltersbutton', {
    template:
        '<button type="button" v-on:click="transitInner" class="btn btn-secondary">Reset Filters</button>',
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
        /* PAGE */
        homePage: true,
        handleReservationPage: false,
        newReservationPage: false,
        searchTeacherPage: false,
        searchCoursePage: false,
        signInPage: false,
        signUpPage: false,
        handleTeacherAndCourseAdminPage: false,
        handleReservationAdminPage: false,
        /* HANDLER FOR PASSWORD */
        wrongPassword: false,
        visiblePassword: false,
        /* BUTTON */
        showMore: false,
        redirectFunction: 'Home',
        showMoreText: "Show Done/Cancelled lessons",

        newUserUname: "",
        newUserPassword: "",
        newUserName: "",
        newUserSurname: "",
        username: "",
        role: "",
        upcomingEventsCollection: [],
        /* New Lesson Variables */
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

        /*ADMIN PAGE*/
        /* Teacher */
        newTeacherName: "",
        newTeacherSurname: "",
        selectedTeacher: "",
        rmvTeacherOptions: [],

        /* Course */
        newCourseName: "",
        selectedCourse: "",
        rmvCourseOptions: [],

        /* Teacher Course */
        selectedTeacherCourseT: "",
        selectedTeacherCourseC: "",
        allTeachers: [],
        allCourses: [],
        teacherCourseOptions: [],
        selectedTeacherCourseOption: {
            teacherId: "",
            teacherName: "",
            teacherSurname: "",
            courseName: ""
        },
        selectedTeacherCourse: "",

        /* Handle reservation */
        lessonArray: [{
            teacherName: "",
            teacherSurname: "",
            teacherId: "",
            lessonsArray: [{
                lessonId: "",
                lessonCourse: "",
                lessonDay: "",
                lessonTime: "",
                lessonUser: "",
                lessonState: ""
            }]
        }],

        /*SEARCH TEACHER PAGE*/
        searchTeacherNandS: "",
        selectedCourseFilter: "",
        searchCourse: "",
        selectedTeacherFilter: "",
        teacherFound: [],
        courseFound: [],

        /* LINKS */
        linkSingUpServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/signUp-servlet",
        linkSingInServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/signIn-servlet",
        linkLogOutServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/logOut-servlet",
        linkOnLoadServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/onLoad-servlet",
        linkReservationServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/reservation-servlet",
        linkGetAllTeacherCoursesServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/getAllTeacherCourses-servlet",
        linkGetAllLessonsServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/getAllLessons-servlet",
        linkUpdateLessonServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/updateLesson-servlet",
        linkAddTeacherServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/addTeacher-servlet",
        linkRmvTeacherServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/rmvTeacher-servlet",
        linkAddCourseServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/addCourse-servlet",
        linkRmvCourseServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/rmvCourse-servlet",
        linkAddTeacherCourseServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/addTeacherCourse-servlet",
        linkRmvTeacherCourseServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/rmvTeacherCourse-servlet",
        linkGetAllTeachersServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/getAllTeachers-servlet",
        linkGetAllCoursesServlet: "http://localhost:8080/RepetitaIuvant_war_exploded/getAllCourses-servlet",
    },
    methods: {
        TOPHome: function () {
            this.homePage = true;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.searchTeacherPage = false;
            this.searchCoursePage = false;
            this.signInPage = false;
            this.signUpPage = false;
            this.handleTeacherAndCourseAdminPage = false;
            this.handleReservationAdminPage = false;
        },
        TOPHandleReservation: function () {
            if (this.username.localeCompare('') !== 0) {
                this.homePage = false;
                this.handleReservationPage = true;
                this.newReservationPage = false;
                this.searchTeacherPage = false;
                this.searchCoursePage = false;
                this.signInPage = false;
                this.signUpPage = false;
                this.handleTeacherAndCourseAdminPage = false;
                this.handleReservationAdminPage = false;
            } else {
                this.TOPSignUp();
            }
        },
        TOPnewReservation: function () {
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = true;
            this.searchTeacherPage = false;
            this.searchCoursePage = false;
            this.signInPage = false;
            this.signUpPage = false;
            this.handleTeacherAndCourseAdminPage = false;
            this.handleReservationAdminPage = false;
            this.loadAllForNewReservation();
        },
        TOPSearchTeacher: function () {
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.searchTeacherPage = true;
            this.searchCoursePage = false;
            this.signInPage = false;
            this.signUpPage = false;
            this.handleTeacherAndCourseAdminPage = false;
            this.handleReservationAdminPage = false;
            /* init all and download */
            this.searchTeacherNandS = "";
            this.selectedCourseFilter = "";
            this.teacherFound = [];
            this.allTeachers = [];
            this.getAllTeachers();
            this.loadAllForNewReservation();
            /*init dropdown*/
            this.allCourses = [];
            this.getAllCourses();
        },

        TOPSearchCourse: function () {
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.searchTeacherPage = false;
            this.searchCoursePage = true;
            this.signInPage = false;
            this.signUpPage = false;
            this.handleTeacherAndCourseAdminPage = false;
            this.handleReservationAdminPage = false;
            /* init all and download */
            this.searchCourse = "";
            this.selectedTeacherFilter = "";
            this.courseFound = [];
            this.allCourses = [];
            this.getAllCourses();
            this.loadAllForNewReservation();
            /*init dropdown*/
            this.allTeachers = [];
            this.getAllTeachers();
        },
        TOPSignIn: function () {
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.searchTeacherPage = false;
            this.searchCoursePage = false;
            this.signInPage = true;
            this.signUpPage = false;
            this.handleTeacherAndCourseAdminPage = false;
            this.handleReservationAdminPage = false;
        },
        TOPSignUp: function () {
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.searchTeacherPage = false;
            this.searchCoursePage = false;
            this.signInPage = false;
            this.signUpPage = true;
            this.handleTeacherAndCourseAdminPage = false;
            this.handleReservationAdminPage = false;
        },
        TOPHandleTeacherCourseAdmin: function () {
            if (this.role.localeCompare('Admin') !== 0) return;
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.searchTeacherPage = false;
            this.searchCoursePage = false;
            this.signInPage = false;
            this.signUpPage = false;
            this.handleTeacherAndCourseAdminPage = true;
            this.handleReservationAdminPage = false;
            /*init all*/
            this.newTeacherName = "";
            this.newTeacherSurname = "";
            this.selectedTeacher = "";
            this.rmvTeacherOptions = [];
            this.newCourseName = "";
            this.selectedCourse = "";
            this.rmvCourseOptions = [];
            this.allTeachers = [];
            this.allCourses = [];
            this.selectedTeacherCourseT = "";
            this.selectedTeacherCourseC = "";
            this.allTeachers = [];
            this.allCourses = [];
            this.teacherCourseOptions = [];
            this.selectedTeacherCourse = "";
            this.initTeacherCourseOptions();
            this.loadAllForTeacherOptions();
            this.loadAllForCourseOptions();
            this.getAllTeachers();
            this.getAllCourses();
        },
        TOPHandleReservationAdmin: function () {
            if (this.role.localeCompare('Admin') !== 0) return;
            this.loadAllLessonAdmin();
            this.homePage = false;
            this.handleReservationPage = false;
            this.newReservationPage = false;
            this.searchTeacherPage = false;
            this.searchCoursePage = false;
            this.signInPage = false;
            this.signUpPage = false;
            this.handleTeacherAndCourseAdminPage = false;
            this.handleReservationAdminPage = true;

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
                lessonId: lesson.lessonId,
                nextState: eventState
            }, function (data) {
                alert(data);
                if (data.split('\n')[0].localeCompare('Error:') !== 0) {
                    self.onPageLoad();
                }
            });
        },
        changeStateAdmin: function (teacher, lesson, eventState) {
            var self = this;
            $.post(this.linkUpdateLessonServlet, {
                lessonId: lesson.lessonId,
                nextState: eventState
            }, function (data) {
                alert(data);
                if (data.split('\n')[0].localeCompare('Error:') !== 0) {
                    self.loadAllLessonAdmin();
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
            else if (!nothingEmpty) alert("Please fill everything");
            else return allRight;

        }, registerNewUser: function () {
            if (this.wrongPassword)
                alert("Error:\npassword must have from 8 to 20 chars, your password length is " + this.newUserPassword.length)
            else if (this.newUserUname.length > 30)
                alert("Error:\nusername is too long, please insert a new one " + this.newUserUname.length)
            else if (this.newUserName.length > 30)
                alert("Error:\nName is too long, please insert a new one " + this.newUserUname.length)
            else if (this.newUserSurname.length > 30)
                alert("Error:\nSurname is too long, please insert a new one " + this.newUserUname.length)
            else {
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
            }
        },
        signInUser: function () {
            if (this.wrongPassword)
                alert("Error:\npassword must have from 8 to 20 chars, your password length is " + this.newUserPassword.length)
            else if (this.newUserUname.length > 30)
                alert("Error:\nusername is too long: " + this.newUserUname.length)
            else {
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
                            if (self.role.localeCompare('') !== 0 && self.role.localeCompare('Admin') === 0) {
                                self.loadAllLessonAdmin();
                            }
                            self.onPageLoad();
                            if (self.redirectFunction.localeCompare('Home') === 0) self.TOPHome();
                            if (self.redirectFunction.localeCompare('NewReservation') === 0) self.TOPnewReservation();
                            self.redirectFunction = 'Home';
                        } else
                            alert(data);
                    });
                }
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
                    self.username = obj[0].username;
                    self.role = obj[0].role;
                    obj.shift();
                    self.upcomingEventsCollection = obj;
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
                    self.onPageLoad();
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
        },
        addTeacher: function () {
            var self = this;
            this.newTeacherName = this.stringToUpperCase(this.newTeacherName); /* add first letter to Upper Case */
            this.newTeacherSurname = this.stringToUpperCase(this.newTeacherSurname);
            $.get(this.linkAddTeacherServlet, {
                teacherName: this.newTeacherName,
                teacherSurname: this.newTeacherSurname
            }, function (data) {
                alert(data);
                self.newTeacherName = "";
                self.newTeacherSurname = "";
                self.selectedTeacherCourseT = "";
                self.allTeachers = [];
                self.getAllTeachers();
            });
        },
        rmvTeacher: function () {
            var self = this;
            $.get(this.linkRmvTeacherServlet, {selectedTeacher: this.selectedTeacher}, function (data) {
                alert(data);
                self.selectedTeacher = "";
                self.selectedCourse = "";
                self.rmvTeacherOptions = [];
                self.rmvCourseOptions = [];
                self.loadAllForCourseOptions();
                self.loadAllForTeacherOptions();
                /* UPDATE TeacherCourse dropdown */
                self.selectedTeacherCourse = "";
                self.teacherCourseOptions = [];
                self.initTeacherCourseOptions();
            });
        },
        loadAllForTeacherOptions: function () {
            var self = this;
            $.get(this.linkGetAllTeacherCoursesServlet, function (data) {
                let arrayOfCourses = JSON.parse(data);
                self.initTeachersOptions(arrayOfCourses);
            });
        },
        initTeachersOptions: function (arrayOfCourses) {
            for (let i = 0; i < arrayOfCourses.length; i++) {
                for (let j = 0; j < arrayOfCourses[i].teachers.length; j++) {
                    var teacherId = arrayOfCourses[i].teachers[j].teacherId;
                    var teacherName = arrayOfCourses[i].teachers[j].teacherName;
                    var teacherSurname = arrayOfCourses[i].teachers[j].teacherSurname;
                    var stringTeacher = teacherName + " " + teacherSurname + " " + String(teacherId);
                    if (!this.rmvTeacherOptions.includes(stringTeacher)) {
                        this.rmvTeacherOptions.push(
                            stringTeacher
                        );
                    }
                }
            }
        },
        addCourse: function () {
            var self = this;
            this.newCourseName = this.stringToUpperCase(this.newCourseName); /* add first letter to Upper Case */
            $.get(this.linkAddCourseServlet, {
                courseName: this.newCourseName
            }, function (data) {
                alert(data);
                self.newCourseName = "";
                self.selectedTeacherCourseC = "";
                self.allCourses = [];
                self.getAllCourses();

            });
        },
        rmvCourse: function () {
            var self = this;
            $.get(this.linkRmvCourseServlet, {selectedCourse: this.selectedCourse}, function (data) {
                alert(data);
                self.selectedTeacher = "";
                self.selectedCourse = "";
                self.rmvTeacherOptions = [];
                self.rmvCourseOptions = [];
                self.loadAllForCourseOptions();
                self.loadAllForTeacherOptions();
                /* UPDATE TeacherCourse dropdown */
                self.selectedTeacherCourse = "";
                self.teacherCourseOptions = [];
                self.initTeacherCourseOptions();
            });
        },
        loadAllForCourseOptions: function () {
            var self = this;
            $.get(this.linkGetAllTeacherCoursesServlet, function (data) {
                let arrayOfCourses = JSON.parse(data);
                self.initCoursesOptions(arrayOfCourses);
            });
        },
        initCoursesOptions: function (arrayOfCourses) {
            for (let i = 0; i < arrayOfCourses.length; i++) {
                if (!this.rmvCourseOptions.includes(arrayOfCourses[i].courseName)) {
                    this.rmvCourseOptions.push(arrayOfCourses[i].courseName);
                }
            }
        },
        addTeacherCourse: function () {
            var self = this;
            $.get(this.linkAddTeacherCourseServlet, {
                teacher: this.selectedTeacherCourseT,
                course: this.selectedTeacherCourseC
            }, function (data) {
                alert(data);
                /* UPDATE rmv Teacher and Course dropdown */
                self.selectedTeacher = "";
                self.selectedCourse = "";
                self.rmvTeacherOptions = [];
                self.rmvCourseOptions = [];
                self.loadAllForCourseOptions();
                self.loadAllForTeacherOptions();
                /* UPDATE itself*/
                self.selectedTeacherCourseT = "";
                self.selectedTeacherCourseC = "";
                /* UPDATE rmv TeacherCourse*/
                self.selectedTeacherCourse = "";
                self.teacherCourseOptions = [];
                self.initTeacherCourseOptions();
            });
        },
        getAllTeachers: function () {
            var self = this;
            $.get(this.linkGetAllTeachersServlet, function (data) {
                let teachers = JSON.parse(data);
                for (let i = 0; i < teachers.length; i++) {
                    let teacherName = teachers[i].teacherName;
                    let teacherSurname = teachers[i].teacherSurname;
                    let teacherId = teachers[i].teacherId;
                    let teacherString = teacherName + " " + teacherSurname + " " + String(teacherId);
                    self.allTeachers.push(teacherString);
                }
            });
        },
        getAllCourses: function () {
            var self = this;
            $.get(this.linkGetAllCoursesServlet, function (data) {
                let courses = JSON.parse(data);
                for (let i = 0; i < courses.length; i++) {
                    self.allCourses.push(courses[i].courseName);
                }
            });
        },
        rmvTeacherCourse: function () {
            this.completeParamToPost()
            var self = this;
            $.get(this.linkRmvTeacherCourseServlet, {
                teacherName: this.selectedTeacherCourseOption.teacherName,
                teacherSurname: this.selectedTeacherCourseOption.teacherSurname,
                teacherId: this.selectedTeacherCourseOption.teacherId,
                courseName: this.selectedTeacherCourseOption.courseName
            }, function (data) {
                alert(data);
                /*UPDATE rmv Teacher and Course dropdown*/
                self.selectedTeacher = "";
                self.selectedCourse = "";
                self.rmvTeacherOptions = [];
                self.rmvCourseOptions = [];
                self.loadAllForCourseOptions();
                self.loadAllForTeacherOptions();

                /*UPDATE itself*/
                self.selectedTeacherCourse = "";
                self.teacherCourseOptions = [];
                self.initTeacherCourseOptions();
            });
        },
        initTeacherCourseOptions: function () {
            var self = this;
            $.get(this.linkGetAllTeacherCoursesServlet, function (data) {
                let arrayOfTeachersCourses = JSON.parse(data);
                for (let i = 0; i < arrayOfTeachersCourses.length; i++) {
                    let courseName = arrayOfTeachersCourses[i].courseName;
                    for (let j = 0; j < arrayOfTeachersCourses[i].teachers.length; j++) {
                        let teacherName = arrayOfTeachersCourses[i].teachers[j].teacherName;
                        let teacherSurname = arrayOfTeachersCourses[i].teachers[j].teacherSurname;
                        let teacherId = arrayOfTeachersCourses[i].teachers[j].teacherId;
                        let stringTeacherCourse = teacherName + " " + teacherSurname + " " + teacherId + " " + courseName;
                        let obj = {
                            teacherName: teacherName,
                            teacherSurname: teacherSurname,
                            teacherId: teacherId,
                            courseName: courseName,
                        };
                        cond = self.containsObj(obj, self.teacherCourseOptions);
                        if (!cond) {
                            self.teacherCourseOptions.push(obj);
                        }
                    }
                }
            });
        },

        completeParamToPost: function () {
            let teacherCourseArray = this.selectedTeacherCourse.split(" ");
            for (let i = 0; i < teacherCourseArray.length; i++) {
                for (let j = 0; j < this.teacherCourseOptions.length; j++) {
                    if (teacherCourseArray[i].localeCompare(this.teacherCourseOptions[j].teacherId) === 0) {
                        this.selectedTeacherCourseOption.teacherId = String(this.teacherCourseOptions[j].teacherId);
                        this.selectedTeacherCourseOption.teacherName = this.teacherCourseOptions[j].teacherName;
                        this.selectedTeacherCourseOption.teacherSurname = this.teacherCourseOptions[j].teacherSurname;
                        this.selectedTeacherCourseOption.courseName = this.teacherCourseOptions[j].courseName;
                        break;
                    }
                }
            }
        },
        containsObj: function (obj, list) {
            var i;
            for (i = 0; i < list.length; i++) {
                if (list[i] === obj) {
                    return true;
                }
            }
            return false;
        },
        loadAllLessonAdmin: function () {
            var self = this;
            $.get(this.linkGetAllLessonsServlet, function (data) {
                    /* init all*/
                    self.lessonArray = [{
                        teacherName: "",
                        teacherSurname: "",
                        teacherId: "",
                        lessonsArray: [{
                            lessonId: "",
                            lessonCourse: "",
                            lessonDay: "",
                            lessonTime: "",
                            lessonUser: "",
                            lessonState: ""
                        }]
                    }];
                    let teacherWithLessonsArray = JSON.parse(data)
                    self.allLessons = teacherWithLessonsArray;
                    for (let i = 0; i < self.allLessons.length; i++) {
                        let teacherName = self.allLessons[i].teacherName;
                        let teacherSurname = self.allLessons[i].teacherSurname;
                        let teacherId = self.allLessons[i].teacherId;
                        self.lessonArray[i].teacherName = teacherName;
                        self.lessonArray[i].teacherSurname = teacherSurname;
                        self.lessonArray[i].teacherId = teacherId;
                        for (let j = 0; j < self.allLessons[i].lessons.length; j++) {
                            let courseName = self.allLessons[i].lessons[j].lessonCourse;
                            let lDay = self.allLessons[i].lessons[j].lessonDay;
                            let lTime = self.allLessons[i].lessons[j].lessonTime;
                            let username = self.allLessons[i].lessons[j].lessonWithUser;
                            let lState = self.allLessons[i].lessons[j].lessonState;
                            self.lessonArray[i].lessonsArray[j] = {
                                lessonId: self.allLessons[i].lessons[j].lessonId,
                                lessonCourse: courseName,
                                lessonDay: lDay,
                                lessonTime: lTime,
                                lessonUser: username,
                                lessonState: lState
                            };
                            /* if there isn't next element, don't push another element */
                            if (j !== self.allLessons[i].lessons.length - 1) {
                                self.lessonArray[i].lessonsArray.push({
                                    lessonId: "",
                                    lessonCourse: "",
                                    lessonDay: "",
                                    lessonTime: "",
                                    lessonUser: "",
                                    lessonState: ""
                                });
                            }
                        }
                        /* if there isn't next element, don't push another element */
                        if (i !== self.allLessons.length - 1) {
                            self.lessonArray.push({
                                teacherName: "",
                                teacherSurname: "",
                                teacherId: "",
                                lessonsArray: [{
                                    lessonId: "",
                                    lessonCourse: "",
                                    lessonDay: "",
                                    lessonTime: "",
                                    lessonUser: "",
                                    lessonState: ""
                                }]
                            });
                        }
                    }
                }
            );
        },
        resetFilter: function () {
            /* teacher */
            this.searchTeacherNandS = "";
            this.selectedCourseFilter = "";
            this.teacherFound = [];
            /* course */
            this.searchCourse = "";
            this.selectedTeacherFilter = "";
            this.courseFound = [];
        },
        searchTeacherFunction: function () {
            this.teacherFound = [];
            this.searchTeacherNandS = this.searchTeacherNandS.trim();
            let condTeacherNandS = this.searchTeacherNandS.localeCompare("") !== 0;
            let condCourse = this.selectedCourseFilter.localeCompare("") !== 0;
            if (!condTeacherNandS && !condCourse) {
                this.teacherFound = this.allTeachers;
            } else if (condTeacherNandS && condCourse) { /* TEXTBOX AND DROPDOWN BOTH */
                this.searchTeacherNandS = this.stringToUpperCase(this.searchTeacherNandS);
                for (let i = 0; i < this.allCoursesWithTeachers.length; i++) {
                    let course = this.allCoursesWithTeachers[i].courseName;
                    if (course.localeCompare(this.selectedCourseFilter) === 0) {
                        for (let j = 0; j < this.allCoursesWithTeachers[i].teachers.length; j++) {
                            let teacher = this.allCoursesWithTeachers[i].teachers[j];
                            let teacherString = teacher.teacherName + " " + teacher.teacherSurname + " " + teacher.teacherId;
                            let searchingNameAndSurname = teacher.teacherName + " " + teacher.teacherSurname;
                            let teacherArr = searchingNameAndSurname.split(" ");
                            for (let k = 0; k < teacherArr.length; k++) {
                                let stringTeacherNandSArray = this.searchTeacherNandS.split(" ");
                                for (let h = 0; h < stringTeacherNandSArray.length; h++) {
                                    let cond = teacherArr[k].localeCompare(stringTeacherNandSArray[h]) === 0;
                                    if (cond && !this.teacherFound.includes(teacherString)) {
                                        this.teacherFound.push(teacherString);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (condTeacherNandS) { /* TEXTBOX */
                this.searchTeacherNandS = this.stringToUpperCase(this.searchTeacherNandS);
                for (let i = 0; i < this.allTeachers.length; i++) { /* searching by watching all teacher */
                    let stringTeacherArray = this.allTeachers[i].split(" "); /* split the selected teacher's string */
                    let stringTeacherNandSArray = this.searchTeacherNandS.split(" "); /* split what the user wrote */
                    for (let j = 0; j < stringTeacherArray.length; j++) { /* searching by matching all */
                        for (let k = 0; k < stringTeacherNandSArray.length; k++) {
                            let cond = stringTeacherNandSArray[k].localeCompare(stringTeacherArray[j]) === 0;
                            if (cond && !this.teacherFound.includes(this.allTeachers[i])) {
                                this.teacherFound.push(this.allTeachers[i]);
                            }
                        }
                    }
                }
            } else if (condCourse) {/* DROPDOWN */
                for (let i = 0; i < this.allCoursesWithTeachers.length; i++) {
                    let course = this.allCoursesWithTeachers[i].courseName;
                    if (course.localeCompare(this.selectedCourseFilter) === 0) {
                        for (let j = 0; j < this.allCoursesWithTeachers[i].teachers.length; j++) {
                            let teacher = this.allCoursesWithTeachers[i].teachers[j];
                            let teacherString = teacher.teacherName + " " + teacher.teacherSurname + " " + teacher.teacherId;
                            this.teacherFound.push(teacherString);

                        }
                    }
                }
            }
            if (this.teacherFound.length === 0) {
                this.teacherFound.push("Not Found!");
            }
        },
        searchCourseFunction: function () {
            this.courseFound = [];
            this.searchCourse = this.searchCourse.trim();
            let condCourseN = this.searchCourse.localeCompare("") !== 0;
            let condTeacher = this.selectedTeacherFilter.localeCompare("") !== 0;
            if (!condCourseN && !condTeacher) {
                this.courseFound = this.allCourses;
            } else if (condCourseN && condTeacher) {
                this.searchCourse = this.stringToUpperCase(this.searchCourse);
                for (let i = 0; i < this.allCoursesWithTeachers.length; i++) {
                    for (let j = 0; j < this.allCoursesWithTeachers[i].teachers.length; j++) {
                        let teacher = this.allCoursesWithTeachers[i].teachers[j];
                        let teacherString = teacher.teacherName + " " + teacher.teacherSurname + " " + teacher.teacherId;
                        if (this.selectedTeacherFilter.localeCompare(teacherString) === 0) {
                            let courseArr = this.allCoursesWithTeachers[i].courseName.split(" ");
                            for (let k = 0; k < courseArr.length; k++) {
                                let stringCourseNameArray = this.searchCourse.split(" ");
                                for (let h = 0; h < stringCourseNameArray.length; h++) {
                                    let cond = courseArr[k].localeCompare(stringCourseNameArray[h]) === 0;
                                    if (cond && !this.courseFound.includes(this.allCoursesWithTeachers[i].courseName)) {
                                        this.courseFound.push(this.allCoursesWithTeachers[i].courseName);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (condCourseN) { /* TEXTBOX */
                this.searchCourse = this.stringToUpperCase(this.searchCourse);
                for (let i = 0; i < this.allCourses.length; i++) {
                    let stringCourseArray = this.allCourses[i].split(" ");
                    let stringCourseNameArray = this.searchCourse.split(" ");
                    for (let j = 0; j < stringCourseArray.length; j++) {
                        for (let k = 0; k < stringCourseNameArray.length; k++) {
                            let cond = stringCourseNameArray[k].localeCompare(stringCourseArray[j]) === 0;
                            if (cond && !this.courseFound.includes(this.allCourses[i])) {
                                this.courseFound.push(this.allCourses[i]);
                            }
                        }
                    }
                }
            } else if (condTeacher) { /* DROPDOWN */
                for (let i = 0; i < this.allCoursesWithTeachers.length; i++) {
                    for (let j = 0; j < this.allCoursesWithTeachers[i].teachers.length; j++) {
                        let teacherString = this.allCoursesWithTeachers[i].teachers[j].teacherName + " " + this.allCoursesWithTeachers[i].teachers[j].teacherSurname + " " + this.allCoursesWithTeachers[i].teachers[j].teacherId;
                        if (this.selectedTeacherFilter.localeCompare(teacherString) === 0) {
                            let course = this.allCoursesWithTeachers[i].courseName;
                            if (!this.courseFound.includes(course))
                                this.courseFound.push(course);
                        }
                    }
                }
            }
            if (this.courseFound.length === 0) {
                this.courseFound.push("Not Found!");
            }
        },
        stringToUpperCase: function (str) {
            var splitStr = str.toLowerCase().split(' ');
            for (var i = 0; i < splitStr.length; i++) {
                // You do not need to check if i is larger than splitStr length, as your for does that for you
                // Assign it back to the array
                splitStr[i] = splitStr[i].charAt(0).toUpperCase() + splitStr[i].substring(1);
            }
            // Directly return the joined string
            return splitStr.join(' ');
        }
    },
    beforeMount() {
        this.onPageLoad();
    }
});
