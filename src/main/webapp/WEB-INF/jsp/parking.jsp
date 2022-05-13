<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Parking Page</title>

</head>
<body>
<h1 class="text-center text-light display-5" style="background-color: #5386E8;">PARKING</h1>
<div class="container">
<div class="mb-3">
    <a class="btn btn-primary btn-lg" id="openAddModal" role="button">Add New Vehicle</a>
</div>
<div>
    <table id="parkingData">
        <thead>
        <tr>
            <th>Slot Number</th>
            <th>Slot Type</th>
            <th>Owner Name</th>
            <th>Contact Number</th>
            <th>Vehicle Number</th>
            <th>Park In Time</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="parking" items="${parkings}">
            <tr id="parkingRow_${parking.id}">
                <td>${parking.slotNumber}</td>
                <td>${parking.slotType}</td>
                <td>${parking.ownerName}</td>
                <td>${parking.contactNumber}</td>
                <td>${parking.vehicleNumber}</td>
                <td>${parking.parkInTime}</td>
                <td>${parking.availability}</td>
                <td><a class="btn btn-danger" id ="openParkoutModal" onclick="parkingCharge(${parking.id})">Parkout</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</div>
<!-- Modal for Adding vehicle-->
<div class="modal fade" id="addVehicleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header text-center">
                <h4 class="modal-title w-100 font-weight-bold">Add Vehicle</h4>
                <button type="button" id="closeParkingForm" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body mx-3">
                <form id="addVehicleForm" onsubmit="addVehicle(event)">
                <div class="md-form mb-5">
                    <label for="vehicleNumber" class="col-form-label">Vehicle Number</label>
                    <input type="text" class="form-control" id="vehicleNumber" name="vehiclenumber" placeholder="Enter Vehicle Number" >
                    <label id="vehicleNumber-error" class="error" for="vehicleNumber" style="color: red"></label>
                </div>
                <div class="md-form mb-5">
                    <label for="ownerName" class="col-form-label">Owner Name</label>
                    <input type="text" id="ownerName" class="form-control" name="ownername" placeholder="Enter Owner Name" name="ownername">
                    <label id="ownerName-error" class="error" for="ownerName" style="color: red"></label>
                </div>

                <div class="md-form mb-5">
                    <label for="contactNumber" class="col-form-label">Contact Number</label>
                    <input type="number" id="contactNumber" class="form-control" name="contactnumber" placeholder="Enter Contact Number" maxlength="10">
                    <label id="contactNumber-error" class="error" for="contactNumber" style="color: red"></label>
                </div>

                <div class="md-form mb-5">
                    <label for="slotNumber" class="col-form-label">Slot Number</label>
                    <select class="form-select" id="slotNumber" name="slotnumber" >
                        <option selected value="">Slot Number</option>
                        <c:forEach var="slot" items="${slots}">
                            <option value=${slot.id}>${slot.slotNumber}</option>
                        </c:forEach>
                    </select>
                    <label id="slotNumber-error" class="error" for="slotNumber" style="color: red"></label>
                </div>
                    <div class="modal-footer">
                        <button type="submit" id="savebutton" class="close btn btn-primary" data-dismiss="modal" disabled="disabled">Save</button>
                        <button type="button" id="cancelParkingForm" class="close btn btn-danger" data-dismiss="modal" aria-label="Close">
                            Cancel
                        </button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>

<!-- Modal for parkout popup-->
<div class="modal fade" id="parkoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Parking out</h5>
                <button type="button" id="closeParkingOutModal" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure you want to parkout?<br>
                Parking Charges : <span id="parkingCharge" class="font-weight-bold"></span>
            </div>
            <div class="modal-footer">
                <button type="button" id="cancelParkingOutModal" class="close btn btn-danger" data-dismiss="modal" aria-label="Close">
                    Cancel
                </button>
                <button type="button" class="btn btn-primary" onclick="removeVehicle()">Confirm</button>
            </div>
        </div>
    </div>
</div>


