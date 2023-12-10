package br.com.api.mgdexpress.MGD.EXPRESS.site.pageService;

public class Formulario {

    public static String formulario(){
        return  """
                <!DOCTYPE html>
                <html lang="pt-br">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Formulário de Pedido</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            height: 100vh;
                        }
                                
                        form {
                            background-color: #fff;
                            padding: 20px;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                            width: 400px;
                            margin-top: 10%;
                        }
                                
                        label {
                            display: block;
                            margin-bottom: 8px;
                        }
                                
                        input, select {
                            width: 100%;
                            padding: 8px;
                            margin-bottom: 16px;
                            box-sizing: border-box;
                            border: 1px solid #ccc;
                            border-radius: 4px;
                        }
                                
                        button {
                            background-color: #4caf50;
                            color: #fff;
                            padding: 10px;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                            width: 100%;
                        }
                                
                        button:hover {
                            background-color: #45a049;
                        }
                    </style>
                </head>
                <body>
                                
                <form action="http://localhost:8080/pedidos" method="post">
                    <label for="nomeEstabelecimento">Nome do Estabelecimento:</label>
                    <input type="text" id="nomeEstabelecimento" name="nomeEstabelecimento" required/>
                                
                    <label for="localOrigem">Local de Origem:</label>
                    <input type="text" id="localOrigem" name="localOrigem" required/>
                                
                    <label for="localDestino">Local de Destino:</label>
                    <input type="text" id="localDestino" name="localDestino" required/>
                                
                    <label for="valor">Valor:</label>
                    <input type="number" id="valor" name="valor" required/>
                                
                    <label for="observacao">Observação:</label>
                    <textarea id="observacao" name="observacao"></textarea>
                                
                    <label for="itensDoPedido">Itens do Pedido:</label>
                    <input type="text" id="itensDoPedido" name="itensDoPedido" required/>
                                
                    <label for="status">Status:</label>
                    <select id="status" name="status">
                        <option value="pendente">Pendente</option>
                        <option value="em_andamento">Em Andamento</option>
                        <option value="concluido">Concluído</option>
                    </select>
                                
                    <label for="dataCriacao">Data de Criação:</label>
                    <input type="date" id="dataCriacao" name="dataCriacao" required>
                                
                    <label for="gerenteId">ID do Gerente:</label>
                    <input type="text" id="gerenteId" name="gerenteId" required>
                                
                    <button type="submit">Criar</button>
                </form>
                                
                </body>
                </html>
                """;
    }
}
