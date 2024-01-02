
DROP DATABASE if exists WMS_db;
create database WMS_db;
#CREATE USER WMSadmin identified BY "123456"; 
#GRANT ALL privileges on WMS_db TO WMSadmin;
#flush privileges;
use WMS_db;


#建立超级管理员表
DROP table if exists supermanager;
CREATE TABLE superManager (
    SuperManagerID INT PRIMARY KEY,
    CreateTime DATE NOT NULL,
    UpdateTime DATE NOT NULL,
    Gender VARCHAR(10),
    superManagerName VARCHAR(50),
    superManagerContactInfo VARCHAR(20),
    PWD longtext
);

#建立仓库管理员表
DROP table if exists warehousemanager;
CREATE TABLE WarehouseManager (
    ManagerID INT PRIMARY KEY,
    CreateTime DATE NOT NULL,
    UpdateTime DATE NOT NULL,
    Gender VARCHAR(10),
    ManagerName VARCHAR(50),
    ManagerContactInfo VARCHAR(20),
    PWD longtext
);

#建立仓库表
DROP table if exists Warehouse;
CREATE TABLE Warehouse (
    ManagerID INT,
    WarehouseID INT PRIMARY KEY,
    WarehouseName VARCHAR(50),
    Capacity DECIMAL(10,2),
    OccupiedCapacity DECIMAL(10,2),
    CreateTime DATE NOT NULL,
    UpdateTime DATE NOT NULL,
    UsingStatus ENUM('启用', '停用'),
    FOREIGN KEY (ManagerID) REFERENCES WarehouseManager(ManagerID)
);

#建立分区表
DROP table if exists Part;
CREATE TABLE Part (
    WarehouseID INT,
    PartitionID INT PRIMARY KEY,
    PartitionName VARCHAR(50),#分区名
    Functionality VARCHAR(50),#分区职能描述
    Capacity DECIMAL(10,2),#容量
    OccupiedCapacity DECIMAL(10,2),#已占容量
    CreateTime DATE NOT NULL,
    UpdateTime DATE NOT NULL,
    FOREIGN KEY (WarehouseID) REFERENCES Warehouse(WarehouseID)
);

#创建 orders 表
DROP table if exists Orders;
CREATE TABLE Orders (
    OrderNumber INT AUTO_INCREMENT PRIMARY KEY,
    RecipientName VARCHAR(50),
    RecipientContactInfo VARCHAR(20),
    SupplierName VARCHAR(255) NOT NULL,
    SupplierContact VARCHAR(255) NOT NULL,
    Ordertype longtext,
    OrderDate DATE 
);
-- # 创建 OrderDetails 表
-- DROP table if exists Order_details;
-- CREATE TABLE Order_details (
--     DetailNumber INT AUTO_INCREMENT PRIMARY KEY,
--     OrderNumber INT,
--     GoodsName VARCHAR(50),
--     Quantity INT,
--     UnitPrice DECIMAL(10,2),
--     FOREIGN KEY (OrderNumber) REFERENCES Orders(OrderNumber)
-- );


#建立货物表
DROP table if exists Goods;
CREATE TABLE Goods (
    WarehouseID INT,
    PartitionID INT,
    GoodsID INT PRIMARY KEY,
    GoodsName VARCHAR(50),
    Quantity INT,
    UnitPrice DECIMAL(10,2),
    CreateTime DATE NOT NULL,
    UpdateTime DATE NOT NULL,
    FOREIGN KEY (WarehouseID) REFERENCES Warehouse(WarehouseID),
    FOREIGN KEY (PartitionID) REFERENCES Part(PartitionID)
);
#入库记录表
DROP table if exists InventoryRecord;
CREATE TABLE InventoryRecord (
    InventoryID INT PRIMARY KEY AUTO_INCREMENT,
    OrderNumber INT,
--     GoodsID INT,
    Quantity INT,
    InventoryTime DATE,
    ManagerName VARCHAR(50),
    ManagerContactInfo VARCHAR(20),
    RecipientName VARCHAR(50),
    RecipientContactInfo VARCHAR(20),
    SupplierName VARCHAR(50),
    SupplierContactInfo VARCHAR(20),
    FromWarehouseID VARCHAR(50),
    ToWarehouseID INT,
    Reason VARCHAR(100),
--     FOREIGN KEY (GoodsID) REFERENCES Goods(GoodsID),
    FOREIGN KEY (ToWarehouseID) REFERENCES Warehouse(WarehouseID),
	FOREIGN KEY (OrderNumber) REFERENCES Orders(OrderNumber)
);