</body>

    <script>
        var charges=0;

        var parkingId;

        $(document).ready(function () {
            $('#parkingData').DataTable();
            $('#addVehicleForm').validate({
                rules:{
                    vehiclenumber: {
                        required: true,
                        minlength: 10
                    },
                    ownername:{
                        required: true
                    },
                    contactnumber:{
                        required: true,
                        number: true,
                        minlength: 10,
                        maxlength: 10
                    },
                    slotnumber:{
                        required: true,
                        minlength:1
                    }

                },
                messages:{
                    vehiclenumber: {
                        required: "Please enter valid vehicle number",
                        minlength:"Please enter 10 character vehicle number"
                    },
                    ownername:{
                        required:"Please enter name of vehicle owner"
                    },
                    contactnumber: {
                        required: "Please enter valid contact number",
                        minlength: "Please enter 10 digits contact number"
                    },
                    slotnumber: {
                        required: "Please enter available slot number"
                    }

                }
            })
            $('#vehicleNumber, #ownerName, #contactNumber, #slotNumber').on('blur',function() {
                console.log("parking blur called")
                console.log($('#vehicleNumber').val());
                console.log($('#ownerName').val());
                console.log($('#contactNumber').val());
                console.log($('#slotNumber').val());
                var slotId = $('#slotNumber').val();
                if($('#vehicleNumber').val()=='' ||$('#ownerName').val()=='' ||$('#contactNumber').val()=='' ||$('#slotNumber').val()=='') {
                    $('#savebutton').prop('disabled', true);
                } else {
                    $('#savebutton').prop('disabled', false);
                }
            });
        });

        $('#openAddModal').click(function() {
            $('#addVehicleModal').modal('show');
            $('#addVehicleForm').trigger('reset');
            $('#savebutton').prop('disabled', true);
        });

        $('#closeParkingForm,#cancelParkingForm').click(function(){
            $('#addVehicleModal').modal('hide');
            $('#addVehicleForm').trigger('reset');
        })

        $('#closeParkingOutModal, #cancelParkingOutModal').click(function(){
            $('#parkoutModal').modal('hide');
            charges=0;
            parkingId=null;
        })


        function addVehicle(e){
            e.preventDefault();
            var parkingTable = $('#parkingData').DataTable();
            var vehiclenumber = $('#vehicleNumber').val();
            var ownername = $('#ownerName').val();
            var contactnumber = $('#contactNumber').val();
            var slotnumber = $('#slotNumber').val();
            var vehicleData={};
            vehicleData.vehicleNumber=vehiclenumber;
            vehicleData.ownerName=ownername;
            vehicleData.contactNumber = contactnumber;
            vehicleData.slotId = slotnumber;
            $.ajax({
                url:"/parking/vehicle/add",
                type:"POST",
                contentType:"application/json",
                data: JSON.stringify(vehicleData),

            }).done(function(response){
                    parkingTable.row.add([
                        response.slotNumber,
                        response.slotType,
                        response.ownerName,
                        response.contactNumber,
                        response.vehicleNumber,
                        response.parkInTime,
                        response.availability,
                        '<a class="btn btn-danger"  onclick="parkingCharge('+response.id+')">Parkout</a>'
                    ]).draw(false);
                    $('#addVehicleModal').modal('hide');
                    parking();
                    console.log(response);
                }).fail(function (response){
                    console.log(response.responseJSON.errormessage);
            })

        }

        function parkingCharge(vehicleId){
            $('#parkoutModal').modal('show');

            $.ajax({
                url:"/parking/parking/charges?id="+vehicleId,
                type:"GET",
                contentType:"application/json",
                success: function(response) {
                    $('#parkingCharge').html(response);
                    charges=response;
                    parkingId=vehicleId;
                }
            })
        }

        function removeVehicle(){
            $('#parkoutModal').modal('show');
            var parkingTable = $('#parkingData').DataTable();
            $.ajax({
                url:"/parking/parkout?id="+parkingId+"&charges="+charges,
                type:"PUT",
                contentType:"application/json",
                success: function(response) {
                    parkingTable.row($('#parkingRow_'+parkingId)).remove().draw();
                    $('#parkoutModal').modal('hide');
                    parkingId=null;
                    charges=0;
                }
            })
        }


    </script>

</html>