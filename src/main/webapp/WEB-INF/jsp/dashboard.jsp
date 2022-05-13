<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Dashboard Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>

<%--    <jsp:include page="slot.jsp"></jsp:include>--%>
<%--    <jsp:include page="parking.jsp"></jsp:include>--%>
<%--    <jsp:include page="parkinghistory.jsp"></jsp:include>--%>

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #5F3EA7;">
    <div class="container-fluid">
        <a class="navbar-brand text-light" href="#">Parking System</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <div class="nav-link text-light" aria-current="page" onclick="slot()">Slots</div>
                </li>
                <li class="nav-item">
                    <div class="nav-link text-light" onclick="parking()">Parking</div>
                </li>
                <li class="nav-item">
                    <div class="nav-link text-light" onclick="history()">History</div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div id="dashboard">

</div>
</body>
<script>

    $(document).ready(function (){
        parking();
    })

   function slot() {
               $.ajax({
                   url:"/parkingsystem/dashboard/slot",
                   type:"GET",
                   success:function(response){
                       $('#dashboard').html(response);
                   }
               })
   }

   function parking() {
       $.ajax({
           url:"/parkingsystem/dashboard/parking",
           type:"GET",
           success:function(response){
               $('#dashboard').html(response);
           }
       })
   }

   function history() {
       $.ajax({
           url:"/parkingsystem/dashboard/history",
           type:"GET",
           success:function(response){
               $('#dashboard').html(response);
           }
       })
   }



</script>
</html>