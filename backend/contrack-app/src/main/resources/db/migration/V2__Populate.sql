-- PERFIS (em minúsculas para passar no CHECK)
INSERT INTO perfil (tipo) VALUES
  ('gerente'),   -- id 1
  ('dev'),       -- id 2
  ('qa'),        -- id 3
  ('security');  -- id 4

-- PESSOAS
INSERT INTO pessoa (nome) VALUES
  ('Henrique Marchiori'), -- id 1
  ('Henrique Schultz'),   -- id 2
  ('Isabela Souza'),      -- id 3
  ('João Vithor'),        -- id 4
  ('Gabriel Velloso');    -- id 5

-- PROJETOS
INSERT INTO projeto (nome, data_inicio, data_fim, descricao) VALUES
  ('Projeto de Realidade Virtual', DATE '2025-12-01', DATE '2026-12-15', 'Sistema de E-Commerce em Realidade Virtual'), -- id 1
  ('AGES 4',                        DATE '2025-09-23', DATE '2025-12-31', 'Projeto prático de Engenharia de Software'),  -- id 2
  ('Projeto de Criptomoedas',       DATE '2024-03-15', DATE '2027-06-22', 'Aplicando Bitcoin em Jogos de Luta'),         -- id 3
  ('Contrack',                      DATE '2025-09-22', DATE '2025-09-26', 'Projeto de Gerenciamento de Contratos'),      -- id 4
  ('PUCRS/DELL IT Academy #22',     DATE '2025-06-12', DATE '2026-01-05', 'Treinamento da Cohort #22');                   -- id 5

-- CONTRATOS
INSERT INTO contrato (data_inicio, data_fim, horas_semana, salario_hora, pessoa_id) VALUES
  (DATE '2024-01-01', DATE '2026-12-31', 30,  80.00, 1), -- Henrique M.
  (DATE '2024-02-01', DATE '2026-12-31', 30,  80.00, 2), -- Henrique S.
  (DATE '2024-01-01', DATE '2026-12-31', 30,  70.00, 3), -- Isabela
  (DATE '2024-01-01', DATE '2026-12-31', 30,  90.00, 4), -- João
  (DATE '2024-01-01', DATE '2026-12-31', 40, 120.00, 5); -- Gabriel

-- ALOCAÇÕES
-- Projeto 1
INSERT INTO alocacao (horas_semana, pessoa_id, projeto_id, perfil_id) VALUES
  (8,  5, 1, 1),  -- Gabriel (gerente)
  (10, 1, 1, 2),  -- Henrique M. (dev)
  (6,  3, 1, 3),  -- Isabela (qa)
  (8,  4, 1, 4);  -- João (security)

-- Projeto 2
INSERT INTO alocacao (horas_semana, pessoa_id, projeto_id, perfil_id) VALUES
  (8,  5, 2, 1),
  (16, 2, 2, 2),
  (6,  3, 2, 3),
  (12, 4, 2, 4);

-- Projeto 3
INSERT INTO alocacao (horas_semana, pessoa_id, projeto_id, perfil_id) VALUES
  (8,  5, 3, 1),
  (10, 1, 3, 2),
  (6,  3, 3, 3),
  (10, 4, 3, 4);

-- Projeto 4
INSERT INTO alocacao (horas_semana, pessoa_id, projeto_id, perfil_id) VALUES
  (8,  5, 4, 1),
  (16, 2, 4, 2),
  (6,  3, 4, 3);

-- Projeto 5
INSERT INTO alocacao (horas_semana, pessoa_id, projeto_id, perfil_id) VALUES
  (8,  5, 5, 1),
  (10, 1, 5, 2),
  (6,  3, 5, 3);
