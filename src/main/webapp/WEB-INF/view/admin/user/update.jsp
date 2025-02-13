<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

      <!DOCTYPE html>
      <html lang="en">

      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Update User Page</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
          crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
      </head>

      <body>
        <div class="container mt-5">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <h3>Update a user</h3>
              <hr />
              <form:form method="post" action="/admin/user/update" modelAttribute="user">
                <div class="mb-3" style="display: none;">
                  <label class="form-label">Id: </label>
                  <form:input type="text" class="form-control" path="id" />
                </div>
                <div class="mb-3">
                  <label class="form-label">Email:</label>
                  <form:input type="email" class="form-control" path="email" disabled="true" />
                </div>
                <!-- <div>
                  <form:hidden path="password" />
                </div> -->
                <div class="mb-3">
                  <label class="form-label">Phone number:</label>
                  <form:input type="text" class="form-control" path="phone" />
                </div>
                <div class="mb-3">
                  <label class="form-label">Full name:</label>
                  <form:input type="text" class="form-control" path="fullName" />
                </div>
                <div class="mb-3">
                  <label class="form-label">Address:</label>
                  <form:input type="text" class="form-control" path="address" />
                </div>
                <button type="submit" class="btn btn-warning">Update</button>
                <a href="/admin/user" class="btn btn-success mx-2">Back</a>

              </form:form>
            </div>
          </div>
        </div>
      </body>

      </html