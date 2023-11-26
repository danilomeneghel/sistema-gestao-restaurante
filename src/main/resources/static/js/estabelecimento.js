$(document).ready(function () {
    dataTable = $('#data-table').DataTable({
        responsive: true,
        dom: 'Blfrtip',
        buttons:[
            {
                text:      '<i class="fas fa-plus"></i> Adicionar',
                titleAttr: 'Adicionar',
                className: 'btn btn-success buttons-add',
                action: function (e, dt, node, config) {
                    window.location.href = '/estabelecimento/cadastro';
                }
            },
            {
                extend:    'pdfHtml5',
                text:      '<i class="fas fa-file-pdf"></i> ',
                titleAttr: 'Exportar para PDF',
                className: 'btn btn-danger',
                orientation:'landscape',
                title: function () { return 'Gestão de Restaurante - Estabelecimentos'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)'
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
                title: function () { return 'Gestão de Restaurante - Estabelecimentos'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)'
                }
            },
            {
                extend:    'print',
                text:      '<i class="fa fa-print"></i> ',
                titleAttr: 'Imprimir',
                className: 'btn btn-info',
                orientation:'landscape',
                title: function () { return 'Gestão de Restaurante - Estabelecimentos'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)'
               }
            }
        ],
        ajax: {
            url: '/api/estabelecimento/estabelecimentos',
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
                if(title == 'Status' || title == 'Cidade/UF') {
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
                            d = ((d == true) ? 'Ativo' : 'Inativo');
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
        initComplete: function() {
            $('[type=search]').each(function () {
                $(this).after('<i class="fa fa-search"></i>');
            });
        },
        columns: [
            { "data": "id" },
            { "data": "nome" },
            { "data": "email" },
            { "data": "telefone" },
            {
                "data": null, "render": function ( data, type, row ) {
                    return row.municipio.nome + "/" + row.municipio.estado.uf;
                 }
            },
            {
                "data": "ativo", "render": function (data) {
                    if (data) {
                        return '<span class="badge bg-success">Ativo</span>'
                    } else {
                        return '<span class="badge bg-danger">Inativo</span>'
                    }
                }
            },
            {
                "data": null, "render": function (data) {
                    return '<div class="actions"><a class="btn btn-primary" href="/estabelecimento/editar/'+data.id+'">'+
                    '<i class="fas fa-pen"></i> Editar</a><a class="btn btn-danger" '+
                    'onclick="return confirm(\'Tem certeza que deseja excluir esse registro?\');" '+
                    'href="/estabelecimento/excluir/'+data.id+'"><i class="fas fa-trash"></i> Excluir</a></div>'
                }
            }
        ],
        language: {
            "url": "../json/Portuguese.json"
        }
    });
});
