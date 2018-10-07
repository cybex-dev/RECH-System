// container to save number of section in application form
var numSections = 0;
var indexSections = -1;

var quillInstances = {};
var quillTextAreas = {};
var quillNum = 0;

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
    return createElement("div", "", name, id, classlist);
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
    // Initializes wizard
    initWizard();
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

function downloadFile(data){
    handleDownload(data, applicationRoutes.controllers.ApplicationSystem.ApplicationHandler.download(), "Unable to download document. Please contact the Research and Ethics Committee if this issue persists.")
}

//Source: https://stackoverflow.com/questions/16086162/handle-file-download-from-ajax-post
function handleDownload(params, url, errorMessage, success) {
    let token = document.getElementsByName("csrfToken")[0].value;
    $.ajax({
        type: "POST",
        url: url.url,
        cache: false,
        contentType: 'application/json',
        headers: {
            'Csrf-Token': token
        },
        data: JSON.stringify(params),
        success: function(response, status, request) {
            var disp = request.getResponseHeader('Content-Disposition');
            if (disp && disp.search('attachment') !== -1) {
                window.open(response, "_blank", "", false)
                // var form = $('<form method="POST" action="' + url + '">');
                // $.each(params, function(k, v) {
                //     form.append($('<input type="hidden" name="' + k +
                //         '" value="' + v + '">'));
                // });
                // $('body').append(form);
                // form.submit();
            }
        },
        error: function (error) {
            alert(error + "\n" + errorMessage);
        }
    });
}

/**
 * Function to take <div class="popup"...> and create Model dialogs from them
 */
