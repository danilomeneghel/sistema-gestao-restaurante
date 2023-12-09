$(document).ready(function() {
    $('#filtrar-pedido').on('change', function() {
        var value = $(this).val();
        $.ajax({
            url: '/pedido/pedidos-disponiveis/' + value,
            type: 'GET',
            success: function(result) {
                $("#pedidos").html(result);
            }
        });
    });

    $(".btn-delete-image").click(function showAlert() {
        var btn = $(this);
        if(confirm("Tem certeza que deseja excluir essa foto?")) {
            $.ajax({
                url: '/api/imagem/excluir/' + $(this).attr("id"),
                type: 'DELETE',
                success: function(result) {
                    btn.parent().fadeOut(500, function() {
                        btn.parent().remove();
                    });
                }
            });
        }
        return false;
    });
});

function toggle() {
    $("#wrapper").toggleClass("toggled");
    var elem = document.getElementById("menu-toggle");
}
