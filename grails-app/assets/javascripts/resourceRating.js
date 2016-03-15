$(function () {
    $("#rateYo").rateYo({
        fullStar: true,
        onSet: function (rating, rateYoInstance) {
            $.ajax({
                url: "/resourceRating/save",
                data: {
                    userId: $("#hidden-user-id").val(), resourceId: $("#hidden-resource-id").val(), score: rating
                }
            })
        }
    });
    $("#rateYo").rateYo("rating", $("#default-hidden-resource-rating").val());
});

(function ($) {
    $.fn.rating = function (method, options) {
        method = method || 'create';
        var settings = $.extend({
            limit: 5,
            value: 0,
            glyph: "glyphicon-star",
            coloroff: "gray",
            coloron: "gold",
            size: "2.0em",
            cursor: "default",
            onClick: function () {
            },
            endofarray: "idontmatter"
        }, options);
        var style = "";
        style = style + "font-size:" + settings.size + "; ";
        style = style + "color:" + settings.coloroff + "; ";
        style = style + "cursor:" + settings.cursor + "; ";

        if (method == 'create') {
            this.each(function () {
                attr = $(this).attr('data-rating');
                if (attr === undefined || attr === false) {
                    $(this).attr('data-rating', settings.value);
                }
            });

            for (var i = 0; i < settings.limit; i++) {
                this.append('<span data-value="' + (i + 1) + '" class="ratingicon glyphicon ' + settings.glyph + '" style="' + style + '" aria-hidden="true"></span>');
            }

            this.each(function () {
                paint($(this));
            });
        }

        if (method == 'set') {
            this.attr('data-rating', options);
            this.each(function () {
                paint($(this));
            });
        }

        if (method == 'get') {
            return this.attr('data-rating');
        }

        this.find("span.ratingicon").click(function () {
            rating = $(this).attr('data-value');
            $(this).parent().attr('data-rating', rating);
            paint($(this).parent());
            settings.onClick.call($(this).parent());
        });

        function paint(div) {
            rating = parseInt(div.attr('data-rating'));
            div.find("input").val(rating);
            div.find("span.ratingicon").each(function () {
                var rating = parseInt($(this).parent().attr('data-rating'));
                var value = parseInt($(this).attr('data-value'));
                if (value > rating) {
                    $(this).css('color', settings.coloroff);
                }
                else {
                    $(this).css('color', settings.coloron);
                }
            })
        }
    };
}(jQuery));

$(document).ready(function () {
    $("#stars-default").rating();
});