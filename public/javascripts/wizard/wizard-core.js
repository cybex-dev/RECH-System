// container to save number of section in application form
var numSections = 0;
var indexSections = -1;

var numQuestions = 0;
var indexQuestions = -1;

var risk = 0;


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

function checkRisk() {
    var r = 0;
    document.querySelectorAll(".condition").forEach(function (element) {
        if (element.checked) {
            var v = element.attributes.getNamedItem("risk");
            switch (v.nodeValue) {
                case "low" : {
                    if (r < 1)
                        r = 1;
                    break;
                }

                case "medium" : {
                    if (r < 2)
                        r = 2;
                    break;
                }

                case "high" : {
                    if (r < 3)
                        r = 3;
                    break;
                }
            }
        }
    });
    risk = r;

    switch (risk) {
        case 0: {
            document.getElementById("risk_eval").textContent = "None";
            document.getElementById("risk_eval").style.color = "green";
            break
        }

        case 1: {
            document.getElementById("risk_eval").textContent = "You need to submit an ethics application, which will be sent to the faculty ethics committee";
            document.getElementById("risk_eval").style.color = "orange";
            break
        }

        case 2:
        case 3: {
            document.getElementById("risk_eval").textContent = "You need to submit an ethics application to the Central Ethics Committee";
            document.getElementById("risk_eval").style.color = "red";
            break
        }
    }

    document.getElementById('application_level').value = risk;
}

function completeQuestionnaire() {
    hideQuestions();
    document.getElementById("filter_question_form").children[0].style.height = "15%";
    setHidden(document.getElementsByClassName("question-content")[0]);


    setHidden(document.getElementById("questionnaire_popup"));

    switch (risk) {
        case 0: {
            document.getElementById("risk_message").textContent = "You do not need to submit an ethics application";
            document.getElementById("risk_message").style.color = "green";
            document.getElementById("btn_question_complete_proceed").textContent = "Home";
            document.getElementById("btn_question_complete_proceed").onclick = function (ev) {
                //TODO redirect to home
                window.location.href = homeRoutes.controllers.HomeController.index().url;
            };
            break
        }

        case 1: {
            document.getElementById("risk_message").textContent = "You need to submit an ethics application for your faculty to review";
            document.getElementById("risk_message").style.color = "orange";
            document.getElementById("btn_question_complete_proceed").onclick = function (ev) {
                setHidden(document.getElementById("filter_question_form"));
                nextSection();
            };
            break
        }

        case 2:
        case 3: {
            document.getElementById("risk_message").textContent = "You need to submit an ethics application for the central committee to review";
            document.getElementById("risk_message").style.color = "red";
            document.getElementById("btn_question_complete_proceed").onclick = function (ev) {
                setHidden(document.getElementById("filter_question_form"));
                nextSection();
            };
            break
        }

    }
    setVisible(document.getElementById("complete_popup"));
}

function hideQuestions() {
    var questionList = document.querySelectorAll(".question");
    numQuestions = questionList.length;
    questionList.forEach(function (e) {
        setHidden(e);
    });
}

function eventNextClick() {

    var old = indexQuestions;
    if (indexQuestions < numQuestions) {
        document.getElementById("btnNextQuestion").textContent = "Next";
        if (risk > 1) {
            completeQuestionnaire();
        } else {
            nextQuestion();
        }
        if (old === 0 && indexQuestions === 1)
            show(document.getElementById("btnPreviousQuestion"));
        if (indexQuestions + 1 === (numQuestions)) {
            document.getElementById("btnNextQuestion").textContent = "Complete";
            document.getElementById("btnNextQuestion").onclick = completeQuestionnaire;
        }
        else
            document.getElementById("btnNextQuestion").textContent = "Next";
    }
}

