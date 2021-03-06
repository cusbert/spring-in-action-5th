create table if not exists Ingredient (
  id VARCHAR(4) NOT NULL PRIMARY KEY,
  name varchar(25) not null,
  type varchar(10) not null
);

create table if not exists Taco (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(50) not NULL,
  createdAt timestamp not null
);

create table if not exists Taco_Ingredients (
  taco bigint NOT NULL,
  ingredient VARCHAR(4) not NULL,
  constraint primary key(taco, ingredient)
);

alter table Taco_Ingredients
    add foreign key (taco) references Taco(id);

alter table Taco_Ingredients
    add foreign key (ingredient) references Ingredient(id);

create table if not exists Taco_Order (
  	 id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    deliveryName varchar(50) not null,
    deliveryStreet varchar(50) not null,
    deliveryCity varchar(50) not null,
    deliveryState varchar(2) not null,
    deliveryZip varchar(10) not null,
    ccNumber varchar(16) not null,
    ccExpiration varchar(5) not null,
    ccCVV varchar(3) not null,
    placedAt timestamp not null
);

create table if not exists Taco_Order_Tacos (
  tacoOrder bigint not NULL PRIMARY KEY,
  taco bigint not null
);

alter table Taco_Order_Tacos
    add foreign key (tacoOrder) references Taco_Order(id);

alter table Taco_Order_Tacos
    add foreign key (taco) references Taco(id);
