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