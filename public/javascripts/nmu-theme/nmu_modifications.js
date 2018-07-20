$(function() {
    $('#application-dropdown-menu').smartmenus({ subIndicators: false, subIndicatorsText: 'test', keepHighlighted:false });
});

// Allows the Choose application type Model box popup in the rec_menu_bar

function openApplicationTypePopup() {

    var modal = document.getElementById('modal-wrapper');

    // Display popup
    modal.style.display='block';

    // Add event handler for click anywhere else on screen
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };
}

