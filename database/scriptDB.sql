IF DB_ID('SpeedyProjectDB') IS NULL
BEGIN
    CREATE DATABASE SpeedyProjectDB;
END
GO

USE SpeedyProjectDB;
GO

IF OBJECT_ID('dbo.OrderDetails', 'U') IS NOT NULL DROP TABLE dbo.OrderDetails;
IF OBJECT_ID('dbo.Orders', 'U') IS NOT NULL DROP TABLE dbo.Orders;
IF OBJECT_ID('dbo.Products', 'U') IS NOT NULL DROP TABLE dbo.Products;
IF OBJECT_ID('dbo.Commerces', 'U') IS NOT NULL DROP TABLE dbo.Commerces;
IF OBJECT_ID('dbo.Users', 'U') IS NOT NULL DROP TABLE dbo.Users;
GO

---- Users ----
CREATE TABLE dbo.Users (
    id            INT IDENTITY(1,1) NOT NULL,
    name          NVARCHAR(100)     NOT NULL,
    email         NVARCHAR(150)     NOT NULL,
    passwd        VARBINARY(64)     NOT NULL,
    created_at    DATETIME          NOT NULL CONSTRAINT DF_Users_created_at DEFAULT (GETDATE()),
    CONSTRAINT PK_Users PRIMARY KEY (id),
    CONSTRAINT UQ_Users_email UNIQUE (email),
    CONSTRAINT CK_Users_name CHECK (LEN(LTRIM(name)) > 0)
);
GO
---- Commerces (Tienda de tecnología, Farmacia, Restaurante)----
CREATE TABLE dbo.Commerces (
    id            INT IDENTITY(1,1) NOT NULL,
    name          NVARCHAR(100)     NOT NULL,
    category      NVARCHAR(30)      NOT NULL,
    image_path    NVARCHAR(200)     NULL,
    CONSTRAINT PK_Commerces PRIMARY KEY (id),
    CONSTRAINT CK_Commerces_category CHECK (category IN ('Tecnologia', 'Farmacia', 'Restaurante'))
);
GO

/* ---------------------------------------------------------
   Products
   --------------------------------------------------------- */
CREATE TABLE dbo.Products (
    id            INT IDENTITY(1,1) NOT NULL,
    commerce_id   INT               NOT NULL,
    name          NVARCHAR(100)     NOT NULL,
    description   NVARCHAR(300)     NULL,
    price         DECIMAL(10,2)     NOT NULL,
    image_path    NVARCHAR(200)     NULL,
    CONSTRAINT PK_Products PRIMARY KEY (id),
    CONSTRAINT FK_Products_Commerces FOREIGN KEY (commerce_id) REFERENCES dbo.Commerces(id),
    CONSTRAINT CK_Products_price CHECK (price > 0)
);
GO

CREATE INDEX IX_Products_commerce_id ON dbo.Products(commerce_id);
GO
----- Orders ----
CREATE TABLE dbo.Orders (
    id            INT IDENTITY(1,1) NOT NULL,
    user_id       INT               NOT NULL,
    order_date    DATETIME          NOT NULL CONSTRAINT DF_Orders_date DEFAULT (GETDATE()),
    total         DECIMAL(10,2)     NOT NULL,
    status        NVARCHAR(20)      NOT NULL CONSTRAINT DF_Orders_status DEFAULT ('Preparando'),
    CONSTRAINT PK_Orders PRIMARY KEY (id),
    CONSTRAINT FK_Orders_Users FOREIGN KEY (user_id) REFERENCES dbo.Users(id),
    CONSTRAINT CK_Orders_total CHECK (total >= 0),
    CONSTRAINT CK_Orders_status CHECK (status IN ('Preparando', 'En camino', 'Entregado'))
);
GO

CREATE INDEX IX_Orders_user_id ON dbo.Orders(user_id);
CREATE INDEX IX_Orders_status ON dbo.Orders(status);
GO

---- OrderDetails ----
CREATE TABLE dbo.OrderDetails (
    id            INT IDENTITY(1,1) NOT NULL,
    order_id      INT               NOT NULL,
    product_id    INT               NOT NULL,
    quantity      INT               NOT NULL,
    subtotal      DECIMAL(10,2)     NOT NULL,
    CONSTRAINT PK_OrderDetails PRIMARY KEY (id),
    CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (order_id) REFERENCES dbo.Orders(id) ON DELETE CASCADE,
    CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (product_id) REFERENCES dbo.Products(id),
    CONSTRAINT CK_OrderDetails_quantity CHECK (quantity > 0),
    CONSTRAINT CK_OrderDetails_subtotal CHECK (subtotal >= 0)
);
GO

CREATE INDEX IX_OrderDetails_order_id ON dbo.OrderDetails(order_id);
CREATE INDEX IX_OrderDetails_product_id ON dbo.OrderDetails(product_id);
GO
