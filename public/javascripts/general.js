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
                if (value.getAttribute("href") !== null) {
                    appForm.action = value.getAttribute("href");
                    appForm.method = "POST";

                    formSubmitHandler();

                    appForm.submit();
                }
            };

            if (value.parentNode.tagName === "A") {
                value.setAttribute("href", value.parentNode.getAttribute("href"));
                value.parentElement.removeAttribute("href");
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