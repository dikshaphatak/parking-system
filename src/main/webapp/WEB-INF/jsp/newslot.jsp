<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Add New Slot Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
</head>
<body>
<div class="container">
    <div class=" text-center mt-5 ">
        <h3>Add new Slot</h3>
    </div>
<form id="addSlotForm" onsubmit="addSlot()">
    <div class="form-group">
        <label for="slotnumber">Slot Number</label>
        <input type="number" class="form-control" id="slotnumber" placeholder="Enter Slot Number">
    </div>
    <div class="form-group">
        <label for="slottype">Slot Type</label>
        <select class="form-select" id="slottype">
            <option selected>Slot type</option>
            <option value="TWO_WHEELER">TWO-WHEELER</option>
            <option value="FOUR_WHEELER">FOUR-WHEELER</option>
        </select>
    </div>
    <div>
        <button type="submit" class="btn btn-primary mt-3">Save</button>
        <button class="btn btn-danger mt-3">Cancel</button>
    </div>

</form>
</div>
</body>
<script>
    function addSlot(){
        var slotNumber = $('#slotnumber').val();
        var slotType = $('#slottype').val();
        var slotData={};
        slotData.slotNumber=slotNumber;
        slotData.slotType=slotType;
        $.ajax({
            url:"http://localhost:9090/parking/slot/add",
            type:"POST",
            contentType:"application/json",
            data: JSON.stringify(slotData),
            success:function(data){
                alert(data);
            }
        })

    }



</script>
</html>