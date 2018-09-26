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

function createElement(type, value, name, id,
                       ...
                           classList
) {
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

function createButton(id, name, type, value, callback,
                      ...
                          classlist
) {
    let button = createElement("button", value, name, id, classlist);
    button.type = type;
    button.onclick = callback;
    return button;
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

    // TODO Fix
    document.querySelectorAll(".group > h3").forEach(e => {
        e.click();
        e.click();
    })
    ;

    assignInputListeners();
}

// - sets the 'state' to visible
// - increments the index
// - sets the new index to active

function nextSection() {
    // Set first section active
    var nodelistSections = document.querySelectorAll(".section");
    setHidden(nodelistSections[indexSections]);
    setVisible(nodelistSections[++indexSections]);
}

// - sets the 'state' to hidden
// - decrements the index
// - sets the new index to active

function previousSection() {
    // Set first section active
    var nodelistSections = document.querySelectorAll(".section");
    setHidden(nodelistSections[indexSections]);
    setVisible(nodelistSections[--indexSections]);
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

        // Insert Heading
        let heading = createElement("h4", name, "", "");
        heading.style.marginLeft = "50px";

        // Add heading to container
        divContainer.appendChild(heading);
        divContainer.appendChild(btn);
        divContainer.appendChild(document.createElement("BR"));

        // Insert Show Popup button before current element
        parent.insertBefore(divContainer, element);

        // Remove the element, i.e. all content from the form
        element.remove();

        // Create popup window
        createPopup(copy.id, name, copy, divContainer);

        // Set onclick event
        document.getElementById("btnPopup_" + copy.id).onclick = function () {
            openPopup(copy.id, true);
        };
    });
}

// initialize Wizard
function initWizard() {

    // Add document popups
    createDocumentPopups();

    //Add collapsible to each section header and collapsible-child to each of the children
    function addCollapsibleSections() {
        document.querySelectorAll(".section").forEach(e => e.firstChild.classList.add("section-title", "collapsible")
        )
        ;

        // Add all groups (which are decendants of section) to inherit collapsible-child allowing section to collapse groups
        document.querySelectorAll(".section .group").forEach(e => e.classList.add("collapsible-child")
        )
        ;
    }

    function addCollapsibleGroups() {
        // Add collapsible groups
        document.querySelectorAll(".group > *").forEach(function (e) {
            if (e.tagName !== "BR") {
                e.classList.add("collapsible-child")
            }
            ;
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
    document.querySelectorAll(".section").forEach(function (e) {
        e.firstChild.onclick = function () {
            document.querySelectorAll("#" + e.id + ".section .group").forEach(function (child) {
                child.style.display = (child.style.display === "none" || child.style.display === "") ? "block" : "none";
            })
        }
    });

    // Link each group heading to subcomponents collapbsible, adding an onclick event which collapses or expands a group when clicked
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

    let list = document.querySelectorAll(".section");
    list.item(list.length - 1).style.border = "0";

    // Add collapsible functionality to wizard sections
    addCollapsibleSections();

    // Add collapsible functionality to wizard groups (this includes document groups)
    addCollapsibleGroups();


    // // Deprecated
    // addDocumentOverview();
    //
    // // Deprecated
    // addApplicationOverview();

    // Set hooks to expand an extension when checked
    setExtensionHooks();

    // Adds checkboxes to right side of section indicating if a section is completed or not, etc.
    addSectionStatus();

    addGroupStatus();

    // Completes the application form by getting the db-mapping elements from the form and filling in the application
    // getDataFromServer();
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