CREATE KEYSPACE meudimdim
WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': 3};

USE meudimdim;

DROP TABLE IF EXISTS meudimdim;

CREATE TABLE despesa (
    id_despesa text,
    data_lancamento timestamp,
    categoria text,
    pago boolean,
    valor decimal,
    vencimento int,
    PRIMARY KEY (id_despesa, data_lancamento)
) WITH CLUSTERING ORDER BY (data_lancamento DESC);