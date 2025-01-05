<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Correo Electrónico Bootstrap 4</title>

    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@100;300;400;500;600;700;800;900&display=swap');

        body {
            font-family: 'Inter', sans-serif;
            background-color: rgb(243 244 246);
        }

        .email-container {
            background-color: #FFFFFF;
            overflow: hidden;
            margin-left: auto;
            margin-right: auto;
            max-width: 48rem;

        }

        .header {
            position: relative;
        }

        .header img {
            height: 16rem;
            object-fit: cover;
            width: 100%;
        }

        .content {
            padding: 2rem;
        }

        .content h1 {
            color: rgb(75 85 99);
            font-size: 1.25rem;
            font-weight: 700;
            line-height: 1.75rem;
            margin-bottom: 1rem;
            text-align: center;
        }

        .content ul li {
            margin-top: .75rem;
        }

        .content-text {
            color: rgb(107 114 128);
            font-weight: 600;
            margin-bottom: 1.5rem;
            font-size: 15px;
        }

        .action-btn {
            background-color: #006264;
            border-radius: 0.5rem;
            color: #FFFFFF;
            font-size: 15px;
            font-weight: 700;
            margin-top: 1rem;
            padding: 1rem 1.5rem;
            text-transform: uppercase;
            text-decoration: none;
        }

        .action-btn:hover {
            background-color: #009397;
        }

        .footer {
            padding: 0 2rem;
        }

        .footer-text {
            color: rgb(107 114 128);
            font-size: 0.75rem;
            font-style: italic;
            font-weight: 600;
            line-height: 1rem;
            padding-top: 1.5rem;
        }

        .url-text {
            color: #006264;
            text-decoration: none;
        }

        .text-center {
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <div class="header">
            <img class="w-full h-64 object-cover"
                 src="https://i.imgur.com/fupjjLt.png"
                 alt="Header Image"/>
        </div>
        <div class="content">
            <h1>Se ha creado una nueva hoja de gastos personales</h1>
            <p class="content-text">
                El operario {{fullName}} ha creado la siguiente hoja de gastos personales:
            </p>
            <ul class="content-text">
                <li>Proyecto: {{projectName}}</li>
                <li>Fecha de apertura: {{startDate}}</li>
                <li>Descripción: {{description}}</li>
            </ul>
            <p class="content-text">
                Una vez revisado, la hoja de gastos personales será aprobada o rechazada por administración.
            </p>
            <br />
            <div class="text-center">
                <a href="https://www.google.com/" class="action-btn">
                    Ir al detalle
                </a>
            </div>
        </div>
        <div class="footer">
            <p class="footer-text">Este correo electrónico se ha generado automáticamente a través de la aplicación <a href="https://gestepm.com/" class="url-text">GestEPM</a>.</p>
        </div>
    </div>
</body>
</html>