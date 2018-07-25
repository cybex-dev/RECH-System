$(function() {
    $('#application-dropdown-menu').smartmenus({ subIndicators: false, subIndicatorsText: 'test', keepHighlighted:false });

    // Shows the filter question form when creating a new application
    openPopup('filter_question_form', false);
});

// Allows the Choose application type Model box popup in the rec_menu_bar

function openPopup(id, isModal) {

    var modal = document.getElementById(id);

    // Display popup
    modal.style.display='block';

    // Add event handler for click anywhere else on screen
    if (isModal) {
        window.onclick = function(event) {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        };
    }
}

function hidePopup(id) {
    document.getElementById(id).style.display = 'none';
}

function showApplicationOptions(id) {
    document.getElementById(id).style.display = 'block';
}
