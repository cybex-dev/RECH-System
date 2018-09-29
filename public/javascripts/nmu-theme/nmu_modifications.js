$(function() {
    $('#application-dropdown-menu').smartmenus({ subIndicators: false, subIndicatorsText: 'test', keepHighlighted:false });

    var menuOption = document.getElementById("menu_application_type");
    if (menuOption != null){
        menuOption.onclick = function () {
            openPopup("select_application_type_popup", true)
        }
    }

    // Shows the filter question form when creating a new application
    openPopup('filter_question_form', false);

    // // Hides all dd blocks with class Info
    // hideDDClassInfo();
    //
    // hideDDClassError();
});

// Allows the Choose application type Model box popup in the rec_menu_bar

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

function hidePopup(id) {
    document.getElementById(id).style.display = 'none';
}

function showApplicationOptions(id) {
    document.getElementById(id).style.display = 'block';
}

function hideDDClassInfo() {
    document.querySelectorAll("dd.info").forEach(function (value) { value.style.display = "None" })
}

function hideDDClassError() {
    document.querySelectorAll("dd.error").forEach(function (value) { value.style.display = "None" })
}
