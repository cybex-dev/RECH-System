function addButtonPost() {
    document.querySelectorAll(".form-button").forEach(value => {
        let appForm = document.getElementById("application_form");
        if (appForm !== null) {
            value.onclick = function (event) {
                if (value.getAttribute("href") !== null) {
                    appForm.action = value.getAttribute("href");
                    appForm.submit();
                }
                event.preventDefault();
            };

            if (value.parentNode.tagName === "A") {
                value.setAttribute("href", value.parentNode.getAttribute("href"));
                value.parentNode.onclick = function (event) {
                    event.preventDefault();
                }
            }
        }
    })
}

addButtonPost();