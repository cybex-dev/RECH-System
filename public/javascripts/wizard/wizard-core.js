// container to save number of section in application form
var numSections = 0;
var indexSections = -1;

function hidePopup(id) {
    document.getElementById(id).style.display = 'none';
}

function openPopup(id, isModal) {
    let modal = document.getElementById(id);
    if (modal !== null) {

        // Display popup
        modal.style.display = 'block';

        // Add event handler for click anywhere else on screen
        if (isModal) {
            window.onclick = function (event) {
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            };
        }
    }
}

function createElement(type, value, name, id, ...classList) {
    let element = document.createElement(type);
    if (classList.length !== 0)
        if (classList[0] instanceof Array) {
            element.className = classList[0].join(" ");
        } else {
            element.className = classList[0];
        }
    element.name = name.trim() || "";
    element.id = id.trim() || "";
    element.innerText = value || "";
    return element;
}

function createButton(id, name, type, value, callback, ...classlist) {
    let button = createElement("button", value, name, id, classlist);
    button.type = type;
    button.onclick = callback;
    return button;
}

function createDiv(id, name, ...classlist) {
    return createElement("div", "", id, name, classlist);
}

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

_docReady(docReady());

function findParentGroup(input) {

}

// TODO
function reaccess(parentGroup) {
    // parentGroup.querySelectorAll("input").forEach(function (input) {
    //
    // })
}

function assignInputListeners() {
    let allInputs = document.querySelectorAll("input");
    allInputs.forEach(function (input) {
        let parentGroup = findParentGroup(input);
        reaccess(parentGroup);
    })
}

function docReady() {

    // // Sets number of section sections
    // var sectionList = document.querySelectorAll(".section");
    // numSections = sectionList.length;
    // sectionList.forEach(function (e) {
    //     setHidden(e);
    // });

    // Initializes wizard
    initWizard();

    // // TODO Fix
    // document.querySelectorAll(".group > h3").forEach(e => {
    //     e.click();
    //     e.click();
    // })
    // ;

    assignInputListeners();
}


// hides an element
function setHidden(element) {
    if (element == null)
        return;
    element.classList.remove("visible");
    element.classList.add("hidden");
}

// makes an element visible
function setVisible(element) {
    if (element == null)
        return;
    element.classList.remove("hidden");
    element.classList.add("visible");
}

// hides an element
function hide(element) {
    try {
        if (element == null)
            return;
        element.style.display = "none"
    } catch (e) {
    }
}

// makes an element visible
function show(element) {
    try {
        if (element == null)
            return;
        if (element.nodeType === 1) {
            if (element.tagName === "IC" ||
                element.tagName === "LABEL")
                element.style.display = "inline-block";
            else {
                if (element.tagName === "INPUT") {
                    if (element.type === "checkbox") {
                        element.style.display = "inline-block";
                    } else {
                        element.style.display = "block";
                    }
                } else {
                    if (child.tagName !== "DATALIST")
                        element.style.display = "block";
                }
            }
        }
    } catch (e) {
    }
}

/**
 * Function to take <div class="popup"...> and create Model dialogs from them
 */
