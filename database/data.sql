USE SpeedyProjectDB;
GO
INSERT INTO dbo.Users (name, email, passwd)
VALUES ('Usuario Demo', 'demo@speedy.com', HASHBYTES('SHA2_256', N'demo1234'));
GO

---- Comercios ----
INSERT INTO dbo.Commerces (name, category, image_path) VALUES
    ('Speedy Tech',        'Tecnologia',  'comercio.png'),
    ('Speedy Farmacia',    'Farmacia',    'comercio.png'),
    ('Speedy Restaurante', 'Restaurante', 'comercio.png');
GO

----- Productos - Tecnología ----
INSERT INTO dbo.Products (commerce_id, name, description, price, image_path) VALUES
    (1, 'PC Gamer',        'Computadora de escritorio para videojuegos', 3499.90, 'pc.png'),
    (1, 'Monitor 24"',     'Monitor Full HD 24 pulgadas',                 599.90,  'monitor.png'),
    (1, 'Laptop Pro',      'Laptop para trabajo y estudio',              2899.90, 'laptop.png');
GO

---- Productos - Farmacia ----
INSERT INTO dbo.Products (commerce_id, name, description, price, image_path) VALUES
    (2, 'Termómetro digital', 'Termómetro digital de uso clínico',   25.90, 'termometro.png'),
    (2, 'Pañales talla M',    'Paquete x30 unidades',                 45.50, 'panales.png'),
    (2, 'Ibuprofeno 400mg',   'Caja x20 tabletas',                    12.90, 'Ibuprofeno.png');
GO

---- Productos - Restaurante ----
INSERT INTO dbo.Products (commerce_id, name, description, price, image_path) VALUES
    (3, 'Pizza familiar',   'Pizza familiar de pepperoni',   39.90, 'pizza.png'),
    (3, 'Alitas BBQ',       'Porción x10 alitas BBQ',        29.90, 'alitas.png'),
    (3, 'Burger clásica',   'Hamburguesa doble con papas',   22.90, 'burger.png');
GO

DECLARE @orderId INT;

-- Pedido ya entregado (Tecnología)
INSERT INTO dbo.Orders (user_id, order_date, total, status)
VALUES (1, DATEADD(DAY, -3, GETDATE()), 599.90, 'Entregado');
SET @orderId = SCOPE_IDENTITY();
INSERT INTO dbo.OrderDetails (order_id, product_id, quantity, subtotal)
VALUES (@orderId, 2, 1, 599.90);

-- Pedido en camino (Restaurante)
INSERT INTO dbo.Orders (user_id, order_date, total, status)
VALUES (1, DATEADD(HOUR, -1, GETDATE()), 52.80, 'En camino');
SET @orderId = SCOPE_IDENTITY();
INSERT INTO dbo.OrderDetails (order_id, product_id, quantity, subtotal)
VALUES (@orderId, 8, 1, 22.90);
INSERT INTO dbo.OrderDetails (order_id, product_id, quantity, subtotal)
VALUES (@orderId, 7, 1, 29.90);

-- Pedido preparándose (Farmacia)
INSERT INTO dbo.Orders (user_id, order_date, total, status)
VALUES (1, GETDATE(), 51.70, 'Preparando');
SET @orderId = SCOPE_IDENTITY();
INSERT INTO dbo.OrderDetails (order_id, product_id, quantity, subtotal)
VALUES (@orderId, 4, 1, 25.90);
INSERT INTO dbo.OrderDetails (order_id, product_id, quantity, subtotal)
VALUES (@orderId, 6, 2, 25.80);
GO
