
INSERT INTO perfil (tipo) VALUES
      ('GERENTE'),
      ('DEV'),
      ('QA'),
      ('SECURITY');

INSERT INTO pessoa (nome) VALUES
      ('Henrique Marchiori'),
      ('Henrique Schultz'),
      ('Isabela Souza'),
      ('João Vithor'),
      ('Gabriel Velloso');

INSERT INTO projeto (nome, data_inicio, data_fim, descricao) VALUES
     ('Projeto de Realidade Virtual',     DATE '2025-12-01', DATE '2026-12-15', 'Sistema de E-Commerce em Realidade Virtual'),
     ('AGES 4',   DATE '2025-09-23', DATE '2025-12-31', 'Projeto prático de Engenharia de Software'),
     ('Projeto de Criptomoedas',      DATE '2024-03-15', DATE '2027-06-22', 'Aplicando Bitcoin em Jogos de Luta'),
     ('Contrack',DATE '2025-09-22', DATE '2025-09-26', 'Projeto de Gerenciamento de Contratos'),
     ('PUCRS/DELL IT Academy #22',   DATE '2025-06-12', DATE '2026-01-05', 'Treinamento da Cohort #22');

INSERT INTO contrato (data_inicio, data_fim, horas_semana, salario_hora, pessoa_id, perfil_id) VALUES
   (DATE '2024-01-01', DATE '2026-12-31', 30,  80.00, 1, 2), -- Henrique M. -> DEV
   (DATE '2024-02-01', DATE '2026-12-31', 30,  80.00, 2, 2), -- Henrique S. -> DEV
   (DATE '2024-01-01', DATE '2026-12-31', 30,  70.00, 3, 3), -- Isabela -> QA
   (DATE '2024-01-01', DATE '2026-12-31',  30,  90.00, 4, 4), -- João -> SECURITY
   (DATE '2024-01-01', DATE '2026-12-31', 40, 120.00, 5, 1); -- Gabriel -> GERENTE
-- Projeto 1: Realidade Virtual
INSERT INTO alocacao (horas_semana, pessoa_id, projeto_id) VALUES
   (8,  5, 1),  -- Gabriel (GERENTE)
   (10, 1, 1),  -- Henrique M. (DEV)
   (6,  3, 1),  -- Isabela (QA)
   (8,  4, 1);  -- João (SECURITY)

-- Projeto 2: AGES 4
INSERT INTO alocacao (horas_semana, pessoa_id, projeto_id) VALUES
   (8,  5, 2),  -- Gabriel (GERENTE)
   (16, 2, 2),  -- Henrique S. (DEV)
   (6,  3, 2), -- Isabela (QA)
   (12,  4, 1);  -- João (SECURITY)


-- Projeto 3: Criptomoedas
INSERT INTO alocacao (horas_semana, pessoa_id, projeto_id) VALUES
   (8,  5, 3),  -- Gabriel (GERENTE)
   (10, 1, 3),  -- Henrique M. (DEV)
   (6,  3, 3),  -- Isabela (QA)
   (10,  4, 1);  -- João (SECURITY)

-- Projeto 4: Contrack
INSERT INTO alocacao (horas_semana, pessoa_id, projeto_id) VALUES
   (8,  5, 4),  -- Gabriel (GERENTE)
   (16, 2, 4),  -- Henrique S. (DEV)
   (6,  3, 4);  -- Isabela (QA)

-- Projeto 5: PUCRS/DELL IT Academy #22
INSERT INTO alocacao (horas_semana, pessoa_id, projeto_id) VALUES
   (8,  5, 5),  -- Gabriel (GERENTE)
   (10, 1, 5),  -- Henrique M. (DEV)
   (6,  3, 5);  -- Isabela (QA)