function createDocumentPopups() {
    document.querySelectorAll(".popup").forEach(function (e) {
        // Hack fix for forEach loop not retaining the content of the element. e.parentNode == null at times.
        let element = document.getElementById(e.id);

        // Create copy of element content
        let copy = element.cloneNode(true);

        // Get element parent, where the popup will be placed
        let parent = element.parentElement;

        let name = copy.getAttribute("name");

        let divContainer = createElement("div", "", "", "", 'row');

        // Create button to open popup window
        let btn = createButton("btnPopup_" + copy.id, "Add " + name, "button", "Add " + name, null, "nmu-button", "action-button", "action-alternative");
        btn.style.marginLeft = "20px";

        let textNode = createElement("p", "", "", copy.id + "_filename");
        textNode.style.marginLeft = "50px";
        textNode.style.fontWeight = "bold";

        // Insert Heading
        let heading = createElement("h4", name, "", "");
        heading.style.marginLeft = "50px";

        // Add heading to container
        divContainer.appendChild(heading);
        divContainer.appendChild(btn);
        divContainer.appendChild(textNode);
        divContainer.appendChild(document.createElement("BR"));

        // Insert Show Popup button before current element
        parent.insertBefore(divContainer, element);

        // Remove the element, i.e. all content from the form
        element.remove();

        // Create popup window
        createPopup(copy.id, name, copy, divContainer);
        let inputFile = parent.querySelectorAll("input[type=file]")[0];
        inputFile.onchange = function(){
            // let filename = val.split('\\').pop().split('/').pop();
            // filename = filename.substring(0, filename.lastIndexOf('.'));
            textNode.innerText = inputFile.value;
        };

        // Set onclick event
        document.getElementById("btnPopup_" + copy.id).onclick = function () {
            openPopup(copy.id, true);
        };

        // Set onDownload button click handlers
        document.querySelectorAll("button .downloadfile").forEach(function(button) {
            button.onclick = function () {
                let data = {
                    "application_id": $('#application_id').val(),
                    "new_password": $('#new_password').val(),
                    "confirm_password": $('#confirm_password').val()
                };
                sendRequest(btnPassword, data, userRoutes.controllers.UserSystem.ProfileHandler.getDocument(), "Unable to update password, your old password is still active. Please contact the Research and Ethics Committee if this issue persists.", function (data) {
                    alert("Password updated");
                })
            }
        })
    });
}

function createCollapsibleGroupHeadings() {
    document.querySelectorAll(".section .group").forEach(function (e) {
        e.firstChild.onclick = function () {
            document.querySelectorAll("#" + e.id)[0].childNodes.forEach(function (child) {
                if (child.tagName !== "H3" &&
                    child.tagName !== "DATALIST" &&
                    child.nodeType === Node.ELEMENT_NODE) {
                    if (!child.classList.contains("modal")) {
                        if (child.tagName === "IC" ||
                            child.tagName === "LABEL") {
                            child.style.display = (child.style.display === "none" || child.style.display === "") ? "inline-block" : "none";
                        } else {
                            child.style.display = (child.style.display === "none" || child.style.display === "") ? "block" : "none";
                        }
                    }
                }

            })
        }
    });

}

function createCollapsibleSectionHeadings() {
    document.querySelectorAll(".section").forEach(function (e) {
        e.firstChild.onclick = function () {
            document.querySelectorAll("#" + e.id + ".section .group").forEach(function (child) {
                child.style.display = (child.style.display === "none" || child.style.display === "") ? "block" : "none";
            })
        }
    });
}

