function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

$(document).ready(function () {
	

					

    $('#GalleryCarousel').carousel({
        interval: 0
    })

    $('#MenuMobileMega').click(function () { $('#MegaMenuContainerMobile').toggle(); });


    $('.under').hide();
    $('.post').hide();
    $('.apscalc').hide();
    $('.fac').hide();

    if ($('.under label').filter(function (index) { return $(this).text() === $('#hdfSelected').val(); }).length > 0) {
        $('.under .active').removeClass('active');
        $('.under label:contains("' + $('#hdfSelected').val() + '")').addClass('active');
        $('.post').hide();
        //$('.under').show();		
    }

    if ($('.post label').filter(function (index) { return $(this).text() === $('#hdfSelected').val(); }).length > 0) {

        $('.post .active').removeClass('active');
        $('.post label:contains("' + $('#hdfSelected').val() + '")').addClass('active');
        $('.under').hide();
        //$('.post').show();				
    }

    //$('#selectedQualType').text( $('#hdfSelected').val());
    $('#selectedFac').text($('#hdfSelectedFacText').val());

    $('.underpost .btn').click(function () {
        //alert('asdf');
        if ($(this).find('input').val() == 'undergraduate') {
            $('.under').show();
            $('.post').hide();
            $('.fac').hide();
            //$('#selectedQualType').text('');
            $('#selectedFac').text('');

        }

        if ($(this).find('input').val() == 'postgraduate') {
            $('.under').hide();
            $('.post').show();
            $('.fac').hide();
            //$('#selectedQualType').text('');
            $('#selectedFac').text('');
        }
		
		$('.under label').removeClass('active');
		$('.post label').removeClass('active');
		$('.fac input:checked').removeAttr("checked");
		$('#phResults').hide();

    });

    $('.under .btn').click(function () {
        $('.fac').show();
        //$('.under').hide();
        //$('.post').hide();
        //$(this).parents().eq(4).find('#lblUndergradFilter').text($(this).text());

        $('#hdfSelected').val($(this).text());
        //$('#selectedQualType').text($(this).text());

    });

    $('.post .btn').click(function () {
        $('.fac').show();
        //$('.under').hide();
        //$('.post').hide();
        //$(this).parents().eq(4).find('#lblPostgradFilter').text($(this).text());
        //$(this).parents().eq(3).collapse("hide");
        $('#hdfSelected').val($(this).text());
        //$('#selectedQualType').text($(this).text());

    });

    $('.fac input').click(function () {
        $('.fac').hide();
		$('#imgLoad').toggle();
        //$(this).parents().eq(4).find('#lblUndergradFilter').text($(this).text());

        $('#hdfSelectedFac').val($(this).attr('id').split('_')[1]);
        $('#selectedFac').text("Faculty of " + $(this).val());
        $('#hdfSelectedFacText').val('Faculty of ' + $(this).val());

    });

    $('#btnAPSSubmit').click(function () {
        $('.apscalc').toggle();
    });

    if (window.location.href.toLowerCase().indexOf("career-study-fields") > -1) {
        $('#career').addClass('pactive');
        $('#qualifications').removeClass('pactive');
        $('#aps').removeClass('pactive');
    }

    if (window.location.href.toLowerCase().indexOf("qualifications") > -1) {
        $('#career').removeClass('pactive');
        $('#qualifications').addClass('pactive');
        $('#aps').removeClass('pactive');
    }
	
	if (window.location.href.toLowerCase().indexOf("qualification") > -1) {
	    $('#career').removeClass('pactive');
	    $('#qualifications').addClass('pactive');
	    $('#aps').removeClass('pactive');
    }
	
	if (window.location.href.toLowerCase().indexOf("aps-calculator") > -1) {
	    $('#career').removeClass('pactive');
	    $('#qualifications').removeClass('pactive');
	    $('#aps').addClass('pactive');
    }
	
	var apsScore = getParameterByName('aps');
	
	if(apsScore != "" && window.location.href.toLowerCase().indexOf("qualifications") > -1)
	{
	    $('#career').removeClass('pactive');
	    $('#qualifications').removeClass('pactive');
	    $('#aps').addClass('pactive');
		
		$('.PageHeader h1').text('AVAILABLE QUALIFICATIONS WITH AN APS OF ' + apsScore + " OR LOWER");
		$('.msgInstructions').append('<br><p><strong>PLEASE NOTE:</strong> this calculator will add the points for the subjects you take at school and the marks you achieve. From that calculation, a list of suggested courses will be displayed HOWEVER, some of these courses may have additional requirements e.g. Mathematics and/or Physical Sciences may be compulsory subjects, Life Sciences may be a compulsory subject etc. When you select a possible course, first make sure what the total requirements are.</p>	<br>');
	}
	else
	{
		$('.msgInstructions').append('<br>');
	}

	var sqIndicator = getParameterByName('sq');
	if (sqIndicator != "" && window.location.href.toLowerCase().indexOf("qualifications") > -1) {

	    $('.PageHeader h1').text('FEE ESTIMATION - SELECT QUALIFICATION');
	}

	if (sqIndicator != "" && window.location.href.toLowerCase().indexOf("qualification-details") > -1) {

	    $('.PageHeader h1').text('FEE ESTIMATION - CREATE FEE ESTIMATION');
	}

	if (sqIndicator != "" && window.location.href.toLowerCase().indexOf("career-study-fields") > -1) {

	    $('.PageHeader h1').text('FEE ESTIMATION - SELECT CAREER FIELD');
	}

	if (sqIndicator != "" && window.location.href.toLowerCase().indexOf("career-study-fields-details") > -1) {

	    $('.PageHeader h1').text('FEE ESTIMATION - SELECT QUALIFICATION');
	}

	if (sqIndicator != "" && window.location.href.toLowerCase().indexOf("aps-calculator") > -1) {

	    $('.PageHeader h1').text('FEE ESTIMATION - APS CALCULATOR');
	}

    $('#next').on('click', function () {
        counter++;
        showCurrent();
    });

    $('#prev').on('click', function () {
        counter--;
        showCurrent();
    });

    $('#main-menu').smartmenus({ subIndicators: false, subIndicatorsText: 'test', keepHighlighted:false });

});

