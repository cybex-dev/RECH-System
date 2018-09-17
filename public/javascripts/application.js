_docReady(docReady());

function docReady() {
    fixTables();
    addSearchPerson();
}

function addSearchPerson() {

}

function fixTables() {
    //Get all table rows, the element and index
    document.querySelectorAll("table.list > tbody > tr").forEach(
        function (value, index) {
            // Get all labels and helps from this table row
        }
    );
}