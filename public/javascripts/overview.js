docReady();

function docReady() {
    let first = false;
    if (document.getElementsByClassName("overview") == null)
        return;
    document.querySelectorAll("h3.overview.overview-title").forEach(function(e) {
        e.onclick = function () {
            let next = this.nextElementSibling;
            if(next.style.display === "none") {
                showOverview(next, this);
            } else {
                hideOverview(next, this)
            }
        };
        hideOverview(e.nextElementSibling, e);
        if (!first) {
            first = true;
            showOverview(e.nextElementSibling, e);
        }

    })

    // Add alll applications search hook
    document.getElementById("application_searchbox").addEventListener("change", function () {
        let searchText = document.getElementById("application_searchbox").value;
        search(searchText, "tbl_all_applications")
    });
    document.getElementById("application_searchbox").addEventListener("input", function () {
        let searchText = document.getElementById("application_searchbox").value;
        search(searchText, "tbl_all_applications")
    });
}

function hideOverview(e, title){
    e.style.display = "none";
    title.firstElementChild.classList.remove("fa-angle-down");
    title.firstElementChild.classList.add("fa-angle-right");
}

function showOverview(e, title) {
    e.style.display = "block";
    title.firstElementChild.classList.remove("fa-angle-right");
    title.firstElementChild.classList.add("fa-angle-down");
}

function search(input, tableId){
    // Declare variables
    var filter, tr, td, i;
    filter = input.value.toUpperCase();
    let table = document.getElementById(tableId);
    tr = table.getElementsByTagName("tr");

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}