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
        }).done(function (data) {
            populateValues(data);
        }).fail(function (error) {
            alert(error)
        });
    }

    prpEmail.oninput = function () {
        let val = this.value;
        let list = document.querySelectorAll("#list_prp_contact_email > option");
        for (let i = 0; i < list.length; i++) {
            if (list[i].value === val) {
                let email = val.split("[")[1].replace("]", "");
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
        addReadGuidelines();
        //fix input checkboxes not accepting values
        // document.querySelectorAll("input[type=checkbox]").forEach(checkbox => {
        //     checkbox.onclick = function () {
        //         // checkbox.checked = !checkbox.checked;
        //         // checkbox.value = checkbox.checked;
        //     }
        // })
    }
}

function fixTables() {

    // Remove all left margins from input/select elements
    document.querySelectorAll("table input").forEach(function (value) {
        value.style.marginLeft = 0
    });
    document.querySelectorAll("table select").forEach(function (value) {
        value.style.marginLeft = 0
    });
    document.querySelectorAll("table label").forEach(function (value) {
        value.style.marginLeft = 0
    });

    //Get all table rows, the element and index
    document.querySelectorAll("table > thead > tr").forEach(row => {
        const mockupRow = [];
        var classLists = {};
        var fieldCount = 0;

        classLists[0] = "col-sm-1";
        row.querySelectorAll("th").forEach(headerRow => {

            fieldCount++;
            let label;
            headerRow.childNodes.forEach(value => {
                if (value.tagName === "BR") {
                    value.parentNode.removeChild(value);
                } else if (value.tagName === "LABEL") {
                    label = value;
                }
            });
            headerRow.classList.add("col-sm-4");
            let field = headerRow.children[2];
            if (field.tagName === "INPUT") {
                let fieldType = field.type.toUpperCase();
                if (fieldType === "TEXT") {
                    classLists[fieldCount] = "col-sm-4";
                    field.style.width = "100%";
                } else
                    classLists[fieldCount] = "col-sm-1";
                    field.style.transform = "scale(2.2)";
                }
            } else if (field.tagName === "SELECT") {
                classLists[fieldCount] = "col-sm-3";
            }
            headerRow.removeChild(field);
            mockupRow[mockupRow.length++] = field;
        });
        fieldCount++;
        classLists[fieldCount] = "col-sm-2";
        fieldCount++;

        let table = row.parentElement.parentElement;
        tableElementsContainer[table.id] = mockupRow;
        let th = document.createElement("TH");
        th.innerText = "";
        let buttonContainer = th.cloneNode(true);
        row.insertBefore(th, row.firstElementChild);
        row.appendChild(buttonContainer);

        for (let i = 0; i < row.children.length; i++) {
            row.children[i].classList = "";
            row.children[i].classList.add(classLists[i]);
        }

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
        const newCell = newRow.insertCell(i + 1);
        const e = elements[i].cloneNode(true);
        e.id += "_" + tableRef.rows.length;
        e.name += "_" + tableRef.rows.length;
        e.classList.add("col-sm-1");
        if (e.tagName === "SELECT") {
            e.style.width = "auto";
        }
        newCell.appendChild(e);
    }

    let remove = newRow.insertCell(elements.length + 1);
    const button = document.createElement("BUTTON");
    button.classList.add("col-lg-6", "fa", "fa-trash");
    button.style.width = "auto";
    button.type = "button";
    button.onclick = function () {
        removeRow(tableId, button.closest('tr').rowIndex);
        updateRowIndexes(tableId)
    };
    remove.appendChild(button);
}

function removeRow(tableId, rowIndex) {
    document.getElementById(tableId).deleteRow(rowIndex);
}

function addReadGuidelines() {
    let check_guidelines = document.getElementById("read_ethics_guidelines");
    if (check_guidelines != null) {
        let positiveButton = document.querySelectorAll(".button-positive button")[0];
        let neutralButton = document.querySelectorAll(".button-neutral button")[0];
        check_guidelines.onchange = function () {

            if (!check_guidelines.checked) {
                positiveButton.classList.add("accept-tAc");
                neutralButton.classList.add("accept-tAc");
                positiveButton.setAttribute("disabled", "");
                neutralButton.setAttribute("disabled", "");
            } else {
                positiveButton.classList.remove("accept-tAc");
                neutralButton.classList.remove("accept-tAc");
                positiveButton.removeAttribute("disabled");
                neutralButton.removeAttribute("disabled");
            }
        };

        if (!check_guidelines.checked) {
            positiveButton.classList.add("accept-tAc");
            neutralButton.classList.add("accept-tAc");
            positiveButton.setAttribute("disabled", "");
            neutralButton.setAttribute("disabled", "");
        } else {
            positiveButton.classList.remove("accept-tAc");
            neutralButton.classList.remove("accept-tAc");
            positiveButton.removeAttribute("disabled");
            neutralButton.removeAttribute("disabled");
        }
    }

}