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
    document.querySelectorAll(".condition").forEach(function(element) {
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
            document.getElementById("risk_eval").textContent = "Faculty";
            document.getElementById("risk_eval").style.color = "orange";
            break
        }

        case 2:
        case 3: {
            document.getElementById("risk_eval").textContent = "Committee";
            document.getElementById("risk_eval").style.color = "red";
            break
        }

    }
}

function completeQuestionaire() {
    hideQuestions();
    document.getElementById("filter_question_form").children[0].style.height = "15%"
    setHidden(document.getElementsByClassName("question-content")[0]);

    setHidden(document.getElementById("questionnaire_popup"));

    switch (risk) {
        case 0: {
            document.getElementById("risk_message").textContent = "You do not need to submit an ethics application";
            document.getElementById("risk_message").style.color = "green";
            document.getElementById("btn_question_complete_proceed").textContent = "Home";
            document.getElementById("btn_question_complete_proceed").onclick = function (ev) {
                //TODO redirect to home
            };
            break
        }

        case 1: {
            document.getElementById("risk_message").textContent = "You need to submit an ethics application for your faculty to review";
            document.getElementById("risk_message").style.color = "orange";
            document.getElementById("btn_question_complete_proceed").textContent = "Ok";
            break
        }

        case 2:
        case 3: {
            document.getElementById("risk_message").textContent = "You need to submit an ethics application for the central committee to review";
            document.getElementById("risk_message").style.color = "red";
            document.getElementById("btn_question_complete_proceed").textContent = "Ok";
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

function docReady() {

    // Sets number of section sections
    var sectionList = document.querySelectorAll(".section");
    numSections = sectionList.length;
    sectionList.forEach(function (e) {
        setHidden(e);
    });

    // Sets number of question sections
    hideQuestions();

    document.querySelectorAll(".question")[0].classList.add("visible");

    document.getElementById("btnStartQuestions").onclick = function (ev) {

        //TODO temporary setting only - fix height issue
        document.getElementById("filter_question_form").children[0].style.height = "70%";
        document.getElementsByClassName("question-content")[0].style.height = "90%";

        setHidden(document.getElementById("btnStartQuestions"));
        setVisible(document.querySelectorAll(".question-content").item(0));
        nextQuestion();
    };

    document.querySelectorAll(".condition").forEach(function (value) { value.onclick = checkRisk});

    document.getElementById("btnNextQuestion").onclick = function (ev) {

        if (indexQuestions + 1 < numQuestions) {
            nextQuestion()
        } else {
            completeQuestionaire();
        }
    };
    document.getElementById("btnPreviousQuestion").onclick = function (ev) {
        if (indexQuestions > 0) {
            previousQuestion();
        } else {
            hide(document.getElementById("btnPreviousQuestion"));
        }

    };
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
    if (element == null)
        return;
    element.style.display = "none"
}

// makes an element visible
function show(element) {
    if (element == null)
        return;
    element.style.display = ""
}