// initialize Wizard
function initWizard() {

    document.querySelectorAll(".section > h2").forEach(e => e.classList.add("section-title", "collapsible"));

    // Add all groups (which are decendants of section) to inherit collapsible-child allowing section to collapse groups
    document.querySelectorAll(".group > h3").forEach(e => e.classList.add("group-title","collapsible"));

    // Add groups for each h3 within a group div
    document.querySelectorAll(".group > h3").forEach(function (element) {

        let p = element.parentNode;

        // Duplicate heading
        let heading = element.cloneNode(true);

        // Remove element content from parent
        element.remove();

        // Create extension data div
        let data = createDiv("", "", "group-data");

        //Add the rest of all extension group children to extension-data container
        let count = p.children.length;
        for (let i = 0; i < count; i++) {
            data.appendChild(p.children[i].cloneNode(true));
        }

        // Create div container
        let div = createDiv(parent.id + "_container", "", "");
        div.classList.add("group-container");

        // Add content to div
        div.appendChild(heading);
        div.appendChild(data);

        // Add div to parent node extension/section
        p.parentNode.insertBefore(div, p);

        // Remove group placeholder
        p.remove();
    });

    // Add extension groups for each extension div
    document.querySelectorAll(".extension").forEach(function (element) {

        //Get first three children.
        let label = element.firstElementChild;
        let ic = label.nextElementSibling;
        let input = ic.nextElementSibling;

        // Deep clone the header children
        let headerLabel = label.cloneNode(true);
        let headerIC = ic.cloneNode(true);
        let headerInput = input.cloneNode(true);

        // Remove the children from the extension
        label.remove();
        ic.remove();
        input.remove();

        //create extension-container div containing header and data
        let container = createDiv(parent.id + "_container", "", "extension", "extension-container");

        //create extension-header div containing header
        let header = createDiv("", "", "extension-header");

        // Add header children
        header.appendChild(headerLabel);
        header.appendChild(headerIC);
        header.appendChild(headerInput);

        // Create extension data div
        let data = createDiv("", "", "extension-data");

        //Add the rest of all extension group children to extension-data container
        let count = element.children.length;
        for (let i = 0; i < count; i++) {
            data.appendChild(element.children[i].cloneNode(true));
        }

        // Add content to div
        container.appendChild(header);
        container.appendChild(data);

        // Add div to parent node extension/section
        element.parentNode.insertBefore(container, element);

        // Remove group placeholder
        element.remove();
    });

    // Add onclick section-groups showing and hiding all child groups
    document.querySelectorAll("div.section > h2").forEach(function(e) {

        let p = e.parentNode;

        // Duplicate heading
        let heading = e.cloneNode(true);

        // Remove element content from parent
        e.remove();

        // Get parent div i.e. div group and clone
        let sectionData = createDiv(parent.id + "_data", "", "section-content");

        // Add all group-containers to section-data
        let count = p.children.length;
        for (let i = 0; i < count; i++) {
            sectionData.appendChild(p.children[i].cloneNode(true));
        }

        // Create div container
        let sectionContainer = createDiv(parent.id + "_container", "", "section-container");

        // Add content to div
        sectionContainer.appendChild(heading);
        sectionContainer.appendChild(sectionData);

        // Add div to parent node extension/section
        p.parentNode.insertBefore(sectionContainer, p);

        // Remove group placeholder
        p.remove();
    });

    // Add onclick group-titles showing and hiding all child groups
    document.querySelectorAll("div.group-container > h3").forEach(function(e) {
        e.onclick = function() {
            if (e.nextSibling.style.display === "none") {
                e.nextSibling.style.display = "block";
                e.classList.add("group-active");
                e.parentElement.parentElement.parentElement.firstElementChild.classList.add("section-active")
            } else {
                e.nextSibling.style.display = "none";
                e.classList.remove("group-active");
                e.parentElement.parentElement.parentElement.firstElementChild.classList.remove("section-active")
            }
        };
        e.nextSibling.style.display = "none";
    });

    // Add onclick section-titles showing and hiding group data
    document.querySelectorAll(".section-title.collapsible").forEach(function(e) {
        e.onclick = function() {
            if (e.nextSibling.style.display === "none") {
                e.nextSibling.style.display = "block";
                e.classList.add("section-active");
            } else {
                e.nextSibling.style.display = "none";
                e.classList.remove("section-active");
            }
        };
        e.nextSibling.style.display = "none";
    });

    // Add extension-header input hooks to show and hide extension-data containers
    document.querySelectorAll(".extension-header > input").forEach(function(e) {
        e.onclick = function() {
            if (e.parentElement.nextElementSibling.style.display === "none") {
                e.parentElement.classList.add("extension-active");
                e.parentElement.nextElementSibling.style.display = "block"
            } else {
                e.parentElement.classList.remove("extension-active");
                e.parentElement.nextElementSibling.style.display = "none"
            }
        };
        e.classList.remove("extension-active");
        e.parentElement.nextElementSibling.style.display = "none";
    });

    // Add document popups
    createDocumentPopups();

    function addCollapsibleGroups() {
        // Add collapsible groups
        document.querySelectorAll(".group > *").forEach(function (e) {
            if (e.tagName !== "BR") {
                e.classList.add("collapsible-child")
            };
        });

        // Add collabsible group heading
        document.querySelectorAll(".group > h3").forEach(function (e) {
                e.classList.add("group-title", "collapsible",);
                e.classList.remove("collapsible-child");
            }
        );

        document.querySelectorAll(".group .group").forEach(e => e.classList.add("collapsible-child")
        )
        ;

        // Add all groups (which are decendants of section) to inherit collapsible-child allowing section to collapse groups
        // document.querySelectorAll(".group").forEach(e => e.childNodes.forEach(ee => {
        //     try {
        //         if(ee.nodeName !== "H3" && ee.nodeType === 1) {
        //             ee.classList.add("collapsible-child")
        //         }
        //     } catch (e) {
        //         console.log(e);
        //     }
        // }));
    }

    // Link each section heading to group collapbsible, adding an onclick event which collapses or expands a section when clicked
    // createCollapsibleSectionHeadings();

    // Link each group heading to subcomponents collapbsible, adding an onclick event which collapses or expands a group when clicked
    // createCollapsibleGroupHeadings();

    // let list = document.querySelectorAll(".section");
    // list.item(list.length - 1).style.border = "0";

    // Add collapsible functionality to wizard sections
    //addCollapsibleSections();

    // Add collapsible functionality to wizard groups (this includes document groups)
    //addCollapsibleGroups();


    // // Deprecated
    // addDocumentOverview();
    //
    // // Deprecated
    // addApplicationOverview();

    // Set hooks to expand an extension when checked
    // setExtensionHooks();

    // Adds checkboxes to right side of section indicating if a section is completed or not, etc.
    // addSectionStatus();

    // addGroupStatus();

    // Completes the application form by getting the db-mapping elements from the form and filling in the application
    // getDataFromServer();

    // Check if application has an ID, if so, remove the fields that form part of the primary key
    if(document.getElementById("application_id").value !== "") {
        document.getElementById("app_faculty").disabled = true;
        document.getElementById("app_faculty").style.cursor = "not-allowed";
        document.getElementById("app_department").disabled = true;
        document.getElementById("app_department").style.cursor = "not-allowed";
    }
}

