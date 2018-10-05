function general() {
    // Hook for alert dialog to close - at this time, it does not auto-close
    document.querySelectorAll(".alert span.close").forEach(value => {
        value.onclick = function () {
            value.parentNode.style.display = "none";
        }
    })
}

function addButtonPost() {
    document.querySelectorAll(".form-button").forEach(value => {
        let appForm = document.getElementById("application_form");
        if (appForm !== null) {
            value.onclick = function (event) {

                if (value.getAttribute("method") === "GET") {
                    window.location.href = value.getAttribute("href");
                } else {
                    if (value.getAttribute("href") !== null) {
                        appForm.action = value.getAttribute("href");
                        appForm.method = value.getAttribute("method");
                        appForm.enctype = value.getAttribute("enctype");

                        formSubmitHandler();

                        appForm.submit();
                    }
                }
            };

            if (value.parentNode.tagName === "A") {
                value.setAttribute("href", value.parentNode.getAttribute("href"));
                value.setAttribute("method", value.parentNode.getAttribute("method"));
                value.setAttribute("enctype", value.parentNode.getAttribute("enctype"));
                value.parentElement.removeAttribute("href");
                value.parentElement.removeAttribute("method");
                value.parentElement.removeAttribute("enctype");
            }
        }
    })
}

function formSubmitHandler(){
    if (quillNum > 0) {
        for (let i = 0; i < quillNum; i++) {
            let quill = quillInstances[i];
            let value = document.getElementById(quillTextAreas[i]);

            let contents = quill.getContents();
            let json = JSON.stringify(contents);
            value.value = json;
        }
    }
}

addButtonPost();
general();