$(document).ready(function () {
    $('#p_lt_zoneSearchBox_SearchBox_txtWord').attr('placeholder', 'Search');
    if (window.matchMedia('(max-width: 560px)').matches) {
        $('#p_lt_zoneSearchBox_MobileSearchBox_txtWord').attr('placeholder', 'Search');
    }
});



$(document).ready(function () {

    var headerTop = $('#LogoBar').offset().top;


    if (window.matchMedia('(min-width: 767px)').matches) {
        var headerBottom = headerTop + 60; // Sub-menu should appear after this distance from top.
    } else {
        var headerBottom = headerTop + 50; // mobile Sub-menu should appear after this distance from top.
		$("#divLogo").hide();  //name change
        $("#divText").removeClass("col-xs-10 col-sm-9"); //name change
        $("#divText").addClass("col-xs-12 col-sm-9"); //name change
        $("#EntityHeader").css({ 'text-align': 'center' }); //name change
		$("#StrapLine").replaceWith("<img id='imgLogoS' class='img-responsive' style='margin: 0 auto;'src='/App_Themes/global/images/MandelaUniversity_logo_s_B.png' alt='Logo'>"); //name change
    }
    $(window).scroll(function () {
        var scrollTop = $(window).scrollTop(); // Current vertical scroll position from the top
		//$('.mega-menu').css({ 'top': (parseFloat(165 - scrollTop)) + 'px' });
        if (scrollTop > headerBottom) { // Check to see if we have scrolled more than headerBottom
            $("#aLogo").hide();
            $("#StrapLine").hide();
            $("#NMMUtext").hide();
            $("#divSearchOnMobile").hide();
            //   $("#DepartmentEntity").removeClass("col-xs-8");
            //   $("#DepartmentEntity").addClass("col-xs-12");
            $("#DepartmentEntity").css({ "padding-left": "0px" });
            $("#LogoBar").addClass('navbar-fixed-top');

            if (window.matchMedia('(min-width: 993px)').matches) {
                $('#LogoBar').css({ 'height': '105px' });
                $("#main").css({ "padding-top": "166px" });
                $('.MegaDrop').css({ 'margin-top': '-25px' });
             //   $('.mega-menu').css({ 'top': '105px' });
                $("#DepartmentEntity").css({ "margin-top": "-13px" });
             } else if (window.matchMedia('(min-width: 768px)').matches) {
                $('#LogoBar').css({ 'height': '95px' });
                $("#main").css({ "padding-top": "156px" });
                $('.MegaDrop').css({ 'margin-top': '-35px' });
                $("#DepartmentEntity").css({ "margin-top": "-13px" });
            } else {
                $('#LogoBar').css({ 'height': '76px' });
                $("#main").css({ "padding-top": "127px" });
                $('.MegaDrop').css({ 'margin-top': '-17px' });
                $("#DepartmentEntity").css({ "margin-top": "-9px" });
				$("#divStrapLine").hide();  //name change
}

        } else {
            $("#StrapLine").show();
            $("#aLogo").show();
            
            $("#divSearchOnMobile").show();
            $("#DepartmentEntity").css({ "padding-left": "15px" });
            $('#LogoBar').removeClass('navbar-fixed-top');

            if (window.matchMedia('(min-width: 993px)').matches) {
                $('#LogoBar').css({ 'height': '165px' });
                $("#main").css({ "padding-top": "1px" });
                $('.MegaDrop').css({ 'margin-top': '0px' });
             //   $('.mega-menu').css({ 'top': (parseFloat(165 - scrollTop)) + 'px' });
                $("#DepartmentEntity").css({ "margin-top": "5px" });
             } else if (window.matchMedia('(min-width: 768px)').matches) {
                $('#LogoBar').css({ 'height': '155px' });
                $("#main").css({ "padding-top": "1px" });
                $('.MegaDrop').css({ 'margin-top': '-6px' });
                $("#DepartmentEntity").css({ "margin-top": "10px" });
            } else {
                $('#LogoBar').css({ 'height': '125px' });
                $("#main").css({ "padding-top": "1px" });
                $('.MegaDrop').css({ 'margin-top': '1px' });
                $("#DepartmentEntity").css({ "margin-top": "0px" });
				$("#NMMUtext").show();
				$("#StrapLine").replaceWith("<p id='StrapLine' class='StrapLine'>Change the world</p>"); //name change
				$("#divStrapLine").show(); //name change
             }
			

/*             if (window.matchMedia('(min-width: 767px)').matches) {
                $('#LogoBar').css({ "height": "155px" });
                $("#main").css({ "padding-top": "0px" });
                $(".MegaDrop").css({ "margin-top": "0px" });
                $("#DepartmentEntity").css({ "margin-top": "5px" });
            } else {
                $('#LogoBar').css({ "height": "125px" });
            }
            if (window.matchMedia('(max-width: 365px)').matches) {
                $('#MegaDrop').css({ "bottom": "-32px" });
            } else if (window.matchMedia('(max-width: 560px)').matches) {
                $('#MegaDrop').css({ 'bottom': '-20px' });
            } else {
                $('#MegaDrop').css({ 'bottom': '-5px' });
            }
 */        }
    });
});


