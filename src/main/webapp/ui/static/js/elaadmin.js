$.noConflict();

function checkDeviceSize() {
    const windowWidth = $(window).width();
    $('body').toggleClass('small-device', windowWidth < 1200);
}

function toggleSearch(open) {
    $('.search-trigger').parent('.header-left').toggleClass('open', open);
}

function toggleMenu() {
    const windowWidth = $(window).width();
    const $body = $('body');
    const $leftPanel = $('#left-panel');

    if (windowWidth < 1200) {
        $body.removeClass('open');
        windowWidth < 768 ? $leftPanel.slideToggle() : $leftPanel.toggleClass('open-menu');
    } else {
        $body.toggleClass('open');
        $leftPanel.removeClass('open-menu');
    }
}

function addDropdownSubtitle() {
    $(".menu-item-has-children.dropdown").each(function () {
        const $dropdown = $(this);
        const tempText = $dropdown.children('.dropdown-toggle').html();
        if (!$dropdown.find('.subtitle').length) {
            $dropdown.children('.sub-menu').prepend(`<li class="subtitle">${tempText}</li>`);
        }
    });
}

jQuery(document).ready(function ($) {
    "use strict";

    // Inicializar selectores personalizados
    $('select.cs-select').each(function () {
        new SelectFx(this);
    });

    let selectpicker = $('.selectpicker');
    if (selectpicker.length > 0) {
        selectpicker.selectpicker();
    }

    // Eventos
    $('.search-trigger').on('click', function (event) {
        event.preventDefault();
        toggleSearch(true);
    });

    $('.search-close').on('click', function (event) {
        event.preventDefault();
        toggleSearch(false);
    });

    $('.equal-height').matchHeight({property: 'max-height'});

    $('#menuToggle').on('click', toggleMenu);

    $(".menu-item-has-children.dropdown").on('click', addDropdownSubtitle);

    // Ejecutar verificaciones iniciales
    checkDeviceSize();
    $(window).on('resize', checkDeviceSize);
});
