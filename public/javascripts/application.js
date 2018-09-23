let tableElementsContainer = {};

function get(k) {
    return tableElementsContainer[k];
}

_docReady(docReady());

function enablePopulatePRPFields() {
    let prpEmail = document.getElementById("prp_contact_email");


    function populateValues(data) {
        document.getElementById("prp_firstname").value = data.firstname;
        document.getElementById("prp_lastname").value = data.lastname;
        document.getElementById("prp_contact_telephone").value = data.telephone;
        document.getElementById("prp_contact_mobile").value = data.mobile;
        document.getElementById("prp_campus").value = data.campus;
        document.getElementById("prp_address").value = data.address;
        document.getElementById("prp_faculty").value = data.faculty;
        document.getElementById("prp_department").value = data.department;
    }

    function requestPersonData(email) {
        $.ajax({
            url: apiRoutes.controllers.APIController.searchPerson(email).url
        }).done(function(data) {
            populateValues(data);
        }).fail(function(error) {
            alert(error)
        });
    }

    prpEmail.oninput = function () {
        let val = this.value;
        let list = document.querySelectorAll("#list_prp_contact_email > option");
        for(let i = 0; i < list.length; i++) {
            if (list[i].value === val){
                let email = val.split("[")[1].replace("]","");
                requestPersonData(email);
                break;
            }
        }
    }
}

function docReady() {
    if (document.getElementById("application_type") != null) {
        fixTables();
        enablePopulatePRPFields();
    }
}

function fixTables() {

    // Remove all left margins from input/select elements
    document.querySelectorAll("table input").forEach(function (value) {value.style.marginLeft = 0});
    document.querySelectorAll("table select").forEach(function (value) {value.style.marginLeft = 0});
    document.querySelectorAll("table label").forEach(function (value) {value.style.marginLeft = 0});

    //Get all table rows, the element and index
    document.querySelectorAll("table > thead > tr").forEach(row => {
        const mockupRow = [];
        row.querySelectorAll("th").forEach(headerRow => {
            let label;
            headerRow.childNodes.forEach(value => {
                if (value.tagName === "BR"){
                    value.parentNode.removeChild(value);
                } else if (value.tagName === "LABEL"){
                    label =  value;
                }
            });
            headerRow.classList.add("col-sm-4");
            let field = headerRow.children[2];
            headerRow.removeChild(field);
            mockupRow[mockupRow.length++] = field;
        });
        let table = row.parentElement.parentElement;
        tableElementsContainer[table.id] = mockupRow;

        let th = document.createElement("TH");
        th.innerText = "";
        th.classList.add("col-sm-1");
        let buttonContainer = th.cloneNode(true);
        row.insertBefore(th, row.firstElementChild);
        buttonContainer.classList.add("col-sm-2");
        row.appendChild(buttonContainer);

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
    const tableRef = document.getElementById(id).getElementsByTagName('tfoot')[0];
    const button = document.createElement("BUTTON");
    const footer = tableRef.insertRow(0);
    const cell = footer.insertCell(0);
    cell.colSpan = 2;
    button.classList.add("nmu-button", "button-small", "col-lg-6");
    button.id = id + "_addrow";
    button.innerText = "Add";
    button.type = "button";
    button.onclick = function () {
        addRow(id, get(id));
    };
    cell.appendChild(button)
}

function addRow(tableId, elements) {
    const tableRef = document.getElementById(tableId).getElementsByTagName('tbody')[0];

// Insert a row in the table at the last row
    const newRow = tableRef.insertRow(tableRef.rows.length);
    let cell = newRow.insertCell(0);
    cell.innerText = tableRef.rows.length;
    cell.style.color = "#0A283F";
    cell.style.padding = "2px";

// Insert a cell in the row at index 0
    for (let i = 0; i < elements.length; i++) {
        const newCell = newRow.insertCell(i+1);
        const e = elements[i].cloneNode(true);
        e.id += "_" + tableRef.rows.length;
        e.name += "_" + tableRef.rows.length;
        e.classList.add("col-sm-1");
        if (e.tagName === "SELECT") {
            e.style.width = "auto";
        }
        newCell.appendChild(e);
    }

    let remove = newRow.insertCell(elements.length+1);
    const button = document.createElement("BUTTON");
    button.classList.add("col-lg-6", "fa", "fa-trash");
    button.style.width = "auto";
    button.type = "button";
    button.onclick = function() {
        removeRow(tableId, button.closest('tr').rowIndex);
        updateRowIndexes(tableId)
    };
    remove.appendChild(button);
}

function removeRow(tableId, rowIndex) {
    document.getElementById(tableId).deleteRow(rowIndex);
}