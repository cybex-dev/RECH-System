// Runs functions when document has loaded
function _docReady(funcName, baseObj) {

    // SOURCE: https://github.com/jfriend00/docReady

    // The public function name defaults to window.docReady
    // but you can pass in your own object and own function name and those will be used
    // if you want to put them in a different namespace
    funcName = funcName || "docReady";
    baseObj = baseObj || window;
    var readyList = [];
    var readyFired = false;
    var readyEventHandlersInstalled = false;

    // call this when the document is ready
    // this function protects itself against being called more than once
    function ready() {
        if (!readyFired) {
            // this must be set to true before we start calling callbacks
            readyFired = true;
            for (var i = 0; i < readyList.length; i++) {
                // if a callback here happens to add new ready handlers,
                // the docReady() function will see that it already fired
                // and will schedule the callback to run right after
                // this event loop finishes so all handlers will still execute
                // in order and no new ones will be added to the readyList
                // while we are processing the list
                readyList[i].fn.call(window, readyList[i].ctx);
            }
            // allow any closures held by these functions to free
            readyList = [];
        }
    }

    function readyStateChange() {
        if (document.readyState === "complete") {
            ready();
        }
    }

    // This is the one public interface
    // docReady(fn, context);
    // the context argument is optional - if present, it will be passed
    // as an argument to the callback
    baseObj[funcName] = function (callback, context) {
        if (typeof callback !== "function") {
            throw new TypeError("callback for docReady(fn) must be a function");
        }
        // if ready has already fired, then just schedule the callback
        // to fire asynchronously, but right away
        if (readyFired) {
            setTimeout(function () {
                callback(context);
            }, 1);
            return;
        } else {
            // add the function and context to the list
            readyList.push({fn: callback, ctx: context});
        }
        // if document already ready to go, schedule the ready function to run
        if (document.readyState === "complete") {
            setTimeout(ready, 1);
        } else if (!readyEventHandlersInstalled) {
            // otherwise if we don't have event handlers installed, install them
            if (document.addEventListener) {
                // first choice is DOMContentLoaded event
                document.addEventListener("DOMContentLoaded", ready, false);
                // backup is window load event
                window.addEventListener("load", ready, false);
            } else {
                // must be IE
                document.attachEvent("onreadystatechange", readyStateChange);
                window.attachEvent("onload", ready);
            }
            readyEventHandlersInstalled = true;
        }
    }
}

function docReady() {
    // Add enrol AJAX request and callback
    let btnEnrol = document.getElementById("btnEnrol");
    if (btnEnrol !== null) {
        btnEnrol.onclick = function () {
            let data = {
                "enrol_type": $('#enrol_type').val(),
                "enrol_code": $('#enrol_code').val()
            };
            sendRequest(btnEnrol, data, userRoutes.controllers.UserSystem.ProfileHandler.doEnrol(), "Unable to enrol you for the selected position. Please contact the Research and Ethics Committee if this issue persists.", function (data) {
                alert("Enrolled");
            })
        };
    }

    // Add basic information update AJAX request and callback
    let btnBasic = document.getElementById("btnUpdateBasic");
    if (btnBasic !== null) {
        btnBasic.onclick = function () {
            let data = {
                "title": $('#title').val(),
                "firstname": $('#firstname').val(),
                "lastname": $('#lastname').val(),
                "mobile": $('#mobile').val(),
                "degree": $('#degree').val(),
                "faculty": $('#faculty').val(),
                "department": $('#department').val()
            };
            sendRequest(btnBasic, data, userRoutes.controllers.UserSystem.ProfileHandler.updateBasicInfo(), "Unable to update your personal information. Please contact the Research and Ethics Committee if this issue persists.", function (data) {
                alert("Updated personal information");
            })
        };
    }

    // Add academic information update AJAX request and callback
    let btnAcademic = document.getElementById("btnUpdateAcademic");
    if (btnAcademic !== null) {
        btnAcademic.onclick = function () {
            let data = {
                "campus": $('#campus').val(),
                "telephone": $('#telephone').val(),
                "address": $('#address').val()
            };
            sendRequest(btnAcademic, data, userRoutes.controllers.UserSystem.ProfileHandler.updateAcademicInfo(), "Unable to update your academic information. Please contact the Research and Ethics Committee if this issue persists.", function (data) {
                alert("Updated academic information");
            })
        };
    }

    // Add update password AJAX request and callback
    let btnPassword = document.getElementById("btnChangePassword");
    if (btnPassword !== null) {
        btnPassword.onclick = function () {
            let data = {
                "old_password": $('#old_password').val(),
                "new_password": $('#new_password').val(),
                "confirm_password": $('#confirm_password').val()
            };
            sendRequest(btnPassword, data, userRoutes.controllers.UserSystem.ProfileHandler.updatePassword(), "Unable to update password, your old password is still active. Please contact the Research and Ethics Committee if this issue persists.", function (data) {
                alert("Password updated");
            })
        };
    }
}

function setButtonBusy(senderId, isBusy) {
    if (isBusy) {
        senderId.classList.add("busy");
    } else {
        senderId.classList.remove("busy");
    }
}

function sendRequest(sender, data, requestUrl, err_message, callback) {
    setButtonBusy(sender, true);
    sender.disabled = true;
    let token = document.getElementsByName("csrfToken")[0].value;
    $.ajax({
        url: requestUrl.url,
        cache: false,
        contentType: 'application/json',
        method: 'POST',
        headers: {
            'Csrf-Token': token
        },
        data: JSON.stringify(data),
    }).done(function (data) {
        callback(data);
        window.location.reload();
    }).error(function (error) {
        console.log(error);
        window.location.reload();
    });
}

docReady();

