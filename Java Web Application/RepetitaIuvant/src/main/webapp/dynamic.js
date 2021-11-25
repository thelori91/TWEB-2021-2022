function add3UpcomingEvents() {
    for (let i = 0; i < 3; i++) {
        createUpcomingEvent(i);
    }
}

function createUpcomingEvent(i) {
    let create = document.getElementById("upcomingEvents");
    let row = document.createElement("div");
    row.className = "row";
    row.id = "row" + i;
    create.appendChild(row);
    let rowW = document.getElementById("row" + i);

    let col0 = document.createElement("div");
    col0.className = "col col-md-1";
    col0.innerHTML = "<img  id='puntinaSize' src='Puntina.png' alt='Puntina'>";
    let col1 = document.createElement("div");
    col1.className = "col col-lg-2";
    col1.innerHTML = "<p>Lesson" + (i + 1) + ":</p>";
    let col2 = document.createElement("div");
    col2.className = "col col-lg-2";
    col2.innerHTML = "<p>Teacher</p>";
    let col3 = document.createElement("div");
    col3.className = "col col-lg-2";
    col3.innerHTML = "<p>dd/mm/yyyy</p>";
    let col4 = document.createElement("div");
    col4.className = "col col-lg-2";
    col4.innerHTML = "<p>hh.mm</p>";
    rowW.appendChild(col0);
    rowW.appendChild(col1);
    rowW.appendChild(col2);
    rowW.appendChild(col3);
    rowW.appendChild(col4);
}