//studentquote.aspx


jQuery(document).ready(function ($) {

    $('#news-gallery').royalSlider({
        arrowsNav: false,
        fadeinLoadedSlide: true,
        controlNavigationSpacing: 0,
        controlNavigation: 'thumbnails',

        thumbs: {
            autoCenter: false,
            fitInViewport: true,
            orientation: 'vertical',
            spacing: 0,
            paddingBottom: 0
        },
        keyboardNavEnabled: true,
        imageScaleMode: 'fill',
        imageAlignCenter: true,
        slidesSpacing: 0,
        loop: false,
        loopRewind: true,
        numImagesToPreload: 4,
        video: {
            autoHideArrows: true,
            autoHideControlNav: false,
            autoHideBlocks: true
        },
        autoScaleSlider: true,
        autoScaleSliderWidth: 960,
        autoScaleSliderHeight: 450,

        /* size of all images http://help.dimsemenov.com/kb/royalslider-jquery-plugin-faq/adding-width-and-height-properties-to-images */
        imgWidth: 640,
        imgHeight: 360

    });

    $('.rsThumb:empty').remove();
	
	
});

    $(document).ready(function () {
        $('.rsReadAll').parent().removeClass('rsThumb');
    });
	
	
	$(document).ready(function () {
        if($('.admissioninput').val() != "")
		{
			$('#resAdmissionStatusCheck').modal().toggle();
		}
    });
jQuery(document).ready(function ($) {

    $('#event-gallery').royalSlider({
        arrowsNav: false,
        fadeinLoadedSlide: true,
        controlNavigationSpacing: 0,
        controlNavigation: 'thumbnails',

        thumbs: {
            autoCenter: true,
            fitInViewport: true,
            orientation: 'horizontal',
            spacing: 0,
            paddingBottom: 0
        },
        keyboardNavEnabled: true,
        imageScaleMode: 'fill',
        imageAlignCenter: true,
        slidesSpacing: 0,
        loop: false,
        loopRewind: true,
        numImagesToPreload: 3,
        video: {
            autoHideArrows: true,
            autoHideControlNav: false,
            autoHideBlocks: true
        },
        autoScaleSlider: true,
        autoScaleSliderWidth: 960,
        autoScaleSliderHeight: 450,

        /* size of all images http://help.dimsemenov.com/kb/royalslider-jquery-plugin-faq/adding-width-and-height-properties-to-images */
        imgWidth: 640,
        imgHeight: 360

    });

    $('.rsThumb:empty').remove();
	
	
});
/* $(window).load(function () {
    var maxHeight = $('.col-md-3').height();
    $('.thumbnail').css('height', maxHeight);
});
 */

