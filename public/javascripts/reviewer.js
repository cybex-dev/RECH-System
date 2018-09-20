let list = [];

_docReady(docReady());

function docReady() {
    getReviewers();
    document.getElementById("btnAddReviewer").onclick = function () {
        let tableRow = createTableRow("table_reviewers");

        let tdIndex = createTableDataElement(tableRow);
        let tdEmail = createTableDataElement(tableRow);
        let tdReviewer = createTableDataElement(tableRow);
        let tdFaculty = createTableDataElement(tableRow);
        let tdRemove = createTableDataElement(tableRow);

        let currentIndex = size("table_reviewers");

        tdIndex.value = size("table_reviewers");
        tdIndex.style.textAlign = "center";
        createInputDropdown(tdEmail, "reviewer" + currentIndex, "reviewer" + currentIndex, list);
        tdReviewer.innerText = "";
        tdFaculty.innerText = "";
        tdRemove.appendChild(createButton("reviewer" + currentIndex, "reviewer" + currentIndex, "button", "", removeTableRow("table_reviewers", "table_reviewers", currentIndex), "fa", "fa-trash", "nmu-button", "item-button"));
    }
}

function getReviewers() {
    let id = document.getElementById("application_id").value;
    $.ajax({
        url: apiRoutes.controllers.APIController.findAllReviewers(id).url
    }).done(function(data) {
        list = data;
    });
}

function updateRowIndexes(tableId) {
    let trList = document.querySelectorAll("#" + tableId + " tr");
    for (let i = 0; i < trList.length; i++) {
        trList.item(0).innerText = i+1
    }
}

function removeTableRow(tableId, currentIndex) {
    document.getElementById(tableId).deleteRow(currentIndex);
    updateRowIndexes(tableId);
}

function size(tableId) {
    return document.querySelectorAll("#" + tableId + " tr").length
}

function createDiv(id, name, ...classlist) {
    return createElement("div", "", id, name, classlist);
}

function createElement(type, value, name, id, ...classList) {
    let element = document.createElement(type);
    if (classList.length !== 0)
        if (classList[0].length !== 0)
            element.classList.add(classList[0]);
    element.name = name  || "";
    element.id = id  || "";
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
    inputText.list = id + "_datalist";
    let datalist = createElement("datalist", "", inputText.list, "");
    list.forEach(function(e) {
        createOption(datalist, e.email)
    });
    inputText.oninput = function(){
        let p = list.get(this.value);

        let tr = inputText.closest('tr');
        tr.item(2).innerText = p.title + " " + p.firstname + " " + p.lastname;
        tr.item(3).inner = p.dept + " (" + p.faculty + ") ";
    };
    addChildren(parent, inputText, datalist)
}

function mapToJson(map) {
    return JSON.stringify([...map]);
}
function jsonToMap(jsonStr) {
    return new Map(JSON.parse(jsonStr));
}
