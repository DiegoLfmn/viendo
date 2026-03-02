-- Insertar roles básicos si no existen
INSERT INTO rol (nombre_rol, descripcion_rol)
SELECT 'ADMINISTRADOR', 'Administrador del sistema PIU con acceso total'
WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre_rol = 'ADMINISTRADOR');

INSERT INTO rol (nombre_rol, descripcion_rol)
SELECT 'GESTOR', 'Gestor de dispositivos PIU'
WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre_rol = 'GESTOR');

INSERT INTO rol (nombre_rol, descripcion_rol)
SELECT 'OPERADOR', 'Operador de la red de PIUs'
WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre_rol = 'OPERADOR');

INSERT INTO rol (nombre_rol, descripcion_rol)
SELECT 'ESTUDIANTE', 'Estudiante universitario'
WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre_rol = 'ESTUDIANTE');

INSERT INTO rol (nombre_rol, descripcion_rol)
SELECT 'PROFESOR', 'Profesor universitario'
WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre_rol = 'PROFESOR');

INSERT INTO rol (nombre_rol, descripcion_rol)
SELECT 'FUNCIONARIO', 'Funcionario administrativo universitario'
WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre_rol = 'FUNCIONARIO');

INSERT INTO rol (nombre_rol, descripcion_rol)
SELECT 'VISITANTE', 'Visitante externo sin cuenta'
WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre_rol = 'VISITANTE');
