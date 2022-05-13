<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>History Page</title>
</head>
<body>
<h1 class="text-center text-light display-5" style="background-color: #5386E8;">HISTORY</h1>
<div class="container">
    <table id="historyData">
        <thead>
        <tr>
            <th>Slot Number</th>
            <th>Vehicle Number</th>
            <th>Owner Name</th>
            <th>Contact Number</th>
            <th>Park In Time</th>
            <th>Park Out Time</th>
            <th>Payment</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="history" items="${histories}">
            <tr>
                <td>${history.slotNumber}</td>
                <td>${history.vehicleNumber}</td>
                <td>${history.ownerName}</td>
                <td>${history.contactNumber}</td>
                <td>${history.parkInTime}</td>
                <td>${history.parkOutTime}</td>
                <td>${history.parkingCharge}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
    <script>
        $(document).ready(function () {
            $('#historyData').DataTable({
                "serverSide": true,
                "ajax": "/parking/list/parkoutdetails"
            });

        });
    </script>

</html>