//studentquote.aspx
function getstatus() {
    $.ajax({
        type: "POST",
        url: "https://www.mandela.ac.za/studentquote.asmx/checkDownloadStatus",
        async: false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            if (data.d == "done") {
                console.log("done");
                $('#imgLoad').hide();
            } else {
                console.log("pending");
                setTimeout('getstatus()', 1000);
            }
        }
    });

}

function setImageTally(itemToShow, numItems) {
    $("#currentImage").html((itemToShow + 1) + " of " + numItems);
}

// cater for when the user clicks on the carousel slide show at the bottom
function chooseThumbnail(newCurrItem) {
    counter = newCurrItem;
    showCurrent();
}



$(window).load(function () {
    if (document.documentElement.clientWidth > 767) {
        $('.container .tbsRow').each(function () {
            var heights = $(this).find('.rowSizer').map(function () {
                return $(this).height();
            }).get(),

		    maxHeight = Math.max.apply(null, heights);

            $(this).find('.rowSizer').height(maxHeight);
            $(this).find('.tbsImageURLh').height(maxHeight);
        });
        $('#main-menu').smartmenus({ subIndicators: false });
    } else {
        $('.MegaDropDown ul').remove();
    }
	
	

});

$(function () {
    $('#MenuOnBody').smartmenus();

    // set Shift + F12 hotkey
    //$('#MenuOnBody').smartmenus('keyboardSetHotkey', 123, 'shiftKey');

});


// //admissions
function Form1_Validator() {
        var t = document.getElementById('txtSearch');
        if (t.value == "") {
            alert("Please enter a value for the \"Student_number\" field.");
            t.focus();
            return (false);
        }

        if (t.value.length < 6) {
            alert("Please enter at least 6 characters in the \"Student_number\" field.");
            t.focus();
            return (false);
        }

        if (t.value.length > 9) {
            alert("Please enter at most 9 characters in the \"Student_number\" field.");
            t.focus();
            return (false);
        }

        var checkOK = "0123456789-";
        var checkStr = t.value;
        var allValid = true;
        var validGroups = true;
        var decPoints = 0;
        var allNum = "";
        for (i = 0; i < checkStr.length; i++) {
            ch = checkStr.charAt(i);
            for (j = 0; j < checkOK.length; j++)
                if (ch == checkOK.charAt(j))
                    break;
            if (j == checkOK.length) {
                allValid = false;
                break;
            }
            allNum += ch;
        }
        if (!allValid) {
            alert("Please enter only digit characters in the \"Student_number\" field.");
            t.focus();
            return (false);
        }


        return (true);
    }
 Dropzone.autoDiscover = false;
	 $(window).load(function () {
        $('.outdocs .btn').click(function () {
            if ($(this).find('input').val() == "rbAddDocs_yes") {
                $('#studlogin').show();

            } else if ($(this).find('input').val() == "rbAddDocs_no") {
                $('#studlogin').hide();


            }
        });

        if ($('input[id="rbAddDocs_yes"]').is(':checked')) {
            $('#studlogin').show();
        }
        else {
            $('#studlogin').hide();

        }


        var stno = $('#TextBoxStudentNumber').val();

       
        //Simple Dropzonejs 
        var myDropzone = new Dropzone("div#myId", {
            url: 'http://www.mandela.ac.za/hn_AdmissionFileUpload.ashx/?stuno=' + stno, autoProcessQueue: false, addRemoveLinks: false, maxFiles: 10, parallelUploads: 10, thumbnailWidth: "80",
            thumbnailHeight: "80",
            uploadMultiple: true,
            dictDefaultMessage: "Click here to upload files <br/> or <br/> Drop files here to upload. <br/><strong> Tip:</strong> You can select multiple files before you click Upload.",
            success: function (file, response) {
                var imgName = response;
                file.previewElement.classList.add("dz-success");
                console.log("Successfully uploaded :" + imgName);
            },
            error: function (file, response) {
                file.previewElement.classList.add("dz-error");
            },
        });

        $('#BtnDocsUpload').on('click', function (e) {
            e.preventDefault();
            myDropzone.processQueue();
        });

        myDropzone.on("complete", function (file) {
            $('#AdmissionStatusContent').hide();
            $('.jumbotron').hide();

            $('#alertSuccess').css("display", "block");
            $('#lblSuccess').text("Thank you for uploading your documents. Check regularly to see the status of your application.");
        });

        if ($('#pnlfilesUpload').is(':visible')) {
            $('body,html').animate({ scrollTop: $('body,html').prop("scrollHeight") / 2 }, 1500);
        }

    });