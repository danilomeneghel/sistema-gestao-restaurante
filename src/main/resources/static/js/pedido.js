$(document).ready(function () {
    var dataTable = $('#data-table').DataTable({
        responsive: true,
        ordering: false,
        dom: 'Blfrtip',
        buttons:[
            {
                text:      '<i class="fas fa-plus"></i> Adicionar',
                titleAttr: 'Adicionar',
                className: 'btn btn-success buttons-add',
                action: function (e, dt, node, config) {
                    window.location.href = '/pedido/cadastro';
                }
            },
            {
                extend:    'pdfHtml5',
                text:      '<i class="fas fa-file-pdf"></i> ',
                titleAttr: 'Exportar para PDF',
                className: 'btn btn-danger',
                orientation:'landscape',
                title: function () { return 'Gestão de Restaurante - Pedidos'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)',
                    format: {
                        header: function ( data, columnIdx ) {
                            return $(data).attr('id');
                        }
                    }
                },
                customize: function (doc) {
                    doc.content[1].table.widths = "*";
                }
            },
            {
                extend:    'excelHtml5',
                text:      '<i class="fas fa-file-excel"></i> ',
                titleAttr: 'Exportar para Excel',
                className: 'btn btn-success',
                orientation:'landscape',
                title: function () { return 'Gestão de Restaurante - Pedidos'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)',
                    format: {
                        header: function ( data, columnIdx ) {
                            return $(data).attr('id');
                        }
                    }
                }
            },
            {
                extend:    'print',
                text:      '<i class="fa fa-print"></i> ',
                titleAttr: 'Imprimir',
                className: 'btn btn-info',
                orientation:'landscape',
                title: function () { return 'Gestão de Restaurante - Pedidos'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)',
                    format: {
                        header: function ( data, columnIdx ) {
                            return $(data).attr('id');
                        }
                    }
               }
            }
        ],
        ajax: {
            url: '/api/pedido/pedidos',
            type: 'GET',
            datatype: 'json',
            dataSrc: ''
        },
        deferRender: true,
        initComplete: function() {
            var api = this.api();
            $("#data-table thead .filters .filter").each( function ( colIdx ) {
                var cell = $('.filters .filter').eq($(api.column(colIdx).header()).index());
                var title = $(cell).text();
                if(title == 'Status' || title == 'Item Cardápio' || title == 'Estabelecimento') {
                    var select = $('<select id="'+title+'"><option value="">'+title+'</option></select>')
                    .appendTo( $(this).empty() )
                    .on( 'change', function () {
                        var val = $(this).val();
                        api.column( colIdx )
                            .search( val ? '^'+$(this).val()+'$' : val, true, false )
                            .draw();
                    } );
                    api.column( colIdx ).data().unique().sort().each( function ( d, j ) {
                        if(title == 'Status') {
                            d = ((d == true) ? 'Confirmado' : 'Cancelado');
                            select.append( '<option value="'+d+'">'+d+'</option>' );
                        } else {
                            select.append( '<option value="'+d+'">'+d+'</option>' );
                        }
                    } );
                } else {
                    $(cell).html( '<input type="text" id="'+title+'" placeholder="'+title+'" />' );
                    $('input', $('.filters .filter').eq($(api.column(colIdx).header()).index()) )
                    .off('keyup change')
                    .on('keyup change', function (e) {
                        e.stopPropagation();
                        $(this).attr('title', $(this).val());
                        var regexr = '({search})';
                        var cursorPosition = this.selectionStart;
                        api
                            .column(colIdx)
                            .search((this.value !== "") ? regexr.replace('{search}', '((('+this.value+')))') : "", this.value !== "", this.value === "")
                            .draw();
                        $(this).focus()[0].setSelectionRange(cursorPosition, cursorPosition);
                    });
                }
            });
            $('[type=search]').each(function () {
                $(this).after('<i class="fa fa-search"></i>');
            });
        },
        columns: [
            { "data": "id" },
            { "data": "estabelecimento.nome" },
            { "data": "cardapioItensString" },
            {
                "data": "total", "render": function ( data ) {
                    return "R$" + data;
                 }
            },
            { "data": "data" },
            {
                "data": "status", "render": function ( data ) {
                    if (data) {
                        return '<span class="badge bg-success">Confirmado</span>'
                    } else {
                        return '<span class="badge bg-danger">Cancelado</span>'
                    }
                }
            },
            {
                "data": null, "render": function ( data ) {
                    return '<div class="actions"><a class="btn btn-success" href="/pedido/visualizar/'+data.id+'">'+
                    '<i class="fas fa-eye"></i> Ver</a>'+
                    '<a class="btn btn-primary" href="/pedido/editar/'+data.id+'">'+
                    '<i class="fas fa-pen"></i> Editar</a><a class="btn btn-danger" '+
                    'onclick="return confirm(\'Tem certeza que deseja excluir esse registro?\');" '+
                    'href="/pedido/excluir/'+data.id+'"><i class="fas fa-trash"></i> Excluir</a></div>'
                }
            }
        ],
        language: {
            "url": "../json/Portuguese.json"
        }
    });
});
