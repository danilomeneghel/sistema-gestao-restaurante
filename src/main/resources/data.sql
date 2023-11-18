INSERT INTO `categoria` VALUES (1,'Comida'),(2,'Salgado'),(3,'Bebida'),(4,'Bebida Alcoólica'),(5,'Sobremesa');

INSERT INTO `cardapio` VALUES (1,'Almoço'),(2,'Janta'),(3,'Lanche'),(4,'Café');

INSERT INTO `produto` VALUES (1,'Arroz'),(2,'Feijão'),(3,'Massa'),(4,'Batata Frita'),(5,'Carne Bovina'),(6,'Frango'),(7,'Ovo'),(8,'Guaraná Antarctica'),(9,'Coca-cola'),(10,'Cerveja Skol'),(11,'Xis Salada'),(12,'Pudim');

INSERT INTO `estado` VALUES (1,'São Paulo','SP'),(2,'Rio de Janeiro','RJ'),(3,'Rio Grande do Norte','RN'),(4,'Minas Gerais','MG');

INSERT INTO `municipio` VALUES (1,'Sorocaba',1),(2,'Campinas',1),(3,'São Paulo',1),(4,'Rio de Janeiro',2),(5,'São Gonçalo',2),(6,'Duque de Caxias',2),(7,'Natal',3),(8,'Alexandria',3),(9,'Angicos',3),(10,'Belo Horizonte',4),(11,'Mariana',4),(12,'Betim',4);

INSERT INTO `bairro` VALUES (1,'Campolim',1),(2,'Mangal',1),(3,'Além-Ponte',1),(4,'Botafogo',2),(5,'Cambuí',2),(6,'Guanabara',2),(7,'Sumaré',3),(8,'Lapa',3),(9,'Bela Vista',3),(10,'Cachambi',4),(11,'Méier',4),(12,'Jacarepaguá',4),(13,'Galo Branco',5),(14,'Estrela do Norte',5),(15,'Lindo Parque',5),(16,'Figueira',6),(17,'Pantanal',6),(18,'Campos Elísios',6),(19,'Guarapes',7),(20,'Igapó',7),(21,'Lagoa Azul',7),(22,'Cascalho',8),(23,'Conjunto da Ponte',8),(24,'Santo Antônio',8),(25,'Fernando Pedrosa',9),(26,'Alto da Alegria',9),(27,'Prefeito Jaime Batista',9),(28,'Savassi',10),(29,'Lourdes',10),(30,'Pampulha',10),(31,'Catete',11),(32,'Barro Preto',11),(33,'Galego',11),(34,'Alterosas',12),(35,'Imbiruçu',12),(36,'Petrovale',12);

INSERT INTO `usuario` VALUES (1,1,'admin@admin.com','Admin','$2a$10$1HAxsgoqtXCVASxpXGcnheACN8.SbB8iQZ5o4sktAOPQEE/k2B9Ue','ROLE_ADMIN','admin'),(2,1,'jose@jose.com','jose','$2a$10$5MnLWqAPG0pMmX3JTbXV/.RZMkq8NuMmKj3FaBFi4WH1cOUSpMueK','ROLE_USER','jose'),(3,0,'bruna@bruna.com','Bruna','$2a$10$sSuo.CAGj7FQ3hCqDgwMpOtooYuB6cQgko2.vaZy8JjW/Ru5EHKHu','ROLE_USER','bruna');
