<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Slot Page</title>
</head>
<body>

<h1 class="text-center text-light display-5" style="background-color: #5386E8;">SLOTS</h1>
<div class="container">
<div class="mb-3">
    <a class="btn btn-primary btn-lg float-right" id="openAddModal" role="button">Add New Slot</a>
</div>

<div>
    <table id="slotData">
        <thead>
        <tr>
            <th>Slot Number</th>
            <th>Slot type</th>
            <th>Availability</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="slot" items="${slots}">
            <tr>
                <td>${slot.slotNumber}</td>
                <td>${slot.slotType}</td>
                <td>${slot.availability}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</div>

<div class="modal fade" id="addSlotModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header text-center">
                <h4 class="modal-title w-100 font-weight-bold">Add New Slot</h4>
                <button type="button" id="dismiss" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body mx-3">
                <form id="addSlotForm" onsubmit="addSlot(event)">
                <div class="md-form mb-5">
                    <label for="slotNumber" class="col-form-label">Slot Number</label>
                    <input type="number" class="form-control" id="slotNumber" placeholder="Enter Slot Number" name="slotnumber">
                    <label id="slotNumber-error" class="error" for="slotNumber" style="color: red"></label>

                </div>
                <div class="md-form mb-5">
                    <label for="slotType">Slot Type</label>
                    <select class="form-select" id="slotType" name="slottype">
                        <option value="">Slot type</option>
                        <option value="TWO_WHEELER">TWO-WHEELER</option>
                        <option value="FOUR_WHEELER">FOUR-WHEELER</option>
                    </select>
                    <label id="slotType-error" class="error" for="slotType" style="color: red"></label>
                </div>
                    <div>
                        <button id="savebutton" type="submit" class="close btn btn-primary" data-dismiss="modal"  modelattribute="slot" disabled="disabled">Save</button>
                        <button type="button" id="closeSlotForm" class="close btn btn-danger" data-dismiss="modal" aria-label="Close">
                            Cancel
                        </button>

                    </div>
                </form>
            </div>


        </div>
    </div>
</div>

</body>
    <script>
        $(document).ready(function () {
            $('#slotData').DataTable();
            $('#addSlotForm').validate({
                rules:{
                    slotnumber: {
                        required: true
                    },
                    slottype:{
                        required: true
                    }
                },
                messages:{
                    slotnumber: {
                        required: "Please enter valid slot number"

                    },
                    slottype: {
                        required: "Please enter available slot type"
                    }
                }
            })
            // $('#slotNumber,#slotType').on('keydown change',function() {
            //
            //     if($('#slotNumber').val() == '' || $('#slotType').val()=='') {
            //         $('#savebutton').prop('disabled', true);
            //     } else {
            //         $('#savebutton').prop('disabled', false);
            //     }
            // });

            $('#slotNumber,#slotType').on('blur',function() {
                console.log("blur called!!");
                console.log($('#slotNumber').val());
                console.log($('#slotType').val());
                var slotNumber = $('#slotNumber').val();
                checkSlot(slotNumber);
            });

        });



        $('#openAddModal').click(function() {
            $('#addSlotModal').modal('show');
            $('#addSlotForm').trigger('reset');
            $('#savebutton').prop('disabled', true);
        });

        $('#dismiss,#closeSlotForm').click(function(){
            $('#addSlotModal').modal('hide');
            $('#addSlotForm').trigger('reset');
        })


        function addSlot(e){
            e.preventDefault();
            var datatable= $('#slotData').DataTable();
            var slotNumber = $('#slotNumber').val();
            var slotType = $('#slotType').val();
                var slotData={};
                slotData.slotNumber=slotNumber;
                slotData.slotType=slotType;
                $.ajax({
                    url: "/parking/slot/add",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(slotData),//jsonstring

                }).done(function(response){
                    console.log(response)
                    datatable.row.add([
                            response.slotNumber,
                            response.slotType,
                            response.availability
                        ]
                    ).draw(false);
                    $('#addSlotModal').modal('toggle');
                })
        }

        function checkSlot(slotNumber){
            $('#savebutton').prop('disabled', true);
            $.ajax({
                url: "/parking/slot/checkslot?slotNumber="+slotNumber,
                type: "GET",
                contentType: "application/json",
                success: function (response){
                    console.log(response);
                    if(!response){
                        $('#savebutton').prop('disabled', false);
                        $('#slotNumber-error').html('');
                        $('#slotNumber-error').css('display','none');
                    }else {
                        $('#slotNumber-error').html('');
                        $('#slotNumber-error').html('Slot already exists');
                        $('#slotNumber-error').css('display','block');
                    }
                }
            })
        }

    </script>

</html>