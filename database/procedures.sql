USE SpeedyProjectDB;
GO

IF OBJECT_ID('dbo.sp_register', 'P') IS NOT NULL DROP PROCEDURE dbo.sp_register;
GO
IF OBJECT_ID('dbo.sp_login', 'P') IS NOT NULL DROP PROCEDURE dbo.sp_login;
GO
IF OBJECT_ID('dbo.sp_findByEmail', 'P') IS NOT NULL DROP PROCEDURE dbo.sp_findByEmail;
GO
IF OBJECT_ID('dbo.sp_get_products_by_category', 'P') IS NOT NULL DROP PROCEDURE dbo.sp_get_products_by_category;
GO
IF OBJECT_ID('dbo.sp_create_order', 'P') IS NOT NULL DROP PROCEDURE dbo.sp_create_order;
GO
IF OBJECT_ID('dbo.sp_create_order_detail', 'P') IS NOT NULL DROP PROCEDURE dbo.sp_create_order_detail;
GO
IF OBJECT_ID('dbo.sp_get_orders_by_user', 'P') IS NOT NULL DROP PROCEDURE dbo.sp_get_orders_by_user;
GO
IF OBJECT_ID('dbo.sp_get_order_details', 'P') IS NOT NULL DROP PROCEDURE dbo.sp_get_order_details;
GO
IF OBJECT_ID('dbo.sp_update_order_status', 'P') IS NOT NULL DROP PROCEDURE dbo.sp_update_order_status;
GO
CREATE PROCEDURE dbo.sp_register
    @name   NVARCHAR(100),
    @email  NVARCHAR(150),
    @passwd NVARCHAR(200)
AS
BEGIN
    SET NOCOUNT ON;
    INSERT INTO dbo.Users (name, email, passwd)
    VALUES (@name, @email, HASHBYTES('SHA2_256', @passwd));
END
GO

CREATE PROCEDURE dbo.sp_login
    @email  NVARCHAR(150),
    @passwd NVARCHAR(200)
AS
BEGIN
    SET NOCOUNT ON;
    SELECT id, name, email
    FROM dbo.Users
    WHERE email = @email
      AND passwd = HASHBYTES('SHA2_256', @passwd);
END
GO

CREATE PROCEDURE dbo.sp_findByEmail
    @email NVARCHAR(150)
AS
BEGIN
    SET NOCOUNT ON;
    SELECT id, name, email
    FROM dbo.Users
    WHERE email = @email;
END
GO
CREATE PROCEDURE dbo.sp_get_products_by_category
    @category NVARCHAR(30)
AS
BEGIN
    SET NOCOUNT ON;
    SELECT p.id, p.commerce_id, p.name, p.description, p.price, p.image_path
    FROM dbo.Products p
    INNER JOIN dbo.Commerces c ON c.id = p.commerce_id
    WHERE c.category = @category
    ORDER BY p.name;
END
GO

CREATE PROCEDURE dbo.sp_create_order
    @user_id  INT,
    @total    DECIMAL(10,2),
    @order_id INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    INSERT INTO dbo.Orders (user_id, total, status)
    VALUES (@user_id, @total, 'Preparando');

    SET @order_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE dbo.sp_create_order_detail
    @order_id   INT,
    @product_id INT,
    @quantity   INT,
    @subtotal   DECIMAL(10,2)
AS
BEGIN
    SET NOCOUNT ON;
    INSERT INTO dbo.OrderDetails (order_id, product_id, quantity, subtotal)
    VALUES (@order_id, @product_id, @quantity, @subtotal);
END
GO
CREATE PROCEDURE dbo.sp_get_orders_by_user
    @user_id INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT o.id,
           o.order_date,
           o.total,
           o.status,
           CASE WHEN COUNT(DISTINCT c.category) > 1 THEN 'Variado' ELSE MAX(c.category) END AS category
    FROM dbo.Orders o
    INNER JOIN dbo.OrderDetails od ON od.order_id = o.id
    INNER JOIN dbo.Products p ON p.id = od.product_id
    INNER JOIN dbo.Commerces c ON c.id = p.commerce_id
    WHERE o.user_id = @user_id
    GROUP BY o.id, o.order_date, o.total, o.status
    ORDER BY o.order_date DESC;
END
GO
CREATE PROCEDURE dbo.sp_get_order_details
    @order_id INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT od.id, p.id AS product_id, p.name AS product_name, p.price, p.image_path,
           od.quantity, od.subtotal
    FROM dbo.OrderDetails od
    INNER JOIN dbo.Products p ON p.id = od.product_id
    WHERE od.order_id = @order_id;
END
GO
CREATE PROCEDURE dbo.sp_update_order_status
    @order_id INT,
    @status   NVARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE dbo.Orders
    SET status = @status
    WHERE id = @order_id;
END
GO
