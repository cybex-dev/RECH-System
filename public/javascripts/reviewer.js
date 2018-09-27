let list = [];
let numReviewers = 0;

_docReady(docReady());

function docReady() {
    if (document.getElementById("btnAddReviewer") == null)
        return;
    getReviewers();
    document.getElementById("btnAddReviewer").onclick = function () {
        numReviewers++;
        if (numReviewers >= 4) {
            document.getElementById("btnAddReviewer").style.display = "none";
        }
        let tableRow = createTableRow("table_reviewers");

        let tdIndex = createTableDataElement(tableRow, "nmu-color-dark");
        let tdEmail = createTableDataElement(tableRow, "nmu-color-dark");
        let tdReviewer = createTableDataElement(tableRow, "nmu-color-dark");
        let tdFaculty = createTableDataElement(tableRow, "nmu-color-dark");
        let tdRemove = createTableDataElement(tableRow);

        let currentIndex = size("table_reviewers");
        let delButton = createButton("btnReviewer" + currentIndex, "", "button", "", function () {
            removeTableRow("table_reviewers", document.getElementById("btnReviewer" + currentIndex).closest('tr').rowIndex);
            numReviewers--;
            if (numReviewers < 4){
                document.getElementById("btnAddReviewer").style.display = "inline-block";
            }
        }, "fa", "fa-trash", "nmu-button", "item-button");
        delButton.style.paddingRight = "30px";
        tdRemove.appendChild(delButton);
        tdIndex.innerText = currentIndex;
        tdIndex.style.textAlign = "center";
        createInputDropdown(tdEmail, "reviewer" + currentIndex, "reviewer" + currentIndex, list);
        tdReviewer.innerText = "";
        tdFaculty.innerText = "";
    }
}

function getReviewers() {
    let id = document.getElementById("application_id").value;
    $.ajax({
        url: apiRoutes.controllers.APIController.findAllReviewers(id).url
    }).done(function(data) {
        list = data;
    });l
}

function updateRowIndexes(tableId) {
    let trList = document.querySelectorAll("#" + tableId + " tbody > tr");
    for (let i = 0; i < trList.length; i++) {
        trList[i].children[0].innerText = i+1
    }
}

function removeTableRow(tableId, currentIndex) {
    console.log("deleting row " + currentIndex + " in table " + tableId );
    document.getElementById(tableId).deleteRow(currentIndex);
    updateRowIndexes(tableId);
}

function size(tableId) {
    return document.querySelectorAll("#" + tableId + " tbody > tr").length
}

function createDiv(id, name, ...classlist) {
    return createElement("div", "", name, id, classlist);
}

function createElement(type, value, name, id, ...classList) {
    let element = document.createElement(type);
    if (classList.length !== 0)
        if (classList[0] instanceof Array) {
            element.className = classList[0].join(" ");
        } else {
            element.className = classList[0];
        }
    element.name = name.trim()  || "";
    element.id = id.trim()  || "";
    element.innerText = value  || "";
    return element;
}

function createTableRow(tableId, ...classlist) {
    let tableBody = document.querySelectorAll("#" + tableId + " tbody")[0];
    let tableRow = createElement("tr","","","",classlist);
    tableBody.appendChild(tableRow);
    return tableRow;
}

function createTableDataElement(tableRow, ...classlist) {
    let dataElement = createElement("td","","","",classlist);
    tableRow.appendChild(dataElement);
    return dataElement;
}

function createButton(id, name, type, value, callback, ...classlist) {
    let button = createElement("button", value, name, id, classlist);
    button.type = type;
    button.onclick = callback;
    return button;
}

function addChildren(parentElement, ...items) {
    for (let i = 0; i < items.length; i++) {
        parentElement.appendChild(items[i]);
    }
}

function createOption(parent, value) {
    let option = createElement("option");
    option.value = value;
    parent.appendChild(option)
}

function createInputDropdown(parent, id, name, dataItemsMap, ...classList) {
    let inputText = createElement("input", "", id, name, classList);
    inputText.type = "text";
    let datalist = createElement("datalist", "", inputText.list, id + '_datalist');
    list.forEach(function(e) {
        createOption(datalist, e.email)
    });
    inputText.setAttribute('list', id + '_datalist');
    inputText.oninput = function(){
        for (let i = 0; i < list.length; i++) {
            if (list[i].email === this.value) {
                let p = list[i];
                let tr = inputText.closest('tr');
                tr.children[2].innerText = p.title + " " + p.firstname + " " + p.lastname;
                tr.children[3].innerText = p.dept + " (" + p.faculty + ") ";
                break;
            }
        }
    };
    addChildren(parent, inputText, datalist)
}

function mapToJson(map) {
    return JSON.stringify([...map]);
}
function jsonToMap(jsonStr) {
    return new Map(JSON.parse(jsonStr));
}
