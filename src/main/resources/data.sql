USE projeto_final;

INSERT INTO type (name)
SELECT 'Administrador' WHERE NOT EXISTS (SELECT 1 FROM type WHERE name = 'Administrador');

INSERT INTO type (name)
SELECT 'Técnico' WHERE NOT EXISTS (SELECT 1 FROM type WHERE name = 'Técnico');

INSERT INTO type (name)
SELECT 'Cliente' WHERE NOT EXISTS (SELECT 1 FROM type WHERE name = 'Cliente');

INSERT INTO department (name)
SELECT 'Financeiro' WHERE NOT EXISTS (SELECT 1 FROM department WHERE name = 'Financeiro');

INSERT INTO department (name)
SELECT 'Operacional' WHERE NOT EXISTS (SELECT 1 FROM department WHERE name = 'Operacional');

INSERT INTO department (name)
SELECT 'Recursos Humanos' WHERE NOT EXISTS (SELECT 1 FROM department WHERE name = 'Recursos Humanos');

INSERT INTO department (name)
SELECT 'Administrativo' WHERE NOT EXISTS (SELECT 1 FROM department WHERE name = 'Administrativo');

INSERT INTO department (name)
SELECT 'Comercial' WHERE NOT EXISTS (SELECT 1 FROM department WHERE name = 'Comercial');

INSERT INTO department (name)
SELECT 'Tecnologia da Informação' WHERE NOT EXISTS (SELECT 1 FROM department WHERE name = 'Tecnologia da Informação');

INSERT INTO status (name)
SELECT 'Aguardando Técnico' WHERE NOT EXISTS (SELECT 1 FROM status WHERE name = 'Aguardando Técnico');

INSERT INTO status (name)
SELECT 'Em atendimento' WHERE NOT EXISTS (SELECT 1 FROM status WHERE name = 'Em atendimento');

INSERT INTO status (name)
SELECT 'Escalado para outro setor' WHERE NOT EXISTS (SELECT 1 FROM status WHERE name = 'Escalado para outro setor');

INSERT INTO status (name)
SELECT 'Finalizado' WHERE NOT EXISTS (SELECT 1 FROM status WHERE name = 'Finalizado');

INSERT INTO priority (name)
SELECT 'Baixo' WHERE NOT EXISTS (SELECT 1 FROM priority WHERE name = 'Baixo');

INSERT INTO priority (name)
SELECT 'Médio' WHERE NOT EXISTS (SELECT 1 FROM priority WHERE name = 'Médio');

INSERT INTO priority (name)
SELECT 'Alto' WHERE NOT EXISTS (SELECT 1 FROM priority WHERE name = 'Alto');

INSERT INTO priority (name)
SELECT 'Urgente' WHERE NOT EXISTS (SELECT 1 FROM priority WHERE name = 'Urgente');