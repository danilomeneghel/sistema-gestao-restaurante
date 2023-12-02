$(document).ready(function () {
    dataTable = $('#data-table').DataTable({
        responsive: true,
        dom: 'Blfrtip',
        buttons: [
            {
                extend:    'pdf',
                text:      '<i class="fas fa-file-pdf"></i> ',
                titleAttr: 'Exportar para PDF',
                className: 'btn btn-danger',
                orientation:'landscape',
                title: function () { return 'Gestão de Imóveis - Bairros'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)'
                },
                customize: function (doc) {
                    doc.content[1].table.widths = "*";
                }
            },
            {
                extend:    'excel',
                text:      '<i class="fas fa-file-excel"></i> ',
                titleAttr: 'Exportar para Excel',
                className: 'btn btn-success',
                orientation:'landscape',
                title: function () { return 'Gestão de Imóveis - Bairros'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)'
                }
            },
            {
                extend:    'csv',
                text:      '<i class="fas fa-file-csv"></i> ',
                titleAttr: 'Exportar para CSV',
                className: 'btn btn-info',
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
                title: function () { return 'Gestão de Imóveis - Bairros'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)'
               }
            }
        ],
        ajax: {
            url: '/api/localidade/bairros',
            type: 'GET',
            datatype: 'json',
            dataSrc: ''
        },
        initComplete: function() {
            $('#data-table_length').each(function () {
                $(this).append('<a class="btn btn-success btn-add" href="/localidade/bairro/cadastro">' +
                '<i class="fas fa-plus"></i> Adicionar</a>');
            });
            $('[type=search]').each(function () {
                $(this).after('<i class="fa fa-search"></i>');
            });
        },
        columns: [
            { "data": "id" },
            { "data": "nome" },
            { "data": "municipio.nome" },
            { "data": "municipio.estado.nome" },
            {
                "data": null, "render": function (data) {
                    return '<div class="actions"><a class="btn btn-primary" href="/localidade/bairro/editar/'+data.id+'">'+
                    '<i class="fas fa-pen"></i> Editar</a><a class="btn btn-danger" '+
                    'onclick="return confirm(\'Tem certeza que deseja excluir esse registro?\');" '+
                    'href="/localidade/bairro/excluir/'+data.id+'"><i class="fas fa-trash"></i> Excluir</a></div>'
                }
            }
        ],
        language: {
            "url": "../json/Portuguese.json",
            "searchPlaceholder": "Pesquisar"
        }
    });
});
