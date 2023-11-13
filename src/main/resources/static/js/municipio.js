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
                    window.location.href = '/localidade/municipio/cadastro';
                }
            },
            {
                extend:    'pdfHtml5',
                text:      '<i class="fas fa-file-pdf"></i> ',
                titleAttr: 'Exportar para PDF',
                className: 'btn btn-danger',
                orientation:'landscape',
                title: function () { return 'Gestão de Restaurante - Municípios'; },
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
                title: function () { return 'Gestão de Restaurante - Municípios'; },
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
                title: function () { return 'Gestão de Restaurante - Municípios'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)'
               }
            }
        ],
        ajax: {
            url: '/api/localidade/municipios',
            type: 'GET',
            datatype: 'json',
            dataSrc: ''
        },
        initComplete: function() {
            $('[type=search]').each(function () {
                $(this).after('<i class="fa fa-search"></i>');
            });
        },
        columns: [
            { "data": "id" },
            { "data": "nome" },
            { "data": "estado.nome" },
            {
                "data": null, "render": function (data) {
                    return '<div class="actions"><a class="btn btn-primary" href="/localidade/municipio/editar/'+data.id+'">'+
                    '<i class="fas fa-pen"></i> Editar</a><a class="btn btn-danger" '+
                    'onclick="return confirm(\'Tem certeza que deseja excluir esse registro?\');" '+
                    'href="/localidade/municipio/excluir/'+data.id+'"><i class="fas fa-trash"></i> Excluir</a></div>'
                }
            }
        ],
        language: {
            "url": "../json/Portuguese.json"
        }
    });
});
