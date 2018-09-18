let tableElementsContainer = {};

function get(k) {
    return tableElementsContainer[k];
}

_docReady(docReady());

function docReady() {
    fixTables();
    addSearchPerson();
}

function addSearchPerson() {

}

function fixTables() {

    // Remove all left margins from input/select elements
    document.querySelectorAll("table input").forEach(function (value) {value.style.marginLeft = 0});
    document.querySelectorAll("table select").forEach(function (value) {value.style.marginLeft = 0});
    document.querySelectorAll("table label").forEach(function (value) {value.style.marginLeft = 0});

    //Get all table rows, the element and index
    document.querySelectorAll("table > thead > tr").forEach(row => {
        var mockupRow = [];
        row.querySelectorAll("th").forEach(headerRow => {
            var label;
            headerRow.childNodes.forEach(value => {
                if (value.tagName === "BR"){
                    value.parentNode.removeChild(value);
                } else if (value.tagName === "LABEL"){
                    label =  value;
                }
            });
            headerRow.classList.add("col-sm-5");
            let field = headerRow.children[2];
            headerRow.removeChild(field);
            mockupRow[mockupRow.length++] = field;
        });
        let table = row.parentElement.parentElement;
        tableElementsContainer[table.id] = mockupRow;

        const tableBody = document.createElement("TBODY");
        const tableFooter = document.createElement("TFOOT");
        table.appendChild(tableBody);
        table.appendChild(tableFooter);
    });

    addAddEntryButtons();
}

function addAddEntryButtons() {
    document.querySelectorAll("table > tfoot").forEach(value => {
        addEntryButton(value.parentElement.id);
    });
}


function addEntryButton(id) {
    var tableRef = document.getElementById(id).getElementsByTagName('tfoot')[0];
    var button = document.createElement("BUTTON");
    var footer = tableRef.insertRow(0);
    var cell = footer.insertCell(0);
    button.classList.add("nmu-button", "button-small");
    button.id = id + "_addrow";
    button.innerText = "Add";
    button.onclick = function () {
        addRow(id, get(id));
    };
    cell.appendChild(button)
}

function addRow(tableId, elements) {
    var tableRef = document.getElementById(tableId).getElementsByTagName('tbody')[0];

// Insert a row in the table at the last row
    var newRow   = tableRef.insertRow(tableRef.rows.length);

// Insert a cell in the row at index 0
    for (let i = 0; i < elements.size; i++) {
        var newCell  = newRow.insertCell(i);
        var e = elements[i];
        e.id += "_" + i;
        e.name += "_" + i;
        newCell.appendChild(e);
    }
}



function countRows(tableId) {
    return $('#' + tableId + ' tr').length();
}