#出库记录表
DROP table if exists DeliveryRecord;
CREATE TABLE DeliveryRecord (
    DeliveryID INT PRIMARY KEY AUTO_INCREMENT,
	OrderNumber INT,
    GoodsID INT,
    Quantity INT,
    DeliveryTime DATE,
    ManagerName VARCHAR(50),
    ManagerContactInfo VARCHAR(20),
    RecipientName VARCHAR(50),
    RecipientContactInfo VARCHAR(20),
    SupplierName VARCHAR(50),
    SupplierContactInfo VARCHAR(20),
    FromWarehouseID INT,
    ToWarehouseID VARCHAR(50),
    Reason VARCHAR(100),
    FOREIGN KEY (GoodsID) REFERENCES Goods(GoodsID),
    FOREIGN KEY (FromWarehouseID) REFERENCES Warehouse(WarehouseID),
	FOREIGN KEY (OrderNumber) REFERENCES Orders(OrderNumber)
);

#调拨记录表
-- 调拨执行过程：先生成出入库订单，再生成调拨记录，查询时根据两个订单分别查询出入库情况
DROP table if exists TransferRecord;
CREATE TABLE TransferRecord (
    TransferID INT PRIMARY KEY AUTO_INCREMENT,
	FromOrderNumber INT, -- 出库记录ID
    ToOrderNumber INT, -- 入库记录ID
    SuperManagerID INT, -- 超级管理员ID
	CreateTime DATE NOT NULL,
    UpdateTime DATE NOT NULL,
    FOREIGN KEY (FromOrderNumber) REFERENCES Orders(OrderNumber),
    FOREIGN KEY (ToOrderNumber) REFERENCES Orders(OrderNumber),
    FOREIGN KEY (SuperManagerID) REFERENCES superManager(SuperManagerID)
);
--     GoodsID INT,
--     Quantity INT,
--     TransferTime DATE,
--     ManagerName VARCHAR(50),
--     ManagerContactInfo VARCHAR(20),
--     FromWarehouseID INT,
--     ToWarehouseID INT,
--     Reason VARCHAR(100),
--     FOREIGN KEY (GoodsID) REFERENCES Goods(GoodsID),
--     FOREIGN KEY (FromWarehouseID) REFERENCES Warehouse(WarehouseID),
--     FOREIGN KEY (ToWarehouseID) REFERENCES Warehouse(WarehouseID),
#负责人仓库表
DROP table if exists ManagerWarehouse;
CREATE TABLE ManagerWarehouse (
    ManagerID INT,
    WarehouseID INT,
    PRIMARY KEY (ManagerID, WarehouseID),
    FOREIGN KEY (ManagerID) REFERENCES WarehouseManager(ManagerID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouse(WarehouseID)
);

-- #仓库货物表
-- DROP table if exists WarehouseGoods;
-- CREATE TABLE WarehouseGoods (
--     WarehouseID INT,
--     GoodsID INT,
--     PRIMARY KEY (WarehouseID, GoodsID),
--     FOREIGN KEY (WarehouseID) REFERENCES Warehouse(WarehouseID),
--     FOREIGN KEY (GoodsID) REFERENCES Goods(GoodsID)
-- );



#插入管理员
INSERT INTO WarehouseManager (ManagerID, CreateTime, UpdateTime, Gender, ManagerName, ManagerContactInfo) 
VALUES 
(1, '2023-01-01', '2023-01-01', 'Male', 'John Doe', '1234567890'),
(2, '2023-02-01', '2023-02-01', 'Female', 'Jane Doe', '9876543210'),
(3, '2023-03-01', '2023-03-01', 'Male', 'Jim Smith', '1112223333');
#插入仓库
INSERT INTO Warehouse (ManagerID, WarehouseID, WarehouseName, Capacity, OccupiedCapacity, CreateTime, UpdateTime, UsingStatus)
VALUES 
(1, 1, 'Warehouse A', 1000.00, 0.00, '2023-01-01', '2023-01-01', '启用'),
(2, 2, 'Warehouse B', 2000.00, 0.00, '2023-02-01', '2023-02-01', '停用'),
(3, 3, 'Warehouse C', 1500.00, 0.00, '2023-03-01', '2023-03-01', '启用');
#插入分区
INSERT INTO Part (WarehouseID, PartitionID, PartitionName, Functionality, Capacity, OccupiedCapacity, CreateTime, UpdateTime)
VALUES 
(1, 1, 'Partition A1', 'Storage', 500.00, 0.00, '2023-01-01', '2023-01-01'),
(2, 2, 'Partition B1', 'Receiving', 1000.00, 0.00, '2023-02-01', '2023-02-01'),
(3, 3, 'Partition C1', 'Picking', 750.00, 0.00, '2023-03-01', '2023-03-01');
#插入货物
INSERT INTO Goods (WarehouseID, PartitionID, GoodsID, GoodsName, Quantity, InType, UnitPrice, CreateTime, UpdateTime)
VALUES 
(1, 1, 1, 'Product A', 100, 'Purchase', 10.00, '2023-01-01', '2023-01-01'),
(2, 2, 2, 'Product B', 200, 'Transfer', 20.00, '2023-02-01', '2023-02-01'),
(3, 3, 3, 'Product C', 150, 'Manufacture', 15.00, '2023-03-01', '2023-03-01');