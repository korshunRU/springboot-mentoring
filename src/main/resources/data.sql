-- SET IDENTITY_INSERT privileges ON;
INSERT INTO privileges (id, name)VALUES
(1, 'read'),
(2, 'write'),
(3, 'edit'),
(4, 'delete');
-- SET IDENTITY_INSERT privileges OFF;

-- SET IDENTITY_INSERT roles ON;
INSERT INTO roles (id, name)VALUES
(1, 'admin'),
(2, 'user');
-- SET IDENTITY_INSERT privileges OFF;

INSERT INTO roles_authority (role_id, autority_id)VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(2, 3);