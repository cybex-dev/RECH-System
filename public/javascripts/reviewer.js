var numReviewers = 0;

_docReady(docReady());

function docReady() {
    let button = document.getElementById("btnAddReviewer");
    button.onclick = function () {
        if (document.querySelectorAll("reviewer").length >= 4){
            button.style.display = "none"
        } else {
            button.style.display = "inline-block";
            numReviewers++;
            document.getElementById("reviewer" + numReviewers).style.display = "block";
            document.getElementById("reviewer" + numReviewers).name = "reviewer" + numReviewers;
            document.getElementById("del_reviewer" + numReviewers).onclick = function () {
                numReviewers--;
                document.getElementById("reviewer" + numReviewers).name = "";
                document.getElementById("reviewer" + numReviewers).style.display = "none";
            };
        }
    }
}