function createDocumentPopups() {
    if (document.querySelectorAll(".review-container").length === 0) {
        document.querySelectorAll(".popup").forEach(function (e) {
            // Hack fix for forEach loop not retaining the content of the element. e.parentNode == null at times.
            let element = document.getElementById(e.id);

            // Create copy of element content
            let copy = element.cloneNode(true);

            // Get element parent, where the popup will be placed
            let parent = element.parentElement;

            let name = copy.getAttribute("name");

            let divContainer = createElement("div", "", "", "", 'row');

            let inputFile = parent.querySelectorAll("input[type=file]")[0];

            // Create button to open popup window
            let btn = createButton("btnPopup_" + copy.id, "Add " + name, "button", ((inputFile.value === "") ? "Add " : "Modify ") + name, null, "nmu-button", "action-button", "action-alternative");

            btn.style.marginLeft = "20px";
            // Create text element to sow file name which has been uploaded or has been selected
            let textNode = createElement("label", "", "", copy.id + "_filename");
            textNode.style.marginLeft = "50px";
            textNode.style.fontWeight = "bold";

            textNode.style.display = "block";
            // Insert Heading
            let heading = createElement("h4", name, "", "");

            heading.style.marginLeft = "50px";
            // Add heading to container
            divContainer.appendChild(heading);
            divContainer.appendChild(btn);
            divContainer.appendChild(textNode);

            // Get download block and remove it
            let downloadBlock = element.querySelector("div .download");
            if (downloadBlock !== null) {

                // Insert Show Popup button before current element
                let downBlock = downloadBlock.cloneNode(true);
                divContainer.appendChild(downBlock);

                // Add download block after current parent
                downloadBlock.remove();
            }

            parent.insertBefore(divContainer, element);

            // Remove the element, i.e. all content from the form
            element.remove();
            // Create popup window
            createPopup(copy.id, name, copy, divContainer);
            document.getElementById(inputFile.id).addEventListener("change", function (event) {
                let inputFile = document.getElementById(event.target.id);
                let last = inputFile.value.substring(inputFile.value.lastIndexOf("\\") + 1);
                document.getElementById(textNode.id).innerHTML = "<b>File: </b>" + last;
            });

            // Set existing file name
            let last = inputFile.value.substring(inputFile.value.lastIndexOf("\\") + 1);
            textNode.innerHTML = "<b>Existing File: </b>" + inputFile.value.substr(last + 1);

            // Set onclick event
            document.getElementById("btnPopup_" + copy.id).onclick = function () {
                openPopup(copy.id, false);
            };
        });
    }

    // Set onDownload button click handlers
    document.querySelectorAll("button.downloadfile").forEach(function (button) {
        document.getElementById(button.id).onclick = function () {
            let data = {
                "application_id": $('#application_id').val(),
                "component_id": button.getAttribute("component")
            };
            downloadFile(data);
        }
    })
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
    document.querySelectorAll(".group > h3").forEach(e => e.classList.add("group-title", "collapsible"));

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
        let div = createDiv(p.id + "_container", "", "");
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
    document.querySelectorAll(".extension").forEach(function (el) {

        let element = document.getElementById(el.id);
        let parent = element.parentNode;

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
        let container = createDiv(input.id + "_container", "", "extension", "extension-container");

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
        parent.insertBefore(container, element);

        // Remove group placeholder
        element.remove();
    });

    // Add onclick section-groups showing and hiding all child groups
    document.querySelectorAll("div.section > h2").forEach(function (e) {

        let p = e.parentNode;

        // Duplicate heading
        let heading = e.cloneNode(true);

        // Remove element content from parent
        e.remove();

        // Get parent div i.e. div group and clone
        let sectionData = createDiv(p.id + "_data", "", "section-content");

        // Add all group-containers to section-data
        let count = p.children.length;
        for (let i = 0; i < count; i++) {
            sectionData.appendChild(p.children[i].cloneNode(true));
        }

        // Create div container
        let sectionContainer = createDiv(p.id + "_container", "", "section-container");

        // Add content to div
        sectionContainer.appendChild(heading);
        sectionContainer.appendChild(sectionData);

        // Add div to parent node extension/section
        p.parentNode.insertBefore(sectionContainer, p);

        // Remove group placeholder
        p.remove();
    });

    // Add onclick group-titles showing and hiding all child groups
    document.querySelectorAll("div.group-container > h3").forEach(function (e) {
        e.onclick = function () {
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
    document.querySelectorAll(".section-title.collapsible").forEach(function (e) {
        e.onclick = function () {
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
    document.querySelectorAll(".extension-header > input").forEach(function (e) {
        e.onclick = function () {
            // if (e.parentElement.nextElementSibling.style.display === "none") {
            if (e.checked) {
                e.parentElement.classList.add("extension-active");
                e.parentElement.nextElementSibling.style.display = "block"
            } else {
                e.parentElement.classList.remove("extension-active");
                e.parentElement.nextElementSibling.style.display = "none"
            }
        };
        e.classList.remove("extension-active");
        e.parentElement.nextElementSibling.style.display = "none";

        // if (e.parentElement.nextElementSibling.style.display === "none") {
        if (e.checked) {
            e.parentElement.classList.add("extension-active");
            e.parentElement.nextElementSibling.style.display = "block"
        } else {
            e.parentElement.classList.remove("extension-active");
            e.parentElement.nextElementSibling.style.display = "none"
        }
    });

    // Add section-heading dashed line
    if (document.querySelectorAll(".section-container .section-title")[0] !== null)
        document.querySelectorAll(".section-container .section-title")[0].style.borderTop = "1px dashed #FFFFFF";

    // Add document popups
    createDocumentPopups();

    //Create 2 div containers. Outer container is the new 'textarea' container and inner is the editor itself
    function createTextAreaEditors() {
        let isReviewingFeedback = document.querySelectorAll(".review-container").length !== 0;
        document.querySelectorAll("textarea").forEach(value => {
            let id = value.id;
            let name = value.getAttribute("name");
            let placeholder = value.getAttribute("placeholder");

            let editorDiv = createDiv(id, name, "");
            editorDiv.style.minHeight = "10em";
            editorDiv.id = id + "_editor";

            let outerEditorContainerId = id + "_outer";
            let outerEditorContainer = createDiv(outerEditorContainerId, "", "");
            outerEditorContainer.style.marginLeft = "5%";
            outerEditorContainer.style.marginRight = "5%";
            outerEditorContainer.style.marginTop = "20px";

            outerEditorContainer.appendChild(editorDiv);
            value.parentElement.insertBefore(outerEditorContainer, value);
            value.style.display = "none";

            let isGiveFeedbackTime = value.getAttribute("name").includes("feedback_");

            var quill = null;
            if (isReviewingFeedback) {
                quill = new Quill('#' + editorDiv.id, {
                    modules: {
                    },
                    placeholder: "",
                    theme: 'bubble'  // or 'bubble'
                });
                quill.enable(false);
            } else {
                if (isGiveFeedbackTime) {
                    quill = new Quill('#' + editorDiv.id, {
                        modules: {
                            toolbar: [
                                [{ header: [1, 2, false] }],
                                ['bold', 'italic', 'underline']
                            ]
                        },
                        placeholder: "",
                        theme: 'snow'  // or 'bubble'
                    });
                } else {
                    quill = new Quill('#' + editorDiv.id, {
                        modules: {
                            toolbar: [
                                [{ header: [1, 2, false] }],
                                ['bold', 'italic', 'underline']
                            ]
                        },
                        placeholder: placeholder.replace("_", " "),
                        theme: 'snow'  // or 'bubble'
                    });
                }
                quill.enable(true);
            }


            let content = value.value;
            if (content !== null && content !== "") {
                let jsonData = JSON.parse(content);
                quill.setContents(jsonData);
            }

            quillInstances[quillNum] = quill;
            quillTextAreas[quillNum] = value.id;
            quillNum++;

            outerEditorContainer.firstElementChild.style.maxHeight = "5em";
        })
    }

    createTextAreaEditors();

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
    addSectionStatus();
    addGroupStatus();

    // Completes the application form by getting the db-mapping elements from the form and filling in the application
    // getDataFromServer();

    // Check if application has an ID, if so, remove the fields that form part of the primary key
    let existingApp = document.getElementById("application_id");
    if (existingApp !== null) {
        if (existingApp.value !== "") {
            document.getElementById("app_faculty").disabled = true;
            document.getElementById("app_faculty").style.cursor = "not-allowed";
            document.getElementById("app_department").disabled = true;
            document.getElementById("app_department").style.cursor = "not-allowed";
        }
    }
}

function addSectionStatus() {
    document.querySelectorAll(".section-container .section-title").forEach(value => {
        //get section id and add _check to it
        let id = value.id + "_check";

        // Create checkbox
        let check = document.createElement("i");
        check.id = id;
        check.name = id;
        check.classList.add("fa", "fa-ellipsis-h", "section-checkbox");
        value.appendChild(check);
    })
}

function addGroupStatus() {
    document.querySelectorAll(".group-container .group-title").forEach(value => {
        //get section id and add _check to it
        let id = value.id + "_check";

        // Create checkbox
        let check = document.createElement("i");
        check.id = id;
        check.name = id;
        check.classList.add("fa", "fa-ellipsis-h", "section-checkbox");
        value.appendChild(check);
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

// function setExtensionHooks() {
//     document.querySelectorAll(".extension > input[type=checkbox]").forEach(setAdaptiveHook);
// }

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