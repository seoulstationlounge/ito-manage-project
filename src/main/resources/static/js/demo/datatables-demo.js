// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').show();
  $('#dataTable').DataTable({
    dom : 'Bfrtip',
    buttons: [
      {
        extend:    'copyHtml5',
        text:      '<i class="fa fa-files-o"></i>',
        titleAttr: 'Copy'
      },
      {
        extend:    'excelHtml5',
        text:      '<i class="fa fa-file-excel-o"></i>',
        titleAttr: 'Excel'
      },
      {
        extend:    'csvHtml5',
        text:      '<i class="fa fa-file-text-o"></i>',
        titleAttr: 'CSV'
      }
    ],
    select: true,
    scrollY: 600,
    scrollX: 1000,
    columnDefs: [
      { targets: 0, visible: false }
    ]
  });

});
