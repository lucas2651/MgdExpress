package br.com.api.mgdexpress.MGD.EXPRESS.site.pageService;

public class HistoricoEntregas {
    public static String html(){
        return """
                <head>
                  <meta charset="UTF-8">
                  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
                                
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <style>
                    body {
                      font-family: Arial, sans-serif;
                      margin: 0;
                      padding: 0;
                      background-color: #f4f4f4;
                    }
                                
                    .navbar {
                      background-color: #333;
                      padding: 10px;
                      color: white;
                      text-align: center;
                    }
                                
                    .container {
                      display: inline;
                   
                      justify-content: space-around;
                      padding: 20px;
                    }
                                
                    .card {
                      background-color: white;
                      border: 1px solid #ddd;
                      border-radius: 10px;
                      padding: 15px;
                      margin: 5px;
                     
                    }
                                
                    .btn-detalhe {
                      background-color: #d5dcd5;
                     
                      border: none;
                      padding: 10px 15px;
                      border-radius: 100%;
                      cursor: pointer;
                      margin-left: 15%;
                    }
                    .btn-detalhe i {
                      color: green; /* Cor verde para o ícone */
                    }
                    p{
                        margin-left: 10%;
                        display: inline;
                    }
                    h2{
                        display: inline;
                    }
                   
                  </style>
                </head>
                <body>
                                
                  <div class="navbar">
                   
                    <h1>Entregas Feitas</h1>
                  </div>
                                
                  <div id="container">
                    
                                
                            
                  </div>
                  </body>
                                
                """;
    }
}