function addSectionStatus() {
    document.querySelectorAll(".section").forEach(value => {
        //get section id and add _check to it
        let id = value.id + "_check";

        // Create checkbox
        let check = document.createElement("i");
        check.id = id;
        check.name = id;
        check.classList.add("fa", "fa-ellipsis-h", "section-checkbox");
        value.firstElementChild.appendChild(check);
    })
}

function addGroupStatus() {
    document.querySelectorAll(".section .group").forEach(value => {
        //get section id and add _check to it
        let id = value.id + "_check";

        // Create checkbox
        let check = document.createElement("i");
        check.id = id;
        check.name = id;
        check.classList.add("fa", "fa-ellipsis-h", "section-checkbox");
        value.firstElementChild.appendChild(check);
    })
}

function setAdaptiveHook(element) {
    var parent = element.parentNode;
    element.onclick = function () {
        if (element.checked === false) {
            parent.childNodes.forEach(hide);
            show(element);
            show(element.previousElementSibling);
            show(element.previousElementSibling.previousElementSibling);
        } else {
            parent.childNodes.forEach(show);
        }
    };
    parent.childNodes.forEach(hide);
    show(element);
    show(element.previousElementSibling);
    show(element.previousElementSibling.previousElementSibling);
}

function setExtensionHooks() {
    document.querySelectorAll(".extension > input[type=checkbox]").forEach(setAdaptiveHook);
}

function createLists(listDiv) {
    document.querySelectorAll("#listDiv").forEach(function (div) {
        div.classList.add("")
    })
}

function createPopup(id, title, dialogContent, position) {
    let final = "<div id=\"" + id + "\" class=\"modal\" style=\"display: none;\"><div class=\"modal-content animate nmu-theme-darkbackground\"><div class=\"popup-header\"><span onclick=\"hidePopup('" + id + "')\" class=\"close\" title=\"Close PopUp\">×</span><h2 style=\"color: #fff;\">" + title + "</h2></div><div class=\"lessen-top-margin\" style=\"padding-top: 20px; background-color: #FFF;\" id=\"" + id + "_content" + "\"></div><div class=\"\" style=\"background-color: #061c2c;padding: 10px 20px;\"><button class=\"nmu-button action-button\" type=\"button\" style=\"min-width: 10em !important;\" onclick=\"hidePopup('" + id + "')\">Close</button></div></div></div>";
    position.innerHTML += final;
    document.getElementById(id + "_content").appendChild(dialogContent)
}



/**

 // Set section h2 title hover effect, background and collapsible state
 document.querySelectorAll(".section > h2").forEach(e => e.classList.add("section-title", "collapsible"));

 // Set group h3 title hover effect, background and collapsible state
 document.querySelectorAll(".group > h3").forEach(e => e.classList.add("group-title","collapsible"));


 */