function docReady() {

    // // Sets number of section sections
    // var sectionList = document.querySelectorAll(".section");
    // numSections = sectionList.length;
    // sectionList.forEach(function (e) {
    //     setHidden(e);
    // });

    // Sets number of question sections
    hideQuestions();

    // document.querySelectorAll(".question")[0].classList.add("visible");

    document.getElementById("btnStartQuestions").onclick = function (ev) {

        //TODO temporary setting only - fix height issue
        document.getElementById("filter_question_form").children[0].style.height = "70%";
        document.getElementsByClassName("question-content")[0].style.height = "90%";

        setHidden(document.getElementById("btnStartQuestions"));
        setVisible(document.querySelectorAll(".question-content").item(0));
        nextQuestion();
    };

    document.querySelectorAll(".condition").forEach(function (value) {
        value.onclick = checkRisk
    });
    checkRisk();

    document.getElementById("btnNextQuestion").onclick = eventNextClick;
    document.getElementById("btnPreviousQuestion").onclick = function (ev) {

        var old = indexQuestions;
        if (indexQuestions > 0) {
            previousQuestion();
            if (old === (numQuestions - 1) && indexQuestions === (numQuestions - 2)) {
                show(document.getElementById("btnNextQuestion"));
                document.getElementById("btnNextQuestion").onclick = eventNextClick;
            }
        }

        if (indexQuestions === 0)
            hide(document.getElementById("btnPreviousQuestion"));
    };
    hide(document.getElementById("btnPreviousQuestion"));

    // Initializes wizard
    initWizard();
}

// - sets the 'state' to visible
// - increments the index
// - sets the new index to active

function nextQuestion() {
    // Set first section active
    var nodelistQuestion = document.querySelectorAll(".question");
    setHidden(nodelistQuestion[indexQuestions]);
    setVisible(nodelistQuestion[++indexQuestions]);
}

// - sets the 'state' to hidden
// - decrements the index
// - sets the new index to active

function previousQuestion() {
    // Set first section active
    var nodelistQuestion = document.querySelectorAll(".question");
    setHidden(nodelistQuestion[indexQuestions]);
    setVisible(nodelistQuestion[--indexQuestions]);
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
        console.log(e)
    }
}

// makes an element visible
function show(element) {
    try {
        if (element == null)
            return;
        element.style.display = ""
    } catch (e) {
        console.log(e)
    }
}

function addDocumentOverview() {
    var docsHtml = "";
    document.querySelectorAll("input[type='file']").forEach(function (value) {
        var id = "chk_" + value.id;
        var req = value.attributes.getNamedItem("file_required").value;
        if (req==null)
            req = "false";
        var required = (req === "true") ? "Required" : "Optional";
        var chkString = "<div class=\"row\"><div class=\"col-sm-10\"><p style='display: inline-block;; font-size: 0.8em'>" + value.attributes.getNamedItem("desc").value + " (" + required + ")</p></div><div class=\"col-sm-1\"><input style='padding-right: 10px; float: right' disabled type=\"checkbox\" value='false' id=\"" + id + "\"></div></div>";
        docsHtml += chkString;
    });
    document.getElementById("documents_overview_container").innerHTML += docsHtml;
}

function addApplicationOverview() {
    var docsHtml = "";
    document.querySelectorAll(".section").forEach(function (value) {
        var id = "chk_sect_" + value.id;
        var name = value.getAttribute('name');
        var chkString = "<div class=\"row\"><div class=\"col-sm-10\"><p style='display: inline-block;; font-size: 0.8em'>" + name + "</p></div><div class=\"col-sm-1\"><input style='padding-right: 10px; float: right' disabled type=\"checkbox\" value='false' id=\"" + id + "\"></div></div>";
        docsHtml += chkString;
    });
    document.getElementById("application_overview_container").innerHTML += docsHtml;
}

// initialize Wizard
function initWizard() {
    //Add collapsible to each section header and collapsible-child to each of the children
    function addCollapsibleSections() {
        document.querySelectorAll(".section").forEach(e => e.firstChild.classList.add("section-title", "collapsible"));

        // Add all groups (which are decendants of section) to inherit collapsible-child allowing section to collapse groups
        document.querySelectorAll(".section > .group").forEach(e => e.classList.add("collapsible-child"));
    }

    function addCollapsibleGroups() {
        document.querySelectorAll(".group > h3").forEach(e => e.classList.add("group-title", "collapsible"));

        // Add all groups (which are decendants of section) to inherit collapsible-child allowing section to collapse groups
        document.querySelectorAll(".group").forEach(e => e.childNodes.forEach(ee => {
            try {
                if(ee.nodeName !== "H3" && ee.nodeType === 1) {
                    ee.classList.add("collapsible-child")
                }
            } catch (e) {
                console.log(e);
            }
        }));
    }

    // Link each section heading to group collapbsible, adding an onclick event which collapses or expands a section when clicked
    document.querySelectorAll(".group").forEach(function (e) {
        e.firstChild.onclick = function () {
            document.querySelectorAll("#" + e.id + ".section .group").forEach(function (child) {
                child.style.display = (child.style.display === "none" || child.style.display === "") ? "block" : "none";
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
    document.querySelectorAll("#listDiv").forEach(function(div) {div.classList.add("")})
}