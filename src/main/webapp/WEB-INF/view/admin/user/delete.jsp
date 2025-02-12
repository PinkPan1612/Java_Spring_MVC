<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <title>Deltete User ${id}</title>

                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
                    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
                    crossorigin="anonymous" />
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
                    crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
            </head>

            <body>
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <h3>Delete a user with id = ${id}</h3>
                            <hr />
                            <form action="/admin/user/confirm-delete/${id}">
                                <div class="alert alert-danger" role="alert">
                                    <p>
                                        Do you want to delete user with <span style="font-weight: bold;">id:
                                            ${id}</span>?
                                        <br>
                                        This action cannot be undone.
                                    </p>
                                </div>
                                <div class="mx-2">
                                    <button class="btn btn-danger">Confirm
                                        Delete</button>
                                    <a href="/admin/user" class="btn btn-success mx-2">Back</a>
                                </div>

                            </form>
                        </div>

            </body